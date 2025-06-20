package com.updownx.springx.beans.factory.support;

import com.updownx.springx.beans.BeansException;
import com.updownx.springx.beans.factory.ConfigurableListableBeanFactory;
import com.updownx.springx.beans.factory.config.BeanDefinition;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory
    implements BeanDefinitionRegistry, ConfigurableListableBeanFactory {
  private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

  @Override
  public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
    beanDefinitionMap.put(beanName, beanDefinition);
  }

  @Override
  public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
    Map<String, T> result = new HashMap<>();
    beanDefinitionMap.forEach(
        (beanName, beanDefinition) -> {
          Class beanClass = beanDefinition.getBeanClass();
          if (type.isAssignableFrom(beanClass)) {
            result.put(beanName, (T) getBean(beanName));
          }
        });
    return result;
  }

  @Override
  public void preInstantiateSingletons() throws BeansException {
    beanDefinitionMap.keySet().forEach(this::getBean);
  }

  @Override
  public BeanDefinition getBeanDefinition(String beanName) {
    BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
    if (beanDefinition == null)
      throw new BeansException("Bean with name '" + beanName + "' not found");
    return beanDefinition;
  }

  @Override
  public boolean containsBeanDefinition(String beanName) {
    return beanDefinitionMap.containsKey(beanName);
  }

  @Override
  public String[] getBeanDefinitionNames() {
    return beanDefinitionMap.keySet().toArray(new String[0]);
  }
}
