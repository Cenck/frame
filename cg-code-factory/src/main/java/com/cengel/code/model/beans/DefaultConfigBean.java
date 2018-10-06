package com.cengel.code.model.beans;

import com.cengel.code.task.TypeConverter;
import lombok.Data;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @Title:
 * @Description:
 * @Author zhz
 * @Time 2018/7/26 - 15:42
 * @Version V1.0
 **/
@Data
public class DefaultConfigBean implements Serializable {

	private String              projectPrefix;//项目名，默认为空
	private String              basePackage;//基础表路径
	private String              schema;//dataBase
	private String              outPath;//输入根目录
	private String              tableName;//表名，允许多张表，以英文,号隔开
	private String              webPath;//web资源：ftl,html的路径
	private String              startType;//执行类型，生成所有或仅更新entity
	private String              entityClassName;//baseEntity||BEntity


	private List<LinkedHashMap> templates; //模板信息列表
	private     TypeConverter   typeConverter; //模板信息列表
}
