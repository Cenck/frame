package com.cengel.hibernate.context;

import com.cengel.starbucks.annotation.Description;
import lombok.Data;
import org.hibernate.Session;

import java.io.Serializable;

/**
 * @Title:
 * @Description:
 * @Author zhz
 * @Time 2018/9/12 - 20:02
 * @Version V1.0
 **/
@Data
public class TlSessionBean implements Serializable {

	@Description("是否为线程自定义session")
	private Boolean custom;
	private Session session;
}
