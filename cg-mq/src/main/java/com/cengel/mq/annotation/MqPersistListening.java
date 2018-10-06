package com.cengel.mq.annotation;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.*;

/**
 * @Title: mq消息接收的切面
 * @Description:
 * 1. 统一开启事务
 * 2.
 * @author zhz
 * @time 2018/9/28 - 17:42
 * @version V1.0
 **/
@Transactional(isolation = Isolation.READ_COMMITTED)
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface MqPersistListening {



}
