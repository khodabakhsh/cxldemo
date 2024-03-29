/*
 * $Id: CheckboxInterceptor.java 1330502 2012-04-25 19:25:03Z lukaszlenart $
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

package org.apache.struts2.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.Iterator;

/**
 * <!-- START SNIPPET: description -->
 * Looks for a hidden identification field that specifies the original value of the checkbox.
 * If the checkbox isn't submitted, insert it into the parameters as if it was with the value
 * of 'false'.
 * <!-- END SNIPPET: description -->
 * <p/>
 * <!-- START SNIPPET: parameters -->
 * <ul><li>setUncheckedValue -
 * The default value of an unchecked box can be overridden by setting the 'uncheckedValue' property.
 * </li></ul>
 * <!-- END SNIPPET: parameters -->
 * <p/>
 * <!-- START SNIPPET: extending -->
 * <p/>
 * <!-- END SNIPPET: extending -->
 * 
 * <b>这个拦截器应该只用于struts的标签&lt; s:checkbox &gt; ;并且使用该标签，应保证不能重名，否则拦截器失效。</b>
 */
public class CheckboxInterceptor extends AbstractInterceptor {

    /** Auto-generated serialization id */
    private static final long serialVersionUID = -586878104807229585L;

    private String uncheckedValue = Boolean.FALSE.toString();

    private static final Logger LOG = LoggerFactory.getLogger(CheckboxInterceptor.class);

    public String intercept(ActionInvocation ai) throws Exception {
        Map parameters = ai.getInvocationContext().getParameters();
        Map<String, String[]> newParams = new HashMap<String, String[]>();
        Set<Map.Entry> entries = parameters.entrySet();
        for (Iterator<Map.Entry> iterator = entries.iterator(); iterator.hasNext();) {
            Map.Entry entry = iterator.next();
            String key = (String)entry.getKey();
            //由下面的代码可知，这个拦截器应该只用于struts的标签<s:checkbox>,"__checkbox_"是由该标签生成的hidden域id前缀。
            if (key.startsWith("__checkbox_")) {
                String name = key.substring("__checkbox_".length());

                Object values = entry.getValue();
                iterator.remove();
                //由此可见，<s:checkbox>重名的话，这个拦截器就失效了。。
                if (values != null && values instanceof String[] && ((String[])values).length > 1) {
                    if (LOG.isDebugEnabled()) {
                	    LOG.debug("Bypassing automatic checkbox detection due to multiple checkboxes of the same name: #0", name);
                    }
                    continue;
                }

                // is this checkbox checked/submitted?
                if (!parameters.containsKey(name)) {
                    // if not, let's be sure to default the value to false
                    newParams.put(name, new String[]{uncheckedValue});
                }
            }
        }

        parameters.putAll(newParams);

        return ai.invoke();
    }

    /**
     * Overrides the default value for an unchecked checkbox
     *
     * @param uncheckedValue The uncheckedValue to set
     */
    public void setUncheckedValue(String uncheckedValue) {
        this.uncheckedValue = uncheckedValue;
    }
}
