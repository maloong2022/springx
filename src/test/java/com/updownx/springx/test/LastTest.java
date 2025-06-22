package com.updownx.springx.test;

import com.updownx.springx.context.support.ClassPathXmlApplicationContext;
import com.updownx.springx.test.beans.IUserService;
import org.junit.Test;

public class LastTest {
  @Test
  public void test_scan() {
    ClassPathXmlApplicationContext applicationContext =
        new ClassPathXmlApplicationContext("classpath:spring-last.xml");
    IUserService userService = applicationContext.getBean("userService", IUserService.class);
    System.out.println("测试结果：" + userService.queryUserInfo());
  }
}
