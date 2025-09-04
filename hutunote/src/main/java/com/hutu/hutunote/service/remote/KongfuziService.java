package com.hutu.hutunote.service.remote;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.hutu.hutunote.config.KongfuziConfig;
import com.hutu.hutunote.model.dto.*;
import okhttp3.*;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;

@Service
public class KongfuziService {

    @Autowired
    private KongfuziConfig kongfuziConfig;

    public List<KongCategoryDto> getSellWellCatList() {
        try {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            Request request = new Request.Builder()
                    .url("https://item.kongfz.com/api/Pc/getSellWellCatList")
                    .method("GET", null)
                    .addHeader("Accept", "application/json, text/plain, */*")
                    .addHeader("Accept-Language", "zh-CN,zh;q=0.9")
                    .addHeader("Connection", "keep-alive")
                    .addHeader("Referer", "https://item.kongfz.com/item-views/pc-item/top/seller?type=week")
                    .addHeader("Sec-Fetch-Dest", "empty")
                    .addHeader("Sec-Fetch-Mode", "cors")
                    .addHeader("Sec-Fetch-Site", "same-origin")
                    .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/138.0.0.0 Safari/537.36")
                    .addHeader("sec-ch-ua", "\"Not)A;Brand\";v=\"8\", \"Chromium\";v=\"138\", \"Google Chrome\";v=\"138\"")
                    .addHeader("sec-ch-ua-mobile", "?0")
                    .addHeader("sec-ch-ua-platform", "\"Windows\"")
                    .build();
            Response response = client.newCall(request).execute();
            KongResp<?> resp = JSONUtil.toBean(response.body().string(), KongResp.class);
            if (resp.isStatus()) {
                return JSONUtil.toList(JSONUtil.toJsonStr(resp.getResult()), KongCategoryDto.class);
            }
            return Collections.emptyList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    public List<KongSellWellDetailDto> getSellWellListDetail(Integer catId, int page, int pageSize) {
        try {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            Request request = new Request.Builder()
                    .url(MessageFormat.format(
                            "https://item.kongfz.com/api/pc/getSellWellListDetail?page={0}&pageSize={1}&timeRank=2&catId={2}",
                            page, pageSize, catId))
                    .method("GET", null)
                    .addHeader("Accept", "application/json, text/plain, */*")
                    .addHeader("Accept-Language", "zh-CN,zh;q=0.9")
                    .addHeader("Connection", "keep-alive")
                    .addHeader("Referer", "https://item.kongfz.com/item-views/pc-item/top/seller?type=week")
                    .addHeader("Sec-Fetch-Dest", "empty")
                    .addHeader("Sec-Fetch-Mode", "cors")
                    .addHeader("Sec-Fetch-Site", "same-origin")
                    .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/138.0.0.0 Safari/537.36")
                    .addHeader("sec-ch-ua", "\"Not)A;Brand\";v=\"8\", \"Chromium\";v=\"138\", \"Google Chrome\";v=\"138\"")
                    .addHeader("sec-ch-ua-mobile", "?0")
                    .addHeader("sec-ch-ua-platform", "\"Windows\"")
                    .build();
            Response response = client.newCall(request).execute();
            KongResp<?> resp = JSONUtil.toBean(response.body().string(), KongResp.class);
            if (resp.isStatus()) {
                KongPageResp<?> pageResp = JSONUtil.toBean(JSONUtil.toJsonStr(resp.getResult()), KongPageResp.class);
                return JSONUtil.toList(JSONUtil.toJsonStr(pageResp.getData()), KongSellWellDetailDto.class);
            }
            return Collections.emptyList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    public List<KongSearchResultItemDto> getByIsbn(String isbn) {
        try {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            Request request = new Request.Builder()
                    .url(MessageFormat.format("https://search.kongfz.com/pc-gw/search-web/client/pc/product" +
                            "/keyword/list?dataType=0&keyword={0}&page=1&searchMode=1&sortType=7" +
                            "&hasStock=true&actionPath=sortType&userArea=1006000000", isbn))
                    .method("GET", null)
                    .addHeader("Accept", "application/json, text/plain, */*")
                    .addHeader("Accept-Language", "zh-CN,zh;q=0.9")
                    .addHeader("Connection", "keep-alive")
                    .addHeader("Referer", MessageFormat.format("https://search.kongfz.com/product/?dataType=0&keyword={0}&page=1&quality=85~&quaSelect=2&actionPath=quality,sortType&sortType=7", isbn))
                    .addHeader("Sec-Fetch-Dest", "empty")
                    .addHeader("Sec-Fetch-Mode", "cors")
                    .addHeader("Sec-Fetch-Site", "same-origin")
                    .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/138.0.0.0 Safari/537.36")
                    .addHeader("sec-ch-ua", "\"Not)A;Brand\";v=\"8\", \"Chromium\";v=\"138\", \"Google Chrome\";v=\"138\"")
                    .addHeader("sec-ch-ua-mobile", "?0")
                    .addHeader("sec-ch-ua-platform", "\"Windows\"")
                    .addHeader("Cookie", kongfuziConfig.getCookie())
                    .build();
            Response response = client.newCall(request).execute();
            String body = response.body().string();
            JSON json = JSONUtil.parse(body);
            return JSONUtil.toList(
                    JSONUtil.toJsonStr(JSONUtil.getByPath(json, "data.itemResponse.list")),
                    KongSearchResultItemDto.class);
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    public String getByPrice(BigDecimal price) {
        try {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            String priceStr = price.setScale(2).toString();
            Request request = new Request.Builder()
                    .url("https://shop.kongfz.com/2845/all/0_100_0_0_1_sort_desc_" + priceStr + "_" + priceStr + "/")
                    .method("GET", null)
                    .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
                    .addHeader("Accept-Language", "zh-CN,zh;q=0.9")
                    .addHeader("Connection", "keep-alive")
                    .addHeader("Referer", "https://shop.kongfz.com/2845/cat_1/?price=5.00h5.00&pagenum=1")
                    .addHeader("Sec-Fetch-Dest", "document")
                    .addHeader("Sec-Fetch-Mode", "navigate")
                    .addHeader("Sec-Fetch-Site", "same-origin")
                    .addHeader("Sec-Fetch-User", "?1")
                    .addHeader("Upgrade-Insecure-Requests", "1")
                    .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/139.0.0.0 Safari/537.36")
                    .addHeader("sec-ch-ua", "\"Not;A=Brand\";v=\"99\", \"Google Chrome\";v=\"139\", \"Chromium\";v=\"139\"")
                    .addHeader("sec-ch-ua-mobile", "?0")
                    .addHeader("sec-ch-ua-platform", "\"Windows\"")
                    .addHeader("Cookie", "shoppingCartSessionId=7a5e55660d0caff56e2f133d0c4dfcdb; reciever_area=1006000000; _c_WBKFRo=4kLYLUL5pXxhm54Jgo3m8J9OOYPNotch4fVxg4kd; kfz_uuid=2831f1ed-f482-4a9c-bd05-a273539b90c7; PHPSESSID=244f66d9f60daf52a7bdfdc3cb9dd1edf80a8e78; kfz_trace=2831f1ed-f482-4a9c-bd05-a273539b90c7|22746450|2d80b375782f9ac0|")
                    .build();
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (Exception e) {
            return Strings.EMPTY;
        }
    }

}
