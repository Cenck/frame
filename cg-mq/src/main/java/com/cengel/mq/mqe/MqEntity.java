package com.cengel.mq.mqe;

import lombok.Data;

import java.io.Serializable;

/**
 * @Title:
 * @Description:
 * @Author zhz
 * @Time 2018/9/28 - 17:48
 * @Version V1.0
 **/
public interface MqEntity extends Serializable {

	String getId();
	void setId(String id);

}
