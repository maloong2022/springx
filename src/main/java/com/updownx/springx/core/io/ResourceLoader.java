package com.updownx.springx.core.io;

public interface ResourceLoader {
  String CLASSPATH_URL_PREFIX = "classpath:";

  Resource getResource(String location);
}
