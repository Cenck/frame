package com.cengel.mq.mqe;

import lombok.Data;

/**
 * @Title:
 * @Description:
 * @Author zhz
 * @Time 2018/9/28 - 18:32
 * @Version V1.0
 **/
@Data
public class AbstractMqe implements BaseMqe {

	private BaseMqEntity mqEntity;

	@Override
	public void setMqEntity(MqEntity entity) {
		this.mqEntity = (BaseMqEntity) entity;
	}

}
