package com.updownx.springx.beans.factory.annotation;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.TypeUtil;
import com.updownx.springx.beans.BeansException;
import com.updownx.springx.beans.PropertyValues;
import com.updownx.springx.beans.factory.BeanFactory;
import com.updownx.springx.beans.factory.BeanFactoryAware;
import com.updownx.springx.beans.factory.ConfigurableListableBeanFactory;
import com.updownx.springx.beans.factory.config.InstantiationAwareBeanPostProcessor;
import com.updownx.springx.core.convert.ConversionService;
import com.updownx.springx.util.ClassUtils;
import java.lang.reflect.Field;

/**
 * {@link com.updownx.springx.beans.factory.config.BeanPostProcessor} implementation that autowires
 * annotated fields, setter methods and arbitrary config methods. Such members to be injected are
 * detected through a Java 5 annotation: by default, Spring's {@link Autowired @Autowired} and
 * {@link Value @Value} annotations.
 *
 * <p>处理 @Value、@Autowired，注解的 BeanPostProcessor
 *
 * <p>
 */
public class AutowiredAnnotationBeanPostProcessor
    implements InstantiationAwareBeanPostProcessor, BeanFactoryAware {

  private ConfigurableListableBeanFactory beanFactory;

  @Override
  public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
    this.beanFactory = (ConfigurableListableBeanFactory) beanFactory;
  }

  @Override
  public PropertyValues postProcessPropertyValues(PropertyValues pvs, Object bean, String beanName)
      throws BeansException {
    // 1. 处理注解 @Value
    Class<?> clazz = bean.getClass();
    clazz = ClassUtils.isCglibProxyClass(clazz) ? clazz.getSuperclass() : clazz;

    Field[] declaredFields = clazz.getDeclaredFields();

    for (Field field : declaredFields) {
      Value valueAnnotation = field.getAnnotation(Value.class);
      if (null != valueAnnotation) {
        Object value = valueAnnotation.value();
        value = beanFactory.resolveEmbeddedValue((String) value);

        // 类型转换
        Class<?> sourceType = value.getClass();
        Class<?> targetType = (Class<?>) TypeUtil.getType(field);
        ConversionService conversionService = beanFactory.getConversionService();
        if (conversionService != null) {
          if (conversionService.canConvert(sourceType, targetType)) {
            value = conversionService.convert(value, targetType);
          }
        }

        BeanUtil.setFieldValue(bean, field.getName(), value);
      }
    }

    // 2. 处理注解 @Autowired
    for (Field field : declaredFields) {
      Autowired autowiredAnnotation = field.getAnnotation(Autowired.class);
      if (null != autowiredAnnotation) {
        Class<?> fieldType = field.getType();
        String dependentBeanName = null;
        Qualifier qualifierAnnotation = field.getAnnotation(Qualifier.class);
        Object dependentBean = null;
        if (null != qualifierAnnotation) {
          dependentBeanName = qualifierAnnotation.value();
          dependentBean = beanFactory.getBean(dependentBeanName, fieldType);
        } else {
          dependentBean = beanFactory.getBean(fieldType);
        }
        BeanUtil.setFieldValue(bean, field.getName(), dependentBean);
      }
    }

    return pvs;
  }

  @Override
  public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName)
      throws BeansException {
    return null;
  }

  @Override
  public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
    return true;
  }

  @Override
  public Object postProcessBeforeInitialization(Object bean, String beanName)
      throws BeansException {
    return null;
  }

  @Override
  public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
    // Bug: 返回null会影响代理对象的创建
    // return null;
    return bean;
  }
}
