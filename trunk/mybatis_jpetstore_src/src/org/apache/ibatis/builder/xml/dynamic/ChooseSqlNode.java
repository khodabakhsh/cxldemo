package org.apache.ibatis.builder.xml.dynamic;

import java.util.List;

/**
 * &lt;choose&gt;节点
 */
public class ChooseSqlNode implements SqlNode {
	/**
	 * &lt;otherwise&gt;节点
	 */
  private SqlNode defaultSqlNode;
  /**
	 * &lt;when&gt;节点，使用{@link IfSqlNode}实例集合
	 */
  private List<IfSqlNode> ifSqlNodes;

  public ChooseSqlNode(List<IfSqlNode> ifSqlNodes, SqlNode defaultSqlNode) {
    this.ifSqlNodes = ifSqlNodes;
    this.defaultSqlNode = defaultSqlNode;
  }

  public boolean apply(DynamicContext context) {
	//匹配when节点
    for (SqlNode sqlNode : ifSqlNodes) {
      if (sqlNode.apply(context)) {
        return true;
      }
    }
    //匹配otherwise节点
    if (defaultSqlNode != null) {
      defaultSqlNode.apply(context);
      return true;
    }
    return false;
  }
}
