/*
 * Copyright 2002-2006,2009 The Apache Software Foundation.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package com.opensymphony.xwork2;

import com.opensymphony.xwork2.config.Configuration;
import com.opensymphony.xwork2.config.ConfigurationException;
import com.opensymphony.xwork2.config.entities.ActionConfig;
import com.opensymphony.xwork2.config.entities.InterceptorMapping;
import com.opensymphony.xwork2.config.entities.ResultConfig;
import com.opensymphony.xwork2.inject.Container;
import com.opensymphony.xwork2.inject.Inject;
import com.opensymphony.xwork2.interceptor.PreResultListener;
import com.opensymphony.xwork2.util.ValueStack;
import com.opensymphony.xwork2.util.ValueStackFactory;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;
import com.opensymphony.xwork2.util.profiling.UtilTimerStack;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.dispatcher.mapper.ActionMapping;

/**
 * The Default ActionInvocation implementation
 * 
 * @author Rainer Hermanns
 * @author tmjee
 * @version $Date: 2011-06-09 00:40:33 +0200 (Thu, 09 Jun 2011) $ $Id: DefaultActionInvocation.java 1133590 2011-06-08
 * 22:40:33Z jafl $
 * @see com.opensymphony.xwork2.DefaultActionProxy
 */
public class DefaultActionInvocation implements ActionInvocation {

	private static final long serialVersionUID = -585293628862447329L;

	// static {
	// if (ObjectFactory.getContinuationPackage() != null) {
	// continuationHandler = new ContinuationHandler();
	// }
	// }
	private static final Logger LOG = LoggerFactory.getLogger(DefaultActionInvocation.class);

	private static final Class[] EMPTY_CLASS_ARRAY = new Class[0];
	private static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];

	protected Object action;
	/**
	 * 对应ActionProxy实例
	 */
	protected ActionProxy proxy;
	protected List<PreResultListener> preResultListeners;
	/**
	 * <li>是一个大杂烩对象。
	 * <li>最初来源，看： {@link org.apache.struts2.dispatcher.Dispatcher#serviceAction(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, javax.servlet.ServletContext, org.apache.struts2.dispatcher.mapper.ActionMapping)}
	 */
	protected Map<String, Object> extraContext;
	protected ActionContext invocationContext;
	/**
	 * action对应拦截器链
	 * struts有很多拦截器，
	 * 如
	 * <li>com.opensymphony.xwork2.interceptor.PrepareInterceptor
	 * <li>com.opensymphony.xwork2.interceptor.ParametersInterceptor
	 * <li>com.opensymphony.xwork2.interceptor.ModelDrivenInterceptor
	 * <li>还有很多....
	 */
	protected Iterator<InterceptorMapping> interceptors;
	protected ValueStack stack;
	protected Result result;
	protected Result explicitResult;
	protected String resultCode;
	protected boolean executed = false;
	protected boolean pushAction = true;
	protected ObjectFactory objectFactory;
	protected ActionEventListener actionEventListener;
	protected ValueStackFactory valueStackFactory;
	protected Container container;
	private Configuration configuration;
	protected UnknownHandlerManager unknownHandlerManager;

	public DefaultActionInvocation(final Map<String, Object> extraContext, final boolean pushAction) {
		DefaultActionInvocation.this.extraContext = extraContext;
		DefaultActionInvocation.this.pushAction = pushAction;
	}

	@Inject
	public void setUnknownHandlerManager(UnknownHandlerManager unknownHandlerManager) {
		this.unknownHandlerManager = unknownHandlerManager;
	}

	@Inject
	public void setValueStackFactory(ValueStackFactory fac) {
		this.valueStackFactory = fac;
	}

	@Inject
	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}

	@Inject
	public void setObjectFactory(ObjectFactory fac) {
		this.objectFactory = fac;
	}

	@Inject
	public void setContainer(Container cont) {
		this.container = cont;
	}

	@Inject(required = false)
	public void setActionEventListener(ActionEventListener listener) {
		this.actionEventListener = listener;
	}

	public Object getAction() {
		return action;
	}

	public boolean isExecuted() {
		return executed;
	}

	public ActionContext getInvocationContext() {
		return invocationContext;
	}

	public ActionProxy getProxy() {
		return proxy;
	}

	/**
	 * If the DefaultActionInvocation has been executed before and the Result is an instance of ActionChainResult, this
	 * method will walk down the chain of ActionChainResults until it finds a non-chain result, which will be returned.
	 * If the DefaultActionInvocation's result has not been executed before, the Result instance will be created and
	 * populated with the result params.
	 * 
	 * @return a Result instance
	 * @throws Exception
	 */
	public Result getResult() throws Exception {
		Result returnResult = result;

		// If we've chained to other Actions, we need to find the last result
		while (returnResult instanceof ActionChainResult) {
			ActionProxy aProxy = ((ActionChainResult) returnResult).getProxy();

			if (aProxy != null) {
				Result proxyResult = aProxy.getInvocation().getResult();

				if ((proxyResult != null) && (aProxy.getExecuteResult())) {
					returnResult = proxyResult;
				} else {
					break;
				}
			} else {
				break;
			}
		}

		return returnResult;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		if (isExecuted())
			throw new IllegalStateException("Result has already been executed.");

		this.resultCode = resultCode;
	}

	public ValueStack getStack() {
		return stack;
	}

	/**
	 * Register a com.opensymphony.xwork2.interceptor.PreResultListener to be notified after the Action is executed and
	 * before the Result is executed. The ActionInvocation implementation must guarantee that listeners will be called
	 * in the order in which they are registered. Listener registration and execution does not need to be thread-safe.
	 * 
	 * @param listener
	 */
	public void addPreResultListener(PreResultListener listener) {
		if (preResultListeners == null) {
			preResultListeners = new ArrayList<PreResultListener>(1);
		}

		preResultListeners.add(listener);
	}

	public Result createResult() throws Exception {

		if (explicitResult != null) {
			Result ret = explicitResult;
			explicitResult = null;

			return ret;
		}
		ActionConfig config = proxy.getConfig();
		
		//获得action配置的result节点信息（包含全局配置global-results节点）
		Map<String, ResultConfig> results = config.getResults();

		ResultConfig resultConfig = null;

		try {
			resultConfig = results.get(resultCode);
		} catch (NullPointerException e) {
			// swallow
		}

		if (resultConfig == null) {
			// If no result is found for the given resultCode, try to get a
			// wildcard '*' match.
			
			//没有找到匹配项，尝试匹配*号
			resultConfig = results.get("*");
		}

		if (resultConfig != null) {
			try {
				//构建返回结果@Result
				return objectFactory.buildResult(resultConfig, invocationContext.getContextMap());
			} catch (Exception e) {
				LOG.error(
						"There was an exception while instantiating the result of type " + resultConfig.getClassName(),
						e);
				throw new XWorkException(e, resultConfig);
			}
		} else if (resultCode != null && !Action.NONE.equals(resultCode) && unknownHandlerManager.hasUnknownHandlers()) {
			return unknownHandlerManager.handleUnknownResult(invocationContext, proxy.getActionName(),
					proxy.getConfig(), resultCode);
		}
		return null;
	}

	/**
	 * @throws ConfigurationException If no result can be found with the returned code
	 */
	public String invoke() throws Exception {
		String profileKey = "invoke: ";
		try {
			UtilTimerStack.push(profileKey);

			if (executed) {
				throw new IllegalStateException("Action has already executed");
			}
			// 拦截器
			if (interceptors.hasNext()) {
				final InterceptorMapping interceptor = (InterceptorMapping) interceptors.next();
				String interceptorMsg = "interceptor: " + interceptor.getName();
				UtilTimerStack.push(interceptorMsg);
				try {
					// 这里 通过intercept方法 实现拦截器栈的调用。
					// 每个拦截器可以通过调用{@link DefaultActionInvocation#invoke}
					// 来调用拦截器栈中下一个拦截器，或者可以通过返回结果值直接中断拦截过程。
					// 实现的是一个拦截器栈的设计。
					// 如拦截器a、b、c在intercept前的代码顺序调用，在intercept后的代码按c、b、a反向调用
					resultCode = interceptor.getInterceptor().intercept(DefaultActionInvocation.this);
				} finally {
					UtilTimerStack.pop(interceptorMsg);
				}
			} else {
				// 如果拦截器栈 顺利执行 到最后一个拦截器(顺利执行即中间过程中的拦截器没有中断整个拦截过程)
				// ，这里就是被最后这个拦截器所调用。
				//也是在这里，执行action方法！！！
				resultCode = invokeActionOnly();
			}

			// this is needed because the result will be executed, then control
			// will return to the Interceptor, which will
			// return above and flow through again

			// 整个拦截器栈中应该仅仅让的拦截器执行一次,再把执行结果逆向返回上一个拦截器。
			if (!executed) {
				
				//在某些拦截器(如ModelDrivenInterceptor中)，可以根据设置添加PreResultListener实例
				if (preResultListeners != null) {
					for (Object preResultListener : preResultListeners) {
						PreResultListener listener = (PreResultListener) preResultListener;

						String _profileKey = "preResultListener: ";
						try {
							UtilTimerStack.push(_profileKey);
							listener.beforeResult(this, resultCode);
						} finally {
							UtilTimerStack.pop(_profileKey);
						}
					}
				}

				// now execute the result, if we're supposed to
				//对action方法返回的结果，执行最终的处理，
				//构建最终的响应内容（forward、redirect、或者是其它视图渲染）。
				if (proxy.getExecuteResult()) {
					executeResult();
				}
				executed = true;
			}

			return resultCode;
		} finally {
			UtilTimerStack.pop(profileKey);
		}
	}

	public String invokeActionOnly() throws Exception {
		return invokeAction(getAction(), proxy.getConfig());
	}

	/**
	 * 为{@link #action} 赋值
	 */
	protected void createAction(Map<String, Object> contextMap) {
		// load action
		String timerKey = "actionCreate: " + proxy.getActionName();
		try {
			UtilTimerStack.push(timerKey);
			action = objectFactory.buildAction(proxy.getActionName(), proxy.getNamespace(), proxy.getConfig(),
					contextMap);
		} catch (InstantiationException e) {
			throw new XWorkException("Unable to intantiate Action!", e, proxy.getConfig());
		} catch (IllegalAccessException e) {
			throw new XWorkException("Illegal access to constructor, is it public?", e, proxy.getConfig());
		} catch (Exception e) {
			String gripe = "";

			if (proxy == null) {
				gripe = "Whoa!  No ActionProxy instance found in current ActionInvocation.  This is bad ... very bad";
			} else if (proxy.getConfig() == null) {
				gripe = "Sheesh.  Where'd that ActionProxy get to?  I can't find it in the current ActionInvocation!?";
			} else if (proxy.getConfig().getClassName() == null) {
				gripe = "No Action defined for '" + proxy.getActionName() + "' in namespace '" + proxy.getNamespace()
						+ "'";
			} else {
				gripe = "Unable to instantiate Action, " + proxy.getConfig().getClassName() + ",  defined for '"
						+ proxy.getActionName() + "' in namespace '" + proxy.getNamespace() + "'";
			}

			gripe += (((" -- " + e.getMessage()) != null) ? e.getMessage() : " [no message in exception]");
			throw new XWorkException(gripe, e, proxy.getConfig());
		} finally {
			UtilTimerStack.pop(timerKey);
		}

		if (actionEventListener != null) {
			action = actionEventListener.prepare(action, stack);
		}
	}

	/**
	 * 创建一个contextMap,这个对象也是包罗万象啦！
	 */
	protected Map<String, Object> createContextMap() {
		
		Map<String, Object> contextMap;
		
		//见{@link org.apache.struts2.dispatcher.Dispatcher#serviceAction(HttpServletRequest request,HttpServletResponse response, ServletContext context,ActionMapping mapping)}
		//,已经中为extraContext赋值，且已包含ValueStack值啦
		if ((extraContext != null) && (extraContext.containsKey(ActionContext.VALUE_STACK))) {
			// In case the ValueStack was passed in
			stack = (ValueStack) extraContext.get(ActionContext.VALUE_STACK);

			if (stack == null) {
				throw new IllegalStateException("There was a null Stack set into the extra params.");
			}

			contextMap = stack.getContext();
		} else {
			// create the value stack
			// this also adds the ValueStack to its context
			stack = valueStackFactory.createValueStack();

			// create the action context
			contextMap = stack.getContext();
		}

		// put extraContext in
		if (extraContext != null) {
			//这里让contextMap拥有全部extraContext的内容,当然也包括了value stack值
			contextMap.putAll(extraContext);
		}

		// put this DefaultActionInvocation into the context map
		contextMap.put(ActionContext.ACTION_INVOCATION, this);
		contextMap.put(ActionContext.CONTAINER, container);

		return contextMap;
	}

	/**
	 * Uses getResult to get the final Result and executes it
	 * 
	 * @throws ConfigurationException If not result can be found with the returned code
	 */
	private void executeResult() throws Exception {
		//1.对于type="redirect"的result节点(重定向到页面)，返回@ServletRedirectResult
		//  对于type="redirectAction"的result节点(重定向到action)，返回@ServletActionRedirectResult
		//2.Includes or forwards 到一个视图（通常是jsp页面），返回@ServletDispatcherResult
		//3.result可能为null，例如对于action中的void方法，没有返回值，这时result就是null，又例如action方法返回null时
		//4.其它各种视图result或者自定义的result
		result = createResult();

		String timerKey = "executeResult: " + getResultCode();
		try {
			UtilTimerStack.push(timerKey);
			if (result != null) {
				//构建【最终】响应内容
				result.execute(this);
			} else if (resultCode != null && !Action.NONE.equals(resultCode)) {
				throw new ConfigurationException("No result defined for action " + getAction().getClass().getName()
						+ " and result " + getResultCode(), proxy.getConfig());
			} else {
				if (LOG.isDebugEnabled()) {
					LOG.debug("No result returned for action " + getAction().getClass().getName() + " at "
							+ proxy.getConfig().getLocation());
				}
			}
		} finally {
			UtilTimerStack.pop(timerKey);
		}
	}

	/**
	 * 完成众多属性的赋值:
	 * <li>{@link #proxy}
	 * <li>{@link #action}
	 * <li>{@link #stack}
	 * <li>{@link #invocationContext}
	 * <li>{@link #interceptors}
	 * <li>等等...
	 */
	public void init(ActionProxy proxy) {
		this.proxy = proxy;
		
	    //创建一个contextMap，是一个包罗万象的对象呀。
		Map<String, Object> contextMap = createContextMap();

		// Setting this so that other classes, like object factories, can use
		// the ActionProxy and other
		// contextual information to operate
		ActionContext actionContext = ActionContext.getContext();

		if (actionContext != null) {
			actionContext.setActionInvocation(this);
		}

		//为action属性赋值
		createAction(contextMap);

		if (pushAction) {
			
			//加入action实例到value stack的root中。
			stack.push(action);
			contextMap.put("action", action);
		}

		//为属性invocationContext赋值
		invocationContext = new ActionContext(contextMap);
		invocationContext.setName(proxy.getActionName());

		// get a new List so we don't get problems with the iterator if someone
		// changes the list
		List<InterceptorMapping> interceptorList = new ArrayList<InterceptorMapping>(proxy.getConfig()
				.getInterceptors());
		interceptors = interceptorList.iterator();
	}

	/**
	 * 在这里实现了执行action里的方法。
	 * 注意，里面有代码表明struts里action类用于拦截映射的method方法都不应带有任何参数
	 */
	protected String invokeAction(Object action, ActionConfig actionConfig) throws Exception {
		String methodName = proxy.getMethod();

		if (LOG.isDebugEnabled()) {
			LOG.debug("Executing action method = " + actionConfig.getMethodName());
		}

		String timerKey = "invokeAction: " + proxy.getActionName();
		try {
			UtilTimerStack.push(timerKey);

			boolean methodCalled = false;
			Object methodResult = null;
			Method method = null;
			try {
//				注意，下面的代码表明struts里action类用于拦截映射的method方法都不应带有任何参数
				method = getAction().getClass().getMethod(methodName, EMPTY_CLASS_ARRAY);
			} catch (NoSuchMethodException e) {
				// hmm -- OK, try doXxx instead
				try {
					//拿不到对应的方法，还会去匹配do***的方法
					String altMethodName = "do" + methodName.substring(0, 1).toUpperCase() + methodName.substring(1);
					method = getAction().getClass().getMethod(altMethodName, EMPTY_CLASS_ARRAY);
				} catch (NoSuchMethodException e1) {
					// well, give the unknown handler a shot
					if (unknownHandlerManager.hasUnknownHandlers()) {
						try {
							methodResult = unknownHandlerManager.handleUnknownMethod(action, methodName);
							methodCalled = true;
						} catch (NoSuchMethodException e2) {
							// throw the original one
							throw e;
						}
					} else {
						throw e;
					}
				}
			}
            //真正执行action映射方法。
			if (!methodCalled) {
				methodResult = method.invoke(action, EMPTY_OBJECT_ARRAY);
			}
            //预处理action方法执行后的结果
			return saveResult(actionConfig, methodResult);
		} catch (NoSuchMethodException e) {
			throw new IllegalArgumentException("The " + methodName + "() is not defined in action "
					+ getAction().getClass() + "");
		} catch (InvocationTargetException e) {
			// We try to return the source exception.
			Throwable t = e.getTargetException();

			if (actionEventListener != null) {
				String result = actionEventListener.handleException(t, getStack());
				if (result != null) {
					return result;
				}
			}
			if (t instanceof Exception) {
				throw (Exception) t;
			} else {
				throw e;
			}
		} finally {
			UtilTimerStack.pop(timerKey);
		}
	}

	/**
	 * Save the result to be used later.
	 * 
	 * @param actionConfig
	 * @param methodResult the result of the action.
	 * @return the result code to process.
	 */
	protected String saveResult(ActionConfig actionConfig, Object methodResult) {
		if (methodResult instanceof Result) {
			this.explicitResult = (Result) methodResult;

			// Wire the result automatically
			container.inject(explicitResult);
			return null;
		} else {
			return (String) methodResult;
		}
	}

}
