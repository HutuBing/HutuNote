package com.hutu.hutunote.execute;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.hutu.hutunote.model.dto.KongCategoryDto;
import com.hutu.hutunote.model.dto.KongSearchResultItemDto;
import com.hutu.hutunote.model.dto.KongSellWellDetailDto;
import com.hutu.hutunote.model.vo.KongIsbnInfoVo;
import com.hutu.hutunote.model.vo.KongSellWellDetailVo;
import com.hutu.hutunote.service.remote.KongfuziService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class KongfuziExecute {

    @Autowired
    private KongfuziService kongfuziService;

    private static final BigDecimal DEFAULT_ADD_PRICE = BigDecimal.valueOf(3);

    public List<KongSellWellDetailVo> getSellWellDetailList() {


        Queue<KongSellWellDetailVo> queue = new LinkedBlockingQueue<KongSellWellDetailVo>();
        new Thread(() -> {
            this.downloadImg(queue);
        }).start();

        List<KongCategoryDto> catList = kongfuziService.getSellWellCatList();
        for(int j = 11; j < catList.size(); j++) {
            List<KongSellWellDetailVo> result = new ArrayList<>();
            KongCategoryDto cat = catList.get(j);
            String catName = cat.getKey();
                    List<KongSellWellDetailDto> list = kongfuziService.getSellWellListDetail(cat.getValue(), 1, 100);
                    list.forEach(item -> {
                        try {
                            TimeUnit.SECONDS.sleep(RandomUtil.randomInt(10, 20));
                            KongSellWellDetailVo vo = BeanUtil.copyProperties(item, KongSellWellDetailVo.class);
                            vo.setCatName(catName);
                            List<KongSearchResultItemDto> isbnBookList = kongfuziService.getByIsbn(item.getIsbn());
                            if (CollUtil.isEmpty(isbnBookList)) {
                                return;
                            }
                            List<KongSearchResultItemDto> top5BookList = isbnBookList.size() > 5 ? isbnBookList.subList(0, 5) : isbnBookList;
                            BigDecimal totalPrice = new BigDecimal(0);
                            for (int i = 0; i < top5BookList.size(); i++) {
                                KongSearchResultItemDto book = top5BookList.get(i);
                                BigDecimal price = book.getTotalPrice();
                                totalPrice = totalPrice.add(price);
                                vo.setMinPrice(vo.getMinPrice() == null ? price : (vo.getMinPrice().compareTo(price) < 0 ? vo.getMinPrice() : price));
                                vo.setTop5MinPrice(vo.getMinPrice() == null ? price : (vo.getMinPrice().compareTo(price) > 0 ? vo.getMinPrice() : price));
                                if (i == 0) {
                                    vo.setImgUrl1(book.getImgUrl());
                                }
                                if (i == 1) {
                                    vo.setImgUrl2(book.getImgUrl());
                                }
                                if (i == 2) {
                                    vo.setImgUrl3(book.getImgUrl());
                                }
                                if (i == 3) {
                                    vo.setImgUrl4(book.getImgUrl());
                                }
                                if (i == 4) {
                                    vo.setImgUrl5(book.getImgUrl());
                                }
                            }
                            vo.setPrice(totalPrice.divide(new BigDecimal(top5BookList.size()), 2, RoundingMode.HALF_UP).add(DEFAULT_ADD_PRICE));
                            result.add(vo);
                            queue.add(vo);
                        } catch (Exception e) {
                            System.out.println("查询失败");
                        }
                    });
                    System.out.println("catName:" + catName);
                    System.out.println(JSONUtil.toJsonStr(result));
        }

        return null;
    }

    private void downloadImg(Queue<KongSellWellDetailVo> queue) {
        while (true) {
            try {
                KongSellWellDetailVo book = queue.poll();
                if (book == null) {
                    TimeUnit.SECONDS.sleep(RandomUtil.randomInt(5, 20));
                    continue;
                }
                this.doDownloadImg(book.getIsbn(), book.getImgUrl());
                this.doDownloadImg(book.getIsbn(), book.getImgUrl1());
                this.doDownloadImg(book.getIsbn(), book.getImgUrl2());
                this.doDownloadImg(book.getIsbn(), book.getImgUrl3());
                this.doDownloadImg(book.getIsbn(), book.getImgUrl4());
                this.doDownloadImg(book.getIsbn(), book.getImgUrl5());
            } catch (Exception e) {
                System.out.println("下载图片失败");
            }
        }
    }

    private void doDownloadImg(String isbn, String imgUrl) {
        if (StrUtil.hasBlank(isbn, imgUrl)) {
            return;
        }
        String suffix = ".jpg";
        if (imgUrl.contains(".")) {
            List<String> split = StrUtil.split(imgUrl, ".");
            suffix = "." + split.get(split.size() - 1);
        }
        HttpUtil.downloadFileFromUrl(imgUrl, new File("C:\\Users\\11610\\OneDrive\\图片\\" + isbn + File.separator + System.currentTimeMillis() + suffix));
    }

    public List<KongIsbnInfoVo> getIsbnInfoList() {
        List<KongIsbnInfoVo> result = new ArrayList<>();
        Set<String> isbnSet = new HashSet<>();
        BigDecimal startPrice = new BigDecimal("15");
        BigDecimal endPrice = new BigDecimal(30);
        BigDecimal priceStep = BigDecimal.ONE.divide(BigDecimal.valueOf(100L));
        Pattern isbnPattern = Pattern.compile("isbn=\"\\d+");
        File file = new File("C:\\Users\\11610\\Desktop\\test.txt");
        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        try (FileOutputStream excelOutputStream = new FileOutputStream(new File("C:\\Users\\11610\\Desktop\\test.xlsx"));
             ExcelWriter writer = EasyExcel.write(excelOutputStream, KongIsbnInfoVo.class).build();) {
            WriteSheet sheet = EasyExcel.writerSheet("sheet1").build();
            for (BigDecimal price = startPrice; price.compareTo(endPrice) < 0; price = price.add(priceStep)) {
                try {
                    TimeUnit.SECONDS.sleep(RandomUtil.randomInt(5, 20));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                String webStr = kongfuziService.getByPrice(price);
                Matcher matcher = isbnPattern.matcher(webStr);
                while (matcher.find()) {
                    String isbn = matcher.group().trim();
                    isbn = isbn.split("\"")[1];
                    if (isbnSet.contains(isbn)) {
                        continue;
                    }
                    isbnSet.add(isbn);
                    try {
                        TimeUnit.SECONDS.sleep(RandomUtil.randomInt(10, 13));
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    List<KongSearchResultItemDto> isbnBookList = kongfuziService.getByIsbn(isbn);
                    if (CollUtil.isEmpty(isbnBookList) || isbnBookList.size() < 5) {
                        // 少于5本在售跳过
                        continue;
                    }
                    List<KongSearchResultItemDto> top5BookList = isbnBookList.size() > 5 ? isbnBookList.subList(0, 5) : isbnBookList;
                    BigDecimal maxPrice = price;
                    for (int i = 0; i < top5BookList.size(); i++) {
                        KongSearchResultItemDto book = top5BookList.get(i);
                        BigDecimal bookTotalPrice = book.getTotalPrice();
                        maxPrice = maxPrice.compareTo(bookTotalPrice) < 0 ? bookTotalPrice : maxPrice;
                    }
                    result.add(KongIsbnInfoVo.builder().isbn(isbn).top5MaxPrice(maxPrice).build());
                }
                if (result.size() >= 2000) {
                    try {
                        String jsonStr = JSONUtil.toJsonStr(result);
                        fileOutputStream.write(jsonStr.getBytes(StandardCharsets.UTF_8));
                        fileOutputStream.write("\r\n".getBytes(StandardCharsets.UTF_8));
                        System.out.println(LocalDateTime.now());
                        System.out.println(price);
                        System.out.println(jsonStr);
                        writer.write(result, sheet);
                        result = new ArrayList<>();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
