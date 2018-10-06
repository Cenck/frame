package com.cengel.redis.config;

import com.cengel.starbucks.annotation.Description;
import lombok.Data;

import java.io.Serializable;

/**
 * @Title:
 * @Description:
 * @Author zhz
 * @Time 2018/8/3 - 11:09
 * @Version V1.0
 **/
@Data
public class RedisConfigProp implements Serializable {

	private Integer minIdle;
	private Integer maxIdle;
	private Integer maxTotal;
	private Integer maxWait;
	private Boolean testOnBorrow;

	private String  host;
	private String  password;
	private Integer port;
	private Integer database;

	@Description("加域")
	private String domain;

}
