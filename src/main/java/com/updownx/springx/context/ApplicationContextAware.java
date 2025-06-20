package com.updownx.springx.context;

import com.updownx.springx.beans.BeansException;
import com.updownx.springx.beans.factory.Aware;

/**
 * Interface to be implemented by any object that wishes to be notified of the {@link
 * ApplicationContext} that it runs in.
 *
 * <p>实现此接口，既能感知到所属的 ApplicationContext
 */
public interface ApplicationContextAware extends Aware {
  void setApplicationContext(ApplicationContext applicationContext) throws BeansException;
}
