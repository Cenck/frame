package com.cengel.starbucks.factory.string;

import com.cengel.starbucks.annotation.Description;
import com.cengel.starbucks.util.DateUtil;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Title:
 * @Description:
 * @Author zhz
 * @Time 2018/8/22 - 11:21
 * @Version V1.0
 **/
public class StringFormat {
	private StringFormat() {}

	@Description(("待转化内容"))
	private String content;

	/********************|| ${}替换工具 ||********************/
	@Description("格式化${??}")
	public static final String  $regex   = "\\$\\{(.+?)!?\\}";
	public static final Pattern $pattern = Pattern.compile($regex);

	public static StringFormat $create(String content) {
		StringFormat format = new StringFormat();
		format.content = content;
		return format;
	}

	public final StringFormat $format(String key, String value) {
		if (key == null || value == null) return null;
		Pattern $pattern = Pattern.compile("\\$\\{(" + key + ")!?\\}");
		Matcher matcher = $pattern.matcher(content);
		if (matcher.find()) {
			this.content = matcher.replaceAll(value);
		}
		return this;
	}

	public final StringFormat $fromObj(Object o) {
		Matcher matcher = $pattern.matcher(content);
		StringBuffer sb = new StringBuffer();
		while (matcher.find()) {
			String value = getValueFromObj(matcher.group(1), o);
			if (value != null) matcher.appendReplacement(sb, value);
		}
		this.content = matcher.appendTail(sb).toString();
		return this;
	}

	private String getValueFromObj(String key, Object o) {
		if (key == null || key == "") return null;
		key = key.trim();
		return getValueFromObj_recursive(key, o, o.getClass());
	}

	private String getValueFromObj_recursive(String key, Object o, Class c) {
		try {
			Field field = c.getDeclaredField(key);
			field.setAccessible(true);
			Object value = field.get(o);
			if (value == null) return null;
			if (value instanceof Date) {
				return DateUtil.format((Date) value, DateUtil.yyyy_MM_dd_HH_mm_ss);
			} else {
				return value.toString();
			}
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
			if (c.getSuperclass() != null) return getValueFromObj_recursive(key, o, c.getSuperclass());
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	public final String build() {
		Matcher matcher = $pattern.matcher(content);
		StringBuffer sb = new StringBuffer();
		while (matcher.find()) {
			if (matcher.group().contains("!")) {
				//todo 允许空值 ${name!}表示允许name为空值
				matcher.appendReplacement(sb, "");
			} else {
				throw new RuntimeException("不允许空值：" + matcher.group(1) + "值为空");
			}
		}
		return matcher.appendTail(sb).toString();
	}

	/********************|| {}变长参数填充 - 静态工具类 ||********************/

	/**
	 * 格式化以下语句 : 今天是{}，周{},心情{}. 传入3个参数，按顺序填充
	 *
	 * @param origin
	 * @param params
	 * @return
	 */
	private final static Pattern PATTERN_BRACKET = Pattern.compile("\\{(\\s)?\\}");

	public static String formatBracket(String origin, Object... params) {
		Matcher matcher = PATTERN_BRACKET.matcher(origin);
		StringBuffer sb = new StringBuffer();
		int index = 0;
		while (matcher.find()) {
			Object object = params[index++];
			if (object != null) matcher.appendReplacement(sb, object.toString());
		}
		return matcher.appendTail(sb).toString();
	}

	public static void main(String[] args) {
		String ssss = formatBracket("今天是{}，周{},心情{}", new Date(), "五", "大好");
		System.out.println(ssss);
		String CANCEL_MSG_CONTENT = "尊敬的游客${userName},您好!十分抱歉,由于：${remark},本次团取消，给您带来的不便敬请谅解.";
		String s = StringFormat.$create(CANCEL_MSG_CONTENT).$format("userName", "zhz").$format("remark", "下雨").build();
		System.out.println(s);
	}
}
