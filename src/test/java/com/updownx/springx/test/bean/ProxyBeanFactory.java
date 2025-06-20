package com.updownx.springx.test.bean;

import com.updownx.springx.beans.factory.FactoryBean;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class ProxyBeanFactory implements FactoryBean<IUserDao> {

  @Override
  public IUserDao getObject() throws Exception {
    InvocationHandler handler =
        (proxy, method, args) -> {

          // 添加排除方法
          if ("toString".equals(method.getName())) return this.toString();

          Map<String, User> hashMap = new HashMap<>();
          hashMap.put("10001", new User("10001", "码龙", "腾讯", "深圳"));
          hashMap.put("10002", new User("10002", "八杯水", "腾讯", "深圳"));
          hashMap.put("10003", new User("10003", "阿毛", "腾讯", "深圳"));

          return "你被代理了 " + method.getName() + "：" + hashMap.get(args[0].toString()).toString();
        };
    return (IUserDao)
        Proxy.newProxyInstance(
            Thread.currentThread().getContextClassLoader(), new Class[] {IUserDao.class}, handler);
  }

  @Override
  public Class<?> getObjectType() {
    return IUserDao.class;
  }

  @Override
  public boolean isSingleton() {
    return true;
  }
}
