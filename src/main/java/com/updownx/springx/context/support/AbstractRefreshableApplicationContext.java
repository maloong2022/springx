package com.updownx.springx.context.support;

import com.updownx.springx.beans.BeansException;
import com.updownx.springx.beans.factory.ConfigurableListableBeanFactory;
import com.updownx.springx.beans.factory.support.DefaultListableBeanFactory;

/**
 * Base class for {@link com.updownx.springx.context.ApplicationContext} implementations which are
 * supposed to support multiple calls to {@link #refresh()}, creating a new internal bean factory
 * instance every time. Typically (but not necessarily), such a context will be driven by a set of
 * config locations to load bean definitions from.
 */
public abstract class AbstractRefreshableApplicationContext extends AbstractApplicationContext {
  private DefaultListableBeanFactory beanFactory;

  @Override
  protected void refreshBeanFactory() throws BeansException {
    DefaultListableBeanFactory beanFactory = createBeanFactory();
    loadBeanDefinitions(beanFactory);
    this.beanFactory = beanFactory;
  }

  private DefaultListableBeanFactory createBeanFactory() {
    return new DefaultListableBeanFactory();
  }

  protected abstract void loadBeanDefinitions(DefaultListableBeanFactory beanFactory);

  @Override
  protected ConfigurableListableBeanFactory getBeanFactory() {
    return beanFactory;
  }
}
