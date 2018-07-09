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
 * 表示在表达式求值期间可能出现的任何异常条件。
 *
 * @since 2.1
 */
public class ELException extends RuntimeException {

    private static final long serialVersionUID = -6228042809457459161L;

    /**
     * 创建一个没有详细消息的ELException
     */
    public ELException() {
        super();
    }

    /**
     * 创建一个提供详细消息的ELException。
     *
     * @param message
     *            详细信息
     */
    public ELException(String message) {
        super(message);
    }

    /**
     * 创建一个给定原因的ELException
     *
     * @param cause
     *            此异常(Exception)的起因
     */
    public ELException(Throwable cause) {
        super(cause);
    }

    /**
     * 创建一个带有给定的详细消息和根本原因的ELException
     *
     * @param message
     *            详细信息
     * @param cause
     *            此异常(Exception)的起因
     */
    public ELException(String message, Throwable cause) {
        super(message, cause);
    }
}
