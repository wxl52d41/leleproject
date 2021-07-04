package com.poi.test.controller;

import com.alibaba.fastjson.JSONArray;
import com.poi.test.util.ExcelUtils;
import com.poi.test.vo.DataSetTmpVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: excelTestController
 * @Description: TODO
 * @Author: wang xiao le
 * @Date: 2021/7/4 15:52
 **/
@RestController
@RequestMapping("/excel")
@CrossOrigin
public class excelTestController {


    @PostMapping(value = "/saveExcel", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void excelTest(@RequestPart("file") MultipartFile file) throws Exception {
        JSONArray jsonArray = new JSONArray();
        BufferedInputStream bufferedInput = null;
        if (file.isEmpty()) {
           throw new Exception("文件不存在");
        }

        String fileName = file.getOriginalFilename();
        if (StringUtils.isNotEmpty(fileName)
                && !"xlsx".equals(fileName.substring(fileName.indexOf(".") + 1))) {
            throw new Exception("文件扩展名不正确！");
        }
        bufferedInput = new BufferedInputStream(file.getInputStream());
        Map<String, Object> map = new HashMap();
        List<DataSetTmpVo> list = new ArrayList<DataSetTmpVo>();
        //将excel解析成对象集合
        if (bufferedInput != null) {
            jsonArray = ExcelUtils.getValues(bufferedInput);
            if (jsonArray != null && jsonArray.size() > 0) {
                for (int i = 0; i < jsonArray.size(); i++) {
                    map = (Map) jsonArray.get(i);
                    DataSetTmpVo dataSet;
                    if (map != null && map.size() > 0) {
                        dataSet = new DataSetTmpVo(map);
                        list.add(dataSet);
                    }
                }
            }
        }
    }
}
