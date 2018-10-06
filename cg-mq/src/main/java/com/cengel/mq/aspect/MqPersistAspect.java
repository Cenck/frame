package com.cengel.mq.aspect;

import com.cengel.mq.annotation.MqPersistListening;
import com.cengel.mq.annotation.MqPersistSending;
import com.cengel.mq.biz.MqPersistBiz;
import com.cengel.mq.enums.MqPersistStateEnum;
import com.cengel.mq.mqe.BaseMqe;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import java.io.Serializable;

/**
 * @Title: 使mq持久化到本数据
 * @Description:
 * @Author zhz
 * @Time 2018/9/28 - 17:50
 * @Version V1.0
 **/
@Slf4j
@Aspect
public class MqPersistAspect {

	private MqPersistBiz persistBiz;

	public MqPersistAspect(MqPersistBiz mqPersistBiz) {
		this.persistBiz = mqPersistBiz;
	}

	@Around("execution(public * com.cengel..*.*(..)) && @annotation(sending)")
	public Object handleSending(ProceedingJoinPoint point, MqPersistSending sending) {
		Object ret = null;
		try {
			log.info("消息发送时，被持久到了数据库");
			ret = point.proceed();
			Object[] args = point.getArgs();
			if (args != null) for (Object arg : args) {
				if (arg instanceof BaseMqe) persistBiz.save((Serializable) arg);
			}
		} catch (Throwable throwable) {
			throwable.printStackTrace();
			throw new RuntimeException("错误");
		}
		return ret;
	}

	@Around("execution(public * com.cengel..*.*(..)) && @annotation(listening)")
	public Object handleListening(ProceedingJoinPoint point, MqPersistListening listening) {
		Object ret = null;
		Object[] args = point.getArgs();
		try {
			ret = point.proceed();
			log.info("mq消息接收并执行完毕，变更持久化状态为成功");
			if (args != null) for (Object arg : args) {
				if (arg instanceof BaseMqe) persistBiz.updateState(MqPersistStateEnum.SUCCESS);
			}
		} catch (Throwable throwable) {
			throwable.printStackTrace();
			log.info("mq消息接收并执行错误，执行次数加一");
			log.info("mq尝试错误后重发");
			if (args != null) for (Object arg : args) {
				if (arg instanceof BaseMqe) persistBiz.updateState(MqPersistStateEnum.FAILED);
			}
			throw new RuntimeException("错误");
		}
		return ret;
	}

}
