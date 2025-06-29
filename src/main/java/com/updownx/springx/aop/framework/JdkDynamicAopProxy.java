package com.updownx.springx.aop.framework;

import com.updownx.springx.aop.AdvisedSupport;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import org.aopalliance.intercept.MethodInterceptor;

/**
 * JDK-based {@link AopProxy} implementation for the Spring AOP framework, based on JDK {@link
 * java.lang.reflect.Proxy dynamic proxies}.
 *
 * <p>JDK 动态代理
 *
 * <p>
 */
public class JdkDynamicAopProxy implements AopProxy, InvocationHandler {

  private final AdvisedSupport advised;

  public JdkDynamicAopProxy(AdvisedSupport advised) {
    this.advised = advised;
  }

  @Override
  public Object getProxy() {
    return Proxy.newProxyInstance(
        Thread.currentThread().getContextClassLoader(),
        advised.getTargetSource().getTargetClass(),
        this);
  }

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    if (advised
        .getMethodMatcher()
        .matches(method, advised.getTargetSource().getTarget().getClass())) {
      MethodInterceptor methodInterceptor = advised.getMethodInterceptor();
      return methodInterceptor.invoke(
          new ReflectiveMethodInvocation(advised.getTargetSource().getTarget(), method, args));
    }
    return method.invoke(advised.getTargetSource().getTarget(), args);
  }
}
