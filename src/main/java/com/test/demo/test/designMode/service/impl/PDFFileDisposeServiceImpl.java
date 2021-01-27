package com.test.demo.test.designMode.service.impl;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import com.test.demo.test.designMode.service.FileDisposeService;

import java.io.File;

/**
 * doc格式文件读取，策略实现类
 */
public class PDFFileDisposeServiceImpl implements FileDisposeService {

    @Override
    public String readFile(File targetFile) throws Exception{
        String pageContent="";
        PdfReader pdfReader=new PdfReader(targetFile.getPath());
        int pageNum=pdfReader.getNumberOfPages();

        for(int i=1;i<=pageNum;i++){
            //读取第i页的文档内容
            pageContent+=PdfTextExtractor.getTextFromPage(pdfReader,i);
        }
        return pageContent;
    }

}
