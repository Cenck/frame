package com.cengel.mq.annotation;

import java.lang.annotation.*;

/**
 * @Title: 发送消息的时候，顺便把消息
 * @Description:
 * @author zhz
 * @time 2018/9/28 - 17:52
 * @version V1.0
 **/
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface MqPersistSending {
}
