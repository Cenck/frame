package com.cengel.mq.enums;

import lombok.Getter;
import lombok.Setter;

public enum MqPersistStateEnum {

	FAILED("failed","消息过程执行失败"),
	SUCCESS("success","成功");

	@Getter
	@Setter
	private String code;
	@Getter
	@Setter
	private String desc;

		private MqPersistStateEnum(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public static MqPersistStateEnum getByCode(String code) {
		if (code == null || "" == code) return null;
		MqPersistStateEnum[] allEnums = values();
		for (MqPersistStateEnum thisEnum : allEnums) {
			if (thisEnum.code.equals(code)) {
				return thisEnum;
			}
		}
		return null;
	}

}
