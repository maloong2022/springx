package com.updownx.springx.beans.factory.support;

import com.updownx.springx.beans.BeansException;
import com.updownx.springx.beans.factory.config.BeanDefinition;
import java.lang.reflect.Constructor;

/** Bean实例化策略 */
public interface InstantiationStrategy {
  Object instantiate(
      BeanDefinition beanDefinition, String beanName, Constructor ctor, Object[] args)
      throws BeansException;
}
