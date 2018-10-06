package com.cengel.starbucks.io;

import org.apache.commons.lang3.SerializationUtils;

/**
 * @author zhz
 * @version V1.0
 * @create 2018-03-31 11:24
 **/
public class SerializationUtil extends SerializationUtils {

	public static byte[] serializeStr(String obj) {
		if (obj==null) obj="";
//		return obj.getBytes();
		return serialize(obj);
	}

	public static String  deserializeStr(byte[] strData) {
		//		if (strData!=null) return new String (strData);
		if (strData!=null) return deserializeStr(strData);
		return null;
	}

}
