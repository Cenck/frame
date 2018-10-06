package com.cengel.hibernate.config;

import com.cengel.hibernate.dao.BaseDao;
import com.cengel.hibernate.dao.BaseDaoImpl;
import com.cengel.hibernate.interceptor.HibernateInterceptor;
import com.cengel.starbucks.annotation.Description;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.hibernate5.support.OpenSessionInViewFilter;

import javax.sql.DataSource;
import java.util.Objects;
import java.util.Properties;

/**
 * @Title: 请继承此类, 无法@Import本类
 * @Description:
 * @Author zhz
 * @Time 2018/7/25 - 20:40
 * @Version V1.0
 **/
public abstract class AbstractHibernateConfig extends AbstractTxConfig {

	@Description("hibernate的扫描Entity包路径")
	abstract String[] getEntityScanPkgs();

	@Description("定义事务传播行为")
	protected Properties getTxProp() {
		//todo 重写此方法 来自定义事务的传播行为
		Properties properties = new Properties();
		properties.setProperty("insert*", "PROPAGATION_REQUIRED,-Exception");
		properties.setProperty("save*", "PROPAGATION_REQUIRED,-Exception");
		properties.setProperty("update*", "PROPAGATION_REQUIRED,-Exception");
		properties.setProperty("delete*", "PROPAGATION_REQUIRED,-Exception");
		properties.setProperty("do*", "PROPAGATION_REQUIRED,-Exception");
		properties.setProperty("*", "PROPAGATION_REQUIRED,-Exception,readOnly");
		return properties;
	}

	@Description("定义需要事务aop代理的service")
	protected String[] getServiceBeanNames() {
		//todo 重写此方法 来为相应的serviceBean配置事务aop切面
		return new String[] { "*Service", "*ServiceImpl", "*Facade", "*FacadeImpl", "*Biz", "*BizImpl", "*Dao", "*DaoImpl" };
	}

	//=================HIBERNATE===============
	@Bean
	public OpenSessionInViewFilter sessionInViewFilter() {
		// todo 延长了session关闭的时间，支持懒加载，但是会使session的生命周期变长
		OpenSessionInViewFilter openSessionInViewFilter = new OpenSessionInViewFilter();
		openSessionInViewFilter.setSessionFactoryBeanName("localSessionFactoryBean");
		return openSessionInViewFilter;
	}

	@Bean
	public LocalSessionFactoryBean localSessionFactoryBean(DataSource dataSource) {
		LocalSessionFactoryBean bean = new LocalSessionFactoryBean();
		bean.setDataSource(dataSource);
		bean.setConfigLocation(new ClassPathResource("/hibernate/hibernate.cfg.xml"));
		bean.setPackagesToScan(getEntityScanPkgs()); //Hibernate-Entity扫描路径
		bean.setEntityInterceptor(hibernateInterceptor());
		return bean;
	}

	@Bean
	public HibernateInterceptor hibernateInterceptor() {
		return new HibernateInterceptor();
	}

	@Bean
	public HibernateTemplate hibernateTemplate(LocalSessionFactoryBean bean) {
		return new HibernateTemplate(Objects.requireNonNull(bean.getObject()));
	}

	@Bean
	public JdbcTemplate jdbcTemplate(DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}

	@Bean
	public BaseDao baseDao() {
		return new BaseDaoImpl();
	}

}
