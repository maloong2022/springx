package com.updownx.springx.context.annotation;

import cn.hutool.core.util.ClassUtil;
import com.updownx.springx.beans.factory.config.BeanDefinition;
import com.updownx.springx.stereotype.Component;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * A component provider that scans the classpath from a base package. It then applies exclude and
 * include filters to the resulting classes to find candidates.
 *
 * <p>
 */
public class ClassPathScanningCandidateComponentProvider {

  public Set<BeanDefinition> findCandidateComponents(String basePackage) {
    Set<BeanDefinition> candidates = new LinkedHashSet<>();
    Set<Class<?>> classes = ClassUtil.scanPackageByAnnotation(basePackage, Component.class);
    for (Class<?> clazz : classes) {
      candidates.add(new BeanDefinition(clazz));
    }
    return candidates;
  }
}
