package com.updownx.springx.beans.factory.config;

import com.updownx.springx.beans.factory.HierarchicalBeanFactory;
import com.updownx.springx.core.convert.ConversionService;
import com.updownx.springx.util.StringValueResolver;
import org.jetbrains.annotations.Nullable;

/**
 * Configuration interface to be implemented by most bean factories. Provides facilities to
 * configure a bean factory, in addition to the bean factory client methods in the {@link
 * com.updownx.springx.beans.factory.BeanFactory} interface.
 */
public interface ConfigurableBeanFactory extends HierarchicalBeanFactory, SingletonBeanRegistry {
  String SCOPE_SINGLETON = "singleton";
  String SCOPE_PROTOTYPE = "prototype";

  void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);

  /**
   * Add a String resolver for embedded values such as annotation attributes.
   *
   * @param valueResolver the String resolver to apply to embedded values
   * @since 3.0
   */
  void addEmbeddedValueResolver(StringValueResolver valueResolver);

  /**
   * Resolve the given embedded value, e.g. an annotation attribute.
   *
   * @param value the value to resolve
   * @return the resolved value (may be the original value as-is)
   * @since 3.0
   */
  String resolveEmbeddedValue(String value);

  /**
   * Return the associated ConversionService, if any.
   *
   * @since 3.0
   */
  @Nullable
  ConversionService getConversionService();

  /**
   * Specify a Spring 3.0 ConversionService to use for converting property values, as an alternative
   * to JavaBeans PropertyEditors.
   *
   * @since 3.0
   */
  void setConversionService(ConversionService conversionService);
}
