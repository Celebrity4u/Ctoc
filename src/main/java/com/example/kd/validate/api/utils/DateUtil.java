/**
 * Copyright 2014-现在 国防科技大学
 */
package com.example.kd.validate.api.utils;


import com.example.kd.validate.api.utils.StringUtils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期处理工具类
 *
 * @author zl
 */
public final class DateUtil {

    public static final String YYYY_MM = "yyyy-MM";

    public static final String YYYY_MM_DD = "yyyy-MM-dd";

    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String YYYY_MM_DD_HH_MM_SS_SSS = "yyyy-MM-dd HH:mm:ss.SSS";

    private static final String[] parsePatterns = {
            "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
            "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM",
            "yyyy-MM-dd HH:mm:ss.SSS","yyyy-MM-dd HH:mm:ss.SSSSSS"};

    /**
     * 此类不需要实例化
     */
    private DateUtil() {
    }

    public static final String parseDateToStr(final String format, final Date date) {
        return new SimpleDateFormat(format).format(date);
    }



    /**
     * 获取当前Date型日期
     *
     * @return Date() 当前日期
     */
    public static Date getNowDate(Long currentTimeMillis) {
        if(currentTimeMillis == 0l)
        {
            return new Date();
        }
        return new Date(currentTimeMillis);
    }

    /**
     * 时间转换：长整型转换为日期字符型
     *
     * @param format 格式化类型：yyyy-MM-dd
     * @param time   13位有效数字：1380123456789
     * @return 格式化结果 (yyyy-MM-dd)
     */
    public static String formatToString(String format, long time) {
        if (time == 0) {
            return "";
        }
        return new SimpleDateFormat(format).format(new Date(time));
    }

    /**
     * 时间转换：日期字符型转换为长整型
     *
     * @param format 格式化类型：yyyy-MM-dd
     * @return 13位有效数字 (1380123456789)
     */
    public static long formatToLong(String format) {
        SimpleDateFormat f = new SimpleDateFormat(format);
        return Timestamp.valueOf(f.format(new Date())).getTime();
    }
    /**
     * 时间转换：日期字符型转换为长整型
     * @param date
     * @return
     */
    public static long stringToLong(String date) {
        try {
            Date timeNew = formatToDate(date);
            Long time = timeNew.getTime() / 1000;
            return time;
        }catch (Exception e)
        {
            return 0;
        }

    }
    /**
     * 获取当前年份
     *
     * @return yyyy (2016)
     */
    public static int getYear() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.YEAR);
    }

    /**
     * 获取当前月份
     *
     * @return MM (06)
     */
    public static String getMonth() {
        Calendar cal = Calendar.getInstance();
        return new DecimalFormat("00").format(cal.get(Calendar.MONTH));
    }



    /**
     * 功能描述：格式化日期
     *
     * @param dateStr String 字符型日期
     * @param format  String 格式
     * @return Date 日期
     */
    public static Date parseDate(String dateStr, String format) {
        try {
            DateFormat dateFormat = new SimpleDateFormat(format);
            String dt = dateStr;
            if ((!dt.equals("")) && (dt.length() < format.length())) {
                dt += format.substring(dt.length()).replaceAll("[YyMmDdHhSs]", "0");
            }
            Date date = dateFormat.parse(dt);
            return date;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 功能描述：格式化输出日期
     *
     * @param date   Date 日期
     * @param format String 格式
     * @return 返回字符型日期
     */
    public static String format(Date date, String format) {
        String result = "";
        try {
            if (date != null) {
                DateFormat dateFormat = new SimpleDateFormat(format);
                result = dateFormat.format(date);
            }
        } catch (Exception e) {
        }
        return result;
    }

    /**
     * 功能描述：
     *
     * @param date Date 日期
     * @return
     */
    public static String format(Date date) {
        return format(date, "yyyy-MM-dd");
    }

    /**
     * 功能描述：返回年份
     *
     * @param date Date 日期
     * @return 返回年份
     */
    public static int getYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 功能描述：返回月份
     *
     * @param date Date 日期
     * @return 返回月份
     */
    public static int getMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 功能描述：返回日份
     *
     * @param date Date 日期
     * @return 返回日份
     */
    public static int getDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 计算两个时间相差毫秒数
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static long getMillis(Date startDate, Date endDate) {
        return endDate.getTime() - startDate.getTime();
    }

    /**
     * 功能描述：返回小时
     *
     * @param date 日期
     * @return 返回小时
     */
    public static int getHour(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 功能描述：返回分钟
     *
     * @param date 日期
     * @return 返回分钟
     */
    public static int getMinute(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MINUTE);
    }

    /**
     * 返回秒钟
     *
     * @param date Date 日期
     * @return 返回秒钟
     */
    public static int getSecond(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.SECOND);
    }

    /**
     * 功能描述：返回毫秒
     *
     * @param date 日期
     * @return 返回毫秒
     */
    public static long getMillis(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getTimeInMillis();
    }

    /**
     * 功能描述：返回字符型日期
     *
     * @param date 日期
     * @return 返回字符型日期 yyyy-MM-dd 格式
     */
    public static String getDate(Date date) {
        return format(date, "yyyy-MM-dd");
    }

    /**
     * 功能描述：返回字符型时间
     *
     * @param date Date 日期
     * @return 返回字符型时间 HH:mm:ss 格式
     */
    public static String getTime(Date date) {
        return format(date, "HH:mm:ss");
    }

    /**
     * 功能描述：返回字符型日期时间
     *
     * @param date Date 日期
     * @return 返回字符型日期时间 yyyy-MM-dd HH:mm:ss 格式
     */
    public static String getDateTime(Date date) {
        return format(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 功能描述：日期相加
     *
     * @param date Date 日期
     * @param day  int 天数
     * @return 返回相加后的日期
     */
    public static Date addDate(Date date, int day) {
        Calendar calendar = Calendar.getInstance();
        long millis = getMillis(date) + ((long) day) * 24 * 3600 * 1000;
        calendar.setTimeInMillis(millis);
        return calendar.getTime();
    }

    /**
     * 当前日期加上年
     *
     * @param date
     * @param year
     * @return
     */
    public static Date addYear(Date date, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, year);
        return calendar.getTime();
    }

    /**
     * 功能描述：日期相加
     *
     * @param date yyyy-MM-dd
     * @param day  int 天数
     * @return 返回相加后的日期
     * @throws ParseException
     */
    public static String add(String date, int day) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        long d = df.parse(date).getTime();
        long millis = d + ((long) day) * 24 * 3600 * 1000;
        return df.format(new Date(millis));
    }

    /**
     * 功能描述：日期相减
     *
     * @param date  Date 日期
     * @param date1 Date 日期
     * @return 返回相减后的日期
     */
    public static int diffDate(Date date, Date date1) {
        return (int) ((getMillis(date) - getMillis(date1)) / (24 * 3600 * 1000));
    }

    /**
     * 功能描述：日期相减
     *
     * @param date  Date 日期
     * @param date1 Date 日期
     * @return 返回相减后的日期
     */
    public static Double diffHours(Date date, Date date1) {
        Long diffMillis = getMillis(date) - getMillis(date1);
        return (Double) ((Double.valueOf(diffMillis +"")) / (3600 * 1000));
    }




    public static Date formatToDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
        if (StringUtils.isNotEmpty(date)) {
            //		logger.info(date + " 转日期开始...");
            synchronized (sdf) {
                try {
                    return sdf.parse(date);
                }catch (Exception e)
                {
                    return null;
                }

            }
        } else {
            return null;
        }

    }

    /**
     * 功能描述：常用的格式化日期
     *
     * @param date Date 日期
     * @return String 日期字符串 yyyy-MM-dd格式
     */
    public static String formatDate(Date date) {
        return formatDateByFormat(date, YYYY_MM_DD);
    }

    /**
     * 功能描述：常用的格式化日期
     *
     * @param date Date 日期
     * @return String 日期字符串 yyyy-MM-dd格式
     */
    public static String formatMonthDate(Date date) {
        return formatDateByFormat(date, YYYY_MM);
    }
    /**
     * 功能描述：常用的格式化日期
     *
     * @param date Date 日期
     * @return String 日期字符串 yyyy-MM-dd格式
     */
    public static String formatDateTime(Date date) {
        return formatDateByFormat(date, YYYY_MM_DD_HH_MM_SS);
    }
    /**
     * 功能描述：常用的格式化日期
     *
     * @param date Date 日期
     * @return String 日期字符串 yyyy-MM-dd格式
     */
    public static String formatDateTimeSSS(Date date) {
        return formatDateByFormat(date, YYYY_MM_DD_HH_MM_SS_SSS);
    }
    /**
     * 功能描述：常用的格式化日期
     *
     * @param date Date 日期
     * @return String 日期字符串 yyyy-MM-dd格式
     */
    public static String formatDateTimeNoSpace(Date date) {
        return formatDateByFormat(date, YYYY_MM_DD_HH_MM_SS).replaceAll(" ","_").replaceAll(":","_");
    }
    /**
     * 以指定的格式来格式化日期
     *
     * @param date   Date 日期
     * @param format String 格式
     * @return String 日期字符串
     */
    public static String formatDateByFormat(Date date, String format) {
        String result = "";
        if (date != null) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(format);
                result = sdf.format(date);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 计算日期之间的天数
     *
     * @param beginDate 开始日期 yyy-MM-dd
     * @param endDate   结束日期 yyy-MM-dd
     * @return
     * @throws ParseException
     */
    public static int getDay(String beginDate, String endDate) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat(YYYY_MM_DD);
        long to = df.parse(endDate).getTime();
        long from = df.parse(beginDate).getTime();
        return (int) ((to - from) / (1000 * 60 * 60 * 24));
    }

    /**
     * 计算日期之间的年数
     *
     * @param startYear 开始日期 yyy-MM-dd
     * @param endYear   结束日期 yyy-MM-dd
     * @return
     */
    public static int yearDateDiff(String startYear, String endYear) {
        SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD);
        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        try {
            startDate.setTime(sdf.parse(startYear));
            endDate.setTime(sdf.parse(endYear));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return (endDate.get(Calendar.YEAR) - startDate.get(Calendar.YEAR));
    }


    public static double[] parseDate6(Date date) {
        String dateStr = format(date, YYYY_MM_DD_HH_MM_SS_SSS);
        return parseDate6(dateStr);
    }

    /**
     * 时间字符串转double数组
     *
     * @param str
     * @return
     */
    public static double[] parseDate6(String str) {
        double dateRet[] = new double[]{1970, 01, 01, 0, 0, 0};
        try {
            String dateStr[] = str.split(" ");
            String date[] = dateStr[0].split("-");
            String time[] = dateStr[1].split(":");
            double year = Double.parseDouble(date[0]);
            double mon = Double.parseDouble(date[1]);
            double day = Double.parseDouble(date[2]);
            double hour = Double.parseDouble(time[0]);
            double min = Double.parseDouble(time[1]);
            double sec = Double.parseDouble(time[2]);
            dateRet[0] = year;
            dateRet[1] = mon;
            dateRet[2] = day;
            dateRet[3] = hour;
            dateRet[4] = min;
            dateRet[5] = sec;
        } catch (Exception e) {
        }
        return dateRet;
    }

    /**
     *
     * @param year
     * @param month
     * @param day
     * @param hour
     * @param min
     * @param sec
     * @return
     */
    public static long getMillis(int year, int month, int day, int hour, int min, double sec) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(year, month, day, hour, min, 0);
        return calendar.getTimeInMillis() + new Double(sec * 1000).longValue();
    }
    /**
     * 转带TZ时间
     *
     * @param str
     * @return
     */
    public static String strToTZstr(String str) {
        try {
            SimpleDateFormat simpleDateFormat1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            Date date=simpleDateFormat1.parse(str);
            simpleDateFormat.format(date);
            return simpleDateFormat.format(date);
        } catch (ParseException e) {
            throw new RuntimeException("时间转换double[6]出错");
        }

    }


    private static DecimalFormat df = new DecimalFormat("00.000");
    public static String dateArrayToString(double[] array) {
        //2021-04-07 12:00:00
        StringBuilder utc = new StringBuilder();
        String year, month, day, hour, min, sec;
        year = String.valueOf((int) array[0]);
        month = array[1] < 10 ? "0" + (int) array[1] : String.valueOf((int) array[1]);
        day = array[2] < 10 ? "0" + (int) array[2] : String.valueOf((int) array[2]);
        hour = array[3] < 10 ? "0" + (int) array[3] : String.valueOf((int) array[3]);
        min = array[4] < 10 ? "0" + (int) array[4] : String.valueOf((int) array[4]);
        //sec = df.format(array[5]);
        sec = (array[5] < 10 ? "0" + (int) array[5] : String.valueOf((int) array[5])) +".000";
        if(sec.equals("60.000"))
        {
            sec = "59.999";
        }
        utc.append(year).append('-').append(month).append('-').append(day).append(' ').
                append(hour).append(':').append(min).append(':').append(sec);
        return utc.toString();
    }


    public static long getHourBetween(Date start,Date end)
    {
        double dayM = 1000 *24 * 60* 60;
        double hourM = 1000 * 60 * 60;
        double differ = end.getTime() - start.getTime();
        Double hour = (Double)(differ/ hourM);
        return Math.round(hour);
    }
    public static int dateToJulian(Date date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR)- 1900;
        int datOfYear = calendar.get(Calendar.DAY_OF_YEAR);
        return year * 1000 + datOfYear;
    }
    public static Date julianToDate(int date)
    {
        int year = (date / 1000) +1900;
        int dayOfYear = date % 1000;
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.DAY_OF_YEAR,dayOfYear);
        return calendar.getTime();
    }
}
