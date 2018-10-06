## 1.技术亮点
_2018年7月31日20:15:14_

### 1.1 注解开启事务

#### 1.1.1 在spring-applictaion.xml中配置事务 <tx:advice>
```
<description>数据库连接池及事务配置。</description>

	<!-- 事务管理器 -->
	<bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
		<property name="dataSource" ref="dataSource" />
	</bean>

	<!-- 事务配置 -->
	<tx:advice id="txAdvice" transaction-manager="transactionManager">
		<tx:attributes>
			<tx:method name="insert*" propagation="REQUIRED" />
			<tx:method name="update*" propagation="REQUIRED" />
			<tx:method name="del*" propagation="REQUIRED" />
			<tx:method name="add*" propagation="REQUIRED" />
			<tx:method name="do*" propagation="REQUIRED" />
			<tx:method name="newTran*" propagation="REQUIRES_NEW" />
			<tx:method name="*" read-only="true" />
		</tx:attributes>
	</tx:advice>
	<aop:config>
		<aop:pointcut id="servicePointCut" expression="execution(* com.weiare..*Service.*(..))" />
		<aop:pointcut id="facadePointCut" expression="execution(* com.weiare..*Facade.*(..))" />
		<aop:pointcut id="serverPointCut" expression="execution(* com.weiare..*Server.*(..))" />
		<aop:advisor advice-ref="txAdvice" pointcut-ref="servicePointCut" />
		<aop:advisor advice-ref="txAdvice" pointcut-ref="facadePointCut" />
		<aop:advisor advice-ref="txAdvice" pointcut-ref="serverPointCut" />
	</aop:config>
```

#### 1.1.2 注解配置事务

1.声明事务管理器
```
@Bean //注解-事务管理器
public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
    HibernateTransactionManager dstm = new HibernateTransactionManager();
    dstm.setSessionFactory(sessionFactory);
    return dstm;
}
```
2.创建代理拦截器 aop切面事务通知-txAdvice
```
@Bean("txAdvice") //创建事务的aop代理
public TransactionInterceptor txAdvice(HibernateTransactionManager transactionManager){
    Properties properties = new Properties();
    properties.setProperty("add*", "PROPAGATION_REQUIRED,-Exception");
    properties.setProperty("save*", "PROPAGATION_REQUIRED,-Exception");
    properties.setProperty("update*", "PROPAGATION_REQUIRED,-Exception");
    properties.setProperty("delete*", "PROPAGATION_REQUIRED,-Exception");
    return new TransactionInterceptor(transactionManager,properties);
}
```
3.为业务层Service绑定txAdvice
- 方案一 : 按通配的beanName创建txAdvice(事务的aop切面代理)
```
@Bean
public BeanNameAutoProxyCreator txProxy() {
    //todo 方案一，为不同的beanName创建事务的aop代理
    BeanNameAutoProxyCreator creator = new BeanNameAutoProxyCreator();
    creator.setInterceptorNames("txAdvice");
    creator.setBeanNames(getServiceBeanNames());
    creator.setProxyTargetClass(true);
    return creator;
}
```
- 方案二 : 配置切点，并将切点绑定切面
```
@Bean
public Advisor txAdviceAdvisor(TransactionInterceptor TransactionInterceptor) {
    AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
    pointcut.setExpression("execution (* com.cengel.dota..service.*.*(..))");
    return new DefaultPointcutAdvisor(pointcut, TransactionInterceptor);
}
```
