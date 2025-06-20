package com.updownx.springx.beans.factory.support;

import com.updownx.springx.beans.BeansException;
import com.updownx.springx.beans.factory.config.BeanDefinition;

public interface BeanDefinitionRegistry {
  /**
   * 向注册表中注册 BeanDefinition
   *
   * @param beanName bean 名称
   * @param beanDefinition bean 定义
   */
  void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);

  /**
   * 使用 Bean 名称查询 BeanDefinition
   *
   * @param beanName Bean 名称
   * @return BeanDefinition
   * @throws BeansException 异常
   */
  BeanDefinition getBeanDefinition(String beanName) throws BeansException;

  /**
   * 判断是否包含指定名称的 BeanDefinition
   *
   * @param beanName Bean 名称
   * @return 检查结果
   */
  boolean containsBeanDefinition(String beanName);

  /**
   * Return the names of all beans defined in this registry.
   *
   * @return all beans' name list that defined in this registry
   */
  String[] getBeanDefinitionNames();
}
