package com.cengel.redis.aop;

import com.cengel.redis.annotation.RedisCache;
import com.cengel.redis.helper.RedisHelper;
import com.cengel.starbucks.util.AssertUtil;
import com.cengel.starbucks.util.ReflectionUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * @Title:
 * @Description:
 * @Author zhz
 * @Time 2018/9/26 - 9:30
 * @Version V1.0
 **/
@Slf4j
@Aspect
public class RedisCacheAspect {

	@Autowired
	private RedisHelper redisHelper;

	@Around("execution(public * com.cengel..*.*(..)) && @annotation(redisCache)")
	public Object aroundAnnotation(ProceedingJoinPoint point, RedisCache redisCache) {
		Object o = null;
		String key = renderKey(redisCache.value(), point);
		String filed = renderField(redisCache.key(), point.getArgs());
		AssertUtil.allNotNull("redisCache:kv都不能为空", key, filed);
		long expire = redisCache.expire();
		try {
			//todo step1: 如果redis中有值，则返回
			Serializable redisRecord = redisHelper.get(key + filed);
			if (redisRecord != null) {
				return redisRecord;
			}
			//todo step2: 如果redis中没有值，执行方法
			o = point.proceed();
			if (!(o instanceof Serializable)) {
				throw new IllegalArgumentException("redis等持久化对象未实现可序列化接口或为空");
			}
			//todo step3: 如果方法返回非空，且return值可序列化，存储该值到redis
			redisHelper.setAndExpire(key + filed, (Serializable) o, expire);
		} catch (Throwable throwable) {
			throw new RuntimeException("RedisCache执行错误");
		}
		return o;
	}

	private String renderField(String filed, Object[] args) {
		if (filed.trim().startsWith("#p")) {
			filed = filed.substring(2);
			if (filed.contains(".")) {
				String[] arr = filed.split("\\.");
				int index = Integer.parseInt(arr[0]);
				Object object = args[index];
				String subFiledName = arr[1].toString();//object的字段
				filed = ReflectionUtil.getFiledVal(subFiledName, object).toString();
			} else {
				int index = Integer.parseInt(filed);
				return args[index].toString();
			}
		} else if (filed.trim().startsWith("$")){
			filed = filed.substring(1);
		}else throw new IllegalArgumentException("参数必须以#p或$开头");
		return filed;
	}

	private String renderKey(String key, ProceedingJoinPoint point) {
		if (key == null || "".equals(key)) {
			Signature sig = point.getSignature();
			MethodSignature msig = null;
			if (!(sig instanceof MethodSignature)) {
				throw new IllegalArgumentException("该注解只能用于方法");
			}
			msig = (MethodSignature) sig;
			Object target = point.getTarget();
			try {
				Method currentMethod = target.getClass().getMethod(msig.getName(), msig.getParameterTypes());
				key = currentMethod.getDeclaringClass().getSimpleName() + "." + currentMethod.getName() + "_";
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
		}
		return key;
	}


}
