package com.cengel.redis.helper;
/**
 * @Title: 用于生成序列码
 * @Description:
 * @author zhz
 * @time 2018/9/22 - 16:24
 * @version V1.0
 **/
public interface RedisCodeHelper {

	/**
	 * 生成订单sn后缀
	 * @return 格式20180910-000001
	 */
	String getOrderSnSuffix();
	String getGoodsSnSuffix();

}
