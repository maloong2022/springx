package com.updownx.springx.core.convert.support;

import com.updownx.springx.core.convert.converter.ConverterRegistry;

/**
 * A specialization of {@link GenericConversionService} configured by default with converters
 * appropriate for most environments.
 */
public class DefaultConversionService extends GenericConversionService {

  public DefaultConversionService() {
    addDefaultConverters(this);
  }

  public static void addDefaultConverters(ConverterRegistry converterRegistry) {
    // 添加各类类型转换工厂
    converterRegistry.addConverterFactory(new StringToNumberConverterFactory());
  }
}
