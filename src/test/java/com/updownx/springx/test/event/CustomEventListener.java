package com.updownx.springx.test.event;

import com.updownx.springx.context.ApplicationListener;
import java.util.Date;

public class CustomEventListener implements ApplicationListener<CustomEvent> {

  @Override
  public void onApplicationEvent(CustomEvent event) {
    System.out.println("收到：" + event.getSource() + "消息;时间：" + new Date());
    System.out.println("消息：" + event.getId() + ":" + event.getMessage());
  }
}
