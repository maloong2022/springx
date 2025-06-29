package com.updownx.springx.core.io;

import cn.hutool.core.lang.Assert;
import com.updownx.springx.util.ClassUtils;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class ClassPathResource implements Resource {
  private final String path;
  private final ClassLoader classLoader;

  public ClassPathResource(String path) {
    this(path, null);
  }

  public ClassPathResource(String path, ClassLoader classLoader) {
    Assert.notNull(path, "path must not be null");
    this.path = path;
    this.classLoader = (classLoader != null ? classLoader : ClassUtils.getDefaultClassLoader());
  }

  @Override
  public InputStream getInputStream() throws IOException {
    InputStream is = classLoader.getResourceAsStream(path);
    if (is == null) {
      throw new FileNotFoundException(path + " cannot be opened because it does not exist");
    }
    return is;
  }
}
