package com.updownx.springx.util;

/**
 * Simple strategy interface for resolving a String value. Used by {@link
 * com.updownx.springx.beans.factory.config.ConfigurableBeanFactory}.
 *
 * <p>
 */
public interface StringValueResolver {

  String resolveStringValue(String strVal);
}
