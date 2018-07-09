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

import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public abstract class ELContext {

    private Locale locale;

    private Map<Class<?>, Object> map;

    private boolean resolved;

    private ImportHandler importHandler = null;

    private List<EvaluationListener> listeners = new ArrayList<>();

    private Deque<Map<String,Object>> lambdaArguments = new LinkedList<>();

    public ELContext() {
        this.resolved = false;
    }

    public void setPropertyResolved(boolean resolved) {
        this.resolved = resolved;
    }

    /**
     * Mark the given property as resolved and notfy any interested listeners.
     * 将给定属性标记为已解析并且不对任何感兴趣的侦听器进行标记。
     *
     * @param base     找到该属性的基础对象
     * @param property 已解析(resolved)的属性
     *
     * @since EL 3.0
     */
    public void setPropertyResolved(Object base, Object property) {
        setPropertyResolved(true);
        notifyPropertyResolved(base, property);
    }

    public boolean isPropertyResolved() {
        return this.resolved;
    }

    // 不能使用Class<?>，因为API需要匹配规范
    /**
     * 在给定key的情况下将对象添加到此EL表达式上下文。
     *
     * @param key           存储对象的key
     * @param contextObject 要添加的对象
     *
     * @throws NullPointerException
     *              如果提供的key或上下文(context)是<code> null </code>
     */
    public void putContext(@SuppressWarnings("rawtypes") Class key,
            Object contextObject) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(contextObject);

        if (this.map == null) {
            this.map = new HashMap<>();
        }

        this.map.put(key, contextObject);
    }

    // 不能使用Class<?>，因为API需要匹配规范
    /**
     * 获取给定key的上下文对象。
     *
     * @param key 所需上下文对象的key
     *
     * @return 关联了给定key的上下文对象的值
     *         
     * @throws NullPointerException
     *              如果提供的key是 <code>null</code>
     */
    public Object getContext(@SuppressWarnings("rawtypes") Class key) {
        Objects.requireNonNull(key);
        if (this.map == null) {
            return null;
        }
        return this.map.get(key);
    }

    public abstract ELResolver getELResolver();

    /**
     * 获取此ELContext的ImportHandler，必要时创建一个。 此方法是线程不安全的。
     *
     * @return 此ELContext的ImportHandler
     *
     * @since EL 3.0
     */
    public ImportHandler getImportHandler() {
        if (importHandler == null) {
            importHandler = new ImportHandler();
        }
        return importHandler;
    }

    public abstract FunctionMapper getFunctionMapper();

    public Locale getLocale() {
        return this.locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public abstract VariableMapper getVariableMapper();

    /**
     * 注册一个带有ELContext的EvaluationListener 
     *
     * @param listener 要注册的EvaluationListener
     *
     * @since EL 3.0
     */
    public void addEvaluationListener(EvaluationListener listener) {
        listeners.add(listener);
    }

    /**
     * 获取已注册的EvaluationListener列表。
     *
     * @return 一个已经注册的带有ELContext的EvaluationListener的列表
     *
     * @since EL 3.0
     */
    public List<EvaluationListener> getEvaluationListeners() {
        return listeners;
    }

    /**
     * Notify interested listeners that an expression will be evaluated.
     * 通知感兴趣的listenter对表达式进行评估。
     *
     * @param expression 将被评估的表达式(expression)
     *
     * @since EL 3.0
     */
    public void notifyBeforeEvaluation(String expression) {
        for (EvaluationListener listener : listeners) {
            try {
                listener.beforeEvaluation(this, expression);
            } catch (Throwable t) {
                Util.handleThrowable(t);
                // 忽略 - 没有记录选项
            }
        }
    }

    /**
     * Notify interested listeners that an expression has been evaluated.
     * 通知感兴趣的listener,这是已经评估完成的表达式。
     *
     * @param expression 被评估的表达式
     *
     * @since EL 3.0
     */
    public void notifyAfterEvaluation(String expression) {
        for (EvaluationListener listener : listeners) {
            try {
                listener.afterEvaluation(this, expression);
            } catch (Throwable t) {
                Util.handleThrowable(t);
                // 忽略 - 没有记录选项
            }
        }
    }

    /**
     * Notify interested listeners that a property has been resolved.
     * 通知相关的listener已解析属性。
     *
     * @param base     解析属性的对象
     * @param property 需要解析的属性
     *
     * @since EL 3.0
     */
    public void notifyPropertyResolved(Object base, Object property) {
        for (EvaluationListener listener : listeners) {
            try {
                listener.propertyResolved(this, base, property);
            } catch (Throwable t) {
                Util.handleThrowable(t);
                // 忽略 - 没有记录选项
            }
        }
    }

    /**
     * 确定指定的名称是否被识别为lambda参数的名称。
     *
     * @param name lambda参数的名称
     *
     * @return 名称被识别为lambda参数的名称就为<code>true</code>，否则 <code>false</code>
     *
     * @since EL 3.0
     */
    public boolean isLambdaArgument(String name) {
        for (Map<String,Object> arguments : lambdaArguments) {
            if (arguments.containsKey(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取具有给定名称的lambda参数的值。
     *
     * @param name lambda参数的名称
     *
     * @return 指定参数的值
     *
     * @since EL 3.0
     */
    public Object getLambdaArgument(String name) {
        for (Map<String,Object> arguments : lambdaArguments) {
            Object result = arguments.get(name);
            if (result != null) {
                return result;
            }
        }
        return null;
    }

    /**
     * 开始评估lambda表达式时调用，使得参数可用于EL表达式上下文。
     *
     * @param arguments 当前lambda表达式的范围中的参数。
     * @since EL 3.0
     */
    public void enterLambdaScope(Map<String,Object> arguments) {
        lambdaArguments.push(arguments);
    }

    /**
     * 在评估lambda表达式后调用，以表示不再需要参数。
     *
     * @since EL 3.0
     */
    public void exitLambdaScope() {
        lambdaArguments.pop();
    }

    /**
     * 将提供的对象强制转换为请求的类型。
     *
     * @param obj  需要强制的对象
     * @param type 需要强制对象的类型
     *
     * @return 请求类型的实例
     *
     * @throws ELException
     *              如果转换失败
     *
     * @since EL 3.0
     */
    public Object convertToType(Object obj, Class<?> type) {

        boolean originalResolved = isPropertyResolved();
        setPropertyResolved(false);
        try {
            ELResolver resolver = getELResolver();
            if (resolver != null) {
                Object result = resolver.convertToType(this, obj, type);
                if (isPropertyResolved()) {
                    return result;
                }
            }
        } finally {
            setPropertyResolved(originalResolved);
        }

        return ELManager.getExpressionFactory().coerceToType(obj, type);
    }
}
