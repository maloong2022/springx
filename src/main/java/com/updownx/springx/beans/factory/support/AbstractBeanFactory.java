package com.updownx.springx.beans.factory.support;

import com.updownx.springx.beans.BeansException;
import com.updownx.springx.beans.factory.FactoryBean;
import com.updownx.springx.beans.factory.config.BeanDefinition;
import com.updownx.springx.beans.factory.config.BeanPostProcessor;
import com.updownx.springx.beans.factory.config.ConfigurableBeanFactory;
import com.updownx.springx.util.ClassUtils;
import com.updownx.springx.util.StringValueResolver;
import java.util.ArrayList;
import java.util.List;

/** BeanDefinition注册表接口 */
public abstract class AbstractBeanFactory extends FactoryBeanRegistrySupport
    implements ConfigurableBeanFactory {
  /** BeanPostProcessors to apply in createBean */
  private final List<BeanPostProcessor> beanPostProcessors = new ArrayList<BeanPostProcessor>();

  /** ClassLoader to resolve bean class names with, if necessary */
  private final ClassLoader beanClassLoader = ClassUtils.getDefaultClassLoader();

  /** String resolvers to apply e.g. to annotation attribute values */
  private final List<StringValueResolver> embeddedValueResolvers = new ArrayList<>();

  @Override
  public Object getBean(String name) throws BeansException {
    return doGetBean(name, null);
  }

  @Override
  public Object getBean(String name, Object... args) throws BeansException {
    return doGetBean(name, args);
  }

  @Override
  public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
    return (T) getBean(name);
  }

  protected <T> T doGetBean(String name, Object... args) {
    Object sharedInstance = getSingleton(name);
    if (sharedInstance != null) {
      // 如果是 FactoryBean，则需要调用 FactoryBean#getObject
      return (T) getObjectForBeanInstance(sharedInstance, name);
    }
    BeanDefinition beanDefinition = getBeanDefinition(name);
    Object bean = createBean(name, beanDefinition, args);
    return (T) getObjectForBeanInstance(bean, name);
  }

  private Object getObjectForBeanInstance(Object beanInstance, String beanName) {
    if (!(beanInstance instanceof FactoryBean)) {
      return beanInstance;
    }

    Object object = getCachedObjectForFactoryBean(beanName);

    if (object == null) {
      FactoryBean<?> factoryBean = (FactoryBean<?>) beanInstance;
      object = getObjectFromFactoryBean(factoryBean, beanName);
    }

    return object;
  }

  @Override
  public void addEmbeddedValueResolver(StringValueResolver valueResolver) {
    this.embeddedValueResolvers.add(valueResolver);
  }

  @Override
  public String resolveEmbeddedValue(String value) {
    String result = value;
    for (StringValueResolver resolver : this.embeddedValueResolvers) {
      result = resolver.resolveStringValue(result);
    }
    return result;
  }

  protected abstract Object createBean(String name, BeanDefinition beanDefinition, Object[] args)
      throws BeansException;

  protected abstract BeanDefinition getBeanDefinition(String name) throws BeansException;

  @Override
  public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
    this.beanPostProcessors.remove(beanPostProcessor);
    this.beanPostProcessors.add(beanPostProcessor);
  }

  /**
   * Return the list of BeanPostProcessors that will get applied to beans created with this factory.
   */
  public List<BeanPostProcessor> getBeanPostProcessors() {
    return this.beanPostProcessors;
  }

  public ClassLoader getBeanClassLoader() {
    return this.beanClassLoader;
  }
}
