package com.cengel.starbucks.util;

import com.cengel.starbucks.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

/**
 * 日期工具类
 *
 * @author crazy_cabbage
 *
 */
public final class DateUtil {

	/**
	 * 通用格式标识 yyyy-MM-dd HH:mm
	 */
	public static final String	yyyy_MM_dd_HH_mm	= "yyyy-MM-dd HH:mm";
	/**
	 * 通用格式标识 yyyy-MM-dd HH:mm:ss
	 */
	public static final String	yyyy_MM_dd_HH_mm_ss	= "yyyy-MM-dd HH:mm:ss";
	/**
	 * 通用格式标识 yyyy-MM-dd HH
	 */
	public static final String	yyyy_MM_dd_HH		= "yyyy-MM-dd HH";
	/**
	 * 通用格式标识 yyyy/MM/dd
	 */
	public static final String	yyyy__MM__dd		= "yyyy/MM/dd";
	/**
	 * 通用格式标识 yyyy-MM-dd
	 */
	public static final String	yyyy_MM_dd			= "yyyy-MM-dd";
	/**
	 * 通用格式标识yyyyMMdd
	 */
	public static final String	yyyyMMdd			= "yyyyMMdd";

	public static final String	yyMMdd				= "yyMMdd";

	/**
	 * 通用格式标识 yyyy-MM-dd
	 */
	public static final String	yyyy_MM				= "yyyy-MM";
	public static final String	YEAR_MONTH_SPERATE	= "-";

	/**
	 * 通用格式标识 HH:mm
	 */
	public static final String	HH_mm				= "HH:mm";
	/**
	 * 通用格式标识 HHmm
	 */
	public static final String	HHmm				= "HHmm";
	/**
	 * 通用格式标识ww
	 */
	public static final String	ww					= "ww";
	/**
	 * 通用格式标识w
	 */
	public static final String	w					= "w";
	/**
	 * 通用格式标识yyyy
	 */
	public static final String	yyyy				= "yyyy";
	/**
	 * 通用格式标识 yyyy年MM月
	 */
	public static final String	yyyy__MM__			= "yyyy年MM月";
	/**
	 * 通用格式标识yyyy年ww周
	 */
	public static final String	yyyy__ww__			= "yyyy年ww周";

	public static Timestamp getDateTime() {
		return new Timestamp(new Date().getTime());
	}

	public static Date tomorrow(){
		Date today = new Date();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(today);
		calendar.add(calendar.DATE,1);//把日期往前减少一天，若想把日期向后推一天则将负数改为正数
		today = calendar.getTime();
		return today;
	}
	/**
	 * 把日期格式化字符串
	 *
	 * @param date
	 *            要格式化的日期
	 * @param pattern
	 *            日期的格式
	 * @return 格式化后的字符串
	 */
	public static String format(Date date, String pattern) {
		if (date == null) {
			return StringUtils.EMPTY;
		}
		if (StringUtils.isBlank(pattern)) {
			pattern = yyyy_MM_dd_HH_mm;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}

	/**
	 * 把日期字符串转换成日期对象
	 *
	 * @param dateStr
	 *            日期字符串
	 * @param pattern
	 *            格式串
	 * @return 转换后的日期
	 */
	public static Date parse(String dateStr, String pattern) {
		if (StringUtils.isBlank(dateStr)) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		try {
			return sdf.parse(dateStr);
		} catch (ParseException e) {
			throw new IllegalStateException("日期转换错误");
		}
	}

	/**
	 * 去掉时分秒，毫秒的写法
	 *
	 * @param date
	 *            日期
	 * @return 去掉时分秒，毫秒的方法
	 */
	public static Date shortDate(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}

	public static List<Date> getBetweenDates(Date begin, Date end) {
		List<Date> result = new ArrayList<Date>();
		Calendar tempStart = Calendar.getInstance();
		tempStart.setTime(begin);
		/*
		 * Calendar tempEnd = Calendar.getInstance(); tempStart.add(Calendar.DAY_OF_YEAR, 1); tempEnd.setTime(end); while (tempStart.before(tempEnd)) { result.add(tempStart.getTime()); tempStart.add(Calendar.DAY_OF_YEAR, 1); }
		 */
		while (begin.getTime() <= end.getTime()) {
			result.add(tempStart.getTime());
			tempStart.add(Calendar.DAY_OF_YEAR, 1);
			begin = tempStart.getTime();
		}
		return result;
	}

	/**
	 * 计算出两个日期间所有的 [1..7]一星期中周几 的日期
	 *
	 * @param startTime
	 *            开始日期
	 * @param endTime
	 *            结束日期
	 * @param days
	 *            星期几的集合
	 * @return
	 *
	 * 		日期从 周日开始算的 周日 1， 周一2， 周二3， 周三4， 周四5， 周五6， 周六7
	 *
	 */
	public static List<Date> getComposeDates(Date startTime, Date endTime, List<Integer> days) {
		List<Date> list = new ArrayList<Date>();
		try {
			int i = 0;
			Date initDate = startTime;
			while (initDate.getTime() <= endTime.getTime()) {
				DateFormat df = new SimpleDateFormat(yyyy_MM_dd);
				Calendar c = Calendar.getInstance();
				c.setTime(df.parse(df.format(initDate)));
				Integer day = c.get(Calendar.DAY_OF_WEEK);
				if (days.contains(day)) {
					list.add(initDate);
				}
				initDate = getRelateDay(startTime, ++i);
			}
		} catch (ParseException e) {
			e.printStackTrace();
			throw new IllegalStateException("计算时间段内周几的日期 错误");
		}
		return list;
	}

	/**
	 * 获得根据旧的日期获得新的日期
	 *
	 * @param date
	 *            旧的日期
	 * @param field
	 *            变动的时间部分
	 * @param amount
	 *            变动量
	 * @return 新的时间
	 */
	public static Date add(Date date, int field, int amount) {
		Calendar c = Calendar.getInstance();
		if (date != null) {
			c.setTime(date);
		}
		c.add(field, amount);
		return c.getTime();
	}

	/**
	 * 获得天数
	 *
	 * @param date
	 *            日期
	 * @return 获得天数
	 */
	public static int date(Date date) {
		Calendar c = Calendar.getInstance();
		if (date != null) {
			c.setTime(date);
		}
		return c.get(Calendar.DATE);
	}

	/**
	 * 获得月份
	 *
	 * @param date
	 *            日期
	 * @return 获得天数
	 */
	public static int month(Date date) {
		Calendar c = Calendar.getInstance();
		if (date != null) {
			c.setTime(date);
		}
		return c.get(Calendar.MONTH);
	}

	/**
	 * 获得年份
	 *
	 * @param date
	 *            日期
	 * @return 获得年份
	 */
	public static int year(Date date) {
		Calendar c = Calendar.getInstance();
		if (date != null) {
			c.setTime(date);
		}
		return c.get(Calendar.YEAR);
	}

	/**
	 * 获得日期的最大天数
	 *
	 * @param date
	 *            日期
	 * @return 最大的天数
	 */
	public static int maxDate(Date date) {
		Calendar c = Calendar.getInstance();
		if (date != null) {
			c.setTime(date);
		}
		c.add(Calendar.MONTH, 1);
		c.set(Calendar.DATE, 1);
		c.add(Calendar.DATE, -1);
		return c.get(Calendar.DATE);
	}

	/**
	 * 将日期转换为同一天的最大值
	 *
	 * @param date
	 * @return
	 */
	public static Date getMaxDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		return calendar.getTime();
	}

	/**
	 * 计算日期中相对前几天或者后几天的日期
	 *
	 * @param initialTime
	 *            当前日期
	 * @param relateDay
	 *            往前几天 or 往后几天
	 * @return
	 */
	public static Date getRelateDay(Date initialTime, int relateDay) {
		Calendar cld = Calendar.getInstance();
		cld.setTime(initialTime);
		cld.add(Calendar.DATE, relateDay);
		return cld.getTime();
	}

	/**
	 * 计算日期中相对前几月或者后几月的日期
	 *
	 * @param initialTime
	 *            当前日期
	 * @param relateMonth
	 * @return
	 */
	public static Date getRelateMonth(Date initialTime, int relateDay) {
		Calendar cld = Calendar.getInstance();
		cld.setTime(initialTime);
		cld.add(Calendar.MONTH, relateDay);
		return cld.getTime();
	}

	public static <T extends Date> T parse(String dateString, String dateFormat, Class<T> targetResultType) {
		if (StringUtils.isEmpty(dateString))
			return null;
		DateFormat df = new SimpleDateFormat(dateFormat);
		try {
			long time = df.parse(dateString).getTime();
			return targetResultType.getConstructor(new Class[] { Long.TYPE }).newInstance(new Object[] { Long.valueOf(time) });// (Date)
		} catch (ParseException e) {
			String errorInfo = "cannot use dateformat:" + dateFormat + " parse datestring:" + dateString;
			throw new IllegalArgumentException(errorInfo, e);
		} catch (Exception e) {
			throw new IllegalArgumentException("error targetResultType:" + targetResultType.getName(), e);
		}
	}

	/**
	 * 计算日期中，相对前几个小时或者后几个小时
	 *
	 * @param initialTime
	 * @param relateHour
	 * @return
	 *
	 */
	public static Date getRelateHour(Date initialTime, int relateHour) {
		Calendar cld = Calendar.getInstance();
		cld.setTime(initialTime);
		cld.add(Calendar.HOUR, relateHour);
		return cld.getTime();
	}

	/**
	 * 计算日期中，相对前几分钟或者后几分钟
	 *
	 * @param initialTime
	 * @param relateMinute
	 * @return
	 *
	 */
	public static Date getRelateMinute(Date initialTime, int relateMinute) {
		Calendar cld = Calendar.getInstance();
		cld.setTime(initialTime);
		cld.add(Calendar.MINUTE, relateMinute);
		return cld.getTime();
	}

	/**
	 * 获得当前日期是每周的周几
	 *
	 * @return String
	 */
	public static String getCurrentDayofWeek() {
		StringBuffer sb = new StringBuffer();
		SimpleDateFormat dateFormat = new SimpleDateFormat(yyyy_MM_dd);
		String dateTime = dateFormat.format(new Date());
		int sIndex = dateTime.indexOf("-");
		int eIndex = dateTime.lastIndexOf("-");
		String year = dateTime.substring(0, sIndex);
		String month = dateTime.substring(sIndex + 1, eIndex);
		String day = dateTime.substring(eIndex + 1, dateTime.length());
		sb.append("日期：");
		sb.append(year + "年");
		sb.append(month + "月");
		sb.append(day + "日");
		int temp = getDayOfWeek("yyyy-MM-dd", dateTime);
		switch (temp - 1) {
			case 0:
				sb.append(" 周日");
				break;
			case 1:
				sb.append(" 周一");
				break;
			case 2:
				sb.append(" 周二");
				break;
			case 3:
				sb.append(" 周三");
				break;
			case 4:
				sb.append(" 周四");
				break;
			case 5:
				sb.append(" 周五");
				break;
			case 6:
				sb.append(" 周六");
				break;
			default:
				break;
		}
		return sb.toString();
	}

	/**
	 * 获得当前日期为每周的第几天
	 *
	 * @param pattern
	 * @param dateTime
	 * @return
	 */
	public static int getDayOfWeek(String pattern, String dateTime) {
		int[] weekDays = { 7, 1, 2, 3, 4, 5, 6 };
		Calendar cal = Calendar.getInstance();
		try {
			cal.setTime(new SimpleDateFormat(pattern).parse(dateTime));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0)
			w = 0;
		return weekDays[w];
	}

	/**
	 * 一天中的问候语
	 *
	 * @return
	 */
	public static String getGreetings() {
		Calendar cal = Calendar.getInstance();
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		if (hour >= 6 && hour < 8) {
			return "早上好！";
		} else if (hour >= 8 && hour < 11) {
			return "上午好！";
		} else if (hour >= 11 && hour < 13) {
			return "中午好！";
		} else if (hour >= 13 && hour < 18) {
			System.out.println("");
			return "下午好！";
		} else {
			return "晚上好！";
		}
	}

	/**
	 * 获取本月第一天的日期
	 *
	 * @return yyyy-MM-dd
	 */
	public static Date getCurrentMonthFirstDay() {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, 0);
		c.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
		return c.getTime();
	}

	public static Date getFirstDayOfMonth(String datestr) {
		if (StringUtils.isBlank(datestr))
			return null;
		Date date = strToDateNotDD(datestr);
		LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault());
		LocalDateTime endOfDay = localDateTime.with(TemporalAdjusters.firstDayOfMonth()).with(LocalTime.MIN);
		Date dates = Date.from(endOfDay.atZone(ZoneId.systemDefault()).toInstant());
		return dates;
	}

	/**
	 * 将短时间格式字符串转换为时间 yyyy-MM( 2017-02)
	 *
	 * @param strDate
	 * @return
	 */
	public static Date strToDateNotDD(String strDate) {
		if (StringUtils.isBlank(strDate))
			return null;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		return strtodate;
	}

	/**
	 * 昨天日期
	 *
	 * @return
	 */
	public static String getYesterdayDate() {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_YEAR, -1);
		return DateUtil.format(c.getTime(), yyyy_MM_dd);
	}

	/**
	 * 获取本月第一天的日期
	 *
	 * @return yyyy-MM-dd
	 */
	public static Date getCurrentMonthLastDay() {
		// 获取Calendar
		Calendar calendar = Calendar.getInstance();
		// 设置时间,当前时间不用设置
		// calendar.setTime(new Date());
		// 设置日期为本月最大日期
		calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
		return calendar.getTime();
	}

	public static Date getLastDayOfMonth(String datestr) {
		if (StringUtils.isBlank(datestr))
			return null;
		Date date = strToDateNotDD(datestr);
		LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault());
		LocalDateTime endOfDay = localDateTime.with(TemporalAdjusters.lastDayOfMonth()).with(LocalTime.MAX);
		Date dates = Date.from(endOfDay.atZone(ZoneId.systemDefault()).toInstant());
		return dates;
	}

	/**
	 * 获取前月第一天的日期
	 *
	 * @return yyyy-MM-dd
	 */
	public static String getLashtMonthFirstDay() {
		// 获取前月的第一天
		Calendar cal_1 = Calendar.getInstance();// 获取当前日期
		cal_1.add(Calendar.MONTH, -1);
		cal_1.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
		return DateUtil.format(cal_1.getTime(), yyyy_MM_dd);
	}

	/**
	 * 获取前月第一天的日期
	 *
	 * @return yyyy-MM
	 */
	public static String getLashtMonth() {
		// 获取前月的第一天
		Calendar cal_1 = Calendar.getInstance();// 获取当前日期
		cal_1.add(Calendar.MONTH, -1);
		cal_1.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
		return DateUtil.format(cal_1.getTime(), yyyy_MM);
	}

	/**
	 * 获取前月的最后一天的日期
	 *
	 * @return yyyy-MM-dd
	 */
	public static String getLastMonthLastDay() {
		// 获取前月的最后天
		Calendar cal_1 = Calendar.getInstance();// 获取当前日期
		cal_1.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
		cal_1.add(Calendar.DAY_OF_MONTH, -1);
		return DateUtil.format(cal_1.getTime(), yyyy_MM_dd);
	}

	/**
	 * 获取下月的第一天
	 *
	 * @return
	 */
	public static String getNextMonthFistDay() {
		// 获取前月的最后天
		Calendar cal_1 = Calendar.getInstance();// 获取当前日期
		cal_1.add(Calendar.MONTH, 1);
		cal_1.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
		return DateUtil.format(cal_1.getTime(), yyyy_MM_dd);
	}

	/**
	 * 获取下月
	 *
	 * @return yyyy-MM
	 */
	public static String getNextMonth() {
		// 获取前月的最后天
		Calendar cal_1 = Calendar.getInstance();// 获取当前日期
		cal_1.add(Calendar.MONTH, 1);
		cal_1.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
		return DateUtil.format(cal_1.getTime(), yyyy_MM);
	}

	/**
	 * 获取本月
	 *
	 * @return yyyy-MM
	 */
	public static String getNowMonth() {
		// 获取前月的最后天
		Calendar cal_1 = Calendar.getInstance();// 获取当前日期
		cal_1.setTime(new Date());
		return DateUtil.format(cal_1.getTime(), yyyy_MM);
	}

	/**
	 * 获取下月的最后一天
	 *
	 * @return
	 */
	public static String getNextMonthLastDay() {
		// 获取前月的最后天
		Calendar cal_1 = Calendar.getInstance();// 获取当前日期
		cal_1.add(Calendar.MONTH, 2);
		cal_1.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,
		cal_1.add(Calendar.DAY_OF_MONTH, -1);
		return DateUtil.format(cal_1.getTime(), yyyy_MM_dd);
	}

	public static Date addDayHmS(Date date, int HH, int mm, int SS) {
		return new Date(date.getTime() + ((HH * 60 + mm) * 60 + SS) * 1000);
	}

	// 获去本月最后一天
	public static String getMonthLastDay() {// 获取Calendar
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(calendar.getTime());
	}

	/**
	 * 计算两个日期之间相差的天数
	 *
	 * @param smdate
	 *            较小的时间
	 * @param bdate
	 *            较大的时间
	 * @return 相差天数
	 * @throws ParseException
	 */
	public static int daysBetween(Date smdate, Date bdate) throws ParseException {
		Calendar cal = Calendar.getInstance();
		cal.setTime(smdate);
		long time1 = cal.getTimeInMillis();
		cal.setTime(bdate);
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);

		return Integer.parseInt(String.valueOf(between_days));
	}

	/**
	 * 判断日期是否大于后面的日期
	 *
	 * @param fDateStr
	 *            前面的日期字符串
	 * @param aDateStr
	 *            后面的日期字符串
	 * @param pattern格式
	 * @return
	 */
	public static Boolean before(String fDateStr, String aDateStr, String pattern) {
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		Date fDate = null;
		Date aDate = null;
		try {
			fDate = df.parse(fDateStr);
			aDate = df.parse(aDateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return fDate.before(aDate);
	}

	/**
	 * 判断日期是否小于后面的日期
	 *
	 * @param fDateStr
	 *            前面的日期字符串
	 * @param aDateStr
	 *            后面的日期字符串
	 * @param pattern格式
	 * @return
	 */
	public static Boolean after(String fDateStr, String aDateStr, String pattern) {
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		Date fDate = null;
		Date aDate = null;
		try {
			fDate = df.parse(fDateStr);
			aDate = df.parse(aDateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return fDate.after(aDate);
	}

	public static Date addDays(Date date, int days) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int day = calendar.get(6);
		calendar.set(6, day + days);
		return calendar.getTime();
	}

	/*
	 * 计算两个日期相差的月份数
	 * @param date1 日期1
	 * @param date2 日期2
	 * @param pattern 日期1和日期2的日期格式
	 * @return 相差的月份数
	 * @throws ParseException
	 */
	public static int countMonths(String date1, String date2, String pattern) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);

		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();

		c1.setTime(sdf.parse(date1));
		c2.setTime(sdf.parse(date2));

		int year = c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR);

		// 开始日期若小月结束日期
		if (year < 0) {
			year = -year;
			return year * 12 + c1.get(Calendar.MONTH) - c2.get(Calendar.MONTH);
		}

		return year * 12 + c2.get(Calendar.MONTH) - c1.get(Calendar.MONTH);
	}

	/**
	 * 断一个字符串是不是一个合法的日期格式
	 *
	 * @param DateStr
	 * @param pattern
	 * @return
	 */
	public static boolean isValidDate(String DateStr, String pattern) {
		boolean convertSuccess = true;
		// 指定日期格式为四位年/两位月份/两位日期，注意yyyy/MM/dd区分大小写；
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		try {
			// 设置lenient为false.
			// 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
			format.setLenient(false);
			format.parse(DateStr);
		} catch (ParseException e) {
			// e.printStackTrace();
			// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
			convertSuccess = false;
		}
		return convertSuccess;
	}

	/**
	 * 获取两个时间的时间查 如1天2小时30分钟 public static String getDatePoor(Date endDate, Date nowDate) { long nd = 1000 * 24 * 60 * 60; long nh = 1000 * 60 * 60; // long nm = 1000 * 60; // long ns = 1000; // 获得两个时间的毫秒时间差异 long diff = endDate.getTime() - nowDate.getTime(); // 计算差多少天 // long day = diff / nd; // 计算差多少小时 long hour = diff % nd / nh; // 计算差多少分钟 // long min = diff % nd % nh / nm; // 计算差多少秒//输出结果 // long sec = diff % nd % nh % nm / ns; // return day + "天" + hour + "小时" + min + "分钟"; }
	 */

	/**
	 * 计算2个日期相差小时
	 *
	 * @param endDate
	 * @param nowDate
	 * @return
	 */
	public static Long getDatePoorHour(Date endDate, Date nowDate) {
		long nd = 1000 * 24 * 60 * 60;
		long nh = 1000 * 60 * 60;
		long diff = endDate.getTime() - nowDate.getTime();
		long hour = diff % nd / nh;
		return hour;
	}

	public static Boolean betweenDate(String occDate, String beginDate, String endDate, String pattern) {
		Boolean flag = false;
		if (beginDate.equals(occDate) || endDate.equals(occDate) || (after(occDate, beginDate, pattern) && after(endDate, occDate, pattern))) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 获取星期数
	 *
	 * @param nowDate
	 * @return
	 */
	public static String getWeekOfDate(Date dt) {
		if (dt == null) {
			return StringUtils.EMPTY;
		}
		String[] weekDays = { "7", "1", "2", "3", "4", "5", "6" };
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		int index = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (index < 0)
			index = 0;
		return weekDays[index];
	}

	/**
	 * 获取两个时间段的时间数组，不包含结束日的最后一天 List<date> list
	 *
	 * @param beginDate
	 * @param endDate
	 */
	public static List<Date> getTwoDateList(Date beginDate, Date endDate) {
		Calendar start = Calendar.getInstance();
		start.setTime(beginDate);
		Long startTIme = start.getTimeInMillis();

		Calendar end = Calendar.getInstance();
		end.setTime(getRelateDay(endDate, -1));
		Long endTime = end.getTimeInMillis();

		Long oneDay = 1000 * 60 * 60 * 24l;

		Long time = startTIme;
		List<Date> list = new ArrayList<Date>();
		while (time <= endTime) {
			Date d = new Date(time);
			list.add(d);
			time += oneDay;
		}
		return list;
	}

	/**
	 * 获取当前日期是星期几<br>
	 *
	 * @param dt
	 * @return 当前日期是星期几
	 */
	public static String getWeekOfDate(String dateStr) {
		String[] weekDays = { "7", "1", "2", "3", "4", "5", "6" };
		Calendar cal = Calendar.getInstance();
		cal.setTime(DateUtil.parse(dateStr, DateUtil.yyyy_MM_dd));
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0)
			w = 0;
		return weekDays[w];
	}

	/**
	 * 将字符串的星期转成map
	 *
	 * @param dt
	 * @return 当前日期是星期几
	 */
	public static Map<String, String> converStrToMapWeek(String dateStr) {
		Map<String, String> reuslt = null;
		if (!StringUtils.isEmpty(dateStr)) {
			reuslt = new HashMap<String, String>();
			String[] weeks = dateStr.split(",");
			if (null != weeks) {
				for (String str : weeks) {
					if (StringUtils.isNumeric(str)) {
						reuslt.put(str, str);
					}
				}
			}

		}
		return reuslt;
	}

	/**
	 * 计算开始时间之后几天的时间集合
	 *
	 * @param startDate
	 *            开始时间
	 * @param days
	 *            天数
	 * @return
	 */
	public static List<String> getDateCollections(Date startDate, int days) {
		List<String> list = new ArrayList<String>();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(yyyy_MM_dd);
			list.add(sdf.format(startDate));
			for (int i = 1; i < days; i++) {
				Calendar c = Calendar.getInstance();
				c.add(Calendar.DAY_OF_YEAR, i);
				list.add(sdf.format(c.getTime()));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("计算时间段内周几的日期 错误");
		}

		return list;
	}

	public static Date parseDate(Date date, String pattern) {
		return parse(format(date, pattern), pattern);
	}

	public static List<Date> moreDate(Date begin, Date end, Date comBegin, Date comEnd) {
		List<Date> list = new ArrayList<Date>();
		if (comBegin.before(begin)) {
			if (comEnd.before(begin)) {
				list.addAll(getBetweenDates(comBegin, comEnd));
			} else if (comEnd.equals(begin) || comEnd.after(begin)) {
				list.addAll(getBetweenDates(comBegin, addDays(end, -1)));
				if (comEnd.after(end)) {
					list.addAll(getBetweenDates(comEnd, addDays(end, 1)));
				}
			}
		} else if ((comBegin.after(begin) || comBegin.equals(begin)) && comBegin.before(end)) {
			if (comEnd.after(end)) {
				list.addAll(getBetweenDates(addDays(end, 1), comEnd));
			}
		} else if (comBegin.after(end) || comBegin.equals(end)) {
			list.addAll(getBetweenDates(addDays(comBegin, 1), comEnd));
		}
		return list;
	}

}
