package com.updownx.springx.test;

import com.updownx.springx.beans.PropertyValue;
import com.updownx.springx.beans.PropertyValues;
import com.updownx.springx.beans.factory.config.BeanDefinition;
import com.updownx.springx.beans.factory.config.BeanReference;
import com.updownx.springx.beans.factory.support.DefaultListableBeanFactory;
import com.updownx.springx.beans.factory.xml.XmlBeanDefinitionReader;
import com.updownx.springx.context.support.ClassPathXmlApplicationContext;
import com.updownx.springx.core.io.DefaultResourceLoader;
import com.updownx.springx.test.bean.UserDao;
import com.updownx.springx.test.bean.UserService;
import com.updownx.springx.test.bean.UserService1;
import com.updownx.springx.test.common.MyBeanFactoryPostProcessor;
import com.updownx.springx.test.common.MyBeanPostProcessor;
import com.updownx.springx.test.event.CustomEvent;
import org.junit.Before;
import org.junit.Test;
import org.openjdk.jol.info.ClassLayout;

public class ApiTest {
  private DefaultResourceLoader resourceLoader;

  @Before
  public void init() {
    resourceLoader = new DefaultResourceLoader();
  }

  @Test
  public void test_BeanFactory() {
    // 1. 初始化 BeanFactory
    DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
    // 2. UserDao注册
    beanFactory.registerBeanDefinition("userDao", new BeanDefinition(UserDao.class));
    // 3. UserService设置属性【uId，userDao】
    PropertyValues propertyValues = new PropertyValues();
    propertyValues.addPropertyValue(new PropertyValue("uId", "10001"));
    propertyValues.addPropertyValue(new PropertyValue("userDao", new BeanReference("userDao")));
    // 4. UserService 注入 bean
    BeanDefinition beanDefinition = new BeanDefinition(UserService.class, propertyValues);
    beanFactory.registerBeanDefinition("userService", beanDefinition);
    // 5. 获取 bean
    UserService userService = (UserService) beanFactory.getBean("userService");
    userService.queryUserInfo();
  }

  @Test
  public void test_xml() {
    // 1. 初始化 BeanFactory
    DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
    // 模拟双重注册报错
    // beanFactory.registerBeanDefinition("userDao", new BeanDefinition(UserDao.class));
    // 2. 读取配置文件&注册 Bean
    XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
    reader.loadBeanDefinitions("classpath:spring.xml");

    // 3. 获取 Bean 对象调用方法
    UserService userService = beanFactory.getBean("userService", UserService.class);
    String result = userService.queryUserInfo();
    System.out.println("测试结果：" + result);
  }

  // 不用应用上下文
  @Test
  public void test_BeanFactoryPostProcessorAndBeanPostProcessor() {
    // 1.初始化 BeanFactory
    DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

    // 2. 读取配置文件&注册Bean
    XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
    reader.loadBeanDefinitions("classpath:spring.xml");

    // 3. BeanDefinition 加载完成 & Bean实例化之前，修改 BeanDefinition 的属性值
    MyBeanFactoryPostProcessor beanFactoryPostProcessor = new MyBeanFactoryPostProcessor();
    beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);

    // 4. Bean实例化之后，修改 Bean 属性信息
    MyBeanPostProcessor beanPostProcessor = new MyBeanPostProcessor();
    beanFactory.addBeanPostProcessor(beanPostProcessor);

    // 5. 获取Bean对象调用方法
    UserService userService = beanFactory.getBean("userService", UserService.class);
    String result = userService.queryUserInfo();
    System.out.println("测试结果：" + result);
  }

  //  用应用上下文
  @Test
  public void test_xml_only() {
    // 1.初始化 BeanFactory
    ClassPathXmlApplicationContext applicationContext =
        new ClassPathXmlApplicationContext("classpath:springPostProcessor.xml");

    // 2. 获取Bean对象调用方法
    UserService userService = applicationContext.getBean("userService", UserService.class);
    String result = userService.queryUserInfo();
    System.out.println("测试结果：" + result);
  }

  @Test
  public void test_xml_init_and_destroy() {
    // 1.初始化 BeanFactory
    ClassPathXmlApplicationContext applicationContext =
        new ClassPathXmlApplicationContext("classpath:spring.xml");
    applicationContext.registerShutdownHook();

    // 2. 获取Bean对象调用方法
    UserService userService = applicationContext.getBean("userService", UserService.class);
    String result = userService.queryUserInfo();
    System.out.println("测试结果：" + result);
  }

  @Test
  public void test_xml_aware() {
    // 1.初始化 BeanFactory
    ClassPathXmlApplicationContext applicationContext =
        new ClassPathXmlApplicationContext("classpath:spring.xml");
    applicationContext.registerShutdownHook();

    // 2. 获取Bean对象调用方法
    UserService userService = applicationContext.getBean("userService", UserService.class);
    String result = userService.queryUserInfo();
    System.out.println("测试结果：" + result);

    System.out.println("ApplicationContextAware：" + userService.getApplicationContext());
    System.out.println("BeanFactoryAware：" + userService.getBeanFactory());
  }

  @Test
  public void test_prototype() {
    // 1.初始化 BeanFactory
    ClassPathXmlApplicationContext applicationContext =
        new ClassPathXmlApplicationContext("classpath:spring1.xml");
    applicationContext.registerShutdownHook();

    // 2. 获取Bean对象调用方法
    UserService1 userService01 = applicationContext.getBean("userService", UserService1.class);
    UserService1 userService02 = applicationContext.getBean("userService", UserService1.class);

    // 3. 配置 scope="prototype/singleton"
    System.out.println(userService01);
    System.out.println(userService02);

    // 4. 打印十六进制哈希
    System.out.println(userService01 + " 十六进制哈希：" + Integer.toHexString(userService01.hashCode()));
    System.out.println(ClassLayout.parseInstance(userService01).toPrintable());
  }

  @Test
  public void test_factory_bean() {
    // 1.初始化 BeanFactory
    ClassPathXmlApplicationContext applicationContext =
        new ClassPathXmlApplicationContext("classpath:spring1.xml");
    applicationContext.registerShutdownHook();
    // 2. 调用代理方法
    UserService1 userService = applicationContext.getBean("userService", UserService1.class);
    System.out.println("测试结果：" + userService.queryUserInfo("10003"));
  }

  @Test
  public void test_event() {
    ClassPathXmlApplicationContext applicationContext =
        new ClassPathXmlApplicationContext("classpath:spring-event.xml");
    applicationContext.publishEvent(new CustomEvent(applicationContext, 1019129009086763L, "成功了！"));

    applicationContext.registerShutdownHook();
  }
}
