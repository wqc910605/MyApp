package com.wwf.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DataUtil {
    //    public static final String PATTERN2 = "yyyy年MM月dd日 HH:mm:ss";
    public static final String[] WEEKS = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};

    /**
     * 获取当前日期是星期几<br>
     *
     * @param date
     * @return 当前日期是星期几
     */
    public static String getWeekOfDate(Date date) {
        String[] weekDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    /**
     * 获取几天前或者几天后的时间
     * @param past
     * @return
     */
    public static String getPastDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
        return getTimeStr(calendar.getTime());
    }

    /**
     * 将毫秒值转成自定义格式的字符串
     */
    public static String getTimeStrByPattern(long millis, String pattern) {
        String dateStr = "";
        try {
            Date date = new Date(millis);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            dateStr = simpleDateFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateStr;
    }
    /**
     * 将毫秒值转成, 2018-08-23格式的字符串
     */
    public static String getTimeStr(long millis) {
        String dateStr = "";
        try {
            Date date = new Date(millis);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            dateStr = simpleDateFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateStr;
    }

    /**
     * 将Date对象转成自定义格式的字符串
     */
    public static String getTimeStrByPattern(Date date, String pattern) {
        String dateStr = "";
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            dateStr = simpleDateFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateStr;
    }
    /**
     * 将Date对象转成, 2018-08-23格式的字符串
     */
    public static String getTimeStr(Date date) {
        String dateStr = "";
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            dateStr = simpleDateFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateStr;
    }

    /**
     * 2018-02-18, 将日期字符串, 按照自定义格式, 转成毫秒值
     */
    public static long getDate(String dateStr, String pattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        try {
            Date date = simpleDateFormat.parse(dateStr);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }
    /**
     * 2018-02-18, 将日期字符串, 按照自定义格式, 转成毫秒值
     */
    public static long getDate(String dateStr) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = simpleDateFormat.parse(dateStr);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 获取年月日星期 如:2018年7月24日 周六
     *
     * @return
     */
    public static String getYMDW(long millis) {
        StringBuilder sb = new StringBuilder();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        int year = calendar.get(Calendar.YEAR);//年
        int month = calendar.get(Calendar.MONTH);//月
        int day = calendar.get(Calendar.DAY_OF_MONTH);//日
        int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
        sb.append(year).append("年")
                .append(month + 1).append("月")
                .append(day).append("日 ")
                .append(WEEKS[weekDay - 1]);

        return sb.toString();
    }
}
