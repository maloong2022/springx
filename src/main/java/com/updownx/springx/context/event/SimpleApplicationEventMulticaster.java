package com.updownx.springx.context.event;

import com.updownx.springx.beans.factory.BeanFactory;
import com.updownx.springx.context.ApplicationEvent;
import com.updownx.springx.context.ApplicationListener;

/**
 * Simple implementation of the {@link ApplicationEventMulticaster} interface.
 *
 * <p>
 */
public class SimpleApplicationEventMulticaster extends AbstractApplicationEventMulticaster {

  public SimpleApplicationEventMulticaster(BeanFactory beanFactory) {
    setBeanFactory(beanFactory);
  }

  @Override
  public void multicastEvent(final ApplicationEvent event) {
    for (final ApplicationListener listener : getApplicationListeners(event)) {
      listener.onApplicationEvent(event);
    }
  }
}
