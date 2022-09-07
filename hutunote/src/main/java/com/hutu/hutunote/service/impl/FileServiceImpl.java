package com.hutu.hutunote.service.impl;

import cn.hutool.core.lang.UUID;
import com.hutu.hutunote.service.IFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Service
public class FileServiceImpl implements IFileService {

    private String filePath = "E:\\HutuNote\\files\\";

    @Autowired
    private HttpServletResponse response;

    @Override
    public String upload(MultipartFile file) {

        File path = new File(filePath);
        if(path.isDirectory() && !path.exists()) {
            path.mkdir();
        }

        //后缀
        String suf = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String id = UUID.randomUUID().toString();
        File target = new File(filePath + id + suf);
        try {
            file.transferTo(target);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
        return "hutunote/files/" + id + suf;
    }

    @Override
    public void getFile(String fileName){
        File file = new File(filePath + fileName);
        try {
            response.setContentType("image/jpeg");
            OutputStream out = response.getOutputStream();
            FileInputStream input = new FileInputStream(file);
            byte[] cache = new byte[1024];
            while (input.read(cache) > 0) {
                out.write(cache);
            }
            out.flush();
            input.close();
        } catch (IOException e){
            throw new RuntimeException(e.getMessage());
        }
    }

}
