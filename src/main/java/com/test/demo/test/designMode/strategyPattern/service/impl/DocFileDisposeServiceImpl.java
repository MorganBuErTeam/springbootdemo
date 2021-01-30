package com.test.demo.test.designMode.strategyPattern.service.impl;

import com.test.demo.test.designMode.strategyPattern.service.FileDisposeService;
import org.apache.poi.hwpf.extractor.WordExtractor;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * doc格式文件读取，策略实现类
 */
public class DocFileDisposeServiceImpl implements FileDisposeService {

    @Override
    public String readFile(File targetFile) throws Exception{
        String pageContent="";
        //方式1
        InputStream inputStream=new FileInputStream(targetFile);
        WordExtractor extractor=new WordExtractor(inputStream);
        //方式2
//                FileInputStream fisx=new FileInputStream(targetFile);
//                HWPFDocument document=new HWPFDocument(fisx);
//                WordExtractor extractor=new WordExtractor(document);

        pageContent=extractor.getText();

        inputStream.close();
        extractor.close();
        return pageContent;
    }

}
