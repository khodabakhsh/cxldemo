package org.apache.ibatis.builder;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.session.Configuration;

import java.util.List;

public class StaticSqlSource implements SqlSource {

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

	public BoundSql getBoundSql(Object parameterObject) {
		return new BoundSql(configuration, sql, parameterMappings, parameterObject);
	}

}
