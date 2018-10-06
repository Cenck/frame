package com.cengel.mq.biz;

import com.cengel.mq.enums.MqPersistStateEnum;

import java.io.Serializable;

/**
 * @Title: 请实现该类
 * @Description:
 * @author zhz
 * @time 2018/9/28 - 18:23
 * @version V1.0
 **/
public interface MqPersistBiz {
	//持久化消息实体到数据库
	void save(Serializable msgMqe);
	void updateState(MqPersistStateEnum mqPersistStateEnum);
}
