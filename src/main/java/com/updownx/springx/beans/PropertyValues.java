package com.updownx.springx.beans;

import java.util.ArrayList;
import java.util.List;

public class PropertyValues {
  private final List<PropertyValue> propertyValueList = new ArrayList<>();

  public void addPropertyValue(PropertyValue pv) {
    this.propertyValueList.add(pv);
  }

  public PropertyValue[] getPropertyValues() {
    return this.propertyValueList.toArray(new PropertyValue[0]);
  }

  public PropertyValue getPropertyValue(String name) {
    for (PropertyValue pv : this.propertyValueList) {
      if (pv.getName().equals(name)) {
        return pv;
      }
    }
    return null;
  }
}
