package com.hutu.hutunote.service;

import cn.hutool.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

public interface IFileService {

    void updateConfig(JSONObject params);

    public String upload(MultipartFile file);

    public void getFile(String fileName);

    public void uploadHt(MultipartFile file);

    Object listHt();

    void uploadTemplate(MultipartFile file);

    void uploadImage(MultipartFile file);

    JSONObject getConfig();
}
