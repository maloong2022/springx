package com.updownx.springx.aop.aspectj;

import com.updownx.springx.aop.ClassFilter;
import com.updownx.springx.aop.MethodMatcher;
import com.updownx.springx.aop.Pointcut;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;
import org.aspectj.weaver.tools.PointcutExpression;
import org.aspectj.weaver.tools.PointcutParser;
import org.aspectj.weaver.tools.PointcutPrimitive;

/**
 * Spring {@link com.updownx.springx.aop.Pointcut} implementation that uses the AspectJ weaver to
 * evaluate a pointcut expression.
 *
 * <p>切点表达式
 *
 * <p>
 */
public class AspectJExpressionPointcut implements Pointcut, ClassFilter, MethodMatcher {

  private static final Set<PointcutPrimitive> SUPPORTED_PRIMITIVES =
      new HashSet<PointcutPrimitive>();

  static {
    SUPPORTED_PRIMITIVES.add(PointcutPrimitive.EXECUTION);
  }

  private final PointcutExpression pointcutExpression;

  public AspectJExpressionPointcut(String expression) {
    PointcutParser pointcutParser =
        PointcutParser
            .getPointcutParserSupportingSpecifiedPrimitivesAndUsingSpecifiedClassLoaderForResolution(
                SUPPORTED_PRIMITIVES, this.getClass().getClassLoader());
    pointcutExpression = pointcutParser.parsePointcutExpression(expression);
  }

  @Override
  public boolean matches(Class<?> clazz) {
    return pointcutExpression.couldMatchJoinPointsInType(clazz);
  }

  @Override
  public boolean matches(Method method, Class<?> targetClass) {
    return pointcutExpression.matchesMethodExecution(method).alwaysMatches();
  }

  @Override
  public ClassFilter getClassFilter() {
    return this;
  }

  @Override
  public MethodMatcher getMethodMatcher() {
    return this;
  }
}
