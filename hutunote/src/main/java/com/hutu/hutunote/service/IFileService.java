package com.hutu.hutunote.service;

import org.springframework.web.multipart.MultipartFile;

public interface IFileService {

    public String upload(MultipartFile file);

    public void getFile(String fileName);

}
