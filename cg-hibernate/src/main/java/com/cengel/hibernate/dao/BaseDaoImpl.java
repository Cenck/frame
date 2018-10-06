package com.cengel.hibernate.dao;

import com.cengel.hibernate.exception.DBException;
import com.cengel.hibernate.trasnform.DoResultTransformer;
import com.cengel.hibernate.trasnform.DtoResultTransformer;
import com.cengel.starbucks.db.helper.EntityHelper;
import com.cengel.starbucks.model.entity.BEntity;
import com.cengel.starbucks.model.entity.Entity;
import com.cengel.starbucks.model.entity.VersionEntity;
import com.cengel.starbucks.model.obj.DataGrid;
import com.cengel.starbucks.model.obj.Page;
import com.cengel.starbucks.util.AssertUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.orm.hibernate5.HibernateTemplate;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

import static com.cengel.hibernate.constant.RedisCacheKey.ENTITY_KEY_PREFIX;

/**
 * @Title:
 * @Description:
 * @Author zhz
 * @Time 2018/8/1 - 8:56
 * @Version V1.0
 **/
@Slf4j
public class BaseDaoImpl implements BaseDao {

	@Resource
	private HibernateTemplate hibernateTemplate;

	public static <OBJ> void queryDTO11(OBJ o) {

	}

	@Override
	public <OBJ> OBJ queryDTO(String sql, Class entityClass) {
		return hibernateTemplate.execute((session -> {
			if (entityClass.isAnnotationPresent(Entity.class)) return (OBJ) session.createSQLQuery(sql).addEntity(entityClass).uniqueResult();
			else return (OBJ) session.createSQLQuery(sql).setResultTransformer(new DtoResultTransformer(entityClass)).uniqueResult();
		}));
	}

	@Override
	public <OBJ> List<OBJ> listDTO(String sql, Class entityClass) {
		return hibernateTemplate.execute((session -> {
			if (entityClass.isAnnotationPresent(Entity.class)) return (List<OBJ>) session.createSQLQuery(sql).addEntity(entityClass).list();
			else return (List<OBJ>) session.createSQLQuery(sql).setResultTransformer(new DtoResultTransformer(entityClass)).list();
		}));
	}

	@Override
	public <O> O queryDO(String sql, Class entityClass) {
		return hibernateTemplate.execute((session -> (O) session.createSQLQuery(sql).setResultTransformer(new DoResultTransformer(entityClass)).uniqueResult()));
	}

	@Override
	public <O> List<O> listDO(String sql, Class entityClass) {
		return hibernateTemplate.execute((session -> (List<O>) session.createSQLQuery(sql).setResultTransformer(new DoResultTransformer(entityClass)).list()));
	}

	@Override
	public <O> DataGrid<O> page(BEntity retObj, Page page) {
		DetachedCriteria criteria = CriteriaUtil.buildByEntity(retObj);
		List<O> list = (List<O>) this.hibernateTemplate.findByCriteria(criteria, page.getOffset(), page.getLimit());
		Long total = (Long) criteria.setProjection(Projections.rowCount()).getExecutableCriteria(this.hibernateTemplate.getSessionFactory().getCurrentSession()).uniqueResult();
		if (total == null) total = 0L;
		return new DataGrid<O>(list, total.intValue(), page.getOffsetLimit(), page.getLimit());
	}

	//如果数据无效，则禁用缓存
	@Cacheable(value = ENTITY_KEY_PREFIX, key = "#p1.getSimpleName()+'_'+#id", unless = "#result==null or (#result.getDeleted()!=null and #result.getDeleted()==1) ")
	public <T extends BEntity, PK extends Serializable> T get(PK id, Class<T> entityClass) {
		return hibernateTemplate.get(entityClass, id);
	}

	@CachePut(value = ENTITY_KEY_PREFIX, key = "#p0.getClass().getSimpleName()+'_'+#result.getId()")
	public <T extends BEntity> T insert(T t) {
		if (t.getDeleted() == null) t.setDeleted(false);
		if (t instanceof VersionEntity && ((VersionEntity) t).getVersion() == null) {
			((VersionEntity) t).setVersion(0L);
		}
		hibernateTemplate.save(t);
		return t;
	}

	@CachePut(value = ENTITY_KEY_PREFIX, key = "#p0.getClass().getSimpleName()+'_'+#t.getId()")
	public <T extends BEntity> void update(T t) {
		AssertUtil.notNull(t.getId(), "id不能为空");
		if (t.getDeleted() == null) t.setDeleted(false);
		if (t instanceof VersionEntity) {
			((VersionEntity) t).setVersion(((VersionEntity) t).getVersion() + 1);
			log.info(" 新版本号：{}",   ((VersionEntity) t).getVersion());
		}
		hibernateTemplate.update(t);
	}

	@CachePut(value = ENTITY_KEY_PREFIX, key = "#p0.getClass().getSimpleName()+'_'+#result.getId()")
	public <T extends BEntity> T save(T t) {
		if (t.getDeleted() == null) t.setDeleted(false);
		hibernateTemplate.saveOrUpdate(t);
		return t;
	}

	@Override
	@CacheEvict(value = ENTITY_KEY_PREFIX, key = "#result.getSimpleName()+'_'+#id") //删除缓存
	public <T extends BEntity, PK extends Serializable> T del(PK id, Class<T> entityClass) {
		T t = get(id, entityClass);
		t.setDeleted(false);
		update(t);
		return t;
	}

	public <T extends BEntity> List<T> find(T t) {
		t.setDeleted(false);
		return hibernateTemplate.findByExample(t);
	}

	public <T extends BEntity> List<T> findLike(T t) {
		return null;
	}

	@Override
	public <T extends BEntity> List<T> findAll(Class<T> entityClass) {
		try {
			T t = entityClass.newInstance();
			return find(t);
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getEntityTableName(Class c) {
		return EntityHelper.getDeclaredTableName(c);
	}

}
