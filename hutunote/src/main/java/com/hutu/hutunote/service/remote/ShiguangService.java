package com.hutu.hutunote.service.remote;

import okhttp3.*;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class ShiguangService {

    private static String COOKIE = "PHPSESSID=bc6043ca20916601aac5b5f2ab451148";


    public void uploadOnline(String filePath) {
        try {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.parse("multipart/form-data; boundary=----WebKitFormBoundaryePSxwZ8KMrSelXnV");
            RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart("file0","/C:/Users/11610/Desktop/发布商品模版_单次.xlsx",
                            RequestBody.create(MediaType.parse("application/octet-stream"),
                                    new File(filePath)))
                    .addFormDataPart("type","1")
                    .addFormDataPart("shop_id","43777")
                    .addFormDataPart("start_switch","false")
                    .addFormDataPart("date","")
                    .addFormDataPart("date_num","")
                    .addFormDataPart("date_day","")
                    .addFormDataPart("shop_url","")
                    .addFormDataPart("onsale_type_press","")
                    .addFormDataPart("onsale_type_text","")
                    .addFormDataPart("onsale_type_num","")
                    .addFormDataPart("update_type","2")
                    .addFormDataPart("is_onsale","1")
                    .addFormDataPart("goods_type","1")
                    .addFormDataPart("goods_cat_id","")
                    .addFormDataPart("repeat_type","1")
                    .addFormDataPart("copy_image_origin","1")
                    .addFormDataPart("not_isbn_is_send","1")
                    .addFormDataPart("copy_is_shipping","2")
                    .addFormDataPart("quality_arr","一品,二品,三品,四品,五品,六品,六五品,七品,七五品")
                    .addFormDataPart("repeat_mode","outer_id")
                    .addFormDataPart("update_zero","1")
                    .addFormDataPart("is_log","undefined")
                    .addFormDataPart("is_sale","1")
                    .addFormDataPart("onsale_type","1")
                    .addFormDataPart("quality","85h")
                    .addFormDataPart("shop_group","")
                    .addFormDataPart("copy_price_min","")
                    .addFormDataPart("copy_price_max","")
                    .addFormDataPart("update_price_abs","")
                    .addFormDataPart("quantity","")
                    .addFormDataPart("quantity_max","")
                    .addFormDataPart("fixed_quantity","")
                    .addFormDataPart("kong_job_mode","3")
                    .addFormDataPart("put_goods_date","")
                    .addFormDataPart("copy_skip","")
                    .addFormDataPart("copy_total","")
                    .addFormDataPart("copy_shipping_price","")
                    .addFormDataPart("update_field","is_pre_sale")
                    .build();
            Request request = new Request.Builder()
                    .url("https://erp.yanyi.ink/index/upload/uploadBooks")
                    .method("POST", body)
                    .addHeader("accept", "application/json, text/javascript, */*; q=0.01")
                    .addHeader("accept-language", "zh-CN,zh;q=0.9")
                    .addHeader("origin", "https://erp.yanyi.ink")
                    .addHeader("priority", "u=1, i")
                    .addHeader("referer", "https://erp.yanyi.ink/index/stock/stock")
                    .addHeader("sec-ch-ua", "\"Chromium\";v=\"140\", \"Not=A?Brand\";v=\"24\", \"Google Chrome\";v=\"140\"")
                    .addHeader("sec-ch-ua-mobile", "?0")
                    .addHeader("sec-ch-ua-platform", "\"Windows\"")
                    .addHeader("sec-fetch-dest", "empty")
                    .addHeader("sec-fetch-mode", "cors")
                    .addHeader("sec-fetch-site", "same-origin")
                    .addHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/140.0.0.0 Safari/537.36")
                    .addHeader("x-requested-with", "XMLHttpRequest")
                    .addHeader("Cookie", COOKIE)
                    .addHeader("Content-Type", "multipart/form-data; boundary=----WebKitFormBoundaryePSxwZ8KMrSelXnV")
                    .build();
            Response response = client.newCall(request).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void uploadOffline(String filePath) {
        try {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.parse("text/plain");
            RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart("file0","/C:/Users/11610/Desktop/上下架、删除模板.xlsx",
                            RequestBody.create(MediaType.parse("application/octet-stream"),
                                    new File(filePath)))
                    .addFormDataPart("type","6")
                    .addFormDataPart("shop_id","43777")
                    .addFormDataPart("start_switch","false")
                    .addFormDataPart("date","")
                    .addFormDataPart("date_num","")
                    .addFormDataPart("date_day","")
                    .addFormDataPart("shop_url","")
                    .addFormDataPart("onsale_type_press","")
                    .addFormDataPart("onsale_type_text","")
                    .addFormDataPart("onsale_type_num","")
                    .addFormDataPart("update_type","1")
                    .addFormDataPart("is_onsale","1")
                    .addFormDataPart("goods_type","1")
                    .addFormDataPart("goods_cat_id","")
                    .addFormDataPart("repeat_type","1")
                    .addFormDataPart("copy_image_origin","1")
                    .addFormDataPart("not_isbn_is_send","1")
                    .addFormDataPart("copy_is_shipping","2")
                    .addFormDataPart("quality_arr","一品,二品,三品,四品,五品,六品,六五品,七品,七五品")
                    .addFormDataPart("repeat_mode","outer_id")
                    .addFormDataPart("update_zero","1")
                    .addFormDataPart("is_log","undefined")
                    .addFormDataPart("is_sale","1")
                    .addFormDataPart("onsale_type","1")
                    .addFormDataPart("quality","85h")
                    .addFormDataPart("shop_group","")
                    .addFormDataPart("copy_price_min","")
                    .addFormDataPart("copy_price_max","")
                    .addFormDataPart("update_price_abs","")
                    .addFormDataPart("quantity","")
                    .addFormDataPart("quantity_max","")
                    .addFormDataPart("fixed_quantity","")
                    .addFormDataPart("kong_job_mode","3")
                    .addFormDataPart("put_goods_date","")
                    .addFormDataPart("copy_skip","")
                    .addFormDataPart("copy_total","")
                    .addFormDataPart("copy_shipping_price","")
                    .addFormDataPart("update_field","is_pre_sale")
                    .build();
            Request request = new Request.Builder()
                    .url("https://erp.yanyi.ink/index/upload/uploadBooks")
                    .method("POST", body)
                    .addHeader("accept", "application/json, text/javascript, */*; q=0.01")
                    .addHeader("accept-language", "zh-CN,zh;q=0.9")
                    .addHeader("origin", "https://erp.yanyi.ink")
                    .addHeader("priority", "u=1, i")
                    .addHeader("referer", "https://erp.yanyi.ink/index/stock/stock?type=&status=&cate_input=&nick_input=%E7%BC%A5%E7%BC%83%E8%A7%82%E6%AD%A2%E4%B9%A6%E5%BA%97&date=&ids=")
                    .addHeader("sec-ch-ua", "\"Chromium\";v=\"140\", \"Not=A?Brand\";v=\"24\", \"Google Chrome\";v=\"140\"")
                    .addHeader("sec-ch-ua-mobile", "?0")
                    .addHeader("sec-ch-ua-platform", "\"Windows\"")
                    .addHeader("sec-fetch-dest", "empty")
                    .addHeader("sec-fetch-mode", "cors")
                    .addHeader("sec-fetch-site", "same-origin")
                    .addHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/140.0.0.0 Safari/537.36")
                    .addHeader("x-requested-with", "XMLHttpRequest")
                    .addHeader("Cookie", COOKIE)
                    .build();
            Response response = client.newCall(request).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
