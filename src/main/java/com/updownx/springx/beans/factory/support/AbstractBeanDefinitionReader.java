package com.updownx.springx.beans.factory.support;

import com.updownx.springx.core.io.DefaultResourceLoader;
import com.updownx.springx.core.io.ResourceLoader;

public abstract class AbstractBeanDefinitionReader implements BeanDefinitionReader {
  private final BeanDefinitionRegistry registry;
  private final ResourceLoader resourceLoader;

  public AbstractBeanDefinitionReader(BeanDefinitionRegistry registry) {
    this(registry, new DefaultResourceLoader());
  }

  public AbstractBeanDefinitionReader(
      BeanDefinitionRegistry registry, ResourceLoader resourceLoader) {
    this.registry = registry;
    this.resourceLoader = resourceLoader;
  }

  @Override
  public BeanDefinitionRegistry getRegistry() {
    return registry;
  }

  @Override
  public ResourceLoader getResourceLoader() {
    return resourceLoader;
  }
}
