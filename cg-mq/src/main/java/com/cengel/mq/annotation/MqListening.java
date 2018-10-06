package com.cengel.mq.annotation;

import java.lang.annotation.*;

/**
 * @Title:
 * @Description:
 * @Author zhz
 * @Time 2018/9/28 - 18:18
 * @Version V1.0
 **/
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface MqListening {

}
