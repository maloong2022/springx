package com.updownx.springx.context.support;

import com.updownx.springx.beans.factory.FactoryBean;
import com.updownx.springx.beans.factory.InitializingBean;
import com.updownx.springx.core.convert.ConversionService;
import com.updownx.springx.core.convert.converter.Converter;
import com.updownx.springx.core.convert.converter.ConverterFactory;
import com.updownx.springx.core.convert.converter.ConverterRegistry;
import com.updownx.springx.core.convert.converter.GenericConverter;
import com.updownx.springx.core.convert.support.DefaultConversionService;
import com.updownx.springx.core.convert.support.GenericConversionService;
import java.util.Set;
import org.jetbrains.annotations.Nullable;

/**
 * A factory providing convenient access to a ConversionService configured with converters
 * appropriate for most environments. Set the setConverters "converters" property to supplement the
 * default converters.
 *
 * <p>提供创建 ConversionService 工厂
 */
public class ConversionServiceFactoryBean
    implements FactoryBean<ConversionService>, InitializingBean {

  @Nullable
  private Set<?> converters;

  @Nullable private GenericConversionService conversionService;

  @Override
  public ConversionService getObject() throws Exception {
    return conversionService;
  }

  @Override
  public Class<?> getObjectType() {
    return conversionService.getClass();
  }

  @Override
  public boolean isSingleton() {
    return true;
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    this.conversionService = new DefaultConversionService();
    registerConverters(converters, conversionService);
  }

  private void registerConverters(Set<?> converters, ConverterRegistry registry) {
    if (converters != null) {
      for (Object converter : converters) {
        if (converter instanceof GenericConverter) {
          registry.addConverter((GenericConverter) converter);
        } else if (converter instanceof Converter<?, ?>) {
          registry.addConverter((Converter<?, ?>) converter);
        } else if (converter instanceof ConverterFactory<?, ?>) {
          registry.addConverterFactory((ConverterFactory<?, ?>) converter);
        } else {
          throw new IllegalArgumentException(
              "Each converter object must implement one of the "
                  + "Converter, ConverterFactory, or GenericConverter interfaces");
        }
      }
    }
  }

  public void setConverters(Set<?> converters) {
    this.converters = converters;
  }
}
