package org.apache.ibatis.builder.xml.dynamic;

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

  public boolean apply(DynamicContext context) {
    if (evaluator.evaluateBoolean(test, context.getBindings())) {
      contents.apply(context);
      return true;
    }
    return false;
  }

}
