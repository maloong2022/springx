package com.updownx.springx.test;

import com.updownx.springx.context.support.ClassPathXmlApplicationContext;
import com.updownx.springx.test.bean5.Husband;
import com.updownx.springx.test.bean5.Wife;
import org.junit.Test;

public class CircleTest {
  @Test
  public void test_circular() {
    ClassPathXmlApplicationContext applicationContext =
        new ClassPathXmlApplicationContext("classpath:spring-circle.xml");
    Husband husband = applicationContext.getBean("husband", Husband.class);
    Wife wife = applicationContext.getBean("wife", Wife.class);
    System.out.println("老公的媳妇：" + husband.queryWife());
    System.out.println("媳妇的老公：" + wife.queryHusband());
  }
}
