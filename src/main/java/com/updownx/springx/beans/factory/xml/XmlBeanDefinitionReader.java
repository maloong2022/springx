package com.updownx.springx.beans.factory.xml;

import cn.hutool.core.util.StrUtil;
import com.updownx.springx.beans.BeansException;
import com.updownx.springx.beans.PropertyValue;
import com.updownx.springx.beans.factory.config.BeanDefinition;
import com.updownx.springx.beans.factory.config.BeanReference;
import com.updownx.springx.beans.factory.support.AbstractBeanDefinitionReader;
import com.updownx.springx.beans.factory.support.BeanDefinitionRegistry;
import com.updownx.springx.context.annotation.ClassPathBeanDefinitionScanner;
import com.updownx.springx.core.io.Resource;
import com.updownx.springx.core.io.ResourceLoader;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader {
  public XmlBeanDefinitionReader(BeanDefinitionRegistry registry) {
    super(registry);
  }

  public XmlBeanDefinitionReader(BeanDefinitionRegistry registry, ResourceLoader resourceLoader) {
    super(registry, resourceLoader);
  }

  @Override
  public void loadBeanDefinitions(Resource resource) throws BeansException {
    try (InputStream inputStream = resource.getInputStream()) {
      doLoadBeanDefinitions(inputStream);
    } catch (IOException | ClassNotFoundException | DocumentException e) {
      throw new BeansException("IOException parsing XML document from " + resource, e);
    }
  }

  protected void doLoadBeanDefinitions(InputStream inputStream)
      throws ClassNotFoundException, DocumentException {
    SAXReader reader = new SAXReader();
    Document document = reader.read(inputStream);
    Element root = document.getRootElement();

    // 解析 context:component-scan 标签，扫描包中的类并提取相关信息，用于组装 BeanDefinition
    Element componentScan = root.element("component-scan");
    if (null != componentScan) {
      String scanPath = componentScan.attributeValue("base-package");
      if (StrUtil.isEmpty(scanPath)) {
        throw new BeansException("The value of base-package attribute can not be empty or null");
      }
      scanPackage(scanPath);
    }

    List<Element> beanList = root.elements("bean");
    for (Element bean : beanList) {
      // 解析标签
      String id = bean.attributeValue("id");
      String name = bean.attributeValue("name");
      String className = bean.attributeValue("class");
      String initMethod = bean.attributeValue("init-method");
      String destroyMethodName = bean.attributeValue("destroy-method");
      String beanScope = bean.attributeValue("scope");

      // 获取 Class，方便获取类中的名称
      Class<?> clazz = Class.forName(className);
      // 优先级 id > name
      String beanName = StrUtil.isNotEmpty(id) ? id : name;
      if (StrUtil.isEmpty(beanName)) beanName = StrUtil.lowerFirst(clazz.getSimpleName());

      // 定义 Bean
      BeanDefinition beanDefinition = new BeanDefinition(clazz);
      beanDefinition.setInitMethodName(initMethod);
      beanDefinition.setDestroyMethodName(destroyMethodName);

      if (StrUtil.isNotEmpty(beanScope)) {
        beanDefinition.setScope(beanScope);
      }
      List<Element> propertyList = bean.elements("property");
      // 读取属性并填充
      // 读取属性并填充
      for (Element property : propertyList) {
        // 解析标签：property
        String attrName = property.attributeValue("name");
        String attrValue = property.attributeValue("value");
        String attrRef = property.attributeValue("ref");
        // 获取属性值：引入对象、值对象
        Object value = StrUtil.isNotEmpty(attrRef) ? new BeanReference(attrRef) : attrValue;
        // 创建属性信息
        PropertyValue propertyValue = new PropertyValue(attrName, value);
        beanDefinition.getPropertyValues().addPropertyValue(propertyValue);
      }
      if (getRegistry().containsBeanDefinition(beanName)) {
        throw new BeansException("Duplicate beanName[" + beanName + "] is not allowed");
      }
      // 注册 BeanDefinition
      getRegistry().registerBeanDefinition(beanName, beanDefinition);
    }
  }

  private void scanPackage(String scanPath) {
    String[] basePackages = StrUtil.splitToArray(scanPath, ',');
    ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(getRegistry());
    scanner.doScan(basePackages);
  }

  @Override
  public void loadBeanDefinitions(Resource... resources) throws BeansException {
    for (Resource resource : resources) {
      loadBeanDefinitions(resource);
    }
  }

  @Override
  public void loadBeanDefinitions(String location) throws BeansException {
    ResourceLoader resourceLoader = getResourceLoader();
    Resource resource = resourceLoader.getResource(location);
    loadBeanDefinitions(resource);
  }

  @Override
  public void loadBeanDefinitions(String... locations) throws BeansException {
    for (String location : locations) {
      loadBeanDefinitions(location);
    }
  }
}
