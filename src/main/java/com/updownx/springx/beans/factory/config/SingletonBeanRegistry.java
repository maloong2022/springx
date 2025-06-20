package com.updownx.springx.beans.factory.config;

/** 单例注册表 */
public interface SingletonBeanRegistry {
  Object getSingleton(String name);

  /** 销毁单例对象 */
  void destroySingletons();

  void registerSingleton(String beanName, Object singletonObject);
}
