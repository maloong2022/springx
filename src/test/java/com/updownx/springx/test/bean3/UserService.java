package com.updownx.springx.test.bean3;

import com.updownx.springx.beans.factory.annotation.Autowired;
import com.updownx.springx.beans.factory.annotation.Value;
import com.updownx.springx.stereotype.Component;
import java.util.Random;

@Component
public class UserService implements IUserService {

  @Value("${token}")
  private String token;

  @Autowired private UserDao userDao;

  public String queryUserInfo(String uId) {
    try {
      Thread.sleep(new Random(1).nextInt(100));
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return userDao.queryUserName(uId) + " token: " + token;
  }

  public String register(String userName) {
    try {
      Thread.sleep(new Random(1).nextInt(100));
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return "注册用户：" + userName + " success！";
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }
}
