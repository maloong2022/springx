package com.updownx.springx.test;

import com.updownx.springx.context.support.ClassPathXmlApplicationContext;
import com.updownx.springx.test.bean.IUserService;
import org.junit.Test;

public class AopTest {

  @Test
  public void test_aop() {
    ClassPathXmlApplicationContext applicationContext =
        new ClassPathXmlApplicationContext("classpath:spring-aop.xml");

    IUserService userService = applicationContext.getBean("userService", IUserService.class);
    System.out.println("测试结果：" + userService.queryUserInfo());
  }
}
