package com.updownx.springx.test.bean5;

import com.updownx.springx.aop.MethodBeforeAdvice;
import java.lang.reflect.Method;

public class SpouseAdvice implements MethodBeforeAdvice {

  @Override
  public void before(Method method, Object[] args, Object target) throws Throwable {
    System.out.println("关怀小两口(切面)：" + method);
  }
}
