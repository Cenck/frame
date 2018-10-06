package com.cengel.redis.constant;

import com.cengel.starbucks.annotation.Description;

public class RedisKeys {

	public interface MemberRedisKeys {
		String ONLINE_USER = "online-user:";
		String MEMBER_NAME_DETAIL_CACHE = "member_detail_";


	}

	public interface CodeRedisKeys {
		@Description("当redis中key值大于该值将从0重新开始")
		Integer AUTO_CODE_MAX_VAL  = 100000;
		@Description("自动码的寿命1天")
		Long    AUTO_CODE_LIFETIME = 24 * 60 * 61L;
		String AUTO_CODE       = "auto_code:";
		String AUTO_CODE_GOODS = AUTO_CODE + "goods";
		String AUTO_CODE_ORDER = AUTO_CODE + "order";

	}
}
