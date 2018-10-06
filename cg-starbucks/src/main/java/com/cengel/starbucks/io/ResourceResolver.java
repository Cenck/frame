package com.cengel.starbucks.io;

import com.cengel.starbucks.util.Validator;

import java.io.File;
import java.net.URL;

/**
 * @Title: 资源问题
 * @Description:
 * @Author zhz
 * @Time 2018/7/11 - 12:57
 * @Version V1.0
 **/
public class ResourceResolver {

	public static final String CLASSPATH_PREFIX = "classpath:";

	//	获取classPath根路径
	public static String getRootResource() {
		return ResourceResolver.class.getResource("/").getPath();
	}

	/**
	 * 根据包名获取根路径文件
	 *
	 * @return
	 */
	public static File getClassPathFileByPkg(String pkgName) {
		pkgName = pkgName.replaceAll("\\.", "/");
		String path = getRootResource() + File.separator + pkgName;
		return new File(path);
	}

	public static boolean isClasspathFile(String s) {
		if (Validator.isNotBlank(s) && s.startsWith(CLASSPATH_PREFIX)) {
			return true;
		}
		return false;
	}

	public static File resolveFile(String path) {
		if (isClasspathFile(path)) {
			path = getClassPath() + path.replaceAll(CLASSPATH_PREFIX, "");
			return new File(path);
		}
		return null;
	}
	//	获取classPath根路径
	public static String getClassPath() {
		URL classPath = Thread.currentThread().getContextClassLoader().getResource("/");
		if (classPath==null) classPath=ResourceResolver.class.getResource("/");
		return classPath.getPath();
	}
}
