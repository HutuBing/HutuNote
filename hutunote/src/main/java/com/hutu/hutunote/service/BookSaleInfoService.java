package com.hutu.hutunote.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hutu.hutunote.mapper.BookInfoMapper;
import com.hutu.hutunote.mapper.BookSaleInfoMapper;
import com.hutu.hutunote.model.entity.BookInfo;
import com.hutu.hutunote.model.entity.BookSaleInfo;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class BookSaleInfoService extends ServiceImpl<BookSaleInfoMapper, BookSaleInfo> {

    public void saveOrUpdateByStoreCodeAndIsbn(String storeCode, String isbn, BigDecimal price, String itemId) {
        BookSaleInfo bookSaleInfo = this.getOne(new LambdaQueryWrapper<BookSaleInfo>()
                .eq(BookSaleInfo::getIsbn, isbn).eq(BookSaleInfo::getStoreCode, storeCode).last(" limit 1"));
        LocalDateTime now = LocalDateTime.now();
        if (bookSaleInfo == null) {
            bookSaleInfo = new BookSaleInfo();
            bookSaleInfo.setIsbn(isbn);
            bookSaleInfo.setPrice(price);
            bookSaleInfo.setStoreCode(storeCode);
            bookSaleInfo.setInventory(1L);
            bookSaleInfo.setRefreshTime(now);
            bookSaleInfo.setUpdateTime(now);
            bookSaleInfo.setItemId(itemId);
            this.save(bookSaleInfo);
            return;
        }
        if (bookSaleInfo.getPrice().equals(price)) {
            bookSaleInfo.setInventory(1L);
            bookSaleInfo.setItemId(itemId);
            bookSaleInfo.setRefreshTime(now);
            this.updateById(bookSaleInfo);
            return;
        }

        bookSaleInfo.setPrice(price);
        bookSaleInfo.setInventory(1L);
        bookSaleInfo.setItemId(itemId);
        bookSaleInfo.setRefreshTime(now);
        this.updateById(bookSaleInfo);

    }
}
