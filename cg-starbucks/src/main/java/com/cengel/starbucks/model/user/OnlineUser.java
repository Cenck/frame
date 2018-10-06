package com.cengel.starbucks.model.user;

import com.cengel.starbucks.constant.Symbol;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Title:
 * @Description:
 * @Author zhz
 * @Time 2018/9/21 - 19:38
 * @Version V1.0
 **/
@Data
public class OnlineUser implements Serializable {
	private Integer id;
	private String  account; //用户名 - 相当于security的username
	private String  nickName;
	private String  realName;//用户真实姓名
	private String  platform;
	private Integer rankId;
	private String randName;
	private BigDecimal points;
	private BigDecimal money;
	private BigDecimal funds;


	/********************||  ||********************/
	private Integer distId;//区域id
	private Integer addressId; //默认收货地址

	public String getUsrKey() {
		return this.getPlatform() + Symbol.WAVE + this.getAccount();
	}

	public static String getUsrKey(String account, String platform) {
		return platform + Symbol.WAVE + account;
	}
}
