package com.hutu.hutunote.execute;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hutu.hutunote.model.dto.*;
import com.hutu.hutunote.model.entity.BookInfo;
import com.hutu.hutunote.model.vo.KongIsbnInfoVo;
import com.hutu.hutunote.service.remote.KongfuziService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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

    public BookInfoDto getByIsbn(String isbn) {
        BookInfoDto bookInfo = new BookInfoDto();
        bookInfo.setIsbn(isbn);
        List<KongSearchResultItemDto> isbnBookList = kongfuziService.getByIsbn(bookInfo.getIsbn());
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
        return bookInfo;
    }

   /* public BookInfoDto getByIsbn(String isbn) {
        BookInfoDto bookInfo = new BookInfoDto();
        bookInfo.setIsbn(isbn);
        List<MidBookInfoDto> isbnBookList = this.listMidBook(isbn);
//        isbnBookList = isbnBookList.stream().filter(MidBookInfoDto::isPostIn24Hours).collect(Collectors.toList());
        if (CollUtil.isEmpty(isbnBookList) || isbnBookList.size() < 5) {
            // 少于5本，设置库存为0
            bookInfo.setInventory(0L);
        } else {
            List<MidBookInfoDto> top5BookList = isbnBookList.size() >= 4 ? isbnBookList.subList(0, 3) : isbnBookList;
            BigDecimal maxPrice = new BigDecimal("5");
            for (int j = 0; j < top5BookList.size(); j++) {
                MidBookInfoDto book = top5BookList.get(j);
                BigDecimal bookTotalPrice = book.getPrice();
                maxPrice = maxPrice.compareTo(bookTotalPrice) < 0 ? bookTotalPrice : maxPrice;
            }
            bookInfo.setInventory(1L);
            bookInfo.setPrice(maxPrice);
        }
        return bookInfo;
    }*/

    private List<MidBookInfoDto> listMidBook(String isbn) {
        if (StrUtil.isBlank(isbn)) {
            return Collections.emptyList();
        }
        String mid = kongfuziService.getMid(isbn);
        if (StrUtil.isBlank(mid)) {
            return Collections.emptyList();
        }
        String webStr = kongfuziService.getMidHtml(mid).replaceAll("\n", "").replaceAll("</li>", "</li>\n");
        List<MidBookInfoDto> result = new ArrayList<>();
        Pattern bookLiPattern = Pattern.compile("<li class=\"clearfix item-list.*</li>");
        Pattern bookPricePattern = Pattern.compile("￥<span style=\"font-weight: bold;\">\\d+\\.\\d+</span>");
        Pattern userIdPattern = Pattern.compile("userid=\"\\d+");
        Pattern itemIdPattern = Pattern.compile("itemid=\"\\d+");
        try {
            Matcher matcher = bookLiPattern.matcher(webStr);
            while (matcher.find()) {
                MidBookInfoDto midBookInfoDto = new MidBookInfoDto();
                midBookInfoDto.setIsbn(isbn);
                String bookLiStr = matcher.group();
                Matcher bookPriceMatcher = bookPricePattern.matcher(bookLiStr);
                Matcher userIdMatcher = userIdPattern.matcher(bookLiStr);
                Matcher itemIdMatcher = itemIdPattern.matcher(bookLiStr);
                if (bookPriceMatcher.find()) {
                    String group = bookPriceMatcher.group();
                    midBookInfoDto.setBookPrice(new BigDecimal(group.substring("￥<span style=\"font-weight: bold;\">".length(), group.length() - "</span>".length())));
                }
                if (userIdMatcher.find()) {
                    String group = userIdMatcher.group().trim();
                    midBookInfoDto.setUserId(group.split("\"")[1]);
                }
                if (itemIdMatcher.find()) {
                    String group = itemIdMatcher.group().trim();
                    midBookInfoDto.setItemId(group.split("\"")[1]);
                }
                if (bookLiStr.contains("24小时内发货") || bookLiStr.contains("当日发货")) {
                    midBookInfoDto.setPostIn24Hours(true);
                }

                result.add(midBookInfoDto);
            }
            this.buildFee(result);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    private void buildFee(List<MidBookInfoDto> list) {
        KongBookFeeResp feeResp = kongfuziService.getFeeList(list);
        if (feeResp.isStatus()) {
            Map<String, BookFeeDto> feeMap = feeResp.getData().stream().collect(Collectors.toMap(BookFeeDto::getItemId, Function.identity()));
            list.forEach(item -> {
                BookFeeDto bookFeeDto = feeMap.get(item.getItemId());
                if (bookFeeDto != null) {
                    item.setExpressPrice(bookFeeDto.getTotalFee());
                    item.setPrice(item.getBookPrice().add(item.getExpressPrice()));
                }
            });
        }
    }

    public Map<String, String> getNewBook(String storeCode, BigDecimal price) {
        String webStr = kongfuziService.getByPrice(storeCode, price);
        Pattern isbnPattern = Pattern.compile("itemid=\"\\d+\" isbn=\"\\d+");
        Matcher matcher = isbnPattern.matcher(webStr);
        Map<String, String> isbnMap = new HashMap<>();
        while (matcher.find()) {
            String[] match = matcher.group().trim().split("\"");
            String itemId = match[1];
            String isbn = match[match.length - 1];
            isbnMap.put(isbn, itemId);
        }
        return isbnMap;
    }

    public boolean isBookItemSale(String storeCode, String itemId) {
        try {
            TimeUnit.SECONDS.sleep(RandomUtil.randomInt(2, 3));
        } catch (Exception e) {
            e.printStackTrace();
        }
        int status = kongfuziService.getByStoreCodeAndItemId(storeCode, itemId);
        return status != 503 && status != 404;
    }
}
