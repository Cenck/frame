package com.cengel.redis.helper;

import com.cengel.starbucks.constant.Symbol;
import com.cengel.starbucks.util.DateUtil;

import javax.annotation.Resource;
import java.util.Date;

import static com.cengel.redis.constant.RedisKeys.CodeRedisKeys.*;

/**
 * @Title:
 * @Description:
 * @Author zhz
 * @Time 2018/9/22 - 16:24
 * @Version V1.0
 **/
public class RedisCodeHelperImpl implements RedisCodeHelper {

	@Resource
	private RedisHelper redisHelper;

	@Override
	public String getOrderSnSuffix() {
		return getSnSuffix(AUTO_CODE_ORDER);
	}

	@Override
	public String getGoodsSnSuffix() {
		return getSnSuffix(AUTO_CODE_GOODS);
	}

	private String getSnSuffix(String type) {
		//每天生成
		String date = DateUtil.format(new Date(), DateUtil.yyyyMMdd);
		String key = type + date;
		int value = redisHelper.incr(key);
		if (value >= AUTO_CODE_MAX_VAL) {
			redisHelper.set(key, 0);
			value = 0;
		}
		redisHelper.expire(key, AUTO_CODE_LIFETIME);//1天后失效
		return date + Symbol.DASHED_LINE + value;
	}
}
