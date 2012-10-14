package org.apache.ibatis.executor.result;

import org.apache.ibatis.session.ResultContext;

/**
 * 用于循环处理数据库操作结果时，
 * <li>持有结果实例
 * <li>结果累加计数
 */
public class DefaultResultContext implements ResultContext {

  private Object resultObject;
  private int resultCount;
  private boolean stopped;

  public DefaultResultContext() {
    resultObject = null;
    resultCount = 0;
    stopped = false;
  }

  public Object getResultObject() {
    return resultObject;
  }

  public int getResultCount() {
    return resultCount;
  }

  public boolean isStopped() {
    return stopped;
  }

  public void nextResultObject(Object resultObject) {
    resultCount++;
    this.resultObject = resultObject;
  }

  public void stop() {
    this.stopped = true;
  }

}
