package com.updownx.springx.test.converter;

import com.updownx.springx.core.convert.converter.Converter;

public class StringToIntegerConverter implements Converter<String, Integer> {

  @Override
  public Integer convert(String source) {
    return Integer.valueOf(source);
  }
}
