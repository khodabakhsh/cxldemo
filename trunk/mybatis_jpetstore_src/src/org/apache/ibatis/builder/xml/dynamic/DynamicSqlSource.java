package org.apache.ibatis.builder.xml.dynamic;

import org.apache.ibatis.builder.SqlSourceBuilder;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.session.Configuration;

import java.util.Map;

public class DynamicSqlSource implements SqlSource {

  private Configuration configuration;
  /**
   * 一般是一个{@link org.apache.ibatis.builder.xml.dynamic.MixedSqlNode},
   * 包含了一个sql操作的xml节点（select/insert/delete/update）
   */
  private SqlNode rootSqlNode;

  public DynamicSqlSource(Configuration configuration, SqlNode rootSqlNode) {
    this.configuration = configuration;
    this.rootSqlNode = rootSqlNode;
  }

  /**
   * 解析sql获得{@link org.apache.ibatis.mapping.BoundSql}实例:
   * <li>解析动态sql(mybatis的各种xml节点)
   * <li>解析sql参数#{}
   */
  public BoundSql getBoundSql(Object parameterObject) {
    DynamicContext context = new DynamicContext(configuration, parameterObject);
    //解析动态sql(mybatis的各种xml节点)
    rootSqlNode.apply(context);
    SqlSourceBuilder sqlSourceParser = new SqlSourceBuilder(configuration);
    Class<?> parameterType = parameterObject == null ? Object.class : parameterObject.getClass();
    //解析sql参数#{},返回StaticSqlSource
    SqlSource sqlSource = sqlSourceParser.parse(context.getSql(), parameterType);
    BoundSql boundSql = sqlSource.getBoundSql(parameterObject);
    for (Map.Entry<String, Object> entry : context.getBindings().entrySet()) {
      boundSql.setAdditionalParameter(entry.getKey(), entry.getValue());
    }
    return boundSql;
  }

}
