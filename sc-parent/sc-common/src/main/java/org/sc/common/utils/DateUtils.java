package org.sc.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

/**
 * 
* @ClassName: DateUtils
* @Description: 日期通用工具
* @author guguangming
*
 */
public class DateUtils {

	static Integer[] weekDays = { 7, 1, 2, 3, 4, 5, 6 };

    public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";
    private static final String SDF_1_REG = "^\\d{2,4}\\-\\d{1,2}\\-\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}$";
    public static final SimpleDateFormat SDF_1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final String SDF_2_REG = "^\\d{2,4}\\-\\d{1,2}\\-\\d{1,2}$";
    private static final SimpleDateFormat SDF_2 = new SimpleDateFormat(DEFAULT_DATE_PATTERN);
    private static final String SDF_3_REG = "^\\d{2,4}\\/\\d{1,2}\\/\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}$";
    private static final SimpleDateFormat SDF_3 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private static final String SDF_4_REG = "^\\d{2,4}\\/\\d{1,2}\\/\\d{1,2}$";
    private static final SimpleDateFormat SDF_4 = new SimpleDateFormat("yyyy/MM/dd");
    private static final String SDF_5_REG = "^\\d{2,4}\\/\\d{1,2}\\/\\d{1,2} \\d{1,2}:\\d{1,2}$";
    private static final SimpleDateFormat SDF_5 = new SimpleDateFormat("yyyy/MM/dd HH:mm");
    private static final String SDF_6_REG = "^\\d{2,4}\\-\\d{1,2}\\-\\d{1,2} \\d{1,2}:\\d{1,2}$";
    private static final SimpleDateFormat SDF_6 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	private static SimpleDateFormat getDateParser(String pattern) {
		return new SimpleDateFormat(pattern);
	}


	public static String longToDate(Long time, String pattern) {
		if (time != null)
			return getDateParser(pattern).format(new Date(time));
		else
			return "";
	}

	public static String longToDate(Long time) {
		return getDateParser("yyyy-MM-dd").format(new Date(time));
	}
	public static String getStringYMD(Long time) {
		return getDateParser("yyyy/MM/dd").format(new Date(time));
	}

	public static String longToDate2(Long time) {
		return getDateParser("MM-dd HH:mm").format(new Date(time));
	}

	public static String longToDateAll(Long time) {
		return getDateParser("yyyy-MM-dd HH:mm:ss").format(new Date(time));
	}

	public static String longToDateAllNew(Long time) {
		return getDateParser("yyyyMMddHHmmss").format(new Date(time));
	}

	public static String getCurrentDateString() {
		return getDateParser("yyyy-MM-dd HH:mm:ss").format(
				new Date(System.currentTimeMillis()));
	}

	/***
	 * String型日期转为long型
	 * 
	 * @param source
	 *            String型日期
	 * @return long 日期
	 * @throws ParseException
	 */
	public static long dateToLong(String source) {
		TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
		try {
			return getDateParser("yyyy/MM/dd").parse(source).getTime();
		} catch (ParseException e) {
			try {
				return getDateParser("yyyy-MM-dd").parse(source).getTime();
			} catch (ParseException e1) {
				return -1;
			}
		}

	}
	
	public static long dateToLong(String source, String format) {
		TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
		try {
			return getDateParser(format).parse(source).getTime();
		} catch (ParseException e) {
				return -1;
		}

	}

	public static long dateAddOneDayAndToLong(String source) {
		try {
			Date date = getDateParser("yyyy/MM/dd").parse(source);
			Calendar cd = Calendar.getInstance();
			cd.setTime(date);
			cd.add(Calendar.DAY_OF_MONTH, 1);
			return cd.getTime().getTime();
		} catch (ParseException e) {
			try {
				Date date = getDateParser("yyyy-MM-dd").parse(source);
				Calendar cd = Calendar.getInstance();
				cd.setTime(date);
				cd.add(Calendar.DAY_OF_MONTH, 1);
				return cd.getTime().getTime();
			} catch (ParseException e1) {
				return -1;
			}
		}
	}

	public static long nextDate(String source) {
		try {
			return getDateParser("yyyy/MM/dd").parse(source).getTime() + 24
					* 60 * 60 * 1000;
		} catch (ParseException e) {
			try {
				return getDateParser("yyyy-MM-dd").parse(source).getTime() + 24
						* 60 * 60 * 1000;
			} catch (ParseException e1) {
				return -1;
			}
		}

	}

	public static String longToFrontDate(Long time) {
		return getDateParser("yyyy-MM-dd").format(
				new Date(time - 24 * 60 * 60 * 1000));
	}

	/***
	 * String型日期转为long型
	 * 
	 * @param date
	 *            String型日期
	 * @return long 日期
	 * @throws ParseException
	 */
	public static long dateAllToLong(String source) {
		try {
			return getDateParser("yyyy/MM/dd HH:mm:ss").parse(source).getTime();
		} catch (ParseException e) {
			try {
				return getDateParser("yyyy-MM-dd HH:mm:ss").parse(source)
						.getTime();
			} catch (ParseException e1) {
				return -1;
			}
		}

	}

	/**
	 * 生成流水号
	 * 
	 * @return 类似"20090507095515693"的字符串(15位)
	 */
	public static long genSerialNumber() {
		return Long
				.valueOf(getDateParser("yyyyMMddHHmmsss").format(new Date()));
	}

	/**
	 * 生成流水号
	 * 
	 * @return 类似"2009050709551569"的字符串(14位)
	 */
	public static long gen14SerialNumber() {
		return Long.valueOf(getDateParser("yyyyMMddHHmmss").format(new Date()));
	}

	public static long genYMD() {
		return Long.valueOf(getDateParser("yyyyMMdd").format(new Date()));
	}

	/**
	 * 获得日期
	 */
	public static Date getBirthdayDate(String birthday) {
		return new Date(dateToLong(birthday));
	}
	
	public static Date getDate(String data,String format) {
			return new Date( dateToLong(data,format));
	}

	/**
	 * 获取日期字符
	 * 
	 * @param date
	 *            日期
	 * @param format
	 *            格式 如:yyy-MM-dd
	 * @return
	 */
	public static String getDate(Date date, String format) {
		return getDateParser(format).format(date);
	}

	public static Date toDate(String dataString, String format)
			throws ParseException {
		return getDateParser(format).parse(dataString);
	}

	/**
	 * 获得当前日期
	 * 
	 * @return
	 */
	public static long getNowDate() {
		return DateUtils.dateToLong(getDateParser("yyyy-MM-dd").format(
				new Date()));
	}

	/**
	 * 获得当前日期
	 * 
	 * @return
	 */
	public static long getNowDate(Date curDate) {
		return DateUtils
				.dateToLong(getDateParser("yyyy-MM-dd").format(curDate));
	}

	/**
	 * 获取当前时间加上任意天数后的日期
	 * 
	 * @param dayNum
	 *            天数
	 * @return
	 */
	public static String getNewDateByAdd(int dayNum) {
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, dayNum);
		return (new SimpleDateFormat("yyyy-MM-dd")).format(cal.getTime());
	}

	public static String getNewDateByAdd(int dayNum, String format) {
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, dayNum);
		return (new SimpleDateFormat(format)).format(cal.getTime());
	}

	public static Date getDateByAdd(Date date, int num, int timeType) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(timeType, num);
		return cal.getTime();
	}

	/**
	 * 获取指定时间加上任意小时后的日期
	 * 
	 * @param dayNum
	 *            天数
	 * @return
	 */
	public static String getNewDateByAddHour(int hour) {
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.HOUR, hour);
		return (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(cal
				.getTime());
	}

	/**
	 * @获取当前月份最大天数
	 * @author lidong
	 * @return 当前月份最大天数
	 * @2011-12-26
	 */
	public static int lastDayOfMonth() {
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int value = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		return value;
	}

	/**
	 * 得到有效期天数
	 * 
	 * @param days
	 * @return
	 */
	public static Date addDays(long days) {
		Date date = new Date();
		return new Date(date.getTime() + days * 24 * 60 * 60 * 1000);
	}

	public static Date addDays(Date date, long days) {
		return new Date(date.getTime() + days * 24 * 60 * 60 * 1000);
	}

	/**
	 * 获取某年某月的最后一天
	 * 
	 * @param year
	 *            年
	 * @param month
	 *            月
	 * @return 最后一天
	 */
	public static int getLastDayOfMonth(int year, int month) {
		if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8
				|| month == 10 || month == 12) {
			return 31;
		}
		if (month == 4 || month == 6 || month == 9 || month == 11) {
			return 30;
		}
		if (month == 2) {
			if (isLeapYear(year)) {
				return 29;
			} else {
				return 28;
			}
		}
		return 0;
	}

	/**
	 * 是否闰年
	 * 
	 * @param year
	 *            年
	 * @return
	 */
	public static boolean isLeapYear(int year) {
		return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
	}

	/**
	 * 获得下个月最后一天的日期
	 * 
	 * @return
	 */
	public String getNextMonthEnd(int da) {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.MONTH, da);// 加一个月
		lastDate.set(Calendar.DATE, 1);// 把日期设置为当月第一天
		lastDate.roll(Calendar.DATE, -1);// 日期回滚一天，也就是本月最后一天
		str = sdf.format(lastDate.getTime());
		return str;
	}

	/**
	 * 月份相加
	 * 
	 * @return
	 */
	public String getNextMonth(int da) {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.MONTH, da);// 加一个月
		str = sdf.format(lastDate.getTime());
		return str;
	}

	/**
	 * @获取当前年份及往前5年的年份集合
	 * @author lidong
	 * @return 当前年份及往前5年的年份集合
	 * @2012-01-11
	 */
	public static List<Object> getYearList() {
		List<Object> yearList = new ArrayList<Object>();
		Calendar cal = Calendar.getInstance();
		int currentYear = cal.get(Calendar.YEAR);
		for (int i = 0; i < 5; i++) {
			Map<String, Object> yearMap = new HashMap<String, Object>();
			yearMap.put("id", currentYear);
			yearMap.put("text", currentYear + "年");
			if (i == 0) {
				yearMap.put("selected", true);
			}
			yearList.add(yearMap);
			currentYear = currentYear - 1;
		}
		return yearList;
	}

	/**
	 * 获取当前星期
	 * 
	 * @return 星期几
	 */
	public static Integer getCurrentWeek() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0)
			w = 0;
		return weekDays[w];
	}

	/**
	 * 当月第一天
	 * 
	 * @return
	 */
	public static String getFirstDayOfMonth(String formart) {
		Calendar cal = Calendar.getInstance();

		SimpleDateFormat datef = new SimpleDateFormat(formart);
		cal.set(GregorianCalendar.DAY_OF_MONTH, 1);
		Date beginTime = cal.getTime();

		return datef.format(beginTime);
	}

	public static long nextDate(String formart, String source)
			throws ParseException {
		return getDateParser(formart).parse(source).getTime() + 24 * 60 * 60
				* 1000;
	}

	public static String genHMS() {
		return getDateParser("HH:mm:ss").format(new Date());
	}

	public static String longToDate3(long time) {
		return getDateParser("MM月dd日").format(new Date(time));
	}

	/**
	 * 获取当前星期
	 * 
	 * @return 周几
	 */
	static String[] weekDayStr = { "周日", "周一", "周二", "周三", "周四", "周五", "周六" };

	public static String getCurrentWeek(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0) {
			w = 0;
		}
		return weekDayStr[w];
	}

	/**
	 * 将特定格式的字符串转成date
	 * 
	 * @param date
	 *            20131021 转成2013-19-21
	 * @throws ParseException
	 */
	public static String stringToDate(String date) throws ParseException {
		if (date.length() == 8) {
			date = date.substring(0, 4) + "-" + date.substring(4, 6) + "-"
					+ date.substring(6, 8);
		}
		return date;
	}
	
	/**
	 * 将特定格式的字符串转成date
	 * 
	 * @param date
	 *           2013-10-21 转成10-21
	 * @throws ParseException
	 */
	public static String subToDate(String date)  {
		if (date!=null&&date.length() == 10) {
			date = date.substring(5, 10);
		}
		return date;
	}

	/**  
     * 计算两个日期之间相差的天数  
     * @param smdate 较小的时间 
     * @param bdate  较大的时间 
     * @return 相差天数 
     * @throws ParseException  
     */
	public static int daysBetween(Date smdate,Date bdate) throws ParseException   {    
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
        smdate=sdf.parse(sdf.format(smdate));  
        bdate=sdf.parse(sdf.format(bdate));  
        Calendar cal = Calendar.getInstance();    
        cal.setTime(smdate);    
        long time1 = cal.getTimeInMillis();                 
        cal.setTime(bdate);    
        long time2 = cal.getTimeInMillis();         
        long between_days=(time2-time1)/(1000*3600*24);  
       return Integer.parseInt(String.valueOf(between_days));           
    }

	public static int monthsBetween(Date smdate,Date bdate) throws ParseException   {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		Calendar bef = Calendar.getInstance();
		Calendar aft = Calendar.getInstance();
		bef.setTime(sdf.parse(sdf.format(smdate)));
		aft.setTime(sdf.parse(sdf.format(bdate)));
		int result = aft.get(Calendar.MONTH) - bef.get(Calendar.MONTH);
		int month = (aft.get(Calendar.YEAR) - bef.get(Calendar.YEAR)) * 12;
		return Math.abs(month + result);
	}

    /**
     * <p>
     * 将日期字符串解析成日期对象，支持以下格式
     * <li>yyyy-MM-dd HH:mm:ss
     * <li>yyyy-MM-dd
     * <li>yyyy/MM/dd HH:mm:ss
     * <li>yyyy/MM/dd
     * <li>yyyy-MM-dd HH:mm
     * <li>yyyy/MM/dd HH:mm
     * </p>
     *
     * @param str
     * @return
     */
    public static Date parse(String str) {
        if (str == null) {
            return null;
        }
        if (str.indexOf(".") > 0) {
            str = str.substring(0, str.indexOf(".")); // 截掉微秒
        }
        List<String> sdfRegArray = Arrays.asList(SDF_1_REG, SDF_2_REG, SDF_3_REG, SDF_4_REG, SDF_5_REG, SDF_6_REG);
        List<SimpleDateFormat> sdfArray = Arrays.asList(SDF_1, SDF_2, SDF_3, SDF_4, SDF_5, SDF_6);
        Date date = null;
        try {
            for (int index = 0; index < sdfRegArray.size(); index++) {
                if (Pattern.compile(sdfRegArray.get(index)).matcher(str).matches()) {
                    return sdfArray.get(index).parse(str);
                }
            }
        } catch (ParseException e) {
            throw new RuntimeException("非法日期字符串，解析失败：" + str, e);
        }
        return date;
    }
    
    /**
     * 时间对比
     * @param date1
     * @param date2
     * @return
     */
    public static boolean compareDate(Date date1,Date date2) {
    
        if (date1.getTime() > date2.getTime()) {
    	   return true;
        } 
    	   return false;
    }
    
    /**
     * 拼装搜索页面的日期检索条件
     * @return
     */
    public static List<String> getWeekDays(){
    	List<String> list = new ArrayList<String>();
    	for (int i = 0; i < 7; i++) {
    		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar c = Calendar.getInstance();
            c.add(Calendar.DAY_OF_MONTH, i);
            String format = sf.format(c.getTime());
			list.add(format+"||"+subToDate(format));
		}
    	return list;
    } 
    
    /**
     * 参数传递0  表示当前日期 
     * @difDay  相差的天数
     * @return
     */
    public static String getDay(Integer difDay){
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, difDay);

       return getDateParser("yyyy-MM-dd").format(c.getTime());
    } 
    
    /**
     * 获取当前日期
     * @return
     */
    public static String nowDate() {
		return getDateParser("yyyy-MM-dd").format(new Date());
	}
    
    /**
     * 获取当前日期
     * @return
     */
    public static String getCurrDate() {
		return getDateParser("yyyy/MM/dd").format(new Date());
	}

	public static long getDistanceMin(Date one, Date two) {
		long min = 0;
		try {
			long time1 = one.getTime();
			long time2 = two.getTime();
			long diff;
			if (time1 < time2) {
				diff = time2 - time1;
			} else {
				diff = time1 - time2;
			}
			min = diff / 1000 / 60;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return min;
	}
    
	/**
	* 字符串转换成日期
	* @param str
	* @return date
	*/
	public static Date strToDate(String str) {
	  
	   SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	   Date date = null;
	   try {
	    date = format.parse(str);
	   } catch (ParseException e) {
	    e.printStackTrace();
	   }
	   return date;
	}
	
	/**
	* 字符串转换成日期
	* @param str
	* @return date
	*/
	public static Integer getHour(String str) {
	  
	   SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	   Date date = null;
	   try {
	    date = format.parse(str);
	   } catch (ParseException e) {
	    e.printStackTrace();
	   }
	   return date.getHours();
	}
	
	/**
	* 不全日期
	* @param str
	* @return 
	*/
	public static String arriveTime(String arriveTime) {
	  
		String[] time = arriveTime.split(":");
		String hour = time[0].length()==1?"0"+time[0]:time[0];
		String minu = time[1].length()==1?"0"+time[1]:time[1];
		return  hour+":"+minu;
		
	}
	
   /**
    * 给时间加上几个小时
    * @param day 当前时间 格式：yyyy-MM-dd HH:mm:ss
    * @param hour 需要加的时间
    * @return
    */
   public static String addDateMinut(String day, int hour){   
       SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
       cal.add(Calendar.HOUR, hour);// 24小时制   
       date = cal.getTime();   
       cal = null;   
       return format.format(date);   

   }
	
    public static void main(String[] args) {
		System.out.println(getWeekDays());
	}
    
    
}

