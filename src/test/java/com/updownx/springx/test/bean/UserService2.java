package com.updownx.springx.test.bean;

import java.util.Random;

public class UserService2 implements IUserService {

  public String queryUserInfo() {
    try {
      Thread.sleep(new Random(1).nextInt(100));
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return "码龙，100001，深圳";
  }

  public String register(String userName) {
    try {
      Thread.sleep(new Random(1).nextInt(100));
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return "注册用户：" + userName + " success！";
  }
}
