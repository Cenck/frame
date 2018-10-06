package com.cengel.hibernate.config;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Title:
 * @Description:
 * @Author zhz
 * @Time 2018/7/26 - 12:27
 * @Version V1.0
 **/
@Data
public class HibernateConfigProp implements Serializable {

	private List<String> entityScanPkgs; //entity扫描路径
	private String       driverClass;
	private String       url;
	private String       username;
	private String       password;

}
