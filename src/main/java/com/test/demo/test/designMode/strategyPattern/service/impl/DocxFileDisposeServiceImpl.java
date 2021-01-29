package com.test.demo.test.designMode.strategyPattern.service.impl;

import com.test.demo.test.designMode.strategyPattern.service.FileDisposeService;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.POIXMLTextExtractor;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;

import java.io.File;

/**
 * doc格式文件读取，策略实现类
 */
public class DocxFileDisposeServiceImpl implements FileDisposeService {

    @Override
    public String readFile(File targetFile) throws Exception{
        String pageContent="";
        OPCPackage opcPackage=POIXMLDocument.openPackage(targetFile.getPath());
        POIXMLTextExtractor extractor=new XWPFWordExtractor(opcPackage);

        pageContent=extractor.getText();

        opcPackage.close();
        extractor.close();
        return pageContent;
    }

}
