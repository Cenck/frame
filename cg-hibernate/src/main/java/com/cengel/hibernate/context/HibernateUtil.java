package com.cengel.hibernate.context;

import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 * 该工具类提供了一个属性：SessionFactory sessionFactory
 * 并创建了sessionFactory 将它设置成static 这样其他程序就可以直接通过此工具类引用
 * 提供了二个方法:
 * 1：通过线程创建Session-->currentSession()
 * 2：关闭Session-->closeSession()
 * 需要在主类中手动关闭sessionFactory
 */
public class HibernateUtil {

	//创建Session
	public static Session currentSession() throws HibernateException {
		return HwCurrentSessionContext.getCurrentSession();
	}
	//创建Session
	public static Session newSession() throws HibernateException {
		return HwCurrentSessionContext.newSession();
	}
	//创建Session
	public static void setCurrentSession(Session session) throws HibernateException {
		HwCurrentSessionContext.setTlCurrentSession(session);
	}

	//关闭Session
	public static void closeCurrentSession() throws HibernateException {
		HwCurrentSessionContext.closeSession();
	}


}
