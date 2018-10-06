package com.cengel.hibernate.constant;

import com.cengel.starbucks.annotation.Description;

public interface RedisCacheKey {

	@Description("@Cacheable的value")
	String ENTITY_KEY_PREFIX = "E-";

	@Description("@Cacheable的value")
	String LOGIN_USER_KEY_PREFIX = "LU-";

}
