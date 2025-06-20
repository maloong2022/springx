package com.updownx.springx.beans.factory.support;

import com.updownx.springx.beans.BeansException;
import com.updownx.springx.beans.factory.DisposableBean;
import com.updownx.springx.beans.factory.config.SingletonBeanRegistry;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {
  /**
   * Internal marker for a null singleton object: used as marker value for concurrent Maps (which
   * don't support null values).
   */
  protected static final Object NULL_OBJECT = new Object();

  private final Map<String, Object> singletonObjects = new HashMap<>();
  private final Map<String, DisposableBean> disposableBeans = new HashMap<>();

  @Override
  public Object getSingleton(String beanName) {
    return singletonObjects.get(beanName);
  }

  public void registerDisposableBean(String beanName, DisposableBean disposableBean) {
    disposableBeans.put(beanName, disposableBean);
  }

  public void destroySingletons() {
    Set<String> keySet = this.disposableBeans.keySet();
    Object[] disposableBeanNames = keySet.toArray();
    for (int i = disposableBeanNames.length - 1; i >= 0; i--) {
      Object beanName = disposableBeanNames[i];
      DisposableBean disposableBean = disposableBeans.remove(beanName);
      try {
        disposableBean.destroy();
      } catch (Exception e) {
        throw new BeansException("destroy method on bean with name " + beanName + " failed", e);
      }
    }
  }

  @Override
  public void registerSingleton(String beanName, Object singletonObject) {
    singletonObjects.put(beanName, singletonObject);
  }
}
