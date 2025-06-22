package com.updownx.springx.test;

import com.updownx.springx.context.support.ClassPathXmlApplicationContext;
import com.updownx.springx.test.bean3.IUserService;
import org.junit.Test;

public class ApiFinallyTest {
  @Test
  public void test_autoProxy_2() {
    ClassPathXmlApplicationContext applicationContext =
        new ClassPathXmlApplicationContext("classpath:spring-finally.xml");
    IUserService userService = applicationContext.getBean("userService", IUserService.class);
    System.out.println("测试结果：" + userService.queryUserInfo("10001"));
  }
}
