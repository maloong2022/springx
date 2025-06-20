package com.updownx.springx.test.event;

import com.updownx.springx.context.ApplicationListener;
import com.updownx.springx.context.event.ContextRefreshedEvent;

public class ContextRefreshedEventListener implements ApplicationListener<ContextRefreshedEvent> {

  @Override
  public void onApplicationEvent(ContextRefreshedEvent event) {
    System.out.println("刷新事件：" + this.getClass().getName());
  }
}
