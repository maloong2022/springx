package com.updownx.springx.beans.factory;

import com.updownx.springx.beans.BeansException;
import com.updownx.springx.beans.PropertyValue;
import com.updownx.springx.beans.PropertyValues;
import com.updownx.springx.beans.factory.config.BeanDefinition;
import com.updownx.springx.beans.factory.config.BeanFactoryPostProcessor;
import com.updownx.springx.core.io.DefaultResourceLoader;
import com.updownx.springx.core.io.Resource;
import com.updownx.springx.util.StringValueResolver;
import java.io.IOException;
import java.util.Properties;

/**
 * Allows for configuration of individual bean property values from a property resource, i.e. a
 * properties file. Useful for custom config files targeted at system administrators that override
 * bean properties configured in the application context.
 *
 * <p>
 */
public class PropertyPlaceholderConfigurer implements BeanFactoryPostProcessor {

  /** Default placeholder prefix: {@value} */
  public static final String DEFAULT_PLACEHOLDER_PREFIX = "${";

  /** Default placeholder suffix: {@value} */
  public static final String DEFAULT_PLACEHOLDER_SUFFIX = "}";

  private String location;

  @Override
  public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory)
      throws BeansException {
    try {
      // 加载属性文件
      DefaultResourceLoader resourceLoader = new DefaultResourceLoader();
      Resource resource = resourceLoader.getResource(location);

      // 占位符替换属性值
      Properties properties = new Properties();
      properties.load(resource.getInputStream());

      String[] beanDefinitionNames = beanFactory.getBeanDefinitionNames();
      for (String beanName : beanDefinitionNames) {
        BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);

        PropertyValues propertyValues = beanDefinition.getPropertyValues();
        for (PropertyValue propertyValue : propertyValues.getPropertyValues()) {
          Object value = propertyValue.getValue();
          if (!(value instanceof String)) continue;
          value = resolvePlaceholder((String) value, properties);
          propertyValues.addPropertyValue(new PropertyValue(propertyValue.getName(), value));
        }
      }

      // 向容器中添加字符串解析器，供解析@Value注解使用
      StringValueResolver valueResolver = new PlaceholderResolvingStringValueResolver(properties);
      beanFactory.addEmbeddedValueResolver(valueResolver);

    } catch (IOException e) {
      throw new BeansException("Could not load properties", e);
    }
  }

  private String resolvePlaceholder(String value, Properties properties) {
    String strVal = value;
    StringBuilder buffer = new StringBuilder(strVal);
    int startIdx = strVal.indexOf(DEFAULT_PLACEHOLDER_PREFIX);
    int stopIdx = strVal.indexOf(DEFAULT_PLACEHOLDER_SUFFIX);
    if (startIdx != -1 && stopIdx != -1 && startIdx < stopIdx) {
      String propKey = strVal.substring(startIdx + 2, stopIdx);
      String propVal = properties.getProperty(propKey);
      buffer.replace(startIdx, stopIdx + 1, propVal);
    }
    return buffer.toString();
  }

  public void setLocation(String location) {
    this.location = location;
  }

  private class PlaceholderResolvingStringValueResolver implements StringValueResolver {

    private final Properties properties;

    public PlaceholderResolvingStringValueResolver(Properties properties) {
      this.properties = properties;
    }

    @Override
    public String resolveStringValue(String strVal) {
      return PropertyPlaceholderConfigurer.this.resolvePlaceholder(strVal, properties);
    }
  }
}
