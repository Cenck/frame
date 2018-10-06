## CGLIB动态代理
#### 第一步： 切面拦截器
```angular2html
private static Object doPrintMethodTime(Method method, CglibCallback cglibCallback) {
    long start = System.currentTimeMillis();
    Object result = cglibCallback.exec();
    System.out.println("["+method.getName() + "方法执行计时] 消耗: --> " + (System.currentTimeMillis() - start));
    return result;
}
```

#### 第二步 ： 创建动态代理
```$xslt
public static <T> T createProxy(Class<T> targetClass) {
    Enhancer enhancer = new Enhancer();
    enhancer.setCallback(interceptor);
    enhancer.setSuperclass(targetClass);
    return (T) enhancer.create();
}
```

