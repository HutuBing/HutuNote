package com.hutu.hutunote.execute;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
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
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class KongfuziExecute {

    @Autowired
    private KongfuziService kongfuziService;

    public List<KongIsbnInfoVo> getSellWellDetailList() {

        List<KongCategoryDto> catList = kongfuziService.getSellWellCatList();
        for (int j = 0; j < catList.size(); j++) {
            List<KongIsbnInfoVo> result = new ArrayList<>();
            KongCategoryDto cat = catList.get(j);
            String catName = cat.getKey();
            System.out.println("index:" + j + " start catName:" + catName);
            List<KongSellWellDetailDto> list = kongfuziService.getSellWellListDetail(cat.getValue(), 1, 100);
            for (KongSellWellDetailDto item : list) {
                try {
                    TimeUnit.SECONDS.sleep(RandomUtil.randomInt(10, 20));
                    KongIsbnInfoVo vo = new KongIsbnInfoVo();
                    List<KongSearchResultItemDto> isbnBookList = kongfuziService.getByIsbn(item.getIsbn());
                    if (CollUtil.isEmpty(isbnBookList) || isbnBookList.size() < 5) {
                        continue;
                    }
                    List<KongSearchResultItemDto> top5BookList = isbnBookList.size() > 4 ? isbnBookList.subList(0, 4) : isbnBookList;
                    BigDecimal maxPrice = top5BookList.get(0).getPrice();
                    for (int i = 0; i < top5BookList.size(); i++) {
                        KongSearchResultItemDto book = top5BookList.get(i);
                        BigDecimal bootTotalPrice = book.getTotalPrice();
                        maxPrice = maxPrice.compareTo(bootTotalPrice) < 0 ? bootTotalPrice : maxPrice;
                    }
                    vo.setTop5MaxPrice(maxPrice);
                    vo.setIsbn(item.getIsbn());
                    result.add(vo);
                } catch (Exception e) {
                    System.out.println("查询失败");
                }
            }
            System.out.print(LocalDateTime.now());
            System.out.println(":catName:" + catName);
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
        // 8088 15-40
//        BigDecimal startPrice = new BigDecimal("31.19");
//        BigDecimal endPrice = new BigDecimal("40");
        // 8089 2.98-15
        BigDecimal startPrice = new BigDecimal("2.98");
        BigDecimal endPrice = new BigDecimal("15");
        // 8090 10-15
//        BigDecimal startPrice = new BigDecimal("10");
//        BigDecimal endPrice = new BigDecimal("15");
        BigDecimal priceStep = BigDecimal.ONE.divide(BigDecimal.valueOf(100L));
        Pattern isbnPattern = Pattern.compile("isbn=\"\\d+");
        try {
            for (BigDecimal price = startPrice; price.compareTo(endPrice) < 0; price = price.add(priceStep)) {
                System.out.print(LocalDateTime.now());
                System.out.println(": price:" + price + " start");
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
                        TimeUnit.SECONDS.sleep(RandomUtil.randomInt(12, 20));
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    List<KongSearchResultItemDto> isbnBookList = kongfuziService.getByIsbn(isbn);
                    if (CollUtil.isEmpty(isbnBookList) || isbnBookList.size() < 5) {
                        // 少于5本在售跳过
                        continue;
                    }
                    List<KongSearchResultItemDto> top5BookList = isbnBookList.size() > 4 ? isbnBookList.subList(0, 4) : isbnBookList;
                    BigDecimal maxPrice = price;
                    for (int i = 0; i < top5BookList.size(); i++) {
                        KongSearchResultItemDto book = top5BookList.get(i);
                        BigDecimal bookTotalPrice = book.getTotalPrice();
                        maxPrice = maxPrice.compareTo(bookTotalPrice) < 0 ? bookTotalPrice : maxPrice;
                    }
                    result.add(KongIsbnInfoVo.builder().isbn(isbn).top5MaxPrice(maxPrice).build());
                }
                System.out.print(LocalDateTime.now());
                System.out.println(" price:" + price + " end, result.size:" + result.size());
                String jsonStr = JSONUtil.toJsonStr(result);
                if (result.size() >= 1000) {
                    System.out.println(jsonStr);
                    result = new ArrayList<>();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
