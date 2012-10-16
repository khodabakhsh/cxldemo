package org.apache.ibatis.builder.xml.dynamic;

import ognl.Ognl;
import ognl.OgnlException;

import org.apache.ibatis.builder.BuilderException;
import org.apache.ibatis.parsing.GenericTokenParser;
import org.apache.ibatis.parsing.TokenHandler;
import org.apache.ibatis.type.SimpleTypeRegistry;

/**
 * sql文本节点处理(包括${}参数  ) 
 *
 */
public class TextSqlNode implements SqlNode {
  private String text;

  public TextSqlNode(String text) {
    this.text = text;
  }

  /**
   * 利用ognl获得、替换sql中的${}参数值
   */
  public boolean apply(DynamicContext context) {
	//这里就是处理${}参数  
    GenericTokenParser parser = new GenericTokenParser("${", "}", new BindingTokenParser(context));
    context.appendSql(parser.parse(text));
    return true;
  }

  /**
   * PS:利用OGNL取值
   */
  private static class BindingTokenParser implements TokenHandler {

    private DynamicContext context;

    public BindingTokenParser(DynamicContext context) {
      this.context = context;
    }

    /**
     * 利用ognl在{@link #context}中取值
     */
    public String handleToken(String content) {
      try {
        Object parameter = context.getBindings().get("_parameter");
        if (parameter == null) {
          context.getBindings().put("value", null);
        } else if (SimpleTypeRegistry.isSimpleType(parameter.getClass())) {
          context.getBindings().put("value", parameter);
        }
        Object value = Ognl.getValue(content, context.getBindings());
        return String.valueOf(value);
      } catch (OgnlException e) {
        throw new BuilderException("Error evaluating expression '" + content + "'. Cause: " + e, e);
      }
    }
  }


}

