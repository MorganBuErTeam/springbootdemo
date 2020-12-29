package com.test.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * easyExcel导出Excel文件
 *
 * @author Administrator
 */
@RestController
@RequestMapping("/easyexcel")
@Slf4j
public class EasyExcelExportController {


//    @GetMapping("/easyexcel")
//    public void export(HttpServletResponse response) throws Exception {
//
//        //创建excel文件
//        File excelFile = new File("路径", "文件名");
//        FileUtils.touch(excelFile);
//
//        //查询数据
//        List<Task> list = taskService.selectListAll();
//
//        //导出excel文件
//        writeDateToExcel(createModelList(list), excelFile);
//    }
//
//
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
//            ExcelWriter excelWrite=new ExcelWriter(null,outputStream,ExcelTypeEnum.XLSX,true);
//            excelWrite.write(writeModels,sheet);
//            excelWrite.finish();
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
//    private static class EntityExportWriteModel extends BaseRowModel {
//
//        @ExcelProperty(value="代号",index=0)
//        private String code;
//
//        @ExcelProperty(value="名称",index=1)
//        private String abbr;
//
//    }


}
