package org.apache.ibatis.reflection.property;

import java.util.Iterator;

/**
 * 解析链式表达式，如：person[0].school.schoolName
 */
public class PropertyTokenizer implements Iterable<PropertyTokenizer>, Iterator<PropertyTokenizer> {
  /**
   * 以person[0].school.schoolName为例，name值是person
   */
  private String name;
  
  /**
   * 以person[0].school.schoolName为例，indexedName值是person[0]
   */
  private String indexedName;
  
  /**
   * 以person[0].school.schoolName为例，index值是0
   */
  private String index;
  
  /**
   * 以person[0].school.schoolName为例，children值是school.schoolName
   */
  private String children;

  /**
   * 可以处理链式表达式，如：person[0].school.schoolName
   * @param fullname 原始表达式(链式表达式)
   */
  public PropertyTokenizer(String fullname) {
    int delim = fullname.indexOf('.');
    if (delim > -1) {
      name = fullname.substring(0, delim);
      children = fullname.substring(delim + 1);
    } else {
      name = fullname;
      children = null;
    }
    indexedName = name;
    delim = name.indexOf('[');
    if (delim > -1) {
      index = name.substring(delim + 1, name.length() - 1);
      name = name.substring(0, delim);
    }
  }

  public String getName() {
    return name;
  }

  public String getIndex() {
    return index;
  }

  public String getIndexedName() {
    return indexedName;
  }

  public String getChildren() {
    return children;
  }

  public boolean hasNext() {
    return children != null;
  }

  /**
   * 指向{@link #children}的PropertyTokenizer
   */
  public PropertyTokenizer next() {
    return new PropertyTokenizer(children);
  }

  public void remove() {
    throw new UnsupportedOperationException("Remove is not supported, as it has no meaning in the context of properties.");
  }

  public Iterator<PropertyTokenizer> iterator() {
    return this;
  }
}
