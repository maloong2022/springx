package com.updownx.springx.test.bean;

import com.updownx.springx.stereotype.Component;
import java.util.Random;

@Component("userService")
public class UserService3 implements IUserService {

  private String token;

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

  @Override
  public String toString() {
    return "UserService#token = { " + token + " }";
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }
}
