package com.test.demo.test.designMode;

import com.test.demo.test.designMode.factory.FileDisposeServicesFactory;
import com.test.demo.test.designMode.service.FileDisposeService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.junit.Test;

import java.io.File;

@Slf4j
public class FileDispose {


    @Test
    public void filedispose(){

        FileCopyVo fileCopyVo=new FileCopyVo();
        fileCopyVo.setSourceFilePath("/projects/data/");
        fileCopyVo.setTargetFilePath("/projects/data/tmp");

        String fileName="测试文件.pdf";

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

            //FilenameUtils工具类常用方法：
            //  getExtension(String path)：获取文件的扩展名
            //  getName()：获取文件夹或文件的名称；
            //  isExtension(String fileName,String ext)：判断fileName是否是ext后缀名

            String fileType=FilenameUtils.getExtension(targetFile.getName());

            FileDisposeService fileDisposeService=FileDisposeServicesFactory.getService(fileType);
            if(null==fileDisposeService){
                throw new RuntimeException(String.format("%s 文件,类型无法处理",targetFile.getName()));
            }

            String  pageContent=fileDisposeService.readFile(targetFile);

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
