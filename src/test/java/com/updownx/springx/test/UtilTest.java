package com.updownx.springx.test;

import com.updownx.springx.beans.BeansException;
import com.updownx.springx.beans.factory.config.BeanPostProcessor;
import com.updownx.springx.context.support.ClassPathXmlApplicationContext;
import com.updownx.springx.test.bean.IUserService;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class UtilTest {
  @Test
  public void test_scan() {
    ClassPathXmlApplicationContext applicationContext =
        new ClassPathXmlApplicationContext("classpath:spring-scan.xml");
    IUserService userService = applicationContext.getBean("userService", IUserService.class);
    System.out.println("测试结果：" + userService.queryUserInfo());
  }

  @Test
  public void test_property() {
    ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring-property.xml");
    IUserService userService = applicationContext.getBean("userService", IUserService.class);
    System.out.println("测试结果：" + userService);
  }

  @Test
  public void test_beanPost(){

    BeanPostProcessor beanPostProcessor = new BeanPostProcessor() {
      @Override
      public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return null;
      }

      @Override
      public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return null;
      }
    };

    List<BeanPostProcessor> beanPostProcessors = new ArrayList<BeanPostProcessor>();
    beanPostProcessors.add(beanPostProcessor);
    beanPostProcessors.add(beanPostProcessor);
    beanPostProcessors.remove(beanPostProcessor);

    System.out.println(beanPostProcessors.size());
  }
}
