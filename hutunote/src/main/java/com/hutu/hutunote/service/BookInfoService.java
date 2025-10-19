package com.hutu.hutunote.service;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hutu.hutunote.mapper.BookInfoMapper;
import com.hutu.hutunote.model.entity.BookInfo;
import com.hutu.hutunote.model.entity.BookSaleInfo;
import com.hutu.hutunote.model.entity.ConfigInfo;
import com.hutu.hutunote.model.entity.OfflineInfo;
import com.hutu.hutunote.model.enums.ConfigEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Service
public class BookInfoService extends ServiceImpl<BookInfoMapper, BookInfo> {

    @Autowired
    private BookSaleInfoService bookSaleInfoService;
    @Autowired
    private ConfigInfoService configInfoService;
    @Autowired
    private OfflineInfoService offlineInfoService;

    public BookInfo getByIsbn(String isbn) {
        LambdaQueryWrapper<BookInfo> query = new LambdaQueryWrapper<BookInfo>().eq(BookInfo::getIsbn, isbn).last(" limit 1");
        return this.getOne(query);
    }

    public void refreshBook(String isbn) {
        List<BookSaleInfo> saleList = bookSaleInfoService.list(
                new LambdaQueryWrapper<BookSaleInfo>().eq(BookSaleInfo::getIsbn, isbn).orderByAsc(BookSaleInfo::getPrice));
        ConfigInfo onSaleCountConfig = configInfoService.getByCode(ConfigEnum.ON_SALE_COUNT_MIN);
        Integer minOnSaleCount = Integer.valueOf(onSaleCountConfig.getValue());
        if (CollUtil.isEmpty(saleList) || saleList.size() < minOnSaleCount) {
            this.offlineByIsbn(isbn);
            return;
        }
        BigDecimal price = saleList.get(minOnSaleCount - 1).getPrice();
        BookInfo bookInfo = this.getByIsbn(isbn);
        LocalDateTime now = LocalDateTime.now();
        if (bookInfo == null) {
            bookInfo = new BookInfo();
            bookInfo.setInventory(1L);
            bookInfo.setPrice(price);
            bookInfo.setIsbn(isbn);
            bookInfo.setUpdateTime(now);
            bookInfo.setRefreshTime(now);
            this.save(bookInfo);
        } else {
            if (!bookInfo.getPrice().equals(price) || bookInfo.getInventory() == 0L) {
                bookInfo.setInventory(1L);
                bookInfo.setPrice(price);
                bookInfo.setUpdateTime(now);
                bookInfo.setRefreshTime(now);
                this.updateById(bookInfo);
            } else {
                bookInfo.setRefreshTime(now);
                this.updateById(bookInfo);
            }
        }
    }

    private void offlineByIsbn(String isbn) {
        BookInfo bookInfo = this.getByIsbn(isbn);
        if (bookInfo == null) {
            return;
        }
        bookInfo.setInventory(0L);
        LocalDateTime now = LocalDateTime.now();
        bookInfo.setRefreshTime(now);
        bookInfo.setUpdateTime(now);
        this.updateById(bookInfo);
        // 库存为0，加入下架表
        OfflineInfo offlineInfo = new OfflineInfo();
        offlineInfo.setIsbn(bookInfo.getIsbn());
        offlineInfoService.remove(new LambdaQueryWrapper<OfflineInfo>().eq(OfflineInfo::getIsbn, isbn));
        offlineInfoService.save(offlineInfo);
    }
}
