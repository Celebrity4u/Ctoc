package com.example.kd.validate.api.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class LogUtil {
    private static boolean openSlf4jLog = true;
    public static boolean isOpenSlf4jLog() {
        return openSlf4jLog;
    }

    public static void setOpenSlf4jLog(boolean openSlf4jLog) {
        LogUtil.openSlf4jLog = openSlf4jLog;
    }
    private static final Logger logger = LoggerFactory.getLogger(LogUtil.class);
    static String[] color = {"\033[31m", "\033[92m", "\033[93m", "\033[94m", "\033[95m", "\033[96m"};
    static String endColor ="\033[0m";
    static String isLogFilter = "";
    public static void setFilter(String isLogFilter) {
        isLogFilter = isLogFilter;
    }
    public static void log(String type) {

        if(StringUtils.isEmpty(isLogFilter))
        {
            if(openSlf4jLog)
            {
                logger.info(DateUtil.formatDateTimeSSS(new Date())+":"+type);
            }

            System.out.println(DateUtil.formatDateTimeSSS(new Date())+":"+type);
        }else{
            if(isLogFilter.equals("ALL"))
            {
                if(openSlf4jLog)
                {
                    logger.info(DateUtil.formatDateTimeSSS(new Date())+":"+type);
                }

                System.out.println(DateUtil.formatDateTimeSSS(new Date())+":"+type);
            }else{
                if(isLogFilter.contains("no_"))
                {
                    String isLogFilterSub = isLogFilter.substring(3,isLogFilter.length());
                    if(!isLogFilterSub.equals(type))
                    {
                        if(openSlf4jLog)
                        {
                            logger.info(DateUtil.formatDateTimeSSS(new Date())+":"+type);
                        }

                        System.out.println(DateUtil.formatDateTimeSSS(new Date())+":"+type);
                    }
                }else
                {
                    if(type.contains(isLogFilter))
                    {
                        if(openSlf4jLog)
                        {
                            logger.info(DateUtil.formatDateTimeSSS(new Date())+":"+type);
                        }

                        System.out.println(DateUtil.formatDateTimeSSS(new Date())+":"+type);
                    }
                }
            }
        }

    }
    public static void log(String type,String text) {

        if(StringUtils.isEmpty(isLogFilter))
        {
            if(openSlf4jLog)
            {
                logger.info(DateUtil.formatDateTimeSSS(new Date())+":"+type+":" + text);
            }

            System.out.println(DateUtil.formatDateTimeSSS(new Date())+":"+type+":" + text);
        }else{
            if(isLogFilter.equals("ALL"))
            {
                if(openSlf4jLog)
                {
                    logger.info(DateUtil.formatDateTimeSSS(new Date())+":"+type+":" + text);
                }

                System.out.println(DateUtil.formatDateTimeSSS(new Date())+":"+type+":" + text);
            }else{
                if(isLogFilter.contains("no_"))
                {
                    String isLogFilterSub = isLogFilter.substring(3,isLogFilter.length());
                    if(!isLogFilterSub.equals(type))
                    {
                        if(openSlf4jLog)
                        {
                            logger.info(DateUtil.formatDateTimeSSS(new Date())+":"+type+":" + text);
                        }

                        System.out.println(DateUtil.formatDateTimeSSS(new Date())+":"+type+":" + text);
                    }
                }else
                {
                    if(type.contains(isLogFilter))
                    {
                        if(openSlf4jLog)
                        {
                            logger.info(DateUtil.formatDateTimeSSS(new Date())+":"+type+":" + text);
                        }

                        System.out.println(DateUtil.formatDateTimeSSS(new Date())+":"+type+":" + text);
                    }
                }
            }
        }

    }
    public static void logColor(String type, String text, Integer colorIndex,Boolean addTime) {
        if(colorIndex!= null)
        {
            if(colorIndex>color.length)
            {
                colorIndex = 0;
            }
        }
        String beginColor = color[colorIndex];
        if(StringUtils.isEmpty(beginColor))
        {
            beginColor ="";
        }
        String time = "";
        if(addTime)
        {
            time = DateUtil.formatDateTimeSSS(new Date())+":";
        }
        if(StringUtils.isNotEmpty(text))
        {
            text =":"+text;
        }
        String endColorFla = endColor;
        String flag = System.getenv("MODE");;
        if(flag != null && flag.equalsIgnoreCase("dev"))
        {

        }else{
            if (!SysUtils.isLinux()){
                beginColor ="";
                endColorFla ="";
            }
        }
        if(StringUtils.isEmpty(isLogFilter))
        {
            //System.out.println(beginColor+time+type+text+endColorFla);
            if(openSlf4jLog)
            {
                logger.info(time+type+text);
            }

        }else{
            if(isLogFilter.equals("ALL"))
            {
                //System.out.println(beginColor+time+type+text+endColorFla);
                if(openSlf4jLog)
                {
                    logger.info(time+type+text);
                }

            }else{
                if(isLogFilter.contains("no_"))
                {
                    String isLogFilterSub = isLogFilter.substring(3,isLogFilter.length());
                    if(!isLogFilterSub.equals(type))
                    {
                        if(openSlf4jLog)
                        {
                            logger.info(DateUtil.formatDateTimeSSS(new Date())+":"+beginColor+type+text);
                        }

                        //System.out.println(DateUtil.formatDateTimeSSS(new Date())+":"+beginColor+time+type+text+endColorFla);
                    }
                }else
                {
                    if(type.contains(isLogFilter))
                    {
                        if(openSlf4jLog)
                        {
                            logger.info(DateUtil.formatDateTimeSSS(new Date())+":"+time+type+text);
                        }

                       // System.out.println(DateUtil.formatDateTimeSSS(new Date())+":"+beginColor+time+type+text+endColorFla);
                    }
                }
            }
        }
    }
    public static void log(String type,int text) {
        if(openSlf4jLog)
        {
            logger.info(DateUtil.formatDateTimeSSS(new Date())+":"+type+":" + text);
        }
        System.out.println(DateUtil.formatDateTimeSSS(new Date())+":"+type+":" + text);
    }
}
