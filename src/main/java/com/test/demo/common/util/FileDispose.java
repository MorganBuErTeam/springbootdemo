package com.test.demo.common.util;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.POIXMLTextExtractor;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

@Slf4j
public class FileDispose {


    @Test
    public void filedispose(){

        FileCopyVo fileCopyVo=new FileCopyVo();
        fileCopyVo.setSourceFilePath("/projects/data/");
        fileCopyVo.setTargetFilePath("/projects/data/tmp");

        String fileName="";

        File sourceFile=new File(fileCopyVo.getSourceFilePath(),fileName);
        if(sourceFile.isDirectory()){
            throw new RuntimeException(String.format("源文件路径参数 %s 必须包含文件路径域文件名！",fileCopyVo.getSourceFilePath()));
        }
        if(!sourceFile.exists()){
            throw new RuntimeException(String.format("源文件路径 %s 文件不存在！",fileCopyVo.getSourceFilePath()));
        }

        File targetFile=new File(fileCopyVo.getTargetFilePath(),fileName);
        if(targetFile.isDirectory()){
            throw new RuntimeException(String.format("目标文件路径参数 %s 必须包含文件路径域文件名！",fileCopyVo.getTargetFilePath()));
        }
        if(targetFile.exists() && !targetFile.delete()){
            throw new RuntimeException(String.format("文件 %s 已存在且无法删除！",targetFile.getPath()));
        }

        try{
            //复制文件
            FileUtils.copyFile(sourceFile,targetFile);

            //判断文件格式，使用对应的读取器，读取为string   word:poi     pdf:itextpdf
            String  pageContent="";

            if("pdf".equalsIgnoreCase(FilenameUtils.getExtension(targetFile.getName()))){

                PdfReader pdfReader=new PdfReader(targetFile.getPath());
                int pageNum=pdfReader.getNumberOfPages();

                for(int i=1;i<=pageNum;i++){
                    //读取第i页的文档内容
                    pageContent+=PdfTextExtractor.getTextFromPage(pdfReader,i);
                }
            }else if("doc".equalsIgnoreCase(FilenameUtils.getExtension(targetFile.getName()))){

                InputStream inputStream=new FileInputStream(targetFile);
                WordExtractor extractor=new WordExtractor(inputStream);

                pageContent=extractor.getText();

                inputStream.close();
                extractor.close();
            }else if("docx".equalsIgnoreCase(FilenameUtils.getExtension(targetFile.getName()))){

                OPCPackage opcPackage=POIXMLDocument.openPackage(targetFile.getPath());
                POIXMLTextExtractor extractor=new XWPFWordExtractor(opcPackage);

                pageContent=extractor.getText();

                opcPackage.close();
                extractor.close();
            }else{
                throw new RuntimeException(String.format("%s 文件,类型无法处理",targetFile.getName()));
            }

            if(pageContent.contains("中证")){
                System.out.println("命中关键词");
            }else{
                System.out.println("没有命中关键词");
            }

            FileUtils.deleteQuietly(targetFile);
            System.out.println("处理完成，删除临时文件");

        }catch (Exception e){
            log.error("异常：",e);
        }



    }


    @Data
    public class FileCopyVo{

        String sourceFilePath;

        String targetFilePath;
    }
}
