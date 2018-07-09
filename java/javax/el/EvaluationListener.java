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
 * @since EL 3.0
 */
public abstract class EvaluationListener {

    /**
     * 在表达式评估前触发。
     *
     * @param context    将评估表达式的EL上下文
     * @param expression 将要评估的表达式
     */
    public void beforeEvaluation(ELContext context, String expression) {
        // 空指令
    }

    /**
     * 在表达式评估后触发
     *
     * @param context    将评估表达式的EL上下文
     * @param expression 将要评估的表达式
     */
    public void afterEvaluation(ELContext context, String expression) {
        // 空指令
    }

    /**
     * 属性解析后触发
     *
     * @param context  解析该属性的EL上下文
     * @param base     解析属性的基础对象
     * @param property 已经解析的属性
     */
    public void propertyResolved(ELContext context, Object base, Object property) {
        // 空指令
    }
}
