package org.apache.ibatis.binding;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;

/**
 * <li>包装mapper方法的信息 
 * <li>持有{@link #sqlSession}引用，并利用它来获得数据库结果。
 */
public class MapperMethod {


  private SqlSession sqlSession;
  private Configuration config;

  /**
   * 取值范围：insert/update/select/delete/unknown
   * @see org.apache.ibatis.mapping.SqlCommandType
   */
  private SqlCommandType type;
  /**
   * 值为：mapper接口名.方法名
   */
  private String commandName;

  /**
   * mapper接口
   */
  private Class<?> declaringInterface;
  private Method method;

  /**
   * 返回结果是否{@link java.util.List}
   */
  private boolean returnsList;
  /**
   * 返回结果是否{@link java.util.Map}
   */
  private boolean returnsMap;
  /**
   * 有无返回值
   */
  private boolean returnsVoid;
  private String mapKey;

  /**
   * 记录ResultHandler类型参数的位置
   */
  private Integer resultHandlerIndex;
  
  /**
   * 记录RowBounds类型参数的位置
   */
  private Integer rowBoundsIndex;
  
  /**
   * 记录参数的名称(优先使用参数中@Param注解的值，如果没有注解，则使用普通参数{@link #paramPositions}中位置排序，如0、1、2...)
   */
  private List<String> paramNames;
  
  /**
   * 记录普通参数的位置
   */
  private List<Integer> paramPositions;

  /**
   * 是否有使用了@Param注解的参数
   */
  private boolean hasNamedParameters;

  public MapperMethod(Class<?> declaringInterface, Method method, SqlSession sqlSession) {
    paramNames = new ArrayList<String>();
    paramPositions = new ArrayList<Integer>();
    this.sqlSession = sqlSession;
    this.method = method;
    this.config = sqlSession.getConfiguration();
    this.hasNamedParameters = false;
    this.declaringInterface = declaringInterface;
    //设置#commandName
    setupFields();
    //解析方法声明的相关信息
    setupMethodSignature();
    //设置#type
    setupCommandType();
    validateStatement();
  }

  /**
   * 调用 {@link org.apache.ibatis.session.SqlSession}中的方法，得到结果。
   */
  public Object execute(Object[] args) {
    Object result = null;
    if (SqlCommandType.INSERT == type) {
      Object param = getParam(args);
      result = sqlSession.insert(commandName, param);
    } else if (SqlCommandType.UPDATE == type) {
      Object param = getParam(args);
      result = sqlSession.update(commandName, param);
    } else if (SqlCommandType.DELETE == type) {
      Object param = getParam(args);
      result = sqlSession.delete(commandName, param);
    } else if (SqlCommandType.SELECT == type) {
      if (returnsVoid && resultHandlerIndex != null) {
        executeWithResultHandler(args);
      } else if (returnsList) {
        result = executeForList(args);
      } else if (returnsMap) {
        result = executeForMap(args);
      } else {
    	//返回一个结果
        Object param = getParam(args);
        result = sqlSession.selectOne(commandName, param);
      }
    } else {
      throw new BindingException("Unknown execution method for: " + commandName);
    }
    return result;
  }

  private void executeWithResultHandler(Object[] args) {
    Object param = getParam(args);
    if (rowBoundsIndex != null) {
      RowBounds rowBounds = (RowBounds) args[rowBoundsIndex];
      sqlSession.select(commandName, param, rowBounds, (ResultHandler) args[resultHandlerIndex]);
    } else {
      sqlSession.select(commandName, param, (ResultHandler) args[resultHandlerIndex]);
    }
  }

  private List executeForList(Object[] args) {
    List result;
    Object param = getParam(args);
    if (rowBoundsIndex != null) {
      RowBounds rowBounds = (RowBounds) args[rowBoundsIndex];
      result = sqlSession.selectList(commandName, param, rowBounds);
    } else {
      result = sqlSession.selectList(commandName, param);
    }
    return result;
  }

  private Map executeForMap(Object[] args) {
    Map result;
    Object param = getParam(args);
    if (rowBoundsIndex != null) {
      RowBounds rowBounds = (RowBounds) args[rowBoundsIndex];
      result = sqlSession.selectMap(commandName, param, mapKey, rowBounds);
    } else {
      result = sqlSession.selectMap(commandName, param, mapKey);
    }
    return result;
  }

  /**
   * 使用原始参数，构建新的参数，以适用于mybatis动态sql中的赋值机制
   * @param args 原始参数
   * @return 解析构建的参数
   */
  private Object getParam(Object[] args) {
    final int paramCount = paramPositions.size();
    if (args == null || paramCount == 0) {
      return null;
    } else if (!hasNamedParameters && paramCount == 1) {
      return args[paramPositions.get(0)];
    } else {
      Map<String, Object> param = new HashMap<String, Object>();
      for (int i = 0; i < paramCount; i++) {
        param.put(paramNames.get(i), args[paramPositions.get(i)]);
      }
      return param;
    }
  }

 /**
  *  {@link #commandName} = mapper接口名.方法名
  */
  private void setupFields() {
    this.commandName = declaringInterface.getName() + "." + method.getName();
  }

  /**
   * 设置{@link #returnsVoid}、{@link #returnsList}、{@link #mapKey}、{@link #returnsMap}、{@link #rowBoundsIndex}
   * 、{@link #resultHandlerIndex} 、{@link #paramNames}、{@link #paramPositions}
   */
  private void setupMethodSignature() {
    if( method.getReturnType().equals(Void.TYPE)){
      returnsVoid = true;
    }
    if (List.class.isAssignableFrom(method.getReturnType())) {
      returnsList = true;
    }
    if (Map.class.isAssignableFrom(method.getReturnType())) { 
      //@MapKey 注解
      final MapKey mapKeyAnnotation = method.getAnnotation(MapKey.class);
      if (mapKeyAnnotation != null) {
        mapKey = mapKeyAnnotation.value();
        returnsMap = true;
      }
    }

    final Class<?>[] argTypes = method.getParameterTypes();
    for (int i = 0; i < argTypes.length; i++) {
      //处理RowBounds类型的参数	
      if (RowBounds.class.isAssignableFrom(argTypes[i])) {
        if (rowBoundsIndex == null) {
          rowBoundsIndex = i;
        } else {
          throw new BindingException(method.getName() + " cannot have multiple RowBounds parameters");
        }
      }
      //处理ResultHandler类型的参数	
      else if (ResultHandler.class.isAssignableFrom(argTypes[i])) {
        if (resultHandlerIndex == null) {
          resultHandlerIndex = i;
        } else {
          throw new BindingException(method.getName() + " cannot have multiple ResultHandler parameters");
        }
      } 
      //处理其他普通参数
      else {
        String paramName = String.valueOf(paramPositions.size());
        paramName = getParamNameFromAnnotation(i, paramName);
        paramNames.add(paramName);
        paramPositions.add(i);
      }
    }
  }

  /**
   * 解析第i个参数是否具有@Param 注解 
   * @param i 参数位置
   */
  private String getParamNameFromAnnotation(int i, String paramName) {
    Object[] paramAnnos = method.getParameterAnnotations()[i];
    for (int j = 0; j < paramAnnos.length; j++) {
      if (paramAnnos[j] instanceof Param) {
        hasNamedParameters = true;
        paramName = ((Param) paramAnnos[j]).value();
      }
    }
    return paramName;
  }

  /**
   * 设置{@link #type}
   */
  private void setupCommandType() {
    MappedStatement ms = config.getMappedStatement(commandName);
    type = ms.getSqlCommandType();
    if (type == SqlCommandType.UNKNOWN) {
      throw new BindingException("Unknown execution method for: " + commandName);
    }
  }

  private void validateStatement() {
    try {
      config.getMappedStatement(commandName);
    } catch (Exception e) {
      throw new BindingException("Invalid bound statement (not found): " + commandName, e);
    }
  }

}
