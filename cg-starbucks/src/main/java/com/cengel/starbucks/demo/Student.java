package com.cengel.starbucks.demo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Title:
 * @Description:
 * @Author zhz
 * @Time 2018/7/5 - 10:16
 * @Version V1.0
 **/
@Data
public class Student  implements Serializable {

	public Student(){}
	public Student (int id,String name){
		this.id = id;
		this.name=name;
	}

	private String name;
	private String schoolName;
	private int id;
}
