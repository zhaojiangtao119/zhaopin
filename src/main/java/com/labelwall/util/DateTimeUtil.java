package com.labelwall.util;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2017-12-02. 日期转化工具类
 */
public class DateTimeUtil {
    public static final String STANDARD_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static Date strToData(String dataTimeStr, String formatStr) {
        DateTimeFormatter dateTimeFormat = DateTimeFormat.forPattern(formatStr);
        DateTime dateTime = dateTimeFormat.parseDateTime(dataTimeStr);
        return dateTime.toDate();
    }

    public static String dateToStr(Date date, String formatStr) {
        if (date == null) {
            return StringUtils.EMPTY;
        }
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(formatStr);
    }

    public static Date strToDate(String dataTimeStr) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(STANDARD_FORMAT);
        DateTime dateTime = dateTimeFormatter.parseDateTime(dataTimeStr);
        return dateTime.toDate();
    }

    public static String dateToStr(Date date) {
        if (date == null) {
            return StringUtils.EMPTY;
        }
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(STANDARD_FORMAT);
    }

    //计算两个日期的分钟差
    public static long dateInterval(Date beginDateStr, Date endDateStr) {
        DateTime beginDate = new DateTime(beginDateStr);
        DateTime endDate = new DateTime(endDateStr);
        Duration duration = new Duration(beginDate, endDate);
        long millis = duration.getStandardMinutes();
        return millis;
    }

    //验证时间的格式
    public static boolean isValiDateFormat(String dataStr) {
        boolean falg = true;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        format.setLenient(false);//
        try {
            format.parse(dataStr);
        } catch (ParseException e) {
            e.printStackTrace();
            falg = false;
        }
        return falg;
    }

    //改变时间的格式
    public static String changeDateFormat(String dateStr) {
        String[] date = dateStr.split(" ");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(date[0]);
        stringBuilder.append(" ");
        stringBuilder.append("00:00:00");
        return stringBuilder.toString();
    }
}
