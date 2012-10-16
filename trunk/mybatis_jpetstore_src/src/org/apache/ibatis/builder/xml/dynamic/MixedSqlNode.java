package org.apache.ibatis.builder.xml.dynamic;

import java.util.List;

public class MixedSqlNode implements SqlNode {
  /**
   * 所有子节点的集合
   */
  private List<SqlNode> contents;

  public MixedSqlNode(List<SqlNode> contents) {
    this.contents = contents;
  }

  /**
   * 循环apply每个子节点
   */
  public boolean apply(DynamicContext context) {
    for (SqlNode sqlNode : contents) {
      sqlNode.apply(context);
    }
    return true;
  }
}
