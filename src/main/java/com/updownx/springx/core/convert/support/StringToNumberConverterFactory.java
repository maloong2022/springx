package com.updownx.springx.core.convert.support;

import com.updownx.springx.core.convert.converter.Converter;
import com.updownx.springx.core.convert.converter.ConverterFactory;
import com.updownx.springx.util.NumberUtils;
import org.jetbrains.annotations.Nullable;

/**
 * Converts from a String any JDK-standard Number implementation.
 *
 * <p>
 */
public class StringToNumberConverterFactory implements ConverterFactory<String, Number> {

  @Override
  public <T extends Number> Converter<String, T> getConverter(Class<T> targetType) {
    return new StringToNumber<>(targetType);
  }

  private static final class StringToNumber<T extends Number> implements Converter<String, T> {

    private final Class<T> targetType;

    public StringToNumber(Class<T> targetType) {
      this.targetType = targetType;
    }

    @Override
    @Nullable
    public T convert(String source) {
      if (source.isEmpty()) {
        return null;
      }
      return NumberUtils.parseNumber(source, this.targetType);
    }
  }
}
