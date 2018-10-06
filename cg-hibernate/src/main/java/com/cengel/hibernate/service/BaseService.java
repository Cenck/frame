package com.cengel.hibernate.service;

import com.cengel.hibernate.dao.BaseDao;
import com.cengel.starbucks.model.entity.BEntity;
import com.cengel.starbucks.model.obj.DataGrid;
import com.cengel.starbucks.model.obj.Page;
import com.cengel.starbucks.model.obj.Params;
import org.springframework.orm.hibernate5.HibernateTemplate;

import java.io.Serializable;
import java.util.List;

public interface BaseService<T extends BEntity, PK extends Serializable> {

	BaseDao getBaseDao();
	DataGrid<T> pageLike(Params param, Page page);
	HibernateTemplate getHibernateTemplate();

	void saveOrUpdate(T t);

	T get(PK id);

	T insert(T t);

	void update(T t);

	void del(PK id);

	List<T> find(T t);

	List<T> findLike(T t);

	List<T> findAll();

}
