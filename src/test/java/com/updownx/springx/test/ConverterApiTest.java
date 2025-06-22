package com.updownx.springx.test;

import com.updownx.springx.context.support.ClassPathXmlApplicationContext;
import com.updownx.springx.core.convert.converter.Converter;
import com.updownx.springx.core.convert.support.StringToNumberConverterFactory;
import com.updownx.springx.test.bean6.Husband;
import com.updownx.springx.test.converter.StringToIntegerConverter;
import org.junit.Test;

public class ConverterApiTest {
  @Test
  public void test_convert() {
    ClassPathXmlApplicationContext applicationContext =
        new ClassPathXmlApplicationContext("classpath:spring-converter.xml");
    Husband husband = applicationContext.getBean("husband", Husband.class);
    System.out.println("测试结果：" + husband);
  }

  @Test
  public void test_StringToIntegerConverter() {
    StringToIntegerConverter converter = new StringToIntegerConverter();
    Integer num = converter.convert("1234");
    System.out.println("测试结果：" + num);
  }

  @Test
  public void test_StringToNumberConverterFactory() {
    StringToNumberConverterFactory converterFactory = new StringToNumberConverterFactory();

    Converter<String, Integer> stringToIntegerConverter =
        converterFactory.getConverter(Integer.class);
    System.out.println("测试结果：" + stringToIntegerConverter.convert("1234"));

    Converter<String, Long> stringToLongConverter = converterFactory.getConverter(Long.class);
    System.out.println("测试结果：" + stringToLongConverter.convert("1234"));
  }
}
