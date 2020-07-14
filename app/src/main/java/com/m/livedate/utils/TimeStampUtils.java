package com.m.livedate.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;


public class TimeStampUtils {

    /**
     * （int）时间戳转Date
     *
     * @param timestamp
     * @return
     */
    public static Date stampForDate(Integer timestamp) {
        return new Date((long) timestamp * 1000);
    }

    /**
     * （long）时间戳转Date
     *
     * @param timestamp
     * @return
     */
    public static Date longStampForDate(long timestamp) {
        return new Date(timestamp);
    }

    /**
     * date转String
     *
     * @param date
     * @return
     */
    public static String dateForString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//时间的格式
        return sdf.format(date);
    }

    /**
     * date转String
     *
     * @param date
     * @return
     */
    public static String longForString(long date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//时间的格式
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+08"));
        return sdf.format(date);
    }

    /**
     * String转Date
     *
     * @param time
     * @return
     */
    public static Date stringForDate(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//时间的格式
        Date date = null;
        try {
            date = sdf.parse(time);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date;
    }

    /**
     * Date转时间戳
     *
     * @param data
     * @return
     */
    public static Integer dateForStamp(Date data) {
        return (int) (data.getTime() / 1000);
    }

    /**
     * 格式化时间
     *
     * @param timeStamp
     * @return
     */
    public static String YYYYMMDDHHmmss(long timeStamp) {
        Date date = new Date(timeStamp);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//时间的格式
        return sdf.format(date);
    }

    /**
     * 格式化时间
     *
     * @param timeStamp
     * @return
     */
    public static String YYYYMMDD(long timeStamp) {
        Date date = new Date(timeStamp);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//时间的格式
        return sdf.format(date);
    }
}
