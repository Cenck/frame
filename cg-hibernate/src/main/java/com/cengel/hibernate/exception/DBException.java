package com.cengel.hibernate.exception;

import lombok.NoArgsConstructor;

/**
 * @Title:
 * @Description:
 * @Author zhz
 * @Time 2018/9/23 - 19:47
 * @Version V1.0
 **/
@NoArgsConstructor
public class DBException extends RuntimeException{

	public DBException(String msg){
		super(msg);
	}
}
