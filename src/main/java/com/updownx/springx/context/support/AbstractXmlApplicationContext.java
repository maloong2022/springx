package com.updownx.springx.context.support;

import com.updownx.springx.beans.factory.support.DefaultListableBeanFactory;
import com.updownx.springx.beans.factory.xml.XmlBeanDefinitionReader;

/**
 * Convenient base class for {@link com.updownx.springx.context.ApplicationContext} implementations,
 * drawing configuration from XML documents containing bean definitions understood by an {@link
 * com.updownx.springx.beans.factory.xml.XmlBeanDefinitionReader}.
 */
public abstract class AbstractXmlApplicationContext extends AbstractRefreshableApplicationContext {
  @Override
  protected void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) {
    XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
    String[] configLocations = getConfigLocations();
    if (null != configLocations) {
      beanDefinitionReader.loadBeanDefinitions(configLocations);
    }
  }

  protected abstract String[] getConfigLocations();
}
