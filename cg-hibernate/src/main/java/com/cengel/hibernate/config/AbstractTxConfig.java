package com.cengel.hibernate.config;

import com.cengel.starbucks.annotation.Description;
import org.hibernate.SessionFactory;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.interceptor.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @Title: 事务配置
 * @Description:
 * @Author zhz
 * @Time 2018/7/31 - 17:44
 * @Version V1.0
 **/
public abstract class AbstractTxConfig {

	private static final int TX_METHOD_TIMEOUT = 5;

	@Description("定义需要事务aop代理的service")
	abstract String[] getServiceBeanNames();

	@Description("定义事务传播行为")
	abstract Properties getTxProp();

	@Bean //注解-事务管理器
	public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
		HibernateTransactionManager dstm = new HibernateTransactionManager();
		dstm.setSessionFactory(sessionFactory);
		return dstm;
	}

	@Bean("txAdvice") //创建事务的aop代理
	public TransactionInterceptor txAdvice(HibernateTransactionManager transactionManager) {
		return new TransactionInterceptor(transactionManager, getTxProp());
	}

	@Bean
	public BeanNameAutoProxyCreator txProxy() {
		//todo 方案一，为不同的beanName绑定事务的aop代理
		BeanNameAutoProxyCreator creator = new BeanNameAutoProxyCreator();
		creator.setInterceptorNames("txAdvice");
		creator.setBeanNames(getServiceBeanNames());
		creator.setProxyTargetClass(true);
		return creator;
	}

	/********************|| 方案二 ||********************/
	//	@Bean //事务拦截器- 定义事务规则
	public TransactionInterceptor txAdviceName(HibernateTransactionManager transactionManager) {
		NameMatchTransactionAttributeSource source = new NameMatchTransactionAttributeSource();
		/*只读事务，不做更新操作*/
		RuleBasedTransactionAttribute readOnlyTx = new RuleBasedTransactionAttribute();
		readOnlyTx.setReadOnly(true);
		readOnlyTx.setPropagationBehavior(TransactionDefinition.PROPAGATION_NOT_SUPPORTED);
		/*当前存在事务就使用当前事务，当前不存在事务就创建一个新的事务*/
		RuleBasedTransactionAttribute requiredTx = new RuleBasedTransactionAttribute();
		requiredTx.setRollbackRules(Collections.singletonList(new RollbackRuleAttribute(Exception.class)));
		requiredTx.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		requiredTx.setTimeout(TX_METHOD_TIMEOUT);
		Map<String, TransactionAttribute> txMap = new HashMap<>();
		txMap.put("add*", requiredTx);
		txMap.put("save*", requiredTx);
		txMap.put("insert*", requiredTx);
		txMap.put("update*", requiredTx);
		txMap.put("delete*", requiredTx);
		txMap.put("*", readOnlyTx);//其他只读
		source.setNameMap(txMap);
		return new TransactionInterceptor(transactionManager, source);
	}

	//	@Bean
	public Advisor txAdviceAdvisor(TransactionInterceptor TransactionInterceptor) {
		AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
		pointcut.setExpression("execution (* com.cengel.dota..service.*.*(..))");
		return new DefaultPointcutAdvisor(pointcut, TransactionInterceptor);
	}

}
