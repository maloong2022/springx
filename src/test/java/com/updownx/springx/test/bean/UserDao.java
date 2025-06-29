package com.updownx.springx.test.bean;

import java.util.HashMap;
import java.util.Map;

public class UserDao {
  private static final Map<String, String> hashMap = new HashMap<>();

  public void initDataMethod() {
    System.out.println("执行：init-method");
    hashMap.put("10001", "码龙");
    hashMap.put("10002", "八杯水");
    hashMap.put("10003", "阿毛");
  }

  public void destroyDataMethod() {
    System.out.println("执行：destroy-method");
    hashMap.clear();
  }

  public String queryUserName(String uid) {
    return hashMap.get(uid);
  }
}
