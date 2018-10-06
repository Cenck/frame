package com.cengel.code.model.beans;

import com.cengel.starbucks.annotation.Description;
import lombok.Data;

import java.io.Serializable;

/**
 * @author zhz
 * @version V1.0
 * @create 2018-04-24 12:34
 **/
@Data
public class TemplateBean implements Serializable {

	private String name;
	@Description("模板路径")
	private String temPath;
	@Description("相对包路径")
	private String pkg;//包相对路径
	@Description("输出文件名后缀")
	private String fileNameSuffix;//文件名后缀
}
