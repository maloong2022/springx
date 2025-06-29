package com.updownx.springx.aop.framework.adapter;

import com.updownx.springx.aop.MethodBeforeAdvice;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * Interceptor to wrap am {@link com.updownx.springx.aop.MethodBeforeAdvice}. Used internally by the
 * AOP framework; application developers should not need to use this class directly.
 */
public class MethodBeforeAdviceInterceptor implements MethodInterceptor {

  private MethodBeforeAdvice advice;

  public MethodBeforeAdviceInterceptor() {}

  public MethodBeforeAdviceInterceptor(MethodBeforeAdvice advice) {
    this.advice = advice;
  }

  @Override
  public Object invoke(MethodInvocation methodInvocation) throws Throwable {
    this.advice.before(
        methodInvocation.getMethod(), methodInvocation.getArguments(), methodInvocation.getThis());
    return methodInvocation.proceed();
  }
}
