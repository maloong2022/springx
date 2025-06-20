package com.updownx.springx.test.bean;

public class User {
  private String uId;
  private String Name;
  private String company;
  private String location;

  public User(String uId, String name, String company, String location) {
    this.uId = uId;
    Name = name;
    this.company = company;
    this.location = location;
  }

  @Override
  public String toString() {
    return "User{"
        + "uId='"
        + uId
        + '\''
        + ", Name='"
        + Name
        + '\''
        + ", company='"
        + company
        + '\''
        + ", location='"
        + location
        + '\''
        + '}';
  }

  public String getuId() {
    return uId;
  }

  public void setuId(String uId) {
    this.uId = uId;
  }

  public String getName() {
    return Name;
  }

  public void setName(String name) {
    Name = name;
  }

  public String getCompany() {
    return company;
  }

  public void setCompany(String company) {
    this.company = company;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }
}
