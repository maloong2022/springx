package com.updownx.springx.test.common;

import com.updownx.springx.beans.BeansException;
import com.updownx.springx.beans.PropertyValue;
import com.updownx.springx.beans.PropertyValues;
import com.updownx.springx.beans.factory.ConfigurableListableBeanFactory;
import com.updownx.springx.beans.factory.config.BeanDefinition;
import com.updownx.springx.beans.factory.config.BeanFactoryPostProcessor;

public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

  @Override
  public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory)
      throws BeansException {
    BeanDefinition beanDefinition = beanFactory.getBeanDefinition("userService");
    PropertyValues propertyValues = beanDefinition.getPropertyValues();
    propertyValues.addPropertyValue(new PropertyValue("company", "改为： 字节跳动"));
  }
}
