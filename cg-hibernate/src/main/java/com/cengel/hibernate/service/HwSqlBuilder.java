package com.cengel.hibernate.service;

import com.cengel.hibernate.context.HibernateUtil;
import com.cengel.starbucks.db.helper.EntityHelper;
import com.cengel.starbucks.exception.BusinessException;
import com.cengel.starbucks.model.entity.BEntity;
import com.cengel.starbucks.model.obj.Page;
import com.cengel.starbucks.util.DateUtil;
import com.cengel.starbucks.util.ObjectUtil;
import com.cengel.starbucks.util.Validator;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.LockMode;
import org.hibernate.Session;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Title: hibernate-wake的sql语句构建器
 * @Description:
 * @Author zhz
 * @Time 2018/8/2 - 11:02
 * @Version V1.0
 **/
public abstract class HwSqlBuilder {

	public static BigInteger selectVersion(Session session,BEntity entity) {
		HibernateUtil.currentSession().lock(entity, LockMode.PESSIMISTIC_WRITE);
		String sql = "select version from " +EntityHelper.getDeclaredTableName(entity.getClass())+" where deleted = 0 and id = " + entity.getId();
		return (BigInteger) session.createSQLQuery(sql).setCacheable(false).uniqueResult();
	}

	//FROM CIS_TRIP
	public static String from(Class entity) {
		return " from " + EntityHelper.getDeclaredTableName(entity);
	}

	/**
	 * @Name: 构建sql模糊查询语句（不包含头部）
	 * @Description:  where name like %a%
	 * @author zhz
	 * @time 2018/8/2 - 11:06
	 **/
	public static String whereLike(BEntity entity) {
		if (entity == null) throw new BusinessException("入参的entity不能为空");
		Class<BEntity> entityClass = (Class<BEntity>) entity.getClass();
		StringBuffer sqlBuilder = new StringBuffer();
		try {
			if (entity.getDeleted() == null) entity.setDeleted(false);//默认取有效非删除数据
			List<String> andStrList = new ArrayList<>();
			whereLike_recycle(entityClass,entity,andStrList);
			for (int i = 0; i < andStrList.size(); i++) {
				String andStr = andStrList.get(i);
				if (i == 0) {
					sqlBuilder.append(" WHERE " + andStr);
				} else {
					//最后一个
					sqlBuilder.append(" AND " + andStr);
				}
			}
			sqlBuilder.append(" ");
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return sqlBuilder.toString();
	}
	private static void whereLike_recycle(Class entityclass,BEntity entity,List<String> andStrList) throws IllegalAccessException {
		//todo 递归遍历dto的父类，取tableAlias=value存放到StringList ，直到遍历到Entity.class注解的类为止
		for (Field field : entityclass.getDeclaredFields()) {
			if (!field.isAnnotationPresent(Column.class)) {
				continue;//todo 非声明数据库列字段跳过
			}
			field.setAccessible(true);
			Object value = field.get(entity);
			if (ObjectUtil.isNull(value)) {
				continue; //todo 值为空跳过
			}
			String javaName = field.getName(); //字段名
			String colName = field.getAnnotation(Column.class).name().toUpperCase();//数据库列名
			Class type = field.getType();
			if (String.class.isAssignableFrom(type)) {
				if (colName.toUpperCase().endsWith("ID")) {
					//todo id列不模糊查询
					andStrList.add(colName + " = '" + value.toString() + "' ");
				} else {
					andStrList.add(colName + " like '%" + value.toString() + "%' ");
				}
			} else if (Number.class.isAssignableFrom(type)) {
				andStrList.add(colName + " = " + value.toString());
			} else if (Date.class.isAssignableFrom(type)) {
				andStrList.add(colName + " = '" + DateUtil.format((Date) value, DateUtil.yyyy_MM_dd_HH_mm_ss) + "' ");
			}
		}
		if (!entityclass.isAnnotationPresent(Entity.class) && entityclass.getSuperclass()!=null){
			whereLike_recycle( entityclass.getSuperclass(), entity, andStrList);
		}
	}

	public static String renderSort(Page page,Class entityClass){
		if (Validator.isBlank(page.getSort())){
			return "";
		}
		for (Field field : entityClass.getDeclaredFields()) {
			if (field.isAnnotationPresent(Column.class)){
				String tableColName =	field.getAnnotation(Column.class).name();
				if (Validator.isBlank(tableColName)) continue;
				String fieldName = field.getName();
				if (fieldName.equals(page.getSort())|| tableColName.equalsIgnoreCase(page.getSort())){
					return "   order by  " + tableColName+"  " + (Validator.isBlank(page.getOrder())?" ":page.getOrder());
				}
			}
		}
		return "";
	}


	/********************|| sql构建器 ||********************/
	public static class SqlBuilder{
		private SqlBuilder(){
		}
		private SqlBuilder(String sql,boolean needWrap){
			if (needWrap){
				sqlSb.append("select * from ( ");
				sqlSb.append(sql);
				sqlSb.append(" ) ____temp" );
			}else{
				sqlSb.append(sql);
			}

		}

		@Getter
		@Setter
		private StringBuffer sqlSb =  new StringBuffer();;

		public SqlBuilder from(Class entity) {
			sqlSb.append(HwSqlBuilder.from(entity));
			return this;
		}

		public SqlBuilder whereLike(BEntity entity) {
			sqlSb.append(HwSqlBuilder.whereLike(entity));
			return this;
		}

		public SqlBuilder renderSort(Page page,Class entityClass){
			sqlSb.append(HwSqlBuilder.renderSort(page,entityClass));
			return this;
		}

		public String build(){
			return this.getSqlSb().toString();
		}
	}
	public static SqlBuilder create(){
		return new SqlBuilder();
	}
	public static SqlBuilder createAndWrap(String selectSql){
		//todo 使用select*from(？)___temp___ 包裹selectSql
		return new SqlBuilder(selectSql,true);
	}
	public static SqlBuilder create(String selectSql){
		return new SqlBuilder(selectSql,false);
	}
}
