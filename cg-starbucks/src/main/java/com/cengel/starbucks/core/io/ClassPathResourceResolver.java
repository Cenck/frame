package com.cengel.starbucks.core.io;

import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

/**
 * @Title:
 * @Description:
 * @Author zhz
 * @Time 2018/8/3 - 17:03
 * @Version V1.0
 **/
public class ClassPathResourceResolver {

	public static String  getClassPathFile(String fileName){
		try {
			return new ClassPathResource(fileName).getURL().getPath();
		} catch (IOException e) {
			e.printStackTrace();
		}
		throw new RuntimeException("classpath文件未找到");
	}
}
