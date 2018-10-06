package com.cengel.starbucks.model.obj;

import com.cengel.starbucks.annotation.Description;
import lombok.Data;

import java.io.Serializable;

/**
 * @Title: 通用系统参数
 * @Description:
 * @Author zhz
 * @Time 2018/8/3 - 20:17
 * @Version V1.0
 **/
@Data
public class BaseContext implements Serializable {

	@Description("静态前缀")
	private String staticBase;
	@Description("ip")
	private String ip;
	@Description("域名")
	private String basePath;
}
