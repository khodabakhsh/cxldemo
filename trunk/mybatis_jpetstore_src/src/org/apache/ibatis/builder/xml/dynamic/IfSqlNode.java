package org.apache.ibatis.builder.xml.dynamic;

/**
 * 能够处理：
 * <ol>
 * <li>&lt;if&gt;节点
 * <li>&lt;choose&gt;节点的子节点&lt;when&gt;
 */
public class IfSqlNode implements SqlNode {

	private ExpressionEvaluator evaluator;
	/**
	 * test表达式
	 */
	private String test;
	/**
	 * 包含所有子节点的{@link org.apache.ibatis.builder.xml.dynamic.MixedSqlNode}
	 */
	private SqlNode contents;

	public IfSqlNode(SqlNode contents, String test) {
		this.test = test;
		this.contents = contents;
		this.evaluator = new ExpressionEvaluator();
	}

	/**
	 * 根据&lt;if&gt;节点的test条件，判断是否解析其子节点
	 */
	public boolean apply(DynamicContext context) {
		if (evaluator.evaluateBoolean(test, context.getBindings())) {
			contents.apply(context);
			return true;
		}
		return false;
	}

}
