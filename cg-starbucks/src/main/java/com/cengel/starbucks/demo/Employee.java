package com.cengel.starbucks.demo;

import com.cengel.starbucks.factory.cglib.PrintMethodCostTime;
import com.cengel.starbucks.model.entity.BaseEntity;
import com.cengel.starbucks.util.SoutUtil;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.io.Serializable;
import java.lang.reflect.Field;

/**
 * @Title: 员工测试
 * @Description:
 * @Author zhz
 * @Time 2018/7/5 - 9:52
 * @Version V1.0
 **/
@Getter
@Setter
public class Employee extends BaseEntity<Integer> implements Serializable {

	public Employee() {}

	public Employee(Integer id, String name, Integer deptNo, String deptName, String mobile) {
		this.setId(id);
		this.setName(name);
		setDeptNo(deptNo);
		setDeptName(deptName);
		setMobile(mobile);
	}

	public void init() {
		this.usrKey = this.getId() + "-" + this.mobile;
	}

	@Column(name = "NAME")
	private String  name;
	private Integer deptNo;
	private String  deptName;
	private String  mobile;

	private transient String  usrKey; //id+mobile组合
	private transient Student student;

	public String getUsrKey() {
		return this.usrKey;
	}

	public void setUsrKey() {
		this.usrKey = this.getId() + "-" + this.mobile;
	}

	@PrintMethodCostTime
	public void destroy() {
		SoutUtil.print("员工：" + name + "销毁...");
	}

	public static void main(String[] args) {
		for (Field field : Employee.class.getDeclaredFields()) {
			System.out.println(field.getName());
		}
	}
}
