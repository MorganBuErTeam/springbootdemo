package com.test.demo.common.util;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	
	/**
	 * 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
	 * @param date 
	 * @return Date
	 * @throws ParseException
	 */
	public static Date stringToDate(String strDate)  {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ParsePosition pos = new ParsePosition(0);
	    Date strtodate = formatter.parse(strDate, pos);
	    return strtodate;
	}
	
	/**
	 * 在指定的日期上加指定的月数
	 * @param time 需加的日期/指定的日期
	 * @param monthNum 月数
	 * @return Date 指定月数之后的日期
	 */
	public static Date getPreMonth(Date time,int monthNum) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        cal.add(Calendar.MONTH, monthNum);//增加一个月   
        return cal.getTime();
    }
	
}
