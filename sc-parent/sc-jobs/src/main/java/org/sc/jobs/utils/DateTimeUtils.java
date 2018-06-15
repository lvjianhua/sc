package org.sc.jobs.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 * 日期时间辅助工具类。
 *
 * @author cary
 * @version 1.0.0
 * @date 2014-11-29-下午5:25:23
 * @Copyright © 2014 www.longsoftware.com
 */
public class DateTimeUtils {
    // 日期默认格式
    static final String FORMAT_DATE_DEFAULT = "yyyy-MM-dd";
    // 日期格式没有分隔符
    static final String FORMAT_DATE_NO_SEPARATOR = "yyyyMMdd";
    // 默认时间格式
    static final String FORMAT_DATETIME_DEFAULT = "yyyy-MM-dd HH:mm:ss";
    // 时间格式没有秒
    static final String FORMAT_DATETIME_NO_SECHODE = "yyyy-MM-dd HH:mm";

    static final String FORMAT_YEAR_MONTH = "yyyy-MM";

    /**
     * 时间相加多少小时。
     *
     * @param day
     * @param x
     * @return
     */
    public static String addDateHour(String day, int x) {
        SimpleDateFormat format = new SimpleDateFormat(
                FORMAT_DATETIME_NO_SECHODE);// 24小时制
        Date date = null;
        try {
            date = format.parse(day);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (date == null)
            return "";
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DECEMBER, x);// 24小时制
        date = cal.getTime();
        cal = null;
        return format.format(date);

    }

    public static Date addDay(int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, day);
        return calendar.getTime();
    }

    /**
     * 将日期格式转换为字符串。
     *
     * @param date
     * @return
     * @author cary.liu
     */
    public static String date2String(Date date) {
        String dateStr = new SimpleDateFormat(FORMAT_DATE_DEFAULT).format(date);
        return dateStr;
    }

    /**
     * 将日期格式转换为字符串。
     *
     * @param date
     * @return
     * @author cary.liu
     */
    public static String date2String(Date date, String format) {
        String dateStr = new SimpleDateFormat(format).format(date);
        return dateStr;
    }

    /**
     * 将日期格式转换为字符串。
     *
     * @param date
     * @return
     * @author cary.liu
     */
    public static String datetime2String(Date date) {
        String dateStr = new SimpleDateFormat(FORMAT_DATETIME_DEFAULT).format(date);
        return dateStr;
    }

    /**
     * 字符串转换为日期
     *
     * @param dateStr
     * @return
     */
    public static Date dateString2Date(String dateStr) {
        SimpleDateFormat dateformat = new SimpleDateFormat(FORMAT_DATETIME_DEFAULT);
        Date date = null;
        try {
            if (dateStr == null || "".equals(dateStr)) {
                return null;
            }
            date = dateformat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        return date;
    }

    /**
     * 字符串转换为日期
     *
     * @param dateStr
     * @return
     */
    public static Date dateString2Date(String dateStr, String format) {
        SimpleDateFormat dateformat = new SimpleDateFormat(format);
        Date date = null;
        try {
            if (dateStr == null || "".equals(dateStr)) {
                return null;
            }
            date = dateformat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        return date;
    }

    /**
     * 返回当前日期。
     *
     * @return
     * @create 2012-8-31 上午09:18:35
     */
    public static String getCurrentDateString() {
        Date date = new Date();
        SimpleDateFormat dateformat = new SimpleDateFormat(FORMAT_DATE_DEFAULT);
        return dateformat.format(date);
    }

    /**
     * 获取当前时间(包含时分秒)。
     *
     * @return
     * @author cary.liu
     */
    public static Date getCurrentDateTime() {
        return new Date(System.currentTimeMillis());
    }

    /**
     * 获取当前时间(指定格式)。
     *
     * @param formatStr
     * @return
     * @author cary.liu
     */
    public static String getCurrentDateTimeString(String formatStr) {
        Date date = new Date();
        SimpleDateFormat formater = null;
        if (formatStr == null || "".equals("")) {
            formater = new SimpleDateFormat(FORMAT_DATETIME_DEFAULT);
        } else {
            formater = new SimpleDateFormat(formatStr);
        }
        return formater.format(date);
    }

    /**
     * 时间加上 days天数.
     *
     * @param days
     * @return
     */
    public static String getCurrentTimeAddDay(int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH)
                + days);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(calendar.getTime());
    }

    /**
     * 获取当前时间戳
     *
     * @return
     * @author cary.liu
     */
    public static Timestamp getCurrentTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    /**
     * 获取日期个时间的年月日格式。
     *
     * @param d
     * @return
     */
    public static Date getDate(Date date) {
        SimpleDateFormat dateformat = new SimpleDateFormat(FORMAT_DATE_DEFAULT);
        Date _date = null;
        if (date != null) {
            try {
                _date = dateformat.parse(dateformat.format(date));
            } catch (ParseException e) {
                e.printStackTrace();
                return null;
            }
        }
        return _date;
    }

    /**
     * 得到某个时间几天后的时间。
     *
     * @param d
     * @param day
     * @return
     */
    public static Date getDateAfter(Date d, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
        return now.getTime();
    }

    /**
     * 得到某个时间几天后的时间。
     *
     * @param d
     * @param day
     * @return
     */
    public static Date getDateAfter(String dateStr, int day) {
        SimpleDateFormat dateformat = new SimpleDateFormat(FORMAT_DATETIME_DEFAULT);
        Date date = null;

        try {
            if (dateStr != null && !dateStr.equals("")) {
                date = dateformat.parse(dateStr);
            }
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
        return now.getTime();
    }

    /**
     * 得到某个时间几天前的日期。
     *
     * @param d
     * @param day
     * @return
     */
    public static Date getDateBefore(Date d, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
        return now.getTime();
    }

    /**
     * 根据日期获取日。
     *
     * @param date
     * @return
     * @author cary.liu
     */
    public static Integer getDayNo(Date date) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DATE);
    }

    /**
     * 返回两个日期相差的天数。
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static long getDifferDayNumber(Date startDate, Date endDate) {
        long totalDate = 0;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        long timestart = calendar.getTimeInMillis();
        calendar.setTime(endDate);
        long timeend = calendar.getTimeInMillis();
        totalDate = Math.abs((timeend - timestart)) / (1000 * 60 * 60 * 24);
        return totalDate;
    }

    /**
     * 返回两个日期相差的分钟数。
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static long getDifferMinuteNumber(Date startDate, Date endDate) {
        long totalMins = 0;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        long timestart = calendar.getTimeInMillis();
        calendar.setTime(endDate);
        long timeend = calendar.getTimeInMillis();
        totalMins = Math.abs((timeend - timestart)) / (1000 * 60);
        return totalMins;
    }

    /**
     * 返回两个相差的分钟。
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static long getDifferMinuteNumber(long startDate, long endDate) {
        long totalMins = (endDate - startDate) / 1000 / 60;
        return totalMins;
    }

    /**
     * 根据日期获取月份。
     *
     * @param date
     * @return
     * @author cary.liu
     */
    public static Integer getMonthNo(Date date) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取几分钟后的时间。
     *
     * @param minutes
     * @return
     */
    public static Date getTimeAddMinute(int minutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, minutes);
        return calendar.getTime();
    }

    /**
     * 获取当前的年和月。
     *
     * @return
     * @author cary.liu
     */
    public static String getYearAndMonth() {
        Date date = new Date();
        SimpleDateFormat dateformat = new SimpleDateFormat(FORMAT_YEAR_MONTH);

        return dateformat.format(date);
    }

    /**
     * 获取上个月的年和月
     */
    public static String getLastYearAndMonth() {
        SimpleDateFormat dateformat = new SimpleDateFormat(FORMAT_YEAR_MONTH);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, -1);
        return dateformat.format(calendar.getTime());
    }

    /**
     * 根据日期获取年份。
     *
     * @param date
     * @return
     * @author cary.liu
     */
    public static Integer getYearNo(Date date) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 获取昨天日期。
     *
     * @return
     */
    public static String getYesterday() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        Date d = cal.getTime();
        SimpleDateFormat sp = new SimpleDateFormat(FORMAT_DATE_DEFAULT);
        return sp.format(d);
    }

    /**
     * 判断空(私有方法)。
     *
     * @param str
     * @return
     */
    @SuppressWarnings("unused")
    private static boolean isEmpay(String str) {
        if (str == null || "".equals("")) {
            return false;
        }
        return true;
    }

    /**
     * 字符串转换为日期。
     *
     * @param dateStr
     * @return
     */
    public static Date string2Date(String dateStr) {
        SimpleDateFormat dateformat = new SimpleDateFormat(FORMAT_DATE_DEFAULT);
        Date date = null;
        try {
            if (dateStr == null || "".equals(dateStr)) {
                return null;
            }
            date = dateformat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        return date;
    }

    /**
     * 根据月份获取该月的开始日期和截止日期
     *
     * @param Month 月份（最小值为1，最大值为12）
     * @param Year  年份 格式为 yyyy,若为空，默认为当前年份
     * @return HashMap<String, String>
     * key:StartTime 	:开始日期，values格式为	 yyyy-MM-dd  00:00:00
     * key:EndTime	 	:截止日期，values格式为	 yyyy-MM-dd  23:59:59
     */
    public static HashMap<String, String> getStartEndDayByMonth(int Month, String Year) {
        if (Month < 1 || Month > 12) {
            throw new RuntimeException("输入的月份非法！！应大于0，小于13。");
        }
        HashMap<String, String> hm = new HashMap<String, String>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (Year == null || Year.trim().length() == 0) {
            Year = PBMeth.GetNowYear();
        }
        String StartTime = null;
        String EndTime = null;
        //根据月份获取当月的第一天和最后一天
        if (Month < 10) {
            StartTime = Year + "-0" + Month + "-01 00:00:00";
        } else {
            StartTime = Year + "-" + Month + "-01 00:00:00";
        }
        Calendar c = Calendar.getInstance();
        c.set(Integer.parseInt(Year), Month, 0, 23, 59, 59);
        EndTime = sdf.format(c.getTime());
        hm.put("StartTime", StartTime);
        hm.put("EndTime", EndTime);
        return hm;
    }

    /**
     * 获取当前是一年中的第几周
     */
    public static Integer getNowWeekNum() {
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.SUNDAY);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        cal.setMinimalDaysInFirstWeek(1);
        cal.setTime(new Date());
        return cal.get(Calendar.WEEK_OF_YEAR);
    }


    /**
     * 根据年中的周数获取开始日期和截止日期
     *
     * @param WeekNum  年中的周数，有效值为1-53
     * @param Year     年份 格式为 yyyy,若为空，默认为当前年份
     * @param IsSunday 每周的第一天是否为星期日,true表示每周第一天为星期日,否则为星期一
     * @return HashMap<String, String>
     * key:StartTime 	:开始日期，values格式为	 yyyy-MM-dd  00:00:00
     * key:EndTime	 	:截止日期，values格式为	 yyyy-MM-dd  23:59:59
     */
    public static HashMap<String, String> getStartEndDayByWeek(int WeekNum, String Year, boolean IsSunday) {
        if (WeekNum < 1 || WeekNum > 53) {
            throw new RuntimeException("输入的周数非法！！应大于0，小于54。");
        }
        if (Year == null || Year.trim().length() == 0) {
            Year = PBMeth.GetNowYear();
        }
        HashMap<String, String> hm = new HashMap<String, String>();
        String StartTime = null;
        String EndTime = null;
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        c.set(Calendar.YEAR, Integer.parseInt(Year));
        // 滚动到周:
        c.set(Calendar.WEEK_OF_YEAR, WeekNum);

        if (IsSunday) {
            // 得到该周第一天:
            c.set(Calendar.DAY_OF_WEEK, 1);
            StartTime = sdf.format(c.getTime());
            if (!StartTime.split("-")[0].equals(Year)) {
                StartTime = Year + "-01-01 00:00:00";
            } else {
                StartTime = StartTime + " 00:00:00";
            }
            // 最后一天:
            c.set(Calendar.DAY_OF_WEEK, 7);
            EndTime = sdf.format(c.getTime());
            if (!EndTime.split("-")[0].equals(Year)) {
                EndTime = Year + "-12-31 23:59:59";
            } else {
                EndTime = EndTime + " 23:59:59";
            }
        } else {
            if (WeekNum == 1) {
                // 得到该周第一天:
                c.set(Calendar.DAY_OF_WEEK, 2);
                StartTime = sdf.format(c.getTime());
                if (!StartTime.split("-")[0].equals(Year)) {
                    StartTime = Year + "-01-01 00:00:00";
                } else {
                    StartTime = StartTime + " 00:00:00";
                }
                c.set(Calendar.DAY_OF_WEEK, 7);
                c.getTime();
                // 最后一天:
                c.set(Calendar.WEEK_OF_YEAR, WeekNum + 1);
                c.set(Calendar.DAY_OF_WEEK, 1);
                EndTime = sdf.format(c.getTime()) + " 23:59:59";
            } else {
                // 得到该周第一天:
                c.set(Calendar.DAY_OF_WEEK, 2);
                StartTime = sdf.format(c.getTime()) + " 00:00:00";
                // 最后一天:
                c.set(Calendar.WEEK_OF_YEAR, WeekNum + 1);
                c.set(Calendar.DAY_OF_WEEK, 1);
                EndTime = sdf.format(c.getTime());
                if (!EndTime.split("-")[0].equals(Year)) {
                    EndTime = Year + "-12-31 23:59:59";
                } else {
                    EndTime = EndTime + " 23:59:59";
                }
            }
        }

        hm.put("StartTime", StartTime);
        hm.put("EndTime", EndTime);
        return hm;
    }

    /**
     * 根据输入的月份获取该月,下月，以及上月的英文，比如1,返回December,January,February
     *
     * @param Month 输入的年月，格式：yyyy-MM
     * @return 英文的月份和年，格式[January,yyyy]
     */
    public static HashMap<String, String> getENMonthByStr(String MonthStr) {
        HashMap<Integer, String> MonthHm = new HashMap<Integer, String>();
        MonthHm.put(1, "January,");
        MonthHm.put(2, "February,");
        MonthHm.put(3, "March,");
        MonthHm.put(4, "April,");
        MonthHm.put(5, "May,");
        MonthHm.put(6, "June,");
        MonthHm.put(7, "July,");
        MonthHm.put(8, "August,");
        MonthHm.put(9, "September,");
        MonthHm.put(10, "October,");
        MonthHm.put(11, "November,");
        MonthHm.put(12, "December,");
        HashMap<String, String> MHm = new HashMap<String, String>();
        int Year = Integer.parseInt(MonthStr.split("-")[0]);
        int Month = Integer.parseInt(MonthStr.split("-")[1]);
        if (Month == 1) {//
            MHm.put("Prev", "December," + (Year - 1));
            MHm.put("Curr", "January," + Year);
            MHm.put("Next", "February," + Year);
            MHm.put("PrevCN", (Year - 1) + "-12");
        } else if (Month == 12) {
            MHm.put("Prev", "November," + Year);
            MHm.put("Curr", "December," + Year);
            MHm.put("Next", "January," + (Year + 1));
            MHm.put("PrevCN", Year + "-11");
        } else {
            MHm.put("Prev", MonthHm.get(Month - 1) + Year);
            MHm.put("Curr", MonthHm.get(Month) + Year);
            MHm.put("Next", MonthHm.get(Month + 1) + Year);
            MHm.put("PrevCN", Year + "-" + (Month - 1));
        }
        MHm.put("LastCurr", MonthHm.get(Month) + (Year - 1));
        MHm.put("LastCurrCN", (Year - 1) + "-" + Month);
        return MHm;
    }

    public static HashMap<String, String> getLastNaturalWeek() {
        int week = PBMeth.getWeekOfDate(new Date());
        Date endDate = new Date();
        if (week == 7) {
            endDate = getDateBefore(new Date(), 1);
        } else {
            endDate = getDateBefore(new Date(), week + 1);
        }
        Date beginDate = getDateBefore(endDate, 6);
        String startTime = new SimpleDateFormat("yyyy-MM-dd").format(beginDate);
        String endTime = new SimpleDateFormat("yyyy-MM-dd").format(endDate);
        HashMap<String, String> timeMap = new HashMap<String, String>();
        timeMap.put("startTime", startTime);
        timeMap.put("endTime", endTime);
        return timeMap;
    }

    /**
     * 根据输入的月份获取N月前或N月后的月份
     *
     * @param Str  格式：yyyy-MM
     * @param Diff 相差的月份 ，正值表示N月后，负值表示N月前
     * @return 所需要的月份
     * @throws ParseException
     */
    public static String getMonthDiff(String Str, int Diff) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Calendar c = Calendar.getInstance();
        c.setTime(sdf.parse(Str));
        c.add(Calendar.MONTH, Diff);
        return sdf.format(c.getTime());
    }

    public static void main(String[] args) throws ParseException {
        Timestamp time = new Timestamp(System.currentTimeMillis());
        System.out.println("timestamp:" + time);
//		System.out.println("年:" + getYearNo(new Date()));
//		System.out.println("月:" + getMonthNo(new Date()));
//		System.out.println("当前时间:" + getCurrentTimestamp());
//		System.out.println("加时间:" + addDay(1));
//	System.out.println(dateString2Date("2012-09-08 12:12:12").getTime());	
//	System.out.println(getMonthDiff("2015-06", -1));
//	System.out.println(getCurrentDateString());
//	System.out.println(getYesterday());
//	System.out.println(getCurrentTimeAddDay(-2));


        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        Calendar calendar = Calendar.getInstance();
        Date mydate = new Date();
        calendar.setTime(mydate);
        calendar.add(Calendar.MONTH, -1);
        System.out.println(sdf.format(calendar.getTime()));

    }


}
