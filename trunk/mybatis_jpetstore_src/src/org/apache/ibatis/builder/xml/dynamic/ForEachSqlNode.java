package org.apache.ibatis.builder.xml.dynamic;

import org.apache.ibatis.parsing.GenericTokenParser;
import org.apache.ibatis.parsing.TokenHandler;
import org.apache.ibatis.session.Configuration;

import java.util.Map;
/**
 * &lt;foreach&gt;节点 
 */
public class ForEachSqlNode implements SqlNode {
  public static final String ITEM_PREFIX = "__frch_";

  private ExpressionEvaluator evaluator;
  
  /**
   * foreach 节点 的collection属性
   */
  private String collectionExpression;
  
  /**
   * foreach 节点包含的所有内容
   */
  private SqlNode contents;
  
  /**
   * foreach 节点 的open属性
   */
  private String open;
  
  /**
   * foreach 节点 的close属性
   */
  private String close;
  
  /**
   * foreach 节点 的separator属性
   */
  private String separator;
  
  /**
   * foreach 节点 的item属性
   */
  private String item;
  
  /**
   * foreach 节点 的index属性
   */
  private String index;
  private Configuration configuration;

  public ForEachSqlNode(Configuration configuration, SqlNode contents, String collectionExpression, String index, String item, String open, String close, String separator) {
    this.evaluator = new ExpressionEvaluator();
    this.collectionExpression = collectionExpression;
    this.contents = contents;
    this.open = open;
    this.close = close;
    this.separator = separator;
    this.index = index;
    this.item = item;
    this.configuration = configuration;
  }

  public boolean apply(DynamicContext context) {
    Map<String, Object> bindings = context.getBindings();
    final Iterable iterable = evaluator.evaluateIterable(collectionExpression, bindings);
    boolean first = true;
    applyOpen(context);
    int i = 0;
    for (Object o : iterable) {
      DynamicContext oldContext = context;
      if (first) {
        first = false;
        context = new PrefixedContext(context, "");
      } else {
        if (separator != null) {
          context = new PrefixedContext(context, separator);
        } else {
          context = new PrefixedContext(context, "");
        }
      }
      int uniqueNumber = context.getUniqueNumber();
      applyItem(context, o, uniqueNumber);
      applyIndex(context, i);
      contents.apply(new FilteredDynamicContext(configuration, context, item, uniqueNumber));
      first = !((PrefixedContext) context).isPrefixApplied();
      context = oldContext;
      i++;
    }
    applyClose(context);
    return true;
  }

  private void applyIndex(DynamicContext context, int i) {
    if (index != null) {
      context.bind(index, i);
    }
  }

  private void applyItem(DynamicContext context, Object o, int i) {
    if (item != null) {
      context.bind(item, o);
      context.bind(itemizeItem(item, i), o);
    }
  }

  private void applyOpen(DynamicContext context) {
    if (open != null) {
      context.appendSql(open);
    }
  }

  private void applyClose(DynamicContext context) {
    if (close != null) {
      context.appendSql(close);
    }
  }

  /**
   * {@link #ITEM_PREFIX} + item + "_" + i
   */
  private static String itemizeItem(String item, int i) {
    return new StringBuilder(ITEM_PREFIX).append(item).append("_").append(i).toString();
  }
  
  /**
   *  根据条件在{@link org.apache.ibatis.builder.xml.dynamic.DynamicContext#appendSql(String)}操作之前，
   *  先解析所有#{{@link #item}}参数，转化为对应的占位符(以itemizeItem计算)
   */
  private static class FilteredDynamicContext extends DynamicContext {
    private DynamicContext delegate;
    private int index;
    private String item;

    public FilteredDynamicContext(Configuration configuration,DynamicContext delegate, String item, int i) {
      super(configuration, null);
      this.delegate = delegate;
      this.index = i;
      this.item = item;
    }

    public Map<String, Object> getBindings() {
      return delegate.getBindings();
    }
    public void bind(String name, Object value) {
      delegate.bind(name, value);
    }

    public String getSql() {
      return delegate.getSql();
    }

    /**
     * 转化#{{@link #item}}参数参数
     */
    public void appendSql(String sql) {
      GenericTokenParser parser = new GenericTokenParser("#{", "}", new TokenHandler() {
        public String handleToken(String content) {
          String newContent = content.replaceFirst(item, itemizeItem(item, index));
          return new StringBuilder("#{").append(newContent).append("}").toString();
        }
      });

      delegate.appendSql(parser.parse(sql));
    }

    @Override
    public int getUniqueNumber() {
      return delegate.getUniqueNumber();
    }

  }


  /**
   *  根据条件在{@link org.apache.ibatis.builder.xml.dynamic.DynamicContext#appendSql(String)}操作前加上前缀
   */
  private class PrefixedContext extends DynamicContext {
    private DynamicContext delegate;
    private String prefix;
    private boolean prefixApplied;

    public PrefixedContext(DynamicContext delegate, String prefix) {
      super(configuration, null);
      this.delegate = delegate;
      this.prefix = prefix;
      this.prefixApplied = false;
    }

    public boolean isPrefixApplied() {
      return prefixApplied;
    }

    public Map<String, Object> getBindings() {
      return delegate.getBindings();
    }

    public void bind(String name, Object value) {
      delegate.bind(name, value);
    }

    /**
     * 根据条件加上前缀
     */
    public void appendSql(String sql) {
      if (!prefixApplied && sql != null && sql.trim().length() > 0) {
        delegate.appendSql(prefix);
        prefixApplied = true;
      }
      delegate.appendSql(sql);
    }

    public String getSql() {
      return delegate.getSql();
    }

    public int getUniqueNumber() {
      return delegate.getUniqueNumber();
    }
  }

}
