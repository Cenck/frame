package com.cengel.mq.mqe;

import lombok.Data;

import java.io.Serializable;

/**
 * @Title:
 * @Description:
 * @Author zhz
 * @Time 2018/9/28 - 17:47
 * @Version V1.0
 **/
public interface  BaseMqe extends Serializable {

	MqEntity getMqEntity();
	void setMqEntity(MqEntity entity);

}
