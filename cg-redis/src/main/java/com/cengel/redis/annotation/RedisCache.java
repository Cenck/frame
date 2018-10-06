package com.cengel.redis.annotation;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Inherited
public @interface RedisCache {

	//redisKey的前缀 ,不写取数名加方法名
	String value() default "";

	//redisKey的后缀，格式：#p0.id (取第一个参数的id属性) 2.$aaaa,真接取aaa
	String key() ;

	//存活时间,默认无限时长,单位是秒
	long expire() default -1;

}
