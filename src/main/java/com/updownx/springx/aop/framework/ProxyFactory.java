package com.updownx.springx.aop.framework;

import com.updownx.springx.aop.AdvisedSupport;

/**
 * Factory for AOP proxies for programmatic use, rather than via a bean factory. This class provides
 * a simple way of obtaining and configuring AOP proxies in code.
 *
 * <p>
 */
public class ProxyFactory {

  private AdvisedSupport advisedSupport;

  public ProxyFactory(AdvisedSupport advisedSupport) {
    this.advisedSupport = advisedSupport;
  }

  public Object getProxy() {
    return createAopProxy().getProxy();
  }

  private AopProxy createAopProxy() {
    if (advisedSupport.isProxyTargetClass()) {
      return new Cglib2AopProxy(advisedSupport);
    }

    return new JdkDynamicAopProxy(advisedSupport);
  }
}
