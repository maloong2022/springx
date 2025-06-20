package com.updownx.springx.test.bean;

import com.updownx.springx.beans.BeansException;
import com.updownx.springx.beans.factory.*;
import com.updownx.springx.context.ApplicationContext;
import com.updownx.springx.context.ApplicationContextAware;

public class UserService
    implements InitializingBean,
        DisposableBean,
        BeanNameAware,
        BeanClassLoaderAware,
        ApplicationContextAware,
        BeanFactoryAware {
  private ApplicationContext applicationContext;
  private BeanFactory beanFactory;
  private String uId;
  private UserDao userDao;
  private String company;
  private String location;

  @Override
  public void setBeanName(String name) {
    System.out.println("Bean Name is：" + name);
  }

  @Override
  public void setBeanClassLoader(ClassLoader classLoader) {
    System.out.println("ClassLoader：" + classLoader);
  }

  public String queryUserInfo() {
    return userDao.queryUserName(uId) + "," + company + "," + location;
  }

  public String getuId() {
    return uId;
  }

  public void setuId(String uId) {
    this.uId = uId;
  }

  public UserDao getUserDao() {
    return userDao;
  }

  public void setUserDao(UserDao userDao) {
    this.userDao = userDao;
  }

  public String getCompany() {
    return company;
  }

  public void setCompany(String company) {
    this.company = company;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  @Override
  public void destroy() throws Exception {
    System.out.println("执行：UserService.destroy");
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    System.out.println("执行：UserService.afterPropertiesSet");
  }

  public ApplicationContext getApplicationContext() {
    return applicationContext;
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.applicationContext = applicationContext;
  }

  public BeanFactory getBeanFactory() {
    return beanFactory;
  }

  @Override
  public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
    this.beanFactory = beanFactory;
  }
}
