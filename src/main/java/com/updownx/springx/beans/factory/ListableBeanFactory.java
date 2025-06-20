package com.updownx.springx.beans.factory;

import com.updownx.springx.beans.BeansException;
import java.util.Map;

/**
 * Extension of the {@link BeanFactory} interface to be implemented by bean factories that can
 * enumerate all their bean instances, rather than attempting bean lookup by name one by one as
 * requested by clients. BeanFactory implementations that preload all their bean definitions (such
 * as XML-based factories) may implement this interface.
 */
public interface ListableBeanFactory extends BeanFactory {
  /**
   * 按照类型返回 Bean 实例
   *
   * @param type 类型
   * @return Bean 实例
   * @param <T> 类型定义
   * @throws BeansException
   */
  <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException;

  /**
   * 返回注册表中所有的 Bean 名称
   *
   * @return Beans' name
   */
  String[] getBeanDefinitionNames();
}
