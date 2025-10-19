package com.hutu.hutunote.service.remote;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.hutu.hutunote.config.KongfuziConfig;
import com.hutu.hutunote.model.dto.*;
import com.hutu.hutunote.model.params.FeeParams;
import okhttp3.*;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.util.UriEncoder;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class KongfuziService {

    private static final OkHttpClient STORE_ITEM_CLIENT = new OkHttpClient().newBuilder().build();
    private static final OkHttpClient PRICE_CLIENT = new OkHttpClient().newBuilder().build();
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
                    .url(MessageFormat.format("https://search.kongfz.com/pc-gw/search-web/client/pc/product/keyword" +
                            "/list?dataType=0&keyword={0}&page=1&deliverTime=24h&actionPath=deliverTime,sortType,quality" +
                            "&sortType=7&quality=85~&quaSelect=2&userArea=1006000000", isbn))
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

    public List<KongSearchResultItemDto> getByIsbn2(String isbn) {
        try {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            Request request = new Request.Builder()
                    .url(MessageFormat.format("https://search.kongfz.com/pc-gw/search-web/client/pc/product/keyword" +
                            "/list?dataType=0&keyword={0}&page=1&actionPath=deliverTime,sortType,quality" +
                            "&sortType=7&quality=85~&quaSelect=2&userArea=1006000000", isbn))
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
                    .addHeader("Cookie", kongfuziConfig.getCookie2())
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

    public String getByPrice(String storeCode, BigDecimal price) {
        try {
            OkHttpClient client = PRICE_CLIENT;
            String priceStr = price.setScale(2).toString();
            Request request = new Request.Builder()
                    .url(MessageFormat.format("https://shop.kongfz.com/{0}/all/0_100_0_0_1_sort_desc_{1}_{2}/",
                                    storeCode, priceStr, priceStr))
                    .method("GET", null)
                    .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
                    .addHeader("Accept-Language", "zh-CN,zh;q=0.9")
                    .addHeader("Connection", "keep-alive")
                    .addHeader("Referer", MessageFormat.format("https://shop.kongfz.com/{0}/cat_1/?price={1}h{2}&pagenum=1", storeCode, priceStr, priceStr))
                    .addHeader("Sec-Fetch-Dest", "document")
                    .addHeader("Sec-Fetch-Mode", "navigate")
                    .addHeader("Sec-Fetch-Site", "same-origin")
                    .addHeader("Sec-Fetch-User", "?1")
                    .addHeader("Upgrade-Insecure-Requests", "1")
                    .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/139.0.0.0 Safari/537.36")
                    .addHeader("sec-ch-ua", "\"Not;A=Brand\";v=\"99\", \"Google Chrome\";v=\"139\", \"Chromium\";v=\"139\"")
                    .addHeader("sec-ch-ua-mobile", "?0")
                    .addHeader("sec-ch-ua-platform", "\"Windows\"")
                    .addHeader("Cookie", "shoppingCartSessionId=7a5e55660d0caff56e2f133d0c4dfcdb; _c_WBKFRo=4kLYLUL5pXxhm54Jgo3m8J9OOYPNotch4fVxg4kd; kfz-tid=219fc44837ab320a885ce4c31d349443; TY_SESSION_ID=3f43f2b6-66a4-45ee-af3a-d60d28960f20; kfz_uuid=7e39458b-3120-42fa-86a3-91dfc8c001f0; reciever_area=1006000000; PHPSESSID=42f2133feb0dc139e5b5b2d53e816ad64e714feb; kfz_trace=7e39458b-3120-42fa-86a3-91dfc8c001f0|23793580|a140a1a93e6c1fc9|")
                    .build();
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (Exception e) {
            return Strings.EMPTY;
        }
    }

    public String getMid(String isbn) {
        try {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            Request request = new Request.Builder()
                    .url(MessageFormat.format("https://search.kongfz.com/pc-gw/search-web/client/pc/bookLib" +
                            "/keyword/list?keyword={0}&sortType=19&page=1", isbn))
                    .method("GET", null)
                    .addHeader("Accept", "application/json, text/plain, */*")
                    .addHeader("Accept-Language", "zh-CN,zh;q=0.9")
                    .addHeader("Connection", "keep-alive")
                    .addHeader("Referer", MessageFormat.format("https://search.kongfz.com/booklib/?keyword={0}&sortType=20&page=1", isbn))
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
            return JSONUtil.getByPath(json, "data.itemResponse.list[0].mid").toString();
        } catch (Exception e) {
            return null;
        }
    }

    public String getMidHtml(String mid) {
        try {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            Request request = new Request.Builder()
                    .url(MessageFormat.format("https://item.kongfz.com/book/{0}_10_4_1.html", mid))
                    .method("GET", null)
                    .addHeader("Accept", "application/json, text/plain, */*")
                    .addHeader("Accept-Language", "zh-CN,zh;q=0.9")
                    .addHeader("Connection", "keep-alive")
                    .addHeader("Referer", MessageFormat.format("https://item.kongfz.com/book/{0}_10_4_1.html", mid))
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
            return body;
        } catch (Exception e) {
            return null;
        }
    }

    public KongBookFeeResp getFeeList(List<MidBookInfoDto> list) {
        try {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            String jquery = "jQuery111209805640713606538_1758354416876";
            List<FeeParams> feeParams = list.stream()
                    .map(item -> FeeParams.builder().itemId(item.getItemId()).userId(item.getUserId()).build())
                    .collect(Collectors.toList());
            Map<String, Object> params = new HashMap<>();
            params.put("area", "1006000000");
            params.put("params", feeParams);
            Request request = new Request.Builder()
                    .url(MessageFormat.format("https://shop.kongfz.com/book/shopsearch/getShippingFee?callback={0}&params={1}",
                            jquery, UriEncoder.encode(JSONUtil.toJsonStr(params))))
                    .method("GET", null)
                    .addHeader("Accept", "application/json, text/plain, */*")
                    .addHeader("Accept-Language", "zh-CN,zh;q=0.9")
                    .addHeader("Connection", "keep-alive")
                    .addHeader("Referer", "https://item.kongfz.com/")
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
            return JSONUtil.toBean(body.substring(jquery.length() + 1, body.length() - 1), KongBookFeeResp.class);
        } catch (Exception e) {
            return null;
        }
    }

    public int getByStoreCodeAndItemId(String storeCode, String itemId) {
        try {
            OkHttpClient client = STORE_ITEM_CLIENT;
            Request request = new Request.Builder()
                    .url(MessageFormat.format("https://book.kongfz.com/{0}/{1}", storeCode, itemId))
                    .method("GET", null)
                    .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
                    .addHeader("Accept-Language", "zh-CN,zh;q=0.9")
                    .addHeader("Cache-Control", "max-age=0")
                    .addHeader("Connection", "keep-alive")
                    .addHeader("Sec-Fetch-Dest", "document")
                    .addHeader("Sec-Fetch-Mode", "navigate")
                    .addHeader("Sec-Fetch-Site", "none")
                    .addHeader("Sec-Fetch-User", "?1")
                    .addHeader("Upgrade-Insecure-Requests", "1")
                    .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/140.0.0.0 Safari/537.36")
                    .addHeader("sec-ch-ua", "\"Chromium\";v=\"140\", \"Not=A?Brand\";v=\"24\", \"Google Chrome\";v=\"140\"")
                    .addHeader("sec-ch-ua-mobile", "?0")
                    .addHeader("sec-ch-ua-platform", "\"Windows\"")
                    .build();
            Response response = client.newCall(request).execute();
            int status = response.code();
            try {
                response.close();
            } catch (Exception e) {

            }
            return status;
        } catch (Exception e) {
            return 401;
        }
    }
}
