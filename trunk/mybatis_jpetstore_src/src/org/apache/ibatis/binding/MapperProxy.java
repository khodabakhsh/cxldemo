package org.apache.ibatis.binding;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashSet;
import java.util.Set;

import org.apache.ibatis.session.SqlSession;

public class MapperProxy implements InvocationHandler, Serializable {

  private static final long serialVersionUID = -6424540398559729838L;

  private static final Set<String> OBJECT_METHODS = new HashSet<String>() {
    private static final long serialVersionUID = -1782950882770203582L;
  {
    add("toString");
    add("getClass");
    add("hashCode");
    add("equals");
    add("wait");
    add("notify");
    add("notifyAll");
  }};

  private SqlSession sqlSession;

  private <T> MapperProxy(SqlSession sqlSession) {
    this.sqlSession = sqlSession;
  }

  /**
   * 使用代理之后，这里就是mapper类所有方法的入口.
   * 原理是：通过mapper类方法对应的MapperMethod实例，执行MapperMethod#execute获得结果
   */
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    if (!OBJECT_METHODS.contains(method.getName())) {
      final Class<?> declaringInterface = findDeclaringInterface(proxy, method);
      final MapperMethod mapperMethod = new MapperMethod(declaringInterface, method, sqlSession);
      //mapperMethod.execute内部是利用SqlSession来真正执行数据库操作，并获得执行结果
      final Object result = mapperMethod.execute(args);
      //方法声明中返回值是基本数据类型的方法，result为null时直接抛异常
      if (result == null && method.getReturnType().isPrimitive() && !method.getReturnType().equals(Void.TYPE)) {
        throw new BindingException("Mapper method '" + method.getName() + "' (" + method.getDeclaringClass() + ") attempted to return null from a method with a primitive return type (" + method.getReturnType() + ").");
      }
      return result;
    }
    //对于@java.lang.Object 对象的方法，返回null
    return null;
  }

  private Class<?> findDeclaringInterface(Object proxy, Method method) {
    Class<?> declaringInterface = null;
    for (Class<?> iface : proxy.getClass().getInterfaces()) {
      try {
        Method m = iface.getMethod(method.getName(), method.getParameterTypes());
        if (declaringInterface != null) {
          throw new BindingException("Ambiguous method mapping.  Two mapper interfaces contain the identical method signature for " + method);
        } else if (m != null) {
          declaringInterface = iface;
        }
      } catch (Exception e) {
        // Intentionally ignore.
        // This is using exceptions for flow control,
        // but it's definitely faster.
      }
    }
    if (declaringInterface == null) {
      throw new BindingException("Could not find interface with the given method " + method);
    }
    return declaringInterface;
  }

  /**
   * 返回一个MapperProxy代理
   */
  @SuppressWarnings("unchecked")
public static <T> T newMapperProxy(Class<T> mapperInterface, SqlSession sqlSession) {
    ClassLoader classLoader = mapperInterface.getClassLoader();
    Class<?>[] interfaces = new Class[]{mapperInterface};
    MapperProxy proxy = new MapperProxy(sqlSession);
    return (T) Proxy.newProxyInstance(classLoader, interfaces, proxy);
  }

}
