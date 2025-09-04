package com.hutu.hutunote.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.hutu.hutunote.service.IFileService;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FileServiceImpl implements IFileService {

    @Value("${upload.filePath}")
    private String filePath;

    @Autowired
    private HttpServletResponse response;
    private List<JSONObject> data;

    //时间格式
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/M/d");

    private String rootPath = "C:\\temp";
    private String targetRootPath = "C:\\temp";
    private String templateName = "购销合同";
    private String imageName = "公章.png";
    private String configName = "config.txt";
    private String password = "654321";
    private JSONObject config = new JSONObject();

    //初始化配置信息
    @PostConstruct
    public void initConfig() {
        String configPath = rootPath + File.separator + configName;
        File configFile = new File(configPath);
        if(configFile.exists()){
            String json = FileUtil.readString(configFile, CharsetUtil.charset("UTF-8"));
            config = JSONUtil.parseObj(json);
            this.rootPath = config.getStr("rootPath");
            this.targetRootPath = config.getStr("targetRootPath");
            this.password = config.getStr("password");
        }else{
            config.putOpt("targetRootPath", this.targetRootPath);
            config.putOpt("rootPath", this.rootPath);
            config.putOpt("password", this.password);
            FileUtil.writeString(JSONUtil.toJsonStr(config), configPath, "UTF-8");
        }
    }

    @Override
    public void updateConfig(JSONObject params){
        this.config = params;
        this.rootPath = params.getStr("rootPath", this.rootPath);
        this.targetRootPath = params.getStr("targetRootPath", this.targetRootPath);
        this.password = params.getStr("password", this.password);
        String configPath = rootPath + File.separator + configName;
        FileUtil.writeString(JSONUtil.toJsonStr(config), configPath, "UTF-8");
    }

    @Override
    public String upload(MultipartFile file) {

        File path = new File(filePath);
        if(!path.exists()) {
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

    @Override
    public void uploadHt(MultipartFile file) {
        List<JSONObject> data = null;
        try {
            //读取文件内容
            data = this.readFile(file.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if(CollectionUtils.isEmpty(data)) {
            return;
        }

        //数据分组
        Map<String, List<JSONObject>> dataByGroup = data.stream().collect(Collectors.groupingBy(item -> item.getStr("需方")));
        //模板文件路径
        String sourcePath = rootPath + File.separator + templateName + ".xls";
        //遍历生成文件
        for (Map.Entry<String, List<JSONObject>> entry : dataByGroup.entrySet()) {
            String targetPath = targetRootPath + File.separator + templateName + "_"+ entry.getKey() + ".xls";
            //根据模板创建文件
            FileUtil.copy(sourcePath, targetPath, true);

            // 读取Excel文件
            ExcelWriter writer = ExcelUtil.getWriter(targetPath, "购销合同");
            //编辑第1个sheet
            writer.setSheet(0);
            JSONObject row0 = entry.getValue().get(0);
            writer.getCell(6, 3).setCellValue(row0.getStr("合同编码"));
            writer.getCell(6, 4).setCellValue(formatter.format(row0.getLocalDateTime("签订日期", LocalDateTime.now())));
            writer.getCell(1, 4).setCellValue(row0.getStr("需方"));
            writer.getCell(1, 419).setCellValue(row0.getStr("客户名称"));
            writer.getCell(1, 420).setCellValue(row0.getStr("单位地址"));
            writer.getCell(1, 421).setCellValue(row0.getStr("电话"));
            writer.getCell(1, 422).setCellValue(row0.getStr("开户行"));
            writer.getCell(1, 423).setCellValue(row0.getStr("账号"));
            writer.getCell(1, 424).setCellValue(row0.getStr("纳税识别码"));

            // 动态插入数据行位置
            int insertRow = this.getInsertRowIndex(sourcePath);
            int index = 1;
            for (JSONObject item : entry.getValue()) {
                writer.getCell(0, insertRow).setCellValue(index++);
                writer.getCell(1, insertRow).setCellValue(item.getStr("产品类型"));
                writer.getCell(2, insertRow).setCellValue(item.getStr("规格型号"));
                writer.getCell(3, insertRow).setCellValue(item.getStr("单位"));
                writer.getCell(4, insertRow).setCellValue(item.getDouble("数量"));
                writer.getCell(5, insertRow).setCellValue(item.getDouble("含税单价"));
                writer.getCell(6, insertRow).setCellValue(item.getDouble("税率"));
                writer.getCell(7, insertRow).setCellValue(item.getDouble("含税金额"));
                insertRow++;
            }
            //设置公式
            writer.getCell(4, 408).setCellFormula("SUM(E9:E" + insertRow + ")");
            writer.getCell(7, 408).setCellFormula("SUM(H9:H" + insertRow + ")");
            for(int i = insertRow; i < 408; i++) {
                writer.getSheet().removeRow(writer.getSheet().getRow(i));
            }
            writer.getSheet().shiftRows(408, writer.getSheet().getLastRowNum(), insertRow-408,true,true);

            // 插入图片
            int rowIndex = insertRow + 11; // 图片所在行索引
            int columnIndex = 5; // 图片所在列索引
            String imagePath = rootPath + File.separator + imageName; // 图片文件路径

            // 读取图片文件
            File imageFile = FileUtil.file(imagePath);

            // 将图片插入指定位置
            Drawing<?> drawing = writer.getSheet().createDrawingPatriarch();
            ClientAnchor anchor = drawing.createAnchor(0, 0, 0, 0, columnIndex, rowIndex, columnIndex + 1, rowIndex + 1);
            Picture picture = drawing.createPicture(anchor, writer.getWorkbook().addPicture(FileUtil.readBytes(imageFile), Workbook.PICTURE_TYPE_PNG));

            // 设置图片大小
            picture.resize();
            picture.resize(0.45, 0.5);

            // 获取Workbook对象
            Workbook workbook = writer.getWorkbook();
            if(StringUtils.isNotBlank(password)) {

                // 创建Sheet对象
                Sheet sheet = workbook.getSheetAt(0);

                // 创建Cell对象
                Cell cell = writer.getCell(columnIndex, rowIndex);

                // 设置锁定单元格的样式
                CellStyle lockedStyle = cell.getCellStyle();
                lockedStyle.setLocked(true);
                lockedStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());

                // 设置单元格锁定
                sheet.protectSheet(password);
            }

            //编辑第2个sheet
            writer.setSheet(1);
            writer.getCell(1, 6).setCellValue(row0.getStr("客户名称"));
            writer.getCell(1, 7).setCellValue(row0.getStr("单位地址"));
            writer.getCell(6, 7).setCellValue(formatter.format(row0.getLocalDateTime("送货日期", LocalDateTime.now())));

            // 动态插入数据行位置
            int insertRow2 = 9;
            int index2 = 1;
            for (JSONObject item : entry.getValue()) {
                writer.getCell(0, insertRow2).setCellValue(index2++);
                writer.getCell(1, insertRow2).setCellValue(item.getStr("产品类型"));
                writer.getCell(2, insertRow2).setCellValue(item.getStr("规格型号"));
                writer.getCell(3, insertRow2).setCellValue(item.getStr("单位"));
                writer.getCell(4, insertRow2).setCellValue(item.getDouble("数量"));
                writer.getCell(5, insertRow2).setCellValue(item.getDouble("含税单价"));
                writer.getCell(6, insertRow2).setCellValue(item.getDouble("税率"));
                writer.getCell(7, insertRow2).setCellValue(item.getDouble("含税金额"));
                insertRow2++;
            }
            //设置公式
            writer.getCell(4, 409).setCellFormula("SUM(E10:E" + insertRow2 + ")");
            writer.getCell(7, 409).setCellFormula("SUM(H10:H" + insertRow2 + ")");
            for(int i = insertRow2; i < 409; i++) {
                writer.getSheet().removeRow(writer.getSheet().getRow(i));
            }
            writer.getSheet().shiftRows(409, writer.getSheet().getLastRowNum(), insertRow2-409,true,true);

            // 插入图片
            int rowIndex2 = insertRow2 - 1; // 图片所在行索引
            int columnIndex2 = 3; // 图片所在列索引

            // 将图片插入指定位置
            Drawing<?> drawing2 = writer.getSheet().createDrawingPatriarch();
            ClientAnchor anchor2 = drawing2.createAnchor(0, 0, 0, 0, columnIndex2, rowIndex2, columnIndex2 + 1, rowIndex2 + 1);
            Picture picture2 = drawing2.createPicture(anchor2, writer.getWorkbook().addPicture(FileUtil.readBytes(imageFile), Workbook.PICTURE_TYPE_PNG));

            // 设置图片大小
            picture2.resize();
            picture2.resize(0.45, 0.5);

            if(StringUtils.isNotBlank(password)) {
                // 创建Cell对象
                Cell cell2 = writer.getCell(columnIndex2, rowIndex2);

                // 设置锁定单元格的样式
                CellStyle lockedStyle2 = cell2.getCellStyle();
                lockedStyle2.setLocked(true);
                lockedStyle2.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());

                // 创建Sheet对象
                workbook.getSheetAt(1).protectSheet(password);
            }

            // 关闭writer，完成修改操作
            writer.close();
        }

    }

    private int getInsertRowIndex(String sourcePath) {
        return 8;
    }

    @Override
    public Object listHt() {
        return this.data;
    }

    private List<JSONObject> readFile(InputStream in){
        // 导入Excel文件
        ExcelReader reader = null;
        reader = ExcelUtil.getReader(in);
        List<List<Object>> rows = reader.read();
        //获取表头
        List<String> headers = new ArrayList<>();
        for (Object o : rows.get(0)) {
            headers.add(o.toString());
        }
        //获取数据内容装入map
        List<JSONObject> data = new ArrayList<>();
        for (int i = 1; i < rows.size(); i++) {
            JSONObject item = new JSONObject();
            List<Object> row = rows.get(i);
            for (int j = 0; j < row.size() && j < headers.size(); j++) {
                item.putOpt(headers.get(j), row.get(j));
            }
            data.add(item);
        }
        this.data = data;
        return data;
    }

    @Override
    public void uploadTemplate(MultipartFile file) {
        //创建文件夹
        File root = new File(rootPath);
        if(!root.exists()){
            root.mkdir();
        }
        //后缀
        String suf = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        File target = new File(rootPath + File.separator + templateName + suf);
        try {
            target.deleteOnExit();
            file.transferTo(target);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void uploadImage(MultipartFile file) {
        //创建文件夹
        File root = new File(rootPath);
        if(!root.exists()){
            root.mkdir();
        }
        File target = new File(rootPath + File.separator + imageName);
        try {
            target.deleteOnExit();
            file.transferTo(target);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public JSONObject getConfig() {
        return this.config;
    }
}
