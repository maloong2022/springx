package com.updownx.springx.beans.factory.support;

import cn.hutool.core.bean.BeanException;
import cn.hutool.core.util.StrUtil;
import com.updownx.springx.beans.factory.DisposableBean;
import com.updownx.springx.beans.factory.config.BeanDefinition;
import java.lang.reflect.Method;

/**
 * Adapter that implements the {@link DisposableBean} and {@link Runnable} interfaces performing
 * various destruction steps on a given bean instance:
 *
 * <p>
 */
public class DisposableBeanAdapter implements DisposableBean {
  private final Object bean;
  private final String beanName;
  private final String destroyMethodName;

  public DisposableBeanAdapter(Object bean, String beanName, BeanDefinition beanDefinition) {
    this.bean = bean;
    this.beanName = beanName;
    this.destroyMethodName = beanDefinition.getDestroyMethodName();
  }

  @Override
  public void destroy() throws Exception {
    // 1. 实现接口 DisposableBean
    if (bean instanceof DisposableBean) {
      ((DisposableBean) bean).destroy();
    }

    // 2. 注解配置 destroy-method{判断是为了避免二次执行销毁}
    if (StrUtil.isNotEmpty(destroyMethodName)
        && !(bean instanceof DisposableBean && "destroy".equals(this.destroyMethodName))) {
      Method destroyMethod = bean.getClass().getMethod(destroyMethodName);
      if (null == destroyMethod) {
        throw new BeanException(
            "Cannot find destroy method " + destroyMethodName + " in bean " + beanName);
      }
      destroyMethod.invoke(bean);
    }
  }
}
