package com.updownx.springx.test.event;

import com.updownx.springx.context.ApplicationListener;
import com.updownx.springx.context.event.ContextClosedEvent;

public class ContextClosedEventListener implements ApplicationListener<ContextClosedEvent> {

  @Override
  public void onApplicationEvent(ContextClosedEvent event) {
    System.out.println("关闭事件：" + this.getClass().getName());
  }
}
