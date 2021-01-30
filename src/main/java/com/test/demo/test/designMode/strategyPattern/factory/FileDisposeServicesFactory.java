package com.test.demo.test.designMode.strategyPattern.factory;

import com.test.demo.test.designMode.strategyPattern.service.FileDisposeService;
import com.test.demo.test.designMode.strategyPattern.service.impl.DocFileDisposeServiceImpl;
import com.test.demo.test.designMode.strategyPattern.service.impl.DocxFileDisposeServiceImpl;
import com.test.demo.test.designMode.strategyPattern.service.impl.PDFFileDisposeServiceImpl;

import java.util.HashMap;
import java.util.Map;

/**
 * 文件处理，策略工厂类
 */
public class FileDisposeServicesFactory {

    private static final Map<String,FileDisposeService> map=new HashMap<>();

    static {
        map.put("doc",new DocFileDisposeServiceImpl());
        map.put("docx",new DocxFileDisposeServiceImpl());
        map.put("pdf",new PDFFileDisposeServiceImpl());
    }

    public static FileDisposeService getService(String fileType){
        return map.get(fileType);
    }
}
