/*
 * $Id: DefaultActionMapper.java 1215224 2011-12-16 17:02:42Z lukaszlenart $
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

package org.apache.struts2.dispatcher.mapper;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.config.Configuration;
import com.opensymphony.xwork2.config.ConfigurationManager;
import com.opensymphony.xwork2.config.entities.PackageConfig;
import com.opensymphony.xwork2.inject.Container;
import com.opensymphony.xwork2.inject.Inject;
import org.apache.struts2.RequestUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.StrutsConstants;
import org.apache.struts2.dispatcher.ServletRedirectResult;
import org.apache.struts2.util.PrefixTrie;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <!-- START SNIPPET: javadoc -->
 * <p/>
 * Default action mapper implementation, using the standard *.[ext] (where ext
 * usually "action") pattern. The extension is looked up from the Struts
 * configuration key <b>struts.action.extension</b>.
 * <p/>
 * <p/> To help with dealing with buttons and other related requirements, this
 * mapper (and other {@link ActionMapper}s, we hope) has the ability to name a
 * button with some predefined prefix and have that button name alter the
 * execution behaviour. The four prefixes are:
 * <p/>
 * <ul>
 * <p/>
 * <li>Method prefix - <i>method:default</i></li>
 * <p/>
 * <li>Action prefix - <i>action:dashboard</i></li>
 * <p/>
 * <li>Redirect prefix - <i>redirect:cancel.jsp</i></li>
 * <p/>
 * <li>Redirect-action prefix - <i>redirectAction:cancel</i></li>
 * <p/>
 * </ul>
 * <p/>
 * <p/> In addition to these four prefixes, this mapper also understands the
 * action naming pattern of <i>foo!bar</i> in either the extension form (eg:
 * foo!bar.action) or in the prefix form (eg: action:foo!bar). This syntax tells
 * this mapper to map to the action named <i>foo</i> and the method <i>bar</i>.
 * <p/>
 * <!-- END SNIPPET: javadoc -->
 * <p/>
 * <p/> <b>Method Prefix</b> <p/>
 * <p/>
 * <!-- START SNIPPET: method -->
 * <p/>
 * With method-prefix, instead of calling baz action's execute() method (by
 * default if it isn't overriden in struts.xml to be something else), the baz
 * action's anotherMethod() will be called. A very elegant way determine which
 * button is clicked. Alternatively, one would have submit button set a
 * particular value on the action when clicked, and the execute() method decides
 * on what to do with the setted value depending on which button is clicked.
 * <p/>
 * <!-- END SNIPPET: method -->
 * <p/>
 * <pre>
 *  &lt;!-- START SNIPPET: method-example --&gt;
 *  &lt;s:form action=&quot;baz&quot;&gt;
 *      &lt;s:textfield label=&quot;Enter your name&quot; name=&quot;person.name&quot;/&gt;
 *      &lt;s:submit value=&quot;Create person&quot;/&gt;
 *      &lt;s:submit name=&quot;method:anotherMethod&quot; value=&quot;Cancel&quot;/&gt;
 *  &lt;/s:form&gt;
 *  &lt;!-- END SNIPPET: method-example --&gt;
 * </pre>
 * <p/>
 * <p/> <b>Action prefix</b> <p/>
 * <p/>
 * <!-- START SNIPPET: action -->
 * <p/>
 * With action-prefix, instead of executing baz action's execute() method (by
 * default if it isn't overriden in struts.xml to be something else), the
 * anotherAction action's execute() method (assuming again if it isn't overriden
 * with something else in struts.xml) will be executed.
 * <p/>
 * <!-- END SNIPPET: action -->
 * <p/>
 * <pre>
 *  &lt;!-- START SNIPPET: action-example --&gt;
 *  &lt;s:form action=&quot;baz&quot;&gt;
 *      &lt;s:textfield label=&quot;Enter your name&quot; name=&quot;person.name&quot;/&gt;
 *      &lt;s:submit value=&quot;Create person&quot;/&gt;
 *      &lt;s:submit name=&quot;action:anotherAction&quot; value=&quot;Cancel&quot;/&gt;
 *  &lt;/s:form&gt;
 *  &lt;!-- END SNIPPET: action-example --&gt;
 * </pre>
 * <p/>
 * <p/> <b>Redirect prefix</b> <p/>
 * <p/>
 * <!-- START SNIPPET: redirect -->
 * <p/>
 * With redirect-prefix, instead of executing baz action's execute() method (by
 * default it isn't overriden in struts.xml to be something else), it will get
 * redirected to, in this case to www.google.com. Internally it uses
 * ServletRedirectResult to do the task.
 * <p/>
 * <!-- END SNIPPET: redirect -->
 * <p/>
 * <pre>
 *  &lt;!-- START SNIPPET: redirect-example --&gt;
 *  &lt;s:form action=&quot;baz&quot;&gt;
 *      &lt;s:textfield label=&quot;Enter your name&quot; name=&quot;person.name&quot;/&gt;
 *      &lt;s:submit value=&quot;Create person&quot;/&gt;
 *      &lt;s:submit name=&quot;redirect:www.google.com&quot; value=&quot;Cancel&quot;/&gt;
 *  &lt;/s:form&gt;
 *  &lt;!-- END SNIPPET: redirect-example --&gt;
 * </pre>
 * <p/>
 * <p/> <b>Redirect-action prefix</b> <p/>
 * <p/>
 * <!-- START SNIPPET: redirect-action -->
 * <p/>
 * With redirect-action-prefix, instead of executing baz action's execute()
 * method (by default it isn't overriden in struts.xml to be something else), it
 * will get redirected to, in this case 'dashboard.action'. Internally it uses
 * ServletRedirectResult to do the task and read off the extension from the
 * struts.properties.
 * <p/>
 * <!-- END SNIPPET: redirect-action -->
 * <p/>
 * <pre>
 *  &lt;!-- START SNIPPET: redirect-action-example --&gt;
 *  &lt;s:form action=&quot;baz&quot;&gt;
 *      &lt;s:textfield label=&quot;Enter your name&quot; name=&quot;person.name&quot;/&gt;
 *      &lt;s:submit value=&quot;Create person&quot;/&gt;
 *      &lt;s:submit name=&quot;redirectAction:dashboard&quot; value=&quot;Cancel&quot;/&gt;
 *  &lt;/s:form&gt;
 *  &lt;!-- END SNIPPET: redirect-action-example --&gt;
 * </pre>
 */
public class DefaultActionMapper implements ActionMapper {

    protected static final String METHOD_PREFIX = "method:";

    protected static final String ACTION_PREFIX = "action:";

    protected static final String REDIRECT_PREFIX = "redirect:";

    protected static final String REDIRECT_ACTION_PREFIX = "redirectAction:";

    /**
     * 是否允许动态方法调用
     */
    protected boolean allowDynamicMethodCalls = true;

    /**
     * action名中是否能包含”/“号
     */
    protected boolean allowSlashesInActionNames = false;

    /**
     * 为true的话(默认为false)，以uri中最后一个"/"为分隔符，前面的内容作namespace，后面的内容作name
     */
    protected boolean alwaysSelectFullNamespace = false;

    /**
     * 默认过滤处理这四个参数名："method:"、"action:"、"redirect:" 、"redirectAction:"
     */
    protected PrefixTrie prefixTrie = null;

    /**
     * 由struts.action.extension配置值指定后缀，该配置项若以","号结尾的话，会加入空字符串做后缀
     */
    protected List<String> extensions = new ArrayList<String>() {{
        add("action");
        add("");
    }};

    protected Container container;

    public DefaultActionMapper() {
        prefixTrie = new PrefixTrie() {
            {
                put(METHOD_PREFIX, new ParameterAction() {
                    public void execute(String key, ActionMapping mapping) {
                        if (allowDynamicMethodCalls) {
                            mapping.setMethod(key.substring(
                                    METHOD_PREFIX.length()));
                        }
                    }
                });

                put(ACTION_PREFIX, new ParameterAction() {
                    public void execute(String key, ActionMapping mapping) {
                        String name = key.substring(ACTION_PREFIX.length());
                        if (allowDynamicMethodCalls) {
                            int bang = name.indexOf('!');
                            if (bang != -1) {
                                String method = name.substring(bang + 1);
                                mapping.setMethod(method);
                                name = name.substring(0, bang);
                            }
                        }
                        mapping.setName(name);
                    }
                });

                put(REDIRECT_PREFIX, new ParameterAction() {
                    public void execute(String key, ActionMapping mapping) {
                        ServletRedirectResult redirect = new ServletRedirectResult();
                        container.inject(redirect);
                        redirect.setLocation(key.substring(REDIRECT_PREFIX
                                .length()));
                        mapping.setResult(redirect);
                    }
                });

                put(REDIRECT_ACTION_PREFIX, new ParameterAction() {
                    public void execute(String key, ActionMapping mapping) {
                        String location = key.substring(REDIRECT_ACTION_PREFIX
                                .length());
                        ServletRedirectResult redirect = new ServletRedirectResult();
                        container.inject(redirect);
                        String extension = getDefaultExtension();
                        if (extension != null && extension.length() > 0) {
                            location += "." + extension;
                        }
                        redirect.setLocation(location);
                        mapping.setResult(redirect);
                    }
                });
            }
        };
    }

    /**
     * Adds a parameter action.  Should only be called during initialization
     *
     * @param prefix          The string prefix to trigger the action
     * @param parameterAction The parameter action to execute
     * @since 2.1.0
     */
    protected void addParameterAction(String prefix, ParameterAction parameterAction) {
        prefixTrie.put(prefix, parameterAction);
    }

    @Inject(StrutsConstants.STRUTS_ENABLE_DYNAMIC_METHOD_INVOCATION)
    public void setAllowDynamicMethodCalls(String allow) {
        allowDynamicMethodCalls = "true".equals(allow);
    }

    @Inject(StrutsConstants.STRUTS_ENABLE_SLASHES_IN_ACTION_NAMES)
    public void setSlashesInActionNames(String allow) {
        allowSlashesInActionNames = "true".equals(allow);
    }

    @Inject(StrutsConstants.STRUTS_ALWAYS_SELECT_FULL_NAMESPACE)
    public void setAlwaysSelectFullNamespace(String val) {
        this.alwaysSelectFullNamespace = "true".equals(val);
    }

    @Inject
    public void setContainer(Container container) {
        this.container = container;
    }

    @Inject(StrutsConstants.STRUTS_ACTION_EXTENSION)
    public void setExtensions(String extensions) {
        if (extensions != null && !"".equals(extensions)) {
            List<String> list = new ArrayList<String>();
            String[] tokens = extensions.split(",");
            Collections.addAll(list, tokens);
            //以","号结尾，会加入空字符串后缀
            if (extensions.endsWith(",")) {
                list.add("");
            }
            this.extensions = Collections.unmodifiableList(list);
        } else {
            this.extensions = null;
        }
    }

    public ActionMapping getMappingFromActionName(String actionName) {
        ActionMapping mapping = new ActionMapping();
        mapping.setName(actionName);
        return parseActionName(mapping);
    }

    /**
     * (non-Javadoc)
     *
     * @see org.apache.struts2.dispatcher.mapper.ActionMapper#getMapping(javax.servlet.http.HttpServletRequest)
     * 
     */

    public ActionMapping getMapping(HttpServletRequest request,
                                    ConfigurationManager configManager) {
        ActionMapping mapping = new ActionMapping();
        String uri = getUri(request);

        int indexOfSemicolon = uri.indexOf(";");
        uri = (indexOfSemicolon > -1) ? uri.substring(0, indexOfSemicolon) : uri;

        //1.去掉后缀(后缀由struts.action.extension配置值指定后缀，该配置项若以","号结尾的话，会加入空字符串做后缀)，
        //注意：“支持无后缀名的Action请求”经常带来一些混乱，最典型的就是“/*”错误地拦截了其他的映射为无后缀名的Servlet请求。比如DWR、FCKEditor等都存在这种问题
        //2.把后缀保存在mapping里
        uri = dropExtension(uri, mapping);
        if (uri == null) {
            return null;
        }

        //根据configManager中的config.getPackageConfigs获得所有action配置 ,在里面寻找匹配最长 namespace的action配置
        //设置mapping的name、namespace值,
        parseNameAndNamespace(uri, mapping, configManager);

        /**
         * 处理一些特殊的请求参数
         * 1.image button location info
           2.利用属性prefixTrie默认过滤处理这四个参数名："method:"、"action:"、"redirect:" 、"redirectAction:"
         */
        handleSpecialParameters(request, mapping);

        if (mapping.getName() == null) {
            return null;
        }
        
        //当allowDynamicMethodCalls为true时,处理"name!method"方式的调用,
        //为mapping的name、method赋值
        parseActionName(mapping);

        return mapping;
    }

    /**
     * 当{@link #allowDynamicMethodCalls}为true时,处理"name!method"方式的调用,
     *  为mapping的name、method赋值
     */
    protected ActionMapping parseActionName(ActionMapping mapping) {
        if (mapping.getName() == null) {
            return mapping;
        }
        if (allowDynamicMethodCalls) {
            // handle "name!method" convention.
            String name = mapping.getName();
            int exclamation = name.lastIndexOf("!");
            if (exclamation != -1) {
                mapping.setName(name.substring(0, exclamation));

                mapping.setMethod(name.substring(exclamation + 1));
            }
        }
        return mapping;
    }

    /**
     * Special parameters, as described in the class-level comment, are searched
     * for and handled.
     *
     * @param request The request
     * @param mapping The action mapping
     */
    public void handleSpecialParameters(HttpServletRequest request,
                                        ActionMapping mapping) {
        // handle special parameter prefixes.
        Set<String> uniqueParameters = new HashSet<String>();
        Map parameterMap = request.getParameterMap();
        for (Iterator iterator = parameterMap.keySet().iterator(); iterator
                .hasNext();) {
            String key = (String) iterator.next();

            // Strip off the image button location info, if found
            if (key.endsWith(".x") || key.endsWith(".y")) {
                key = key.substring(0, key.length() - 2);
            }

            // Ensure a parameter doesn't get processed twice
            if (!uniqueParameters.contains(key)) {
                ParameterAction parameterAction = (ParameterAction) prefixTrie
                        .get(key);
                if (parameterAction != null) {
                    parameterAction.execute(key, mapping);
                    uniqueParameters.add(key);
                    //只要有一个匹配了，就终止循环
                    break;
                }
            }
        }
    }

    /**
     * Parses the name and namespace from the uri
     *
     * @param uri     The uri
     * @param mapping The action mapping to populate
     */
    protected void parseNameAndNamespace(String uri, ActionMapping mapping,
                                         ConfigurationManager configManager) {
        String namespace, name;
        int lastSlash = uri.lastIndexOf("/");
        if (lastSlash == -1) {
            namespace = "";
            name = uri;
        } else if (lastSlash == 0) {
            // ww-1046, assume it is the root namespace, it will fallback to
            // default
            // namespace anyway if not found in root namespace.
            namespace = "/";
            name = uri.substring(lastSlash + 1);
        } else if (alwaysSelectFullNamespace) {
            // Simply select the namespace as everything before the last slash
            namespace = uri.substring(0, lastSlash);
            name = uri.substring(lastSlash + 1);
        } else {
            // Try to find the namespace in those defined, defaulting to ""
            Configuration config = configManager.getConfiguration();
            String prefix = uri.substring(0, lastSlash);
            namespace = "";
            boolean rootAvailable = false;
            // Find the longest matching namespace, defaulting to the default
            for (Object cfg : config.getPackageConfigs().values()) {
                String ns = ((PackageConfig) cfg).getNamespace();
                if (ns != null && prefix.startsWith(ns) && (prefix.length() == ns.length() || prefix.charAt(ns.length()) == '/')) {
                    //在有多个namespace匹配的情况下，使用最长的namespace
                	if (ns.length() > namespace.length()) {
                        namespace = ns;
                    }
                }
                if ("/".equals(ns)) {
                    rootAvailable = true;
                }
            }

            name = uri.substring(namespace.length() + 1);

            // Still none found, use root namespace if found
            // 如果没找到匹配项，使用根路径作匹配
            if (rootAvailable && "".equals(namespace)) {
                namespace = "/";
            }
        }

        //对action名，判断是否过滤"/"号
        //过滤的话，会把action名最后一个"/"之后的值作action
        if (!allowSlashesInActionNames && name != null) {
            int pos = name.lastIndexOf('/');
            if (pos > -1 && pos < name.length() - 1) {
                name = name.substring(pos + 1);
            }
        }

        mapping.setNamespace(namespace);
        mapping.setName(name);
    }

    /**
     * Drops the extension from the action name
     *
     * @param name The action name
     * @return The action name without its extension
     * @deprecated Since 2.1, use {@link #dropExtension(java.lang.String,org.apache.struts2.dispatcher.mapper.ActionMapping)} instead
     */
    protected String dropExtension(String name) {
        return dropExtension(name, new ActionMapping());
    }

    /**
     * Drops the extension from the action name, storing it in the mapping for later use
     *
     * @param name    The action name
     * @param mapping The action mapping to store the extension in
     * @return The action name without its extension
     */
    protected String dropExtension(String name, ActionMapping mapping) {
        if (extensions == null) {
            return name;
        }
        for (String ext : extensions) {
        	//空字符串后缀判断
            if ("".equals(ext)) {
                // This should also handle cases such as /foo/bar-1.0/description. It is tricky to
                // distinquish /foo/bar-1.0 but perhaps adding a numeric check in the future could
                // work
                int index = name.lastIndexOf('.');
                if (index == -1 || name.indexOf('/', index) >= 0) {
                    return name;
                }
            } else {
                String extension = "." + ext;
                if (name.endsWith(extension)) {
                    name = name.substring(0, name.length() - extension.length());
                    mapping.setExtension(ext);
                    return name;
                }
            }
        }
        return null;
    }

    /**
     * Returns null if no extension is specified.
     */
    protected String getDefaultExtension() {
        if (extensions == null) {
            return null;
        } else {
            return (String) extensions.get(0);
        }
    }

    /**
     * Gets the uri from the request
     *
     * @param request The request
     * @return The uri
     */
    protected String getUri(HttpServletRequest request) {
        // handle http dispatcher includes.
        String uri = (String) request
                .getAttribute("javax.servlet.include.servlet_path");
        if (uri != null) {
            return uri;
        }

        uri = RequestUtils.getServletPath(request);
        if (uri != null && !"".equals(uri)) {
            return uri;
        }

        uri = request.getRequestURI();
        return uri.substring(request.getContextPath().length());
    }

    /*
     * (non-Javadoc)
     *
     * @see org.apache.struts2.dispatcher.mapper.ActionMapper#getUriFromActionMapping(org.apache.struts2.dispatcher.mapper.ActionMapping)
     */

    public String getUriFromActionMapping(ActionMapping mapping) {
        StringBuilder uri = new StringBuilder();

        if (mapping.getNamespace() != null) {
            uri.append(mapping.getNamespace());
            if (!"/".equals(mapping.getNamespace())) {
                uri.append("/");
            }
        }
        String name = mapping.getName();
        String params = "";
        if (name.indexOf('?') != -1) {
            params = name.substring(name.indexOf('?'));
            name = name.substring(0, name.indexOf('?'));
        }
        uri.append(name);

        if (null != mapping.getMethod() && !"".equals(mapping.getMethod())) {
            uri.append("!").append(mapping.getMethod());
        }

        String extension = mapping.getExtension();
        if (extension == null) {
            // Look for the current extension, if available
            ActionContext context = ActionContext.getContext();
            if (context != null) {
                ActionMapping orig = (ActionMapping) context.get(ServletActionContext.ACTION_MAPPING);
                if (orig != null) {
                    extension = orig.getExtension();
                }
            }
            if (extension == null) {
                extension = getDefaultExtension();
            }
        }

        if (extension != null) {
            if (extension.length() == 0 || (extension.length() > 0 && uri.indexOf('.' + extension) == -1)) {
                if (extension.length() > 0) {
                    uri.append(".").append(extension);
                }
            }
        }
        if (params.length() > 0) {
            uri.append(params);
        }

        return uri.toString();
    }


    public boolean isSlashesInActionNames() {
        return allowSlashesInActionNames;
    }

}
