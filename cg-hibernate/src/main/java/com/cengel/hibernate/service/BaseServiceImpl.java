package com.cengel.hibernate.service;

import com.cengel.hibernate.dao.BaseDao;
import com.cengel.starbucks.model.entity.BEntity;
import com.cengel.starbucks.model.obj.DataGrid;
import com.cengel.starbucks.model.obj.Page;
import com.cengel.starbucks.model.obj.Params;
import lombok.Getter;
import org.springframework.orm.hibernate5.HibernateTemplate;

import javax.annotation.Resource;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * @Title:
 * @Description:
 * @Author zhz
 * @Time 2018/7/25 - 20:51
 * @Version V1.0
 **/
public class BaseServiceImpl<T extends BEntity, PK extends Serializable> implements BaseService<T, PK> {

	@Resource
	@Getter
	public HibernateTemplate hibernateTemplate;
	@Resource
	private   BaseDao           baseDao;

	@Override
	public BaseDao getBaseDao() {
		return baseDao;
	}

	@Override
	public DataGrid<T> pageLike(Params param, Page page) {
		T t = param.getEntity();
		return baseDao.page(t, page);
	}

	@Override
	public void saveOrUpdate(T t) {
		getBaseDao().save(t);
	}

	@Override
	public T get(PK id) {
		return (T) getBaseDao().get(id, getClassTypeByIndex(0));
	}

	@Override
	public T insert(T t) {
		return getBaseDao().insert(t);
	}

	@Override
	public void update(T t) {
		getBaseDao().update(t);
	}

	@Override
	public void del(PK id) {
		getBaseDao().del(id, getClassTypeByIndex(0));
	}

	@Override
	public List<T> find(T t) {
		return getBaseDao().find(t);
	}

	@Override
	public List<T> findLike(T t) {
		return getBaseDao().findLike(t);
	}

	@Override
	public List<T> findAll() {
		return getBaseDao().findAll(getClassTypeByIndex(0));
	}

	private Class getClassTypeByIndex(int index) {
		//TODO 获取泛型类 0是entity ,默认返回mapper
		if (index != 1) index = 0;
		Type genType = this.getClass()
				.getGenericSuperclass();
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
		return (Class) params[index];
	}

}
