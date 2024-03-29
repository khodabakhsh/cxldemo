package org.apache.ibatis.builder;

import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.parsing.GenericTokenParser;
import org.apache.ibatis.parsing.TokenHandler;
import org.apache.ibatis.reflection.MetaClass;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.TypeHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * <p>处理所有#{}参数,将用?号替代。
 * <p>见 {@link #parse(String, Class)}方法，构建StaticSqlSource(为其parameterMappings赋值)
 */
public class SqlSourceBuilder extends BaseBuilder {

  public SqlSourceBuilder(Configuration configuration) {
    super(configuration);
  }

  /**
   * 解析sql参数#{}
   * @param originalSql xml里配置的未解析的sql
   * @param parameterType 参数类型
   * @return {@link org.apache.ibatis.builder.StaticSqlSource}
   */
  public SqlSource parse(String originalSql, Class<?> parameterType) {
    ParameterMappingTokenHandler handler = new ParameterMappingTokenHandler(configuration, parameterType);
    GenericTokenParser parser = new GenericTokenParser("#{", "}", handler);
    //处理所有#{}参数,将用?号替代
    String sql = parser.parse(originalSql);
    return new StaticSqlSource(configuration, sql, handler.getParameterMappings());
  }

  private static class ParameterMappingTokenHandler extends BaseBuilder implements TokenHandler {

	/**
	 * 所有#{}参数对应的ParameterMapping集合
	 */
    private List<ParameterMapping> parameterMappings = new ArrayList<ParameterMapping>();
    private Class<?> parameterType;

    public ParameterMappingTokenHandler(Configuration configuration, Class<?> parameterType) {
      super(configuration);
      this.parameterType = parameterType;
    }

    public List<ParameterMapping> getParameterMappings() {
      return parameterMappings;
    }

    /**
     * 构建 {@link #parameterMappings}集合
     * @return ?号
     */
    public String handleToken(String content) {
      parameterMappings.add(buildParameterMapping(content));
      return "?";
    }

    /**
     * 处理#{}参数，可配置项：
     * <ol>
     * <li>javaType
     * <li>jdbcType
     * <li>mode
     * <li>numericScale
     * <li>resultMap
     * <li>typeHandler
     * <li>jdbcTypeName
     */
    private ParameterMapping buildParameterMapping(String content) {
      StringTokenizer parameterMappingParts = new StringTokenizer(content, ", \n\r\t");
      String propertyWithJdbcType = parameterMappingParts.nextToken();
      String property = extractPropertyName(propertyWithJdbcType);
      String jdbcType = extractJdbcType(propertyWithJdbcType);
      Class<?> propertyType;
      MetaClass metaClass = MetaClass.forClass(parameterType);
      if (typeHandlerRegistry.hasTypeHandler(parameterType)) {
        propertyType = parameterType;
      } else if ("CURSOR".equals(jdbcType)) {
        propertyType = java.sql.ResultSet.class;
      } else if (metaClass.hasGetter(property)) {
        propertyType = metaClass.getGetterType(property);
      } else {
        propertyType = Object.class;
      }
      ParameterMapping.Builder builder = new ParameterMapping.Builder(configuration, property, propertyType);
      if (jdbcType != null) {
        builder.jdbcType(resolveJdbcType(jdbcType));
      }
      while (parameterMappingParts.hasMoreTokens()) {
        String attribute = parameterMappingParts.nextToken();
        StringTokenizer attributeParts = new StringTokenizer(attribute, "=");
        if (attributeParts.countTokens() == 2) {
          String name = attributeParts.nextToken();
          String value = attributeParts.nextToken();
          if ("javaType".equals(name)) {
            builder.javaType(resolveClass(value));
          } else if ("jdbcType".equals(name)) {
            builder.jdbcType(resolveJdbcType(value));
          } else if ("mode".equals(name)) {
            builder.mode(resolveParameterMode(value));
          } else if ("numericScale".equals(name)) {
            builder.numericScale(Integer.valueOf(value));
          } else if ("resultMap".equals(name)) {
            builder.resultMapId(value);
          } else if ("typeHandler".equals(name)) {
            builder.typeHandler((TypeHandler) resolveInstance(value));
          } else if ("jdbcTypeName".equals(name)) {
            builder.jdbcTypeName(value);
          }
        } else {
          throw new BuilderException("Improper inline parameter map format.  Should be: #{propName,attr1=val1,attr2=val2}");
        }
      }
      return builder.build();
    }

    private String extractPropertyName(String property) {
      if (property.contains(":")) {
        StringTokenizer simpleJdbcTypeParser = new StringTokenizer(property, ": ");
        if (simpleJdbcTypeParser.countTokens() == 2) {
          return simpleJdbcTypeParser.nextToken();
        }
      }
      return property;
    }

    private String extractJdbcType(String property) {
      if (property.contains(":")) {
        StringTokenizer simpleJdbcTypeParser = new StringTokenizer(property, ": ");
        if (simpleJdbcTypeParser.countTokens() == 2) {
          simpleJdbcTypeParser.nextToken();
          return simpleJdbcTypeParser.nextToken();
        }
      }
      return null;
    }

  }
}
