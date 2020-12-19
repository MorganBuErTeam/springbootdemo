package com.test.demo.common.util;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 基于POI的javaee导出Excel工具类
 *
 * @author wjmisaboy@gmail.com
 * @see
 */
public class ExcelUtils {
    private final static String excel2003L = ".xls"; // 2003- 版本的excel
    private final static String excel2007U = ".xlsx"; // 2007+ 版本的excel


    //两个sheet,大任务和子任务
    public static <T> HSSFWorkbook excelExportBacth(HttpServletResponse response, String[] fileNames,
                                                    List<String[]> excelHeaderList, List<List<Object>> taskList) throws Exception {
        // 设置请求
        response.setContentType("application/application/vnd.ms-excel");
        response.setHeader("Content-disposition",
                "attachment;filename=" + URLEncoder.encode(fileNames[0] + ".xls", "UTF-8"));
        // 创建一个Workbook，对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
        // 设置标题样式
        HSSFCellStyle titleStyle = wb.createCellStyle();
        // 设置单元格边框样式
        titleStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框 细边线
        titleStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);// 下边框 细边线
        titleStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框 细边线
        titleStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框 细边线
        // 设置单元格对齐方式
        titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平居中
        titleStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 垂直居中
        // 设置字体样式
        Font titleFont = wb.createFont();
        titleFont.setFontHeightInPoints((short) 15); // 字体高度
        titleFont.setFontName("黑体"); // 字体样式
        titleStyle.setFont(titleFont);
        // 在Workbook中添加多个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // 日期格式化
        for (int i = 0; i < fileNames.length; i++) {
            sheet = wb.createSheet(fileNames[i]);
            int temp = excelHeaderList.get(i).length;
            String[] tempList = excelHeaderList.get(i);
            // 标题数组
            String[] titleArray = new String[temp];
            // 字段名数组
            String[] fieldArray = new String[temp];
            for (int j = 0; j < temp; j++) {
                String[] tempArray = tempList[j].split("#");// 临时数组 分割#
                titleArray[j] = tempArray[0];
                fieldArray[j] = tempArray[1];
            }

            // 在sheet中添加标题行
            HSSFRow row = sheet.createRow((int) 0);// 行数从0开始
            HSSFCell sequenceCell = row.createCell(0);// cell列 从0开始 第一列添加序号
            sequenceCell.setCellValue("序号");
            sequenceCell.setCellStyle(titleStyle);
            sheet.autoSizeColumn(0);// 自动设置宽度
            // 为标题行赋值
            for (int k = 0; k < titleArray.length; k++) {
                HSSFCell titleCell = row.createCell(k + 1);// 0号位被序号占用，所以需+1
                titleCell.setCellValue(titleArray[k]);
                titleCell.setCellStyle(titleStyle);
                sheet.autoSizeColumn(k + 1);// 0号位被序号占用，所以需+1
            }

            // 数据样式 因为标题和数据样式不同 需要分开设置 不然会覆盖
            HSSFCellStyle dataStyle = wb.createCellStyle();
            // 设置数据边框
            dataStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            dataStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
            dataStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            dataStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
            // 设置居中样式
            dataStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平居中
            dataStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 垂直居中
            // 设置数据字体
            Font dataFont = wb.createFont();
            dataFont.setFontHeightInPoints((short) 12); // 字体高度
            dataFont.setFontName("宋体"); // 字体
            dataStyle.setFont(dataFont);

            // 遍历集合数据，产生数据行
            for (Object taskListTemp : taskList.get(i)) {
                Collection<T> sd = (Collection) taskListTemp;
                Iterator<T> it = sd.iterator();
                int index = 0;
                while (it.hasNext()) {
                    index++;// 0号位被占用 所以+1
                    row = sheet.createRow(index);
                    // 为序号赋值
                    HSSFCell sequenceCellValue = row.createCell(0);// 序号值永远是第0列
                    sequenceCellValue.setCellValue(index);
                    sequenceCellValue.setCellStyle(dataStyle);
                    sheet.autoSizeColumn(0);
                    T t = (T) it.next();
                    // 利用反射，根据传过来的字段名数组，动态调用对应的getXxx()方法得到属性值
                    for (int s = 0; s < fieldArray.length; s++) {
                        HSSFCell dataCell = row.createCell(s + 1);
                        dataCell.setCellStyle(dataStyle);
                        sheet.autoSizeColumn(s + 1);
                        String fieldName = fieldArray[s];
                        String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);// 取得对应getXxx()方法
                        Class<? extends Object> tCls = t.getClass();// 泛型为Object以及所有Object的子类
                        Method getMethod = tCls.getMethod(getMethodName, new Class[]{});// 通过方法名得到对应的方法
                        Object value = getMethod.invoke(t, new Object[]{});// 动态调用方,得到属性值
                        if (value != null) {
                            if (fieldName.indexOf("Date") != -1) {
                                value = sdf.format(value);  //日期格式化
                            }
                            dataCell.setCellValue(value.toString());// 为当前列赋值
                        }

                    }
                }
            }
        }

//        OutputStream outputStream = response.getOutputStream();// 打开流
        //导出到指定路径
        String rootFilePath = "D:/exportByJava/";
        FileOutputStream outputStream=new FileOutputStream(rootFilePath+"/"+fileNames[0] + ".xls");
        wb.write(outputStream);// HSSFWorkbook写入流
        // wb.close();// HSSFWorkbook关闭
        outputStream.flush();// 刷新流
        outputStream.close();// 关闭流
        return wb;
    }


    /**
     * @param response    请求
     * @param fileName    文件名 如："学生表"
     * @param excelHeader excel表头数组，存放"姓名#name"格式字符串，"姓名"为excel标题行， "name"为对象字段名
     * @param dataList    数据集合，需与表头数组中的字段名一致，并且符合javabean规范
     * @return 返回一个HSSFWorkbook
     * @throws Exception
     */
    public static <T> HSSFWorkbook export(HttpServletResponse response, String fileName, String[] excelHeader,
                                          Collection<T> dataList) throws Exception {
        // 设置请求
        response.setContentType("application/application/vnd.ms-excel");
        response.setHeader("Content-disposition",
                "attachment;filename=" + URLEncoder.encode(fileName + ".xls", "UTF-8"));
        // 创建一个Workbook，对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
        // 设置标题样式
        HSSFCellStyle titleStyle = wb.createCellStyle();
        // 设置单元格边框样式
        titleStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框 细边线
        titleStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);// 下边框 细边线
        titleStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框 细边线
        titleStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框 细边线
        // 设置单元格对齐方式
        titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平居中
        titleStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 垂直居中
        // 设置字体样式
        Font titleFont = wb.createFont();
        titleFont.setFontHeightInPoints((short) 15); // 字体高度
        titleFont.setFontName("黑体"); // 字体样式
        titleStyle.setFont(titleFont);
        // 在Workbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet(fileName);
        // 标题数组
        String[] titleArray = new String[excelHeader.length];
        // 字段名数组
        String[] fieldArray = new String[excelHeader.length];
        for (int i = 0; i < excelHeader.length; i++) {
            String[] tempArray = excelHeader[i].split("#");// 临时数组 分割#
            titleArray[i] = tempArray[0];
            fieldArray[i] = tempArray[1];
        }
        // 在sheet中添加标题行
        HSSFRow row = sheet.createRow((int) 0);// 行数从0开始
        HSSFCell sequenceCell = row.createCell(0);// cell列 从0开始 第一列添加序号
        sequenceCell.setCellValue("序号");
        sequenceCell.setCellStyle(titleStyle);
        sheet.autoSizeColumn(0);// 自动设置宽度
        // 为标题行赋值
        for (int i = 0; i < titleArray.length; i++) {
            HSSFCell titleCell = row.createCell(i + 1);// 0号位被序号占用，所以需+1
            titleCell.setCellValue(titleArray[i]);
            titleCell.setCellStyle(titleStyle);
            sheet.autoSizeColumn(i + 1);// 0号位被序号占用，所以需+1
        }
        // 数据样式 因为标题和数据样式不同 需要分开设置 不然会覆盖
        HSSFCellStyle dataStyle = wb.createCellStyle();
        // 设置数据边框
        dataStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        dataStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        dataStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        dataStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        // 设置居中样式
        dataStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平居中
        dataStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 垂直居中
        // 设置数据字体
        Font dataFont = wb.createFont();
        dataFont.setFontHeightInPoints((short) 12); // 字体高度
        dataFont.setFontName("宋体"); // 字体
        dataStyle.setFont(dataFont);
        // 遍历集合数据，产生数据行
        Iterator<T> it = dataList.iterator();
        int index = 0;
        while (it.hasNext()) {
            index++;// 0号位被占用 所以+1
            row = sheet.createRow(index);
            // 为序号赋值
            HSSFCell sequenceCellValue = row.createCell(0);// 序号值永远是第0列
            sequenceCellValue.setCellValue(index);
            sequenceCellValue.setCellStyle(dataStyle);
            sheet.autoSizeColumn(0);
            T t = (T) it.next();
            // 利用反射，根据传过来的字段名数组，动态调用对应的getXxx()方法得到属性值
            for (int i = 0; i < fieldArray.length; i++) {
                HSSFCell dataCell = row.createCell(i + 1);
                dataCell.setCellStyle(dataStyle);
                sheet.autoSizeColumn(i + 1);
                String fieldName = fieldArray[i];
                String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);// 取得对应getXxx()方法
                Class<? extends Object> tCls = t.getClass();// 泛型为Object以及所有Object的子类
                Method getMethod = tCls.getMethod(getMethodName, new Class[]{});// 通过方法名得到对应的方法
                Object value = getMethod.invoke(t, new Object[]{});// 动态调用方,得到属性值
                if (value != null) {
                    dataCell.setCellValue(value.toString());// 为当前列赋值
                }
            }
        }

        OutputStream outputStream = response.getOutputStream();// 打开流
        wb.write(outputStream);// HSSFWorkbook写入流
        // wb.close();// HSSFWorkbook关闭
        outputStream.flush();// 刷新流
        outputStream.close();// 关闭流
        return wb;
    }
    // XSSFCellStyle.ALIGN_CENTER 居中对齐
    // XSSFCellStyle.ALIGN_LEFT 左对齐
    // XSSFCellStyle.ALIGN_RIGHT 右对齐
    // XSSFCellStyle.VERTICAL_TOP 上对齐
    // XSSFCellStyle.VERTICAL_CENTER 中对齐
    // XSSFCellStyle.VERTICAL_BOTTOM 下对齐

    // CellStyle.BORDER_DOUBLE 双边线
    // CellStyle.BORDER_THIN 细边线
    // CellStyle.BORDER_MEDIUM 中等边线
    // CellStyle.BORDER_DASHED 虚线边线
    // CellStyle.BORDER_HAIR 小圆点虚线边线
    // CellStyle.BORDER_THICK 粗边线

    /**
     * Excel导入
     *
     * @param in
     * @param fileName
     * @return
     * @throws Exception
     */
    public static List<List<String>> getBankListByExcel(InputStream in, String fileName) throws Exception {
        List<List<String>> list = null;
        // 创建Excel工作薄
        Workbook work = getWorkbook(in, fileName);
        if (null == work) {
            throw new Exception("创建Excel工作薄为空！");
        }
        Sheet sheet = null;
        Row row = null;
        Cell cell = null;
        list = new ArrayList<List<String>>();
        // 遍历Excel中所有的sheet
        for (int i = 0; i < work.getNumberOfSheets(); i++) {
            sheet = work.getSheetAt(i);
            if (sheet == null) {
                continue;
            }
            List<String> rowList = new ArrayList<String>();//每页的行的容器
            // 遍历当前sheet中的所有行
            // 包涵头部，所以要小于等于最后一列数,这里也可以在初始值加上头部行数，以便跳过头部
            for (int j = sheet.getFirstRowNum(); j <= sheet.getLastRowNum(); j++) {
                // 读取一行
                row = sheet.getRow(j);
                // 去掉空行和表头
                if (row == null || row.getFirstCellNum() == j) {
                    continue;
                }
                // 遍历一行的所有的列
                List<Object> li = new ArrayList<Object>();
                for (int y = row.getFirstCellNum(); y < row.getLastCellNum(); y++) {
                    cell = row.getCell(y);
                    li.add(cell == null ? " " : getCellValue(cell));
                }
                rowList.add(li.toString().replace("[", "").replace("]", ""));
            }
            list.add(rowList);
        }
        return list;
    }

    /**
     * 根据文件后缀，自适应上传文件的版本
     *
     * @param inStr
     * @param fileName
     * @return
     * @throws Exception
     */
    public static Workbook getWorkbook(InputStream inStr, String fileName) throws Exception {
        Workbook wb = null;
        String fileType = fileName.substring(fileName.lastIndexOf("."));
        if (excel2003L.equals(fileType)) {
            wb = new HSSFWorkbook(inStr); // 2003-
        } else if (excel2007U.equals(fileType)) {
            wb = new XSSFWorkbook(inStr); // 2007+
        } else {
            throw new Exception("解析的文件格式有误！");
        }
        return wb;
    }

    /**
     * 对表格中数值进行格式化
     *
     * @param cell
     * @return
     */
    public static Object getCellValue(Cell cell) throws Exception {
        Object value = null;
        DecimalFormat df = new DecimalFormat("0"); // 格式化字符类型的数字
        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd"); // 日期格式化
        DecimalFormat df2 = new DecimalFormat("0.00"); // 格式化数字
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_STRING:
                value = cell.getRichStringCellValue().getString();
                break;
            case Cell.CELL_TYPE_NUMERIC:
                if ("General".equals(cell.getCellStyle().getDataFormatString())) {
                    value = df.format(cell.getNumericCellValue());
                } else if ("m/d/yy".equals(cell.getCellStyle().getDataFormatString())) {
                    value = sdf.format(cell.getDateCellValue());
                } else {
                    value = df2.format(cell.getNumericCellValue());
                }
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                value = cell.getBooleanCellValue();
                break;
            case Cell.CELL_TYPE_BLANK:
                value = "";
                break;
            default:
                break;
        }
        return value == null ? "" : value;
    }


}