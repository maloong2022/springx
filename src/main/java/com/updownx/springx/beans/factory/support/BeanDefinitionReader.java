package com.updownx.springx.beans.factory.support;

import com.updownx.springx.beans.BeansException;
import com.updownx.springx.core.io.Resource;
import com.updownx.springx.core.io.ResourceLoader;

public interface BeanDefinitionReader {
  BeanDefinitionRegistry getRegistry();

  ResourceLoader getResourceLoader();

  void loadBeanDefinitions(Resource resource) throws BeansException;

  void loadBeanDefinitions(Resource... resources) throws BeansException;

  void loadBeanDefinitions(String location) throws BeansException;

  void loadBeanDefinitions(String... locations) throws BeansException;
}
