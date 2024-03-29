package org.apache.ibatis.builder.annotation;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.annotations.Arg;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.CacheNamespaceRef;
import org.apache.ibatis.annotations.Case;
import org.apache.ibatis.annotations.ConstructorArgs;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.TypeDiscriminator;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.binding.BindingException;
import org.apache.ibatis.builder.BuilderException;
import org.apache.ibatis.builder.MapperBuilderAssistant;
import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.builder.xml.dynamic.DynamicSqlSource;
import org.apache.ibatis.builder.xml.dynamic.MixedSqlNode;
import org.apache.ibatis.builder.xml.dynamic.SqlNode;
import org.apache.ibatis.builder.xml.dynamic.TextSqlNode;
import org.apache.ibatis.executor.keygen.Jdbc3KeyGenerator;
import org.apache.ibatis.executor.keygen.KeyGenerator;
import org.apache.ibatis.executor.keygen.NoKeyGenerator;
import org.apache.ibatis.executor.keygen.SelectKeyGenerator;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.Discriminator;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultFlag;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.mapping.ResultSetType;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.mapping.StatementType;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

/**
 * <li>完成mapper类对应的xml文件的解析
 * <li>完成mapper类的注解解析
 */
public class MapperAnnotationBuilder {

  private final Set<Class<? extends Annotation>> sqlAnnotationTypes = new HashSet<Class<? extends Annotation>>();
  private final Set<Class<? extends Annotation>> sqlProviderAnnotationTypes = new HashSet<Class<? extends Annotation>>();

  private Configuration configuration;
  private MapperBuilderAssistant assistant;
  /**
   * 应用于mybatis的mapper接口类
   */
  private Class<?> type;

  public MapperAnnotationBuilder(Configuration configuration, Class<?> type) {
    String resource = type.getName().replace('.', '/') + ".java (best guess)";
    this.assistant = new MapperBuilderAssistant(configuration, resource);
    this.configuration = configuration;
    this.type = type;

    sqlAnnotationTypes.add(Select.class);
    sqlAnnotationTypes.add(Insert.class);
    sqlAnnotationTypes.add(Update.class);
    sqlAnnotationTypes.add(Delete.class);

    sqlProviderAnnotationTypes.add(SelectProvider.class);
    sqlProviderAnnotationTypes.add(InsertProvider.class);
    sqlProviderAnnotationTypes.add(UpdateProvider.class);
    sqlProviderAnnotationTypes.add(DeleteProvider.class);
  }

  /**
   * <li>完成mapper类对应的xml文件的解析
   * <li>完成mapper类的注解解析
   */
  public void parse() {
    String resource = type.toString();
    //每个mapper只解析一次
    if (!configuration.isResourceLoaded(resource)) { 
      //加载解析mapper对应的xml，增加到#configuration 
      loadXmlResource();
      configuration.addLoadedResource(resource);
      assistant.setCurrentNamespace(type.getName());
      
      //处理mapper类的注解 {@link org.apache.ibatis.annotations.CacheNamespace}
      parseCache();
      //处理mapper类的注解 {@link org.apache.ibatis.annotations.CacheNamespaceRef}
      parseCacheRef();
      Method[] methods = type.getMethods();
      
      //处理mapper类的注解，增加到#configuration
      for (Method method : methods) {
    	//处理方法的Result、Constructor注解
        parseResultsAndConstructorArgs(method);
        //处理方法的其他注解
        parseStatement(method);
      }
    }
  }

  private void loadXmlResource() {
    // Spring may not know the real resource name so we check a flag
    // to prevent loading again a resource twice
    // this flag is set at XMLMapperBuilder#bindMapperForNamespace
	//这里跟spring有什么关系，还没看？
    if (!configuration.isResourceLoaded("namespace:" + type.getName())) {
      String xmlResource = type.getName().replace('.', '/') + ".xml";
      InputStream inputStream = null;
      try {
    	//寻找该mapper接口所在包路径中相同名称的xml文件
        inputStream = Resources.getResourceAsStream(type.getClassLoader(), xmlResource);
      } catch (IOException e) {
        // ignore, resource is not required
      }
      if (inputStream != null) {
        XMLMapperBuilder xmlParser = new XMLMapperBuilder(inputStream, assistant.getConfiguration(), xmlResource, configuration.getSqlFragments(), type.getName());
        //解析mapper的xml，增加到#configuration 
        xmlParser.parse();
      }
    }
  }

  /**
   * 处理mapper类的注解 {@link org.apache.ibatis.annotations.CacheNamespace}
   */
  private void parseCache() {
    CacheNamespace cacheDomain = type.getAnnotation(CacheNamespace.class);
    if (cacheDomain != null) {
      assistant.useNewCache(cacheDomain.implementation(), cacheDomain.eviction(), cacheDomain.flushInterval(), cacheDomain.size(), cacheDomain.readWrite(), null);
    }
  }
  /**
   * 处理mapper类的注解 {@link org.apache.ibatis.annotations.CacheNamespaceRef}
   */
  private void parseCacheRef() {
    CacheNamespaceRef cacheDomainRef = type.getAnnotation(CacheNamespaceRef.class);
    if (cacheDomainRef != null) {
      assistant.useCacheRef(cacheDomainRef.value().getName());
    }
  }

  /**
   * 处理方法的注解:
   *  <li>{@link org.apache.ibatis.annotations.ConstructorArgs}
   *  <li>{@link org.apache.ibatis.annotations.Results}
   *  <li>{@link org.apache.ibatis.annotations.TypeDiscriminator}
   */
  private void parseResultsAndConstructorArgs(Method method) {
    Class<?> returnType = getReturnType(method);
    if (returnType != null) {
      ConstructorArgs args = method.getAnnotation(ConstructorArgs.class);
      Results results = method.getAnnotation(Results.class);
      TypeDiscriminator typeDiscriminator = method.getAnnotation(TypeDiscriminator.class);
      String resultMapId = generateResultMapName(method);
      applyResultMap(resultMapId, returnType, argsIf(args), resultsIf(results), typeDiscriminator);
    }
  }

  /**
   * <li>带参数的函数，返回形式是:  全路径类名.方法名-参数1类型名-参数2类型名-参数3类型名
   * <li>对于无参函数，返回形式是:  全路径类名.方法名-void
   */
  private String generateResultMapName(Method method) {
    StringBuilder suffix = new StringBuilder();
    for (Class<?> c : method.getParameterTypes()) {
      suffix.append("-");
      suffix.append(c.getSimpleName());
    }
    if (suffix.length() < 1) {
      suffix.append("-void");
    }
    return type.getName() + "." + method.getName() + suffix;
  }
  private void applyResultMap(String resultMapId, Class<?> returnType, Arg[] args, Result[] results, TypeDiscriminator discriminator) {
    List<ResultMapping> resultMappings = new ArrayList<ResultMapping>();
    applyConstructorArgs(args, returnType, resultMappings);
    applyResults(results, returnType, resultMappings);
    Discriminator disc = applyDiscriminator(resultMapId, returnType, discriminator);
    assistant.addResultMap(resultMapId, returnType, null, disc, resultMappings);
    createDiscriminatorResultMaps(resultMapId, returnType, discriminator);
  }

  private void createDiscriminatorResultMaps(String resultMapId, Class<?> resultType, TypeDiscriminator discriminator) {
    if (discriminator != null) {
      for (Case c : discriminator.cases()) {
        String value = c.value();
        Class<?> type = c.type();
        String caseResultMapId = resultMapId + "-" + value;
        List<ResultMapping> resultMappings = new ArrayList<ResultMapping>();
        for (Result result : c.results()) {
          List<ResultFlag> flags = new ArrayList<ResultFlag>();
          if (result.id()) {
            flags.add(ResultFlag.ID);
          }
          ResultMapping resultMapping = assistant.buildResultMapping(
              resultType,
              result.property(),
              result.column(),
              result.javaType() == void.class ? null : result.javaType(),
              result.jdbcType() == JdbcType.UNDEFINED ? null : result.jdbcType(),
              hasNestedSelect(result) ? nestedSelectId(result) : null,
              null,
              result.typeHandler() == TypeHandler.class ? null : result.typeHandler(),
              flags);
          resultMappings.add(resultMapping);
        }
        assistant.addResultMap(caseResultMapId, type, resultMapId, null, resultMappings);
      }
    }
  }

  private Discriminator applyDiscriminator(String resultMapId, Class<?> resultType, TypeDiscriminator discriminator) {
    if (discriminator != null) {
      String column = discriminator.column();
      Class<?> javaType = discriminator.javaType() == void.class ? String.class : discriminator.javaType();
      JdbcType jdbcType = discriminator.jdbcType() == JdbcType.UNDEFINED ? null : discriminator.jdbcType();
      Class<? extends TypeHandler> typeHandler = discriminator.typeHandler() == TypeHandler.class ? null : discriminator.typeHandler();
      Case[] cases = discriminator.cases();
      Map<String, String> discriminatorMap = new HashMap<String, String>();
      for (Case c : cases) {
        String value = c.value();
        String caseResultMapId = resultMapId + "-" + value;
        discriminatorMap.put(value, caseResultMapId);
      }
      return assistant.buildDiscriminator(resultType, column, javaType, jdbcType, typeHandler, discriminatorMap);
    }
    return null;
  }

  private void parseStatement(Method method) {
    Configuration configuration = assistant.getConfiguration();
    SqlSource sqlSource = getSqlSourceFromAnnotations(method);
    if (sqlSource != null) {
      Options options = method.getAnnotation(Options.class);
      final String mappedStatementId = type.getName() + "." + method.getName();
      boolean flushCache = false;
      boolean useCache = true;
      Integer fetchSize = null;
      Integer timeout = null;
      StatementType statementType = StatementType.PREPARED;
      ResultSetType resultSetType = ResultSetType.FORWARD_ONLY;
      SqlCommandType sqlCommandType = getSqlCommandType(method);
      
      KeyGenerator keyGenerator;
      String keyProperty = "id";
      if (SqlCommandType.INSERT.equals(sqlCommandType)) {
        // first check for SelectKey annotation - that overrides everything else
        SelectKey selectKey = method.getAnnotation(SelectKey.class);
        if (selectKey != null) {
            keyGenerator = handleSelectKeyAnnotation(selectKey, mappedStatementId, getParameterType(method));
            keyProperty = selectKey.keyProperty();
        } else {
          if (options == null) {
              keyGenerator = configuration.isUseGeneratedKeys() ? new Jdbc3KeyGenerator(null) : new NoKeyGenerator();
          } else {
              keyGenerator = options.useGeneratedKeys() ? new Jdbc3KeyGenerator(options.keyColumn()) : new NoKeyGenerator();
              keyProperty = options.keyProperty();
          }
        }
      } else {
        keyGenerator = new NoKeyGenerator();
      }
      
      if (options != null) {
        flushCache = options.flushCache();
        useCache = options.useCache();
        fetchSize = options.fetchSize() > -1 ? options.fetchSize() : null;
        timeout = options.timeout() > -1 ? options.timeout() : null;
        statementType = options.statementType();
        resultSetType = options.resultSetType();
      }
      
      ResultMap resultMapAnnotation = method.getAnnotation(ResultMap.class);
      String resultMapId;
      if (resultMapAnnotation == null) {
          resultMapId = generateResultMapName(method);
      } else {
          resultMapId = resultMapAnnotation.value();
      }
      
      assistant.addMappedStatement(
          mappedStatementId,
          sqlSource,
          statementType,
          sqlCommandType,
          fetchSize,
          timeout,
          null,                             // ParameterMapID
          getParameterType(method),
          resultMapId,    // ResultMapID
          getReturnType(method),
          resultSetType,
          flushCache,
          useCache,
          keyGenerator,
          keyProperty);
    }
  }

  private Class<?> getParameterType(Method method) {
    Class<?> parameterType = null;
    Class<?>[] parameterTypes = method.getParameterTypes();
    for(int i=0; i<parameterTypes.length;i++) {
      if (!RowBounds.class.isAssignableFrom(parameterTypes[i])) {
        if (parameterType == null) {
          parameterType = parameterTypes[i];
        } else {
          parameterType = Map.class;
        }
      }
    }
    return parameterType;
  }

  private Class<?> getReturnType(Method method) {
    Class<?> returnType = method.getReturnType();
    if (Collection.class.isAssignableFrom(returnType)) {
      Type returnTypeParameter = method.getGenericReturnType();
      if (returnTypeParameter instanceof ParameterizedType) {
        Type[] actualTypeArguments = ((ParameterizedType) returnTypeParameter).getActualTypeArguments();
        if (actualTypeArguments != null && actualTypeArguments.length == 1) {
          returnTypeParameter = actualTypeArguments[0];
          if (returnTypeParameter instanceof Class) {
            returnType = (Class<?>) returnTypeParameter;
          }
        }
      }
    } else if (Map.class.isAssignableFrom(returnType)) {
      Type returnTypeParameter = method.getGenericReturnType();
      if (returnTypeParameter instanceof ParameterizedType) {
        Type[] actualTypeArguments = ((ParameterizedType) returnTypeParameter).getActualTypeArguments();
        if (actualTypeArguments != null && actualTypeArguments.length == 2) {
          returnTypeParameter = actualTypeArguments[1];
          if (returnTypeParameter instanceof Class) {
            returnType = (Class<?>) returnTypeParameter;
          }
        }
      }
    }

    return returnType;
  }

  private SqlSource getSqlSourceFromAnnotations(Method method) {
    try {
      Class<? extends Annotation> sqlAnnotationType = getSqlAnnotationType(method);
      Class<? extends Annotation> sqlProviderAnnotationType = getSqlProviderAnnotationType(method);
      if (sqlAnnotationType != null) {
        if (sqlProviderAnnotationType != null) {
          throw new BindingException("You cannot supply both a static SQL and SqlProvider to method named " + method.getName());
        }
        Annotation sqlAnnotation = method.getAnnotation(sqlAnnotationType);
        final String[] strings = (String[]) sqlAnnotation.getClass().getMethod("value").invoke(sqlAnnotation);
        return buildSqlSourceFromStrings(strings);
      } else if (sqlProviderAnnotationType != null) {
        Annotation sqlProviderAnnotation = method.getAnnotation(sqlProviderAnnotationType);
        return new ProviderSqlSource(assistant.getConfiguration(), sqlProviderAnnotation);
      }
      return null;
    } catch (Exception e) {
      throw new BuilderException("Could not find value method on SQL annotation.  Cause: " + e, e);
    }
  }

  private SqlSource buildSqlSourceFromStrings(String[] strings) {
    final StringBuilder sql = new StringBuilder();
    for (String fragment : strings) {
      sql.append(fragment);
      sql.append(" ");
    }
    ArrayList<SqlNode> contents = new ArrayList<SqlNode>();
    contents.add(new TextSqlNode(sql.toString()));
    MixedSqlNode rootSqlNode = new MixedSqlNode(contents);
    return new DynamicSqlSource(configuration, rootSqlNode);
  }
  
  private SqlCommandType getSqlCommandType(Method method) {
    Class<? extends Annotation> type = getSqlAnnotationType(method);

    if (type == null) {
      type = getSqlProviderAnnotationType(method);

      if (type == null) {
          return SqlCommandType.UNKNOWN;
      }

      if (type == SelectProvider.class) {
        type = Select.class;
      } else if (type == InsertProvider.class) {
        type = Insert.class;
      } else if (type == UpdateProvider.class) {
        type = Update.class;
      } else if (type == DeleteProvider.class) {
        type = Delete.class;
      }
    }

    return SqlCommandType.valueOf(type.getSimpleName().toUpperCase(Locale.ENGLISH));
  }

  private Class<? extends Annotation> getSqlAnnotationType(Method method) {
    return chooseAnnotationType(method, sqlAnnotationTypes);
  }

  private Class<? extends Annotation> getSqlProviderAnnotationType(Method method) {
    return chooseAnnotationType(method, sqlProviderAnnotationTypes);
  }

  private Class<? extends Annotation> chooseAnnotationType(Method method, Set<Class<? extends Annotation>> types) {
    for (Class<? extends Annotation> type : types) {
      Annotation annotation = method.getAnnotation(type);
      if (annotation != null) {
        return type;
      }
    }
    return null;
  }

  private void applyResults(Result[] results, Class<?> resultType, List<ResultMapping> resultMappings) {
    if (results.length > 0) {
      for (Result result : results) {
        ArrayList<ResultFlag> flags = new ArrayList<ResultFlag>();
        if (result.id()) flags.add(ResultFlag.ID);

        ResultMapping resultMapping = assistant.buildResultMapping(
            resultType,
            result.property(),
            result.column(),
            result.javaType() == void.class ? null : result.javaType(),
            result.jdbcType() == JdbcType.UNDEFINED ? null : result.jdbcType(),
            hasNestedSelect(result) ? nestedSelectId(result) : null,
            null,
            result.typeHandler() == TypeHandler.class ? null : result.typeHandler(),
            flags);
        resultMappings.add(resultMapping);
      }
    }
  }

  private String nestedSelectId(Result result) {
    String nestedSelect = result.one().select();
    if (nestedSelect.length() < 1) {
      nestedSelect = result.many().select();
    }
    if (!nestedSelect.contains(".")) {
      nestedSelect = type.getName() + "." + nestedSelect;
    }
    return nestedSelect;
  }

  private boolean hasNestedSelect(Result result) {
    return result.one().select().length() > 0
        || result.many().select().length() > 0;
  }

  private void applyConstructorArgs(Arg[] args, Class<?> resultType, List<ResultMapping> resultMappings) {
    if (args.length > 0) {
      for (Arg arg : args) {
        ArrayList<ResultFlag> flags = new ArrayList<ResultFlag>();
        flags.add(ResultFlag.CONSTRUCTOR);
        if (arg.id()) flags.add(ResultFlag.ID);
        ResultMapping resultMapping = assistant.buildResultMapping(
            resultType,
            null,
            arg.column(),
            arg.javaType() == void.class ? null : arg.javaType(),
            arg.jdbcType() == JdbcType.UNDEFINED ? null : arg.jdbcType(),
            null,
            null,
            arg.typeHandler() == TypeHandler.class ? null : arg.typeHandler(),
            flags);
        resultMappings.add(resultMapping);
      }
    }
  }

  private Result[] resultsIf(Results results) {
    return results == null ? new Result[0] : results.value();
  }

  private Arg[] argsIf(ConstructorArgs args) {
    return args == null ? new Arg[0] : args.value();
  }

  private KeyGenerator handleSelectKeyAnnotation(SelectKey selectKeyAnnotation, String baseStatementId, Class<?> parameterTypeClass) {
    String id = baseStatementId + SelectKeyGenerator.SELECT_KEY_SUFFIX;
    Class<?> resultTypeClass = selectKeyAnnotation.resultType();
    StatementType statementType = selectKeyAnnotation.statementType();
    String keyProperty = selectKeyAnnotation.keyProperty();
    boolean executeBefore = selectKeyAnnotation.before();

    // defaults
    boolean useCache = false;
    KeyGenerator keyGenerator = new NoKeyGenerator();
    Integer fetchSize = null;
    Integer timeout = null;
    boolean flushCache = false;
    String parameterMap = null;
    String resultMap = null;
    ResultSetType resultSetTypeEnum = null;

    SqlSource sqlSource = buildSqlSourceFromStrings(selectKeyAnnotation.statement());
    SqlCommandType sqlCommandType = SqlCommandType.SELECT;

    assistant.addMappedStatement(id, sqlSource, statementType, sqlCommandType,
          fetchSize, timeout, parameterMap, parameterTypeClass, resultMap, resultTypeClass,
          resultSetTypeEnum, flushCache, useCache, keyGenerator, keyProperty);

    id = assistant.applyCurrentNamespace(id);

    MappedStatement keyStatement = configuration.getMappedStatement(id, false);
    SelectKeyGenerator answer = new SelectKeyGenerator(keyStatement, executeBefore); 
    configuration.addKeyGenerator(id, answer);
    return answer;
  }
}
