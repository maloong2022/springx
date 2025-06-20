package com.updownx.springx.test.bean;

public class UserService1 {
  private IUserDao userDao;

  public String queryUserInfo(String uId) {
    return userDao.queryUserName(uId);
  }

  public IUserDao getUserDao() {
    return userDao;
  }

  public void setUserDao(IUserDao userDao) {
    this.userDao = userDao;
  }
}
