package com.labelwall.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class OrderUtils {

	public static String getOrderNum(Integer countNum){
		String num=countNum.toString();
		for (int i = 0; i < (5-countNum.toString().length()); i++) {
			num="0"+num;
		}
		SimpleDateFormat date=new SimpleDateFormat("yyMMddhhmmss");
		String result=date.format(new Date())+num;
		return result;
	}
	
	
}
