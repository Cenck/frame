package com.cengel.starbucks.util;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class PkCreator {

	public static String getSerialNumber(){
		return produceUID();
	}
	public static String getSerialNumber(String str){
		return UUID.fromString(str).toString().replace("-", "");
	}
	public static String getSerialNumber(byte[] bytes){
		return UUID.nameUUIDFromBytes(bytes).toString().replace("-", "");
	}

	private final static char[] _b = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
		'9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L','M', 'N', 
		'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y','Z'};
	
	private static ThreadLocal<char[]> chartl = new ThreadLocal<char[]>() {
		protected char[] initialValue() {
			return new char[20];
		};
	};
	
	private static void to36String(int number, int offset, char[] buf, int charPos){
		char[] chars = chartl.get();
		while (number >= 36) {
			buf[--charPos] = _b[number % 36];
			number = number / 36;
		}
		buf[--charPos] = _b[number];
		System.arraycopy(buf, 0, chars, offset, buf.length);
    }

	/**
     * 生成20位长ID串
     * 创建时间:	2015-4-24
     */
    public static String produceUID(){
    	char[] chars = chartl.get();
		Calendar calendar = Calendar.getInstance();
		ThreadLocalRandom rd = ThreadLocalRandom.current();
//		chars[2] = chars[3] = '0';
		chars[0] = _b[rd.nextInt(36)];
		chars[1] = _b[rd.nextInt(36)];
		chars[2] = _b[rd.nextInt(36)];
		chars[3] = _b[rd.nextInt(36)];
		chars[4] = _b[rd.nextInt(36)];
		to36String(calendar.get(Calendar.YEAR), 5, new char[]{'0','0','0'}, 3);
		chars[8] = _b[rd.nextInt(36)];
		to36String(calendar.get(Calendar.DAY_OF_YEAR), 9, new char[]{'0','0'}, 2);
		chars[11] = _b[rd.nextInt(36)];
		to36String(calendar.get(Calendar.HOUR_OF_DAY) * 60 * 60
				+ calendar.get(Calendar.MINUTE) * 60
				+ calendar.get(Calendar.SECOND), 12,
				new char[]{'0','0','0','0'}, 4);
		chars[16] = _b[rd.nextInt(36)];
		to36String(calendar.get(Calendar.MILLISECOND), 17, new char[]{'0','0'}, 2);
		chars[19] = _b[rd.nextInt(36)];
		return new String(chars);
	}
    
    /**
     * 生成20位长ID串
     * @param groupId	集团组织
     * 创建时间 :2016-3-24
     */
    public static String produceUID(String groupId) {
    	return produceUID(groupId, "01");
    }
    
    /**
     * 生成20位长ID串
     * @param groupId	集团组织
     * @param serialNo	单据编号
     * 创建时间 :2015-4-24
     */
    private static String produceUID(String groupId, String serialNo){
    	char[] chars = chartl.get();
    	Calendar calendar = Calendar.getInstance();
    	ThreadLocalRandom rd = ThreadLocalRandom.current();
    	if(groupId.matches("[0-9a-zA-Z]{2}")) {
    		groupId.toUpperCase().getChars(0, 2, chars, 0);
    	} else {
    		throw new IllegalArgumentException("集团组织必须为2位长!");
    	}
    	if(serialNo.matches("[0-9a-zA-Z]{2}")) {
    		serialNo.toUpperCase().getChars(0, 2, chars, 2);
    	} else {
    		throw new IllegalArgumentException("单据编号必须为2位长!");
    	}
    	chars[4] = _b[rd.nextInt(36)];
    	to36String(calendar.get(Calendar.YEAR), 5, new char[]{'0','0','0'}, 3);
    	chars[8] = _b[rd.nextInt(36)];
    	to36String(calendar.get(Calendar.DAY_OF_YEAR), 9, new char[]{'0','0'}, 2);
    	chars[11] = _b[rd.nextInt(36)];
    	to36String(calendar.get(Calendar.HOUR_OF_DAY) * 60 * 60
    			+ calendar.get(Calendar.MINUTE) * 60
    			+ calendar.get(Calendar.SECOND), 12,
    			new char[]{'0','0','0','0'}, 4);
    	chars[16] = _b[rd.nextInt(36)];
    	to36String(calendar.get(Calendar.MILLISECOND), 17, new char[]{'0','0'}, 2);
    	chars[19] = _b[rd.nextInt(36)];
    	return new String(chars);
    }
	
	/** 
     * 格式化金额         
     */  
    public static String formatMoney(double num, int len) {
        NumberFormat formater = null;
        if (len == 0) {  
            formater = new DecimalFormat("###,###");
        } else {  
            StringBuffer buff = new StringBuffer();
            buff.append("###,###.");  
            for (int i = 0; i < len; i++) {  
                buff.append("#");  
            }  
            formater = new DecimalFormat(buff.toString());
        }  
        String result = formater.format(num);
        if(result.indexOf(".") == -1) {  
            result = result + ".00";  
        }  
        return result;
    }  
    
	/** 
     * 格式化金额         
     */  
    public static String formatStringMoney(String s, int len) {
        if (s == null || s.length() < 1) {  
            return "";  
        }  
        NumberFormat formater = null;
        double num = Double.parseDouble(s);
        if (len == 0) {  
            formater = new DecimalFormat("###,###");
  
        } else {  
            StringBuffer buff = new StringBuffer();
            buff.append("###,###.");  
            for (int i = 0; i < len; i++) {  
                buff.append("#");  
            }  
            formater = new DecimalFormat(buff.toString());
        }  
        String result = formater.format(num);
        if(result.indexOf(".") == -1) {  
            result = result + ".00";  
        }
        return result;  
    }
    
    /**
     * 类型转换
     */
    private static int transType(Object type) {
        int typecode;
        typecode = 93;
		if (type.equals("java.lang.String")) {
			typecode = 93;
		}
		if (type.equals("double")) {
			typecode = 3;
		}
		if (type.equals("int")) {
			typecode = 5;
		}
		if (type.equals("java.math.BigDecimal")) {
			typecode = 98;
		}
        return typecode;
    }
    
	public Object transFields(Object a, Object b) {
		
		Class<?> c1 = a.getClass();
		Class<?> c2 = b.getClass();

		Field[] fieldsDest = c1.getDeclaredFields();
		Field[] fieldsOrg = c2.getDeclaredFields();

		AccessibleObject.setAccessible(fieldsDest, true);
		AccessibleObject.setAccessible(fieldsOrg, true);

		for (int i = 0; i < fieldsDest.length; i++) {
			Field f = fieldsDest[i];
			Class<?> type = f.getType();
			String name = f.getName();
			String typeName = type.getName();
			if (name.equals("FIELDNUM") || name.equals("PK")
					|| name.equals("mErrors") || name.equals("fDate")) {
				continue;
			}
			for (int j = 0; j < fieldsOrg.length; j++) {
				// 得到数据源的数据
				Field f1 = fieldsOrg[j];
				String name1 = f1.getName();
				String typeName1 = type.getName();
				// 取出冗余变量
				if (name.equals("FIELDNUM") || name.equals("PK")
						|| name.equals("mErrors") || name.equals("fDate")) {
					continue;
				}
				// 赋值转换
				if ((typeName.equals(typeName1)) && (name1.equals(name))) {

					switch (transType(typeName)) {
					case 3:
						try {
							f.setDouble(a, f1.getDouble(b));
						} catch (Exception e) {
							e.printStackTrace();
						}
						break;
					case 5:
						try {
							f.setInt(a, f1.getInt(b));
						} catch (Exception e) {
							e.printStackTrace();
						}
						break;
					case 93:
						try {
							f.set(a, f1.get(b));
						} catch (Exception e) {
							e.printStackTrace();
						}
						break;
					case 98:
						try {
							f.set(a, f1.get(b));
						} catch (Exception e) {
							e.printStackTrace();
						}
						break;

					default:
						try {
							f.set(a, f1.get(b));
						} catch (Exception e) {
							e.printStackTrace();
						}
						break;
					}
				}
			}
		}
		return a;
	}
}
