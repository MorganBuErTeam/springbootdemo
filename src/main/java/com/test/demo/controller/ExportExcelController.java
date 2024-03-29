package com.test.demo.controller;


import com.test.demo.common.util.DateUtil;
import com.test.demo.common.util.ExcelUtils;
import com.test.demo.domain.Task;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * 导出Excel文件
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/excel")
@Slf4j
public class ExportExcelController {

    
    @GetMapping("/taskLog")
    public void taskLog(String state,String taskType,String taskState,String startDate,String endDate,HttpServletResponse response){
        try {
            List<String[]> excelHeaderList=new ArrayList<>();
            List<List<Object>> taskList = new ArrayList<>();
            String export ="";
            //fileNames:生成的excel默认文件名和sheet页
            String[] fileNames={"任务历史日志","子任务历史"};
            export = "AGV编号#agvCode,任务名称#taskName,任务状态#state,任务类型#taskType,产生时间#createDate," +
                    "响应时间#realStartDate,完成时间#finishDate,备注#remark,优先级#sort,搬运次数#count,完成次数#finishCount,任务频率#frequency," +
                    "起始停靠点#startSitCode,目标停靠点#targetSitCode";
            String[] excelHeader = export.split(",");
            excelHeaderList.add(excelHeader);
            export ="";
            export= "任务编号#taskId,AGV编号#agvCode,关联路径#routCode,起始站点编号#startSitCode,起始停靠点#pickSitCode," +
                    "终止停靠点#putSitCode,任务状态#state,任务信息#taskInfo,备注#remark,类型#type,产生时间#createDate,响应时间#realStartDate," +
                    "完成时间#finishDate";
            String[] excelHeaderTwo = export.split(",");
            excelHeaderList.add(excelHeaderTwo);
            //声明数据集合
            if(null!=taskState && !"".equals(taskState)){
                state=taskState;
            }
//            List<Task> taskLogs = taskService.queryInfoBy(state,taskType,startDate, endDate);
            List<String> taskLogs =null;
            Date startTime = null;
            Date endTime = null;
            if (StringUtils.isNotBlank(startDate) && !"null".equals(startDate)) {
                startTime = DateUtil.stringToDate((startDate+" 00:00:00"));
                if (StringUtils.isNotBlank(endDate) && !"null".equals(endDate)) {
                    endTime = DateUtil.stringToDate((endDate+" 23:59:59"));
                }
            }
//            List<SubTask> subTaskList = subTaskMapper.queryInfoBy(state,taskType,startTime, endTime);
            List<String> subTaskList =null;
            taskList.add(Collections.singletonList(taskLogs));
            taskList.add(Collections.singletonList(subTaskList));
            ExcelUtils.excelExportBacth(response, fileNames, excelHeaderList, taskList);//调用封装好的导出方法，具体方法在下面
        } catch (Exception e) {
        	System.out.println("导出错误信息： "+e.getMessage());
        }
    }


//    public void export() throws Exception{
//        File excelFile=getExcelFile();
//        List<Task> list=new ArrayList<>();
//        writeDateToExcel(createModelList(list),excelFile);
//    }


//    /**
//     * 创建Excel文件
//     * @return Excel临时文件
//     * @throws IOException
//     */
//    private File getExcelFile() throws IOException{
//        String excelFileName=String.format("名字_%s.xlsx",LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
//        String excelFilePath="";
//        File excelfile=new File(excelFilePath,excelFileName);
//        if(excelfile.exists() && !excelfile.delete()){
//            throw new IOException("文件已存在且无法删除！");
//        }
//        FileUtils.touch(excelfile);
//        log.info("文件路径{}",excelfile.getPath());
//        return excelfile;
//    }
//
//
//    /**
//     * 构造写入文件模型了吧
//     * @param results
//     * @return
//     */
//    private List<EntityExportWriteModel> createModelList(List<Task> results){
//        List<EntityExportWriteModel> writeModels=new ArrayList<>();
//        for(Task result:results){
//            EntityExportWriteModel writeModel=EntityExportWriteModel.builder()
//                    .code(result.getTaskType())
//                    .abbr(result.getTaskName())
//                    .build();
//            writeModels.add(writeModel);
//        }
//        return writeModels;
//    }
//
//    /**
//     * 将数据写入文件
//     *  @param writeModels
//     * @param excelFile
//     * @throws IOException
//     */
//    private void writeDateToExcel(List<EntityExportWriteModel> writeModels,File excelFile) throws IOException{
//        try(OutputStream outputStream=new FileOutputStream(excelFile)){
//            System.out.println("写入开始");
//            Sheet sheet=new Sheet(1,1,EntityExportWriteModel.class,"名称",null);
//            ExcelWriter excelWrite=new ExclWriter(null,outputStream,ExcelTypeEnum.XLSX,true);
//            excelWrite.write(writeModels,sheet);
//            excelFile.finish();
//            System.out.println("写入结束");
//        }
//    }
//
//
//
//    /**
//     * 数据导出模型
//     */
//    @Data
//    @Builder
//    @EqualsAndHashCode(callSuper = true)
//    private static class EntityExportWriteModel extends BaseRowModel{
//
//        @ExcelProperty(value="代号",index=0)
//        private String code;
//
//        @ExcelProperty(value="名称",index=1)
//        private String abbr;
//
//    }

    
    
}
