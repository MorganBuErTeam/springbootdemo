package com.test.demo.test.designMode.factory;

import com.test.demo.test.designMode.service.FileDisposeService;
import com.test.demo.test.designMode.service.impl.DocFileDisposeServiceImpl;
import com.test.demo.test.designMode.service.impl.DocxFileDisposeServiceImpl;
import com.test.demo.test.designMode.service.impl.PDFFileDisposeServiceImpl;

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
