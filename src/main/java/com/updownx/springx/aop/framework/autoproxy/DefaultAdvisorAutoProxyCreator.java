package com.updownx.springx.aop.framework.autoproxy;

import com.updownx.springx.aop.*;
import com.updownx.springx.aop.aspectj.AspectJExpressionPointcutAdvisor;
import com.updownx.springx.aop.framework.ProxyFactory;
import com.updownx.springx.beans.BeansException;
import com.updownx.springx.beans.PropertyValues;
import com.updownx.springx.beans.factory.BeanFactory;
import com.updownx.springx.beans.factory.BeanFactoryAware;
import com.updownx.springx.beans.factory.config.InstantiationAwareBeanPostProcessor;
import com.updownx.springx.beans.factory.support.DefaultListableBeanFactory;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;

/**
 * BeanPostProcessor implementation that creates AOP proxies based on all candidate Advisors in the
 * current BeanFactory. This class is completely generic; it contains no special code to handle any
 * particular aspects, such as pooling aspects.
 *
 * <p>
 */
public class DefaultAdvisorAutoProxyCreator
    implements InstantiationAwareBeanPostProcessor, BeanFactoryAware {

  private final Set<Object> earlyProxyReferences =
      Collections.synchronizedSet(new HashSet<Object>());
  private DefaultListableBeanFactory beanFactory;

  @Override
  public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
    this.beanFactory = (DefaultListableBeanFactory) beanFactory;
  }

  @Override
  public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName)
      throws BeansException {

    //    if (isInfrastructureClass(beanClass)) return null;
    //
    //    Collection<AspectJExpressionPointcutAdvisor> advisors =
    //        beanFactory.getBeansOfType(AspectJExpressionPointcutAdvisor.class).values();
    //
    //    for (AspectJExpressionPointcutAdvisor advisor : advisors) {
    //      ClassFilter classFilter = advisor.getPointcut().getClassFilter();
    //      if (!classFilter.matches(beanClass)) continue;
    //
    //      AdvisedSupport advisedSupport = new AdvisedSupport();
    //
    //      TargetSource targetSource = null;
    //      try {
    //        targetSource = new TargetSource(beanClass.getDeclaredConstructor().newInstance());
    //      } catch (Exception e) {
    //        e.printStackTrace();
    //      }
    //      advisedSupport.setTargetSource(targetSource);
    //      advisedSupport.setMethodInterceptor((MethodInterceptor) advisor.getAdvice());
    //      advisedSupport.setMethodMatcher(advisor.getPointcut().getMethodMatcher());
    //      advisedSupport.setProxyTargetClass(false);
    //
    //      return new ProxyFactory(advisedSupport).getProxy();
    //    }

    return null;
  }

  @Override
  public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
    return true;
  }

  private boolean isInfrastructureClass(Class<?> beanClass) {
    return Advice.class.isAssignableFrom(beanClass)
        || Pointcut.class.isAssignableFrom(beanClass)
        || Advisor.class.isAssignableFrom(beanClass);
  }

  @Override
  public Object postProcessBeforeInitialization(Object bean, String beanName)
      throws BeansException {
    return bean;
  }

  @Override
  public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
    if (!earlyProxyReferences.contains(beanName)) {
      return wrapIfNecessary(bean, beanName);
    }

    return bean;
  }

  protected Object wrapIfNecessary(Object bean, String beanName) {
    if (isInfrastructureClass(bean.getClass())) return bean;

    Collection<AspectJExpressionPointcutAdvisor> advisors =
        beanFactory.getBeansOfType(AspectJExpressionPointcutAdvisor.class).values();

    for (AspectJExpressionPointcutAdvisor advisor : advisors) {
      ClassFilter classFilter = advisor.getPointcut().getClassFilter();
      // 过滤匹配类
      if (!classFilter.matches(bean.getClass())) continue;

      AdvisedSupport advisedSupport = new AdvisedSupport();

      TargetSource targetSource = new TargetSource(bean);
      advisedSupport.setTargetSource(targetSource);
      advisedSupport.setMethodInterceptor((MethodInterceptor) advisor.getAdvice());
      advisedSupport.setMethodMatcher(advisor.getPointcut().getMethodMatcher());
      advisedSupport.setProxyTargetClass(true);

      // 返回代理对象
      return new ProxyFactory(advisedSupport).getProxy();
    }

    return bean;
  }

  @Override
  public Object getEarlyBeanReference(Object bean, String beanName) {
    earlyProxyReferences.add(beanName);
    return wrapIfNecessary(bean, beanName);
  }

  @Override
  public PropertyValues postProcessPropertyValues(PropertyValues pvs, Object bean, String beanName)
      throws BeansException {
    return pvs;
  }
}
