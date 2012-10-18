package org.apache.ibatis.builder;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.session.Configuration;

import java.util.List;

/**
 * 已经解析了动态sql语句和参数#{}(比如把参数替换为?号占位符)的{@link org.apache.ibatis.mapping.SqlSource}
 *
 */
public class StaticSqlSource implements SqlSource {

	/**
	 * <p>这个sql，应该是能让 {@link java.sql.Connection}直接用来构建statement的，比如 {@link java.sql.Connection#prepareStatement(String)}，
	 * 即已经解析了动态sql语句和参数#{}(比如把参数替换为?号占位符)
	 */
	private String sql;
	
	
	/**
	 * 所有#{}参数对应的ParameterMapping集合
	 */
	private List<ParameterMapping> parameterMappings;
	
	private Configuration configuration;

	public StaticSqlSource(Configuration configuration, String sql) {
		this(configuration, sql, null);
	}

	public StaticSqlSource(Configuration configuration, String sql, List<ParameterMapping> parameterMappings) {
		this.sql = sql;
		this.parameterMappings = parameterMappings;
		this.configuration = configuration;
	}

	/**
	 * new 一个 BoundSql实例
	 */
	public BoundSql getBoundSql(Object parameterObject) {
		return new BoundSql(configuration, sql, parameterMappings, parameterObject);
	}

}
