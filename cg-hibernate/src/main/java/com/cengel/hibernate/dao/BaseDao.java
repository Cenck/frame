package com.cengel.hibernate.dao;

import com.cengel.starbucks.model.entity.BEntity;
import com.cengel.starbucks.model.entity.Entity;
import com.cengel.starbucks.model.obj.DataGrid;
import com.cengel.starbucks.model.obj.Page;

import java.io.Serializable;
import java.util.List;

public interface BaseDao {

	<T extends BEntity,PK extends Serializable> T get(PK id, Class<T> entityClass);

	<T extends BEntity> T insert(T t);

	<T extends BEntity> void update(T t);

	<T extends BEntity> T save(T t);

	<T extends BEntity,PK extends Serializable> T del(PK id, Class<T> entityClass);

	<T extends BEntity> List<T> find(T t);

	<T extends BEntity> List<T> findLike(T t);

	<T extends BEntity> List<T> findAll(Class<T> entityClass);

	<O> O queryDTO(String sql, Class entityClass);
	<O> List<O> listDTO(String sql, Class entityClass);
	<O> O queryDO(String sql, Class entityClass);
	<O> List<O> listDO(String sql, Class entityClass);
	<O> DataGrid<O> page(BEntity obj, Page page);

}