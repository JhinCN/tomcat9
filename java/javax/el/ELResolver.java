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

import java.util.Iterator;

/**
 * @author Jacob Hookom [jacob/hookom.net]
 *
 */
public abstract class ELResolver {

    public static final String TYPE = "type";

    public static final String RESOLVABLE_AT_DESIGN_TIME = "resolvableAtDesignTime";

    /**
     * @param context 需要评估的EL上下文 
     * @param base 需要找到属性的基础对象
     * @param property 返回值的属性
     * @return 提供属性的值
     * @throws NullPointerException
     *             如果提供的上下文是 <code>null</code>
     * @throws PropertyNotFoundException
     *              如果提供给解析程序的基本/属性组合是解析程序可以处理但未找到匹配项或
     *              找到匹配但基本/属性组合不可读
     * @throws ELException
     *              在解析属性时包装任何异常抛出
     */
    public abstract Object getValue(ELContext context, Object base,
            Object property);

    /**
     * 在给定对象上调用方法。 此默认实现始终返回<code> null </code>。
     *
     * @param context    需要评估的EL上下文
     * @param base       要查找方法的基本对象
     * @param method     要调用的方法
     * @param paramTypes 要调用的方法的参数类型
     * @param params     用于调用方法的参数
     *
     * @return 永远是 <code>null</code>
     *
     * @since EL 2.2
     */
    public Object invoke(ELContext context, Object base, Object method,
            Class<?>[] paramTypes, Object[] params) {
        return null;
    }

    /**
     * @param context 需要评估的EL上下文
     * @param base 要查找属性的基本对象
     * @param property 要返回其类型的属性
     * @return 提供的属性的类型
     * @throws NullPointerException
     *             如果提供的上下文是<code> null </code>
     * @throws PropertyNotFoundException
     *             如果提供给解析程序的基本/属性组合是解析程序可以处理
     *             但未找到匹配项或找到匹配项但是不可读的基本/属性组合
     * @throws ELException
     *             在解析属性时包装任何异常抛出
     */
    public abstract Class<?> getType(ELContext context, Object base,
            Object property);

    /**
     * @param context  需要评估的EL上下文
     * @param base     要查找属性的基本对象
     * @param property 要设置其值的属性
     * @param value    设置属性的值
     * @throws NullPointerException
     *             如果提供的上下文是<code> null </code>
     * @throws PropertyNotFoundException
     *              如果提供给解析程序的基本/属性组合是解析程序可以处理
     *              但未找到匹配项或找到匹配项但是不可读的基本/属性组合
     * @throws PropertyNotWritableException
     *              如果提供给解析程序的基本/属性组合是解析程序可以处理的属性，但该属性不可写
     * @throws ELException
     *              在解析属性时包装任何异常抛出
     */
    public abstract void setValue(ELContext context, Object base,
            Object property, Object value);

    /**
     * @param context 需要评估的EL上下文
     * @param base 要查找属性的基础对象
     * @param property 需要检查的属性是否为只读状态
     * @return 如果标识的属性是只读的就为<code>true</code> ，否则<code> false </ code>
     * @throws NullPointerException
     *              如果提供的上下文是<code> null </code>
     * @throws PropertyNotFoundException
     *              如果提供给解析程序的基本/属性组合是解析程序可以处理
     *              但未找到匹配项或找到匹配项但是不可读的基本/属性组合
     * @throws ELException
     *             在解析属性时包装任何异常抛出
     */
    public abstract boolean isReadOnly(ELContext context, Object base,
            Object property);

    public abstract Iterator<java.beans.FeatureDescriptor> getFeatureDescriptors(ELContext context, Object base);

    public abstract Class<?> getCommonPropertyType(ELContext context,
            Object base);

    /**
     * 将给定对象转换为给定类型。 此默认实现始终返回<code> null </code>。
     *
     * @param context 此评估的EL上下文
     * @param obj     要转换的对象
     * @param type    对象应转换为的类型
     *
     * @return 永远是 <code>null</code>
     *
     * @since EL 3.0
     */
    public Object convertToType(ELContext context, Object obj, Class<?> type) {
        context.setPropertyResolved(false);
        return null;
    }
}
