/*
 *    Copyright 2010 The myBatis Team
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.mybatis.spring.mapper;

import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.session.Configuration;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.util.Assert;

/**
 * BeanFactory that enables injection of MyBatis mapper interfaces. It can be set up with a
 * SqlSessionFactory or a pre-configured SqlSessionTemplate.
 * <p>
 * Sample configuration:
 *
 * <pre class="code">
 * {@code
 *   <bean id="baseMapper" class="org.mybatis.spring.mapper.MapperFactoryBean" abstract="true" lazy-init="true">
 *     <property name="sqlSessionFactory" ref="sqlSessionFactory" />
 *   </bean>
 *
 *   <bean id="oneMapper" parent="baseMapper">
 *     <property name="mapperInterface" value="my.package.MyMapperInterface" />
 *   </bean>
 * 
 *   <bean id="anotherMapper" parent="baseMapper">
 *     <property name="mapperInterface" value="my.package.MyAnotherMapperInterface" />
 *   </bean>
 * }
 * </pre>
 * <p>
 * Note that this factory can only inject <em>interfaces</em>, not concrete classes.
 *
 * 实现了spring的接口　{@link org.springframework.beans.factory.FactoryBean},该接口详见:http://static.springsource.org/spring/docs/2.0.0/api/org/springframework/beans/factory/FactoryBean.html,
 * MapperFactoryBean这个类，是为了spring在给service层注入mapper接口，而提供的一个生成mybatis的mapper的工厂
 *
 * @see SqlSessionTemplate
 * @version $Id: MapperFactoryBean.java 3650 2011-02-28 22:45:59Z eduardo.macarron $
 */
public class MapperFactoryBean<T> extends SqlSessionDaoSupport implements FactoryBean<T> {

    private Class<T> mapperInterface;

    private boolean addToConfig = true;

    public void setMapperInterface(Class<T> mapperInterface) {
        this.mapperInterface = mapperInterface;
    }

    public void setAddToConfig(boolean addToConfig) {
        this.addToConfig = addToConfig;
    }

    /**
     * 解析mapper对应xml,
     * 解析mapper类注解
     */
    protected void checkDaoConfig() {
    	//父类中验证{@link #sqlSession} 不为空
        super.checkDaoConfig();
        
        Assert.notNull(this.mapperInterface, "Property 'mapperInterface' is required");

        Configuration configuration = getSqlSession().getConfiguration();
        if (this.addToConfig && !configuration.hasMapper(this.mapperInterface)) {
            try {
            	//解析mapper对应xml,解析mapper类注解
                configuration.addMapper(this.mapperInterface);
            } catch (Throwable t) {
                logger.error("Error while adding the mapper '" + this.mapperInterface + "' to configuration.", t);
                throw new IllegalArgumentException(t);
            } finally {
            	//?????
                ErrorContext.instance().reset();
            }
        }
    }

    /**
     * 实现接口{@link org.springframework.beans.factory.FactoryBean}的方法，
     * spring在给service层注入mapper接口时，从这里获得mabatis的mapper,
     * 追踪后面的代码，可知返回的是一个由MapperProxy代理的mapper实例。
     */
    public T getObject() throws Exception {
        return getSqlSession().getMapper(this.mapperInterface);
    }

    /**
     * 实现接口{@link org.springframework.beans.factory.FactoryBean}的方法
     */
    public Class<T> getObjectType() {
        return this.mapperInterface;
    }

    /**
     * 实现接口{@link org.springframework.beans.factory.FactoryBean}的方法
     */
    public boolean isSingleton() {
        return true;
    }

}
