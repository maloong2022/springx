package com.updownx.springx.context;

import com.updownx.springx.beans.factory.ListableBeanFactory;

/**
 * Central interface to provide configuration for an application. This is read-only while the
 * application is running, but may be reloaded if the implementation supports this.
 *
 * <p>应用上下文
 */
public interface ApplicationContext extends ListableBeanFactory, ApplicationEventPublisher {}
