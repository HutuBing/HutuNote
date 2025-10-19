package com.hutu.hutunote.execute;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.hutu.hutunote.config.KongfuziConfig;
import com.hutu.hutunote.model.dto.BookInfoDto;
import com.hutu.hutunote.model.dto.KongSearchResultItemDto;
import com.hutu.hutunote.model.entity.BookInfo;
import com.hutu.hutunote.model.entity.BookSaleInfo;
import com.hutu.hutunote.model.entity.ConfigInfo;
import com.hutu.hutunote.model.entity.OfflineInfo;
import com.hutu.hutunote.model.enums.ConfigEnum;
import com.hutu.hutunote.model.vo.KongIsbnInfoVo;
import com.hutu.hutunote.model.vo.UploadOfflineBookInfo;
import com.hutu.hutunote.model.vo.UploadOnlineBookInfo;
import com.hutu.hutunote.service.BookInfoService;
import com.hutu.hutunote.service.BookSaleInfoService;
import com.hutu.hutunote.service.ConfigInfoService;
import com.hutu.hutunote.service.OfflineInfoService;
import com.hutu.hutunote.service.remote.KongfuziService;
import com.hutu.hutunote.service.remote.ShiguangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class BookExecute {

    @Autowired
    private BookInfoService bookInfoService;
    @Autowired
    private KongfuziService kongfuziService;
    @Autowired
    private OfflineInfoService offlineInfoService;
    @Autowired
    private ConfigInfoService configInfoService;
    @Autowired
    private KongfuziExecute kongfuziExecute;
    @Autowired
    private ShiguangService shiguangService;
    @Autowired
    private KongfuziConfig kongfuziConfig;
    @Autowired
    private BookSaleInfoService bookSaleInfoService;

    public void uploadBook(MultipartFile file) {
        // 清理表
        bookInfoService.remove(null);
        // 读取文件写入
        try (InputStream inputStream = file.getInputStream()) {
            EasyExcel.read(inputStream, BookInfo.class, new ReadListener<BookInfo>() {
                private List<BookInfo> list = new ArrayList<>();

                @Override
                public void invoke(BookInfo bookInfo, AnalysisContext analysisContext) {
                    list.add(bookInfo);
                }

                @Override
                public void doAfterAllAnalysed(AnalysisContext analysisContext) {
                    bookInfoService.saveBatch(list, 1000);
                }
            }).sheet().doRead();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Async
    public void refreshOfflineBook() {
        while(true) {
            List<BookInfo> list = bookInfoService.list(new LambdaQueryWrapper<BookInfo>()
                    .eq(BookInfo::getInventory, 0)
                    .orderByAsc(BookInfo::getRefreshTime)
                    .orderByAsc(BookInfo::getUpdateTime)
                    .last(" limit 10"));
            for (int i = 0; i < list.size(); i++) {
                try {
                    TimeUnit.SECONDS.sleep(RandomUtil.randomInt(20,30));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                BookInfo oldBookInfo = list.get(i);
                BookInfo bookInfo = BeanUtil.copyProperties(oldBookInfo, BookInfo.class);
                List<KongSearchResultItemDto> isbnBookList = kongfuziService.getByIsbn2(bookInfo.getIsbn());
                if (CollUtil.isEmpty(isbnBookList) || isbnBookList.size() < 5) {
                    // 少于5本，设置库存为0
                    bookInfo.setInventory(0L);
                } else {
                    bookInfo.setInventory(1L);
                    BigDecimal price = new BigDecimal("5");
                    for (KongSearchResultItemDto kongSearchResultItemDto : isbnBookList.subList(0, 5)) {
                        if (kongSearchResultItemDto.getTotalPrice().compareTo(price) > 0) {
                            price = kongSearchResultItemDto.getTotalPrice();
                        }
                    }
                    bookInfo.setPrice(price);
                }
                bookInfo.setRefreshTime(LocalDateTime.now());
                // 库存变化或价格变化，修改updateTime
                if (!Objects.equals(bookInfo.getInventory(), oldBookInfo.getInventory()) || !Objects.equals(bookInfo.getPrice(), oldBookInfo.getPrice())) {
                    bookInfo.setUpdateTime(LocalDateTime.now());
                }
                bookInfoService.updateById(bookInfo);
            }
        }
    }

    @Async
    public void refreshOnlineBook() {
        while (true) {
            List<BookInfo> list = bookInfoService.list(new LambdaQueryWrapper<BookInfo>()
                    .eq(BookInfo::getInventory, 1)
                    .lt(BookInfo::getRefreshTime, LocalDateTime.now().minusMinutes(30))
                    .orderByAsc(BookInfo::getRefreshTime)
                    .orderByAsc(BookInfo::getUpdateTime)
                    .last(" limit 1"));
            if (CollUtil.isEmpty(list)) {
                continue;
            }
            List<String> isbnList = list.stream().map(BookInfo::getIsbn).collect(Collectors.toList());
            List<BookSaleInfo> saleList = bookSaleInfoService.list(new LambdaQueryWrapper<BookSaleInfo>()
                    .in(BookSaleInfo::getIsbn, isbnList)
                    .ne(BookSaleInfo::getItemId, ""));
            if (CollUtil.isEmpty(saleList)) {
                bookInfoService.update(new LambdaUpdateWrapper<BookInfo>()
                        .set(BookInfo::getRefreshTime, LocalDateTime.now())
                        .in(BookInfo::getIsbn, isbnList));
                continue;
            }
            for (BookSaleInfo bookSaleInfo : saleList) {
                try {
                    TimeUnit.SECONDS.sleep(RandomUtil.randomInt(25, 35));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                kongfuziConfig.getStoreList().stream().filter(item -> item.getStoreCode().equals(bookSaleInfo.getStoreCode())).forEach(storeConfig -> {
                    Map<String, String> isbnMap = kongfuziExecute.getNewBook(storeConfig.getStoreCode(), bookSaleInfo.getPrice().subtract(storeConfig.getExpressPrice()));
                    List<BookSaleInfo> hisSaleList = bookSaleInfoService.list(new LambdaQueryWrapper<BookSaleInfo>()
                            .eq(BookSaleInfo::getStoreCode, bookSaleInfo.getStoreCode())
                            .eq(BookSaleInfo::getPrice, bookSaleInfo.getPrice()));
                    // price涉及的数据都要删除更新
                    if (CollUtil.isEmpty(isbnMap)) {
                        hisSaleList.forEach(his -> {
                            bookSaleInfoService.removeById(his.getId());
                            bookInfoService.refreshBook(his.getIsbn());
                        });
                        return;
                    }
                    isbnMap.forEach((isbn, itemId) -> {
                        bookSaleInfoService.saveOrUpdateByStoreCodeAndIsbn(storeConfig.getStoreCode(), isbn,
                                bookSaleInfo.getPrice(), itemId);
                        bookInfoService.refreshBook(isbn);
                    });

                    hisSaleList.forEach(his -> {
                        if (!isbnMap.containsValue(his.getItemId())) {
                            bookSaleInfoService.removeById(his.getId());
                            bookInfoService.refreshBook(his.getIsbn());
                        }
                    });

                });

            }
        }
    }

    public void downloadBook(Long inventory, HttpServletResponse response) throws Exception{
        // 下载待下架的列表
        if (inventory == 0L) {
            List<OfflineInfo> offlineList = offlineInfoService.list();
            // 设置响应头为下载文件的方式，并指定下载的文件名（需要编码）
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode("下架商品", "UTF-8"); // 文件名编码，防止中文乱码
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");

            // 使用EasyExcel写入数据到response的输出流中
            EasyExcel.write(response.getOutputStream(), OfflineInfo.class).sheet("下架商品").doWrite(offlineList);
            offlineInfoService.remove(null);
            return;
        }

        // 下载有更新的列表
        LocalDateTime downLoadTime = LocalDateTime.now();
        ConfigInfo onlineConfig = configInfoService.getByCode(ConfigEnum.ONLINE_UPDATE_TIME);
        List<BookInfo> list = bookInfoService.list(new LambdaQueryWrapper<BookInfo>()
                .eq(BookInfo::getInventory, inventory)
                .ge(BookInfo::getUpdateTime, onlineConfig.getUpdateTime())
                .lt(BookInfo::getUpdateTime, downLoadTime)
                .orderByAsc(BookInfo::getUpdateTime)
        );
        // 设置响应头为下载文件的方式，并指定下载的文件名（需要编码）
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("上架或更新商品", "UTF-8"); // 文件名编码，防止中文乱码
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");

        // 使用EasyExcel写入数据到response的输出流中
        EasyExcel.write(response.getOutputStream(), BookInfo.class).sheet("上架或更新商品").doWrite(list);
        // 修改更新时间
        onlineConfig.setUpdateTime(downLoadTime);
        configInfoService.updateById(onlineConfig);
    }

    @Async
    public void getNewBook() {
        ConfigInfo minConfig = configInfoService.getByCode(ConfigEnum.REFRESH_PRICE_MIN);
        ConfigInfo maxConfig = configInfoService.getByCode(ConfigEnum.REFRESH_PRICE_MAX);
        BigDecimal startPrice = new BigDecimal(minConfig.getValue());
        BigDecimal endPrice = new BigDecimal(maxConfig.getValue());
        BigDecimal priceStep = BigDecimal.ONE.divide(BigDecimal.valueOf(100L));

        try {
            for (BigDecimal price = startPrice; price.compareTo(endPrice) < 0; price = price.add(priceStep)) {
                BigDecimal finalPrice = price;
                System.out.print(LocalDateTime.now());
                System.out.println(": price:" + finalPrice + " start");
                kongfuziConfig.getStoreList().forEach(storeConfig -> {
                    try {
                        TimeUnit.SECONDS.sleep(RandomUtil.randomInt(25, 35));
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    Map<String, String> isbnMap = kongfuziExecute.getNewBook(storeConfig.getStoreCode(), finalPrice);
                    if (CollUtil.isEmpty(isbnMap)) {
                        return;
                    }
                    isbnMap.forEach((isbn, itemId) -> {
                        bookSaleInfoService.saveOrUpdateByStoreCodeAndIsbn(storeConfig.getStoreCode(), isbn,
                                finalPrice.add(storeConfig.getExpressPrice()), itemId);
                        bookInfoService.refreshBook(isbn);
                    });
                });
                System.out.print(LocalDateTime.now());
                System.out.println(": price:" + finalPrice + " end");
                minConfig.setValue(finalPrice.toString());
                configInfoService.updateById(minConfig);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String generateExcel(Long inventory) throws Exception{
        String basePath = "C:\\Users\\11610\\Desktop\\isbn\\";
        // 下载待下架的列表
        if (inventory == 0L) {
            List<OfflineInfo> offlineList = offlineInfoService.list();
            String fileName = "下架商品_" + LocalDateTimeUtil.format(LocalDateTime.now(), "yyyyMMddHHmmss");
            String filePath = basePath + "offline\\" + fileName + ".xlsx";
            // 使用EasyExcel写入数据到response的输出流中
            List<UploadOfflineBookInfo> uploadList = new ArrayList<>();
            List<UploadOfflineBookInfo> collect = offlineList.stream().map(UploadOfflineBookInfo::new).collect(Collectors.toList());
            if (CollUtil.isNotEmpty(collect)) {
                uploadList.add(collect.get(0));
                uploadList.addAll(collect);
            }
            EasyExcel.write(filePath, UploadOfflineBookInfo.class).sheet("下架商品").doWrite(uploadList);
            offlineInfoService.remove(null);
            return filePath;
        }

        // 下载有更新的列表
        LocalDateTime downLoadTime = LocalDateTime.now();
        ConfigInfo onlineConfig = configInfoService.getByCode(ConfigEnum.ONLINE_UPDATE_TIME);
        List<BookInfo> list = bookInfoService.list(new LambdaQueryWrapper<BookInfo>()
                .eq(BookInfo::getInventory, inventory)
                .ge(BookInfo::getUpdateTime, onlineConfig.getUpdateTime())
                .lt(BookInfo::getUpdateTime, downLoadTime)
                .orderByAsc(BookInfo::getUpdateTime)
        );

        String fileName = "上架或更新商品_" + LocalDateTimeUtil.format(LocalDateTime.now(), "yyyyMMddHHmmss");
        String filePath = basePath + "online\\" + fileName + ".xlsx";
        // 使用EasyExcel写入数据到response的输出流中
        List<UploadOnlineBookInfo> onlineList = list.stream().map(UploadOnlineBookInfo::new).collect(Collectors.toList());
        EasyExcel.write(filePath, UploadOnlineBookInfo.class).sheet("上架或更新商品").doWrite(onlineList);
        // 修改更新时间
        onlineConfig.setUpdateTime(downLoadTime);
        configInfoService.updateById(onlineConfig);
        return filePath;
    }

    @Async
    public void autoUploadOnline() {
        while(true) {
            try {
                String filePath = this.generateExcel(1L);
                shiguangService.uploadOnline(filePath);
            }catch (Exception e) {
                System.out.println("上传文件失败");
            }
            try {
                TimeUnit.HOURS.sleep(3L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Async
    public void autoUploadOffline() {
        while(true) {
            try {
                TimeUnit.HOURS.sleep(1L);
                String filePath = this.generateExcel(0L);
                shiguangService.uploadOffline(filePath);
            }catch (Exception e) {
                System.out.println("上传文件失败");
            }
        }
    }
}
