package com.test.demo.common.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TxtDocUtil {
	/**
	 * 读取指的路径的指定的.txt文件信息
	 * 
	 * @param path
	 *            文件的所在的路径
	 * @return List<String> 行的集合
	 */
	public static List<String> readTxtReturnList(MultipartFile file) throws Exception {
		List<String> lineList = new ArrayList<String>();
		/* 读入TXT文件 */
		File f = null;
		f = File.createTempFile("tmp", null);
	    file.transferTo(f);
	    InputStreamReader reader = new InputStreamReader(new FileInputStream(f)); // 建立一个输入流对象reader
		BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
		String line = "";
		while ((line = br.readLine()) != null) {// 一次读入一行数据
			lineList.add(line);
		}
	    f.deleteOnExit();     
		return lineList;
	}
	
	
	/**
	 * 读取指的路径的指定的.json文件信息
	 * 
	 * @param path
	 *            文件的所在的路径
	 * @return String 内容
	 */
	public static String readJsonReturnString(MultipartFile file) throws Exception {
		StringBuffer resultStr = new StringBuffer();
		/* 读入JSON文件 */
		File f = null;
		f = File.createTempFile("tmp", null);
	    file.transferTo(f);
	    InputStreamReader reader = new InputStreamReader(new FileInputStream(f)); // 建立一个输入流对象reader
		BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
		String line = "";
		while ((line = br.readLine()) != null) {// 一次读入一行数据
			resultStr.append(line.trim());
		}
	    f.deleteOnExit();     
		return resultStr.toString();
	}

	/**
	 * 写入指的路径的.txt文件信息
	 * 
	 * @param path
	 *            文件的所在的路径
	 * @param context
	 *            写入文件的内容
	 */
	private static void writeTxt(String path, String context) {
		try {
			/* 写入Txt文件 */
			File writename = new File(path); // 如果没有则要建立一个新的output.txt文件
			writename.createNewFile(); // 创建新文件
			BufferedWriter out = new BufferedWriter(new FileWriter(writename));
			out.write(context); // \r\n即为换行
			out.flush(); // 把缓存区内容压入文件
			out.close(); // 最后记得关闭文件

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
