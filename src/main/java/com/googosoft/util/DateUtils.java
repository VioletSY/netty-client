package com.googosoft.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

	private static String timeStr;
	
	/**
	 * 获取当前时间
	 * @return
	 */
	public static String getDateTime(){
        Date date = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        timeStr = sf.format(date);
        return timeStr;
    }
	
}
