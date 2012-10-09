/*
 * $Id: HelloWorld.java 471756 2006-11-06 15:01:43Z husted $
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package example;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

/**
 * <code>Set welcome message.</code>
 */
public class HelloWorld extends ExampleSupport {

	//未指定method属性时，执行execute方法
    public String execute() throws Exception {
        setMessage(getText(MESSAGE));
        return SUCCESS;
    }
    //最普通应用
    public String echo(){
    	ServletActionContext.getRequest().setAttribute("echo", " u just call the {@link HelloWorld#echo}");
    	return SUCCESS;
    }
    //直接write响应信息，无返回值(void)
    public void write(){
    	try {
			ServletActionContext.getResponse().getWriter().write("directly write response");
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    //重定向到页面
    public String redirect_url(){
    	return "success";
    }
    // 重定向到action
    public String redirect_action(){
    	return "success";
    }
    public String handle_redirect_from_action(){
    	try {
			ServletActionContext.getResponse().getWriter().write("handle_redirect_from_action");
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return null;
    }
    public String subPathEcho(){
    	try {
			ServletActionContext.getResponse().getWriter().write("/sub/path/echo");
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return null;
    }
    
    public String echoUserName(){
    	try {
			ServletActionContext.getResponse().getWriter().write("请求参数"+"userName --- >  "+userName);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return null;
    }
    public String echoActionParam(){
    	try {
			ServletActionContext.getResponse().getWriter().write("配置在当前<action>中的<param>节点值  -->  "+actionParam);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return null;
    }
    public void setActionParam(String value){
    	actionParam = value;
    }
    private String actionParam;
    
    private String userName;
    
    

    public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
     * Provide default valuie for Message property.
     */
    public static final String MESSAGE = "HelloWorld.message";

    /**
     * Field for Message property.
     */
    private String message;

    /**
     * Return Message property.
     *
     * @return Message property
     */
    public String getMessage() {
        return message;
    }

    /**
     * Set Message property.
     *
     * @param message Text to display on HelloWorld page.
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
