/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package javax.el;

/**
 * 提供应用程序开发人员扩展的最小默认实现的基本实现。
 *
 * @since EL 3.0
 */
public abstract class BeanNameResolver {

    /**
     * 这个解析器可以解析给定的bean名称吗？
     *
     * @param beanName 要解析的bean的名字
     *
     * @return 此默认实现始终返回<code>false</code>
     */
    public boolean isNameResolved(String beanName) {
        return false;
    }


    /**
     * 返回指定的bean。
     *
     * @param beanName 要返回的bean的名字
     *
     * @return 此默认实现始终返回<code>false</code>
     */
    public Object getBean(String beanName) {
        return null;
    }


    /**
     * 设置给定名称的bean的值。 如果命名bean不存在且
     * {@link #canCreateBean}返回<code> true </code>，则使用给定值创建bean。
     *
     * @param beanName 要设置/创建的bean的名称
     * 
     * @param value 要设置/创建的bean的值
     *
     * @throws PropertyNotWritableException 如果bean是只读的
     */
    public void setBeanValue(String beanName, Object value)
            throws PropertyNotWritableException {
        throw new PropertyNotWritableException();
    }


    /**
     * 命名的bean是只读的吗？
     *
     * @param beanName 感兴趣的bean()的名字
     *
     * @return 如果bean是只读的为<code>true</code>，否则<code>false</code>
     */
    public boolean isReadOnly(String beanName) {
        return true;
    }


    /**
     * 是否允许创建给定名称的bean？
     *
     * @param beanName 感兴趣的bean(bean of interest)的名字
     *
     * @return 如果可以创建bean 就是<code>true</code> ，否则<code>false</code>
     */
    public boolean canCreateBean(String beanName) {
        return false;
    }
}
