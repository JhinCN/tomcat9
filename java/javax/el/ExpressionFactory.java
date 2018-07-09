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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 *
 * @since 2.1
 */
public abstract class ExpressionFactory {

    private static final boolean IS_SECURITY_ENABLED =
        (System.getSecurityManager() != null);

    private static final String SERVICE_RESOURCE_NAME =
        "META-INF/services/javax.el.ExpressionFactory";

    private static final String PROPERTY_NAME = "javax.el.ExpressionFactory";

    private static final String PROPERTY_FILE;

    private static final CacheValue nullTcclFactory = new CacheValue();
    private static final Map<CacheKey, CacheValue> factoryCache = new ConcurrentHashMap<>();

    static {
        if (IS_SECURITY_ENABLED) {
            PROPERTY_FILE = AccessController.doPrivileged(
                    new PrivilegedAction<String>(){
                        @Override
                        public String run() {
                            return System.getProperty("java.home") + File.separator +
                                    "lib" + File.separator + "el.properties";
                        }

                    }
            );
        } else {
            PROPERTY_FILE = System.getProperty("java.home") + File.separator + "lib" +
                    File.separator + "el.properties";
        }
    }

    /**
     * 创建一个新的{@link ExpressionFactory}。 要使用的类由以下搜索顺序确定：
     * <ol>
     * <li>services API (META-INF/services/javax.el.ExpressionFactory)</li>
     * <li>$JRE_HOME/lib/el.properties - key javax.el.ExpressionFactory</li>
     * <li>javax.el.ExpressionFactory</li>
     * <li>平台默认的实现 -
     *     org.apache.el.ExpressionFactoryImpl</li>
     * </ol>
     * @return 新的ExpressionFactory
     */
    public static ExpressionFactory newInstance() {
        return newInstance(null);
    }

    /**
     * 通过提供的{@link Properties}创建一个新的{@link ExpressionFactory}。 
     * 搜索顺序与{@link #newInstance()}相同。
     *
     * @param properties 要传递给新实例的属性（可能为null）
     * @return 新的 ExpressionFactory
     */
    public static ExpressionFactory newInstance(Properties properties) {
        ExpressionFactory result = null;

        ClassLoader tccl = Thread.currentThread().getContextClassLoader();

        CacheValue cacheValue;
        Class<?> clazz;

        if (tccl == null) {
            cacheValue = nullTcclFactory;
        } else {
            CacheKey key = new CacheKey(tccl);
            cacheValue = factoryCache.get(key);
            if (cacheValue == null) {
                CacheValue newCacheValue = new CacheValue();
                cacheValue = factoryCache.putIfAbsent(key, newCacheValue);
                if (cacheValue == null) {
                    cacheValue = newCacheValue;
                }
            }
        }

        final Lock readLock = cacheValue.getLock().readLock();
        readLock.lock();
        try {
            clazz = cacheValue.getFactoryClass();
        } finally {
            readLock.unlock();
        }

        if (clazz == null) {
            String className = null;
            try {
                final Lock writeLock = cacheValue.getLock().writeLock();
                writeLock.lock();
                try {
                    className = cacheValue.getFactoryClassName();
                    if (className == null) {
                        className = discoverClassName(tccl);
                        cacheValue.setFactoryClassName(className);
                    }
                    if (tccl == null) {
                        clazz = Class.forName(className);
                    } else {
                        clazz = tccl.loadClass(className);
                    }
                    cacheValue.setFactoryClass(clazz);
                } finally {
                    writeLock.unlock();
                }
            } catch (ClassNotFoundException e) {
                throw new ELException(
                    "Unable to find ExpressionFactory of type: " + className,
                    e);
            }
        }

        try {
            Constructor<?> constructor = null;
            // 我们需要寻找一个可以获取属性的构造函数吗？
            if (properties != null) {
                try {
                    constructor = clazz.getConstructor(Properties.class);
                } catch (SecurityException se) {
                    throw new ELException(se);
                } catch (NoSuchMethodException nsme) {
                    // 这可以忽略
                    // 这个构造函数不存在就可以了
                }
            }
            if (constructor == null) {
                result = (ExpressionFactory) clazz.getConstructor().newInstance();
            } else {
                result =
                    (ExpressionFactory) constructor.newInstance(properties);
            }

        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            Util.handleThrowable(cause);
            throw new ELException(
                    "Unable to create ExpressionFactory of type: " + clazz.getName(),
                    e);
        } catch (ReflectiveOperationException | IllegalArgumentException e) {
            throw new ELException(
                    "Unable to create ExpressionFactory of type: " + clazz.getName(),
                    e);
        }

        return result;
    }

    /**
     * 创建一个新的值表达式。
     *
     * @param context      此评估的EL上下文
     * @param expression   值表达式的String表示形式
     * @param expectedType 评估表达式的结果的预期类型
     *
     * @return 由输入参数组成的新值表达式
     *
     * @throws NullPointerException
     *              如果期望的类型是<code> null </code>
     * @throws ELException
     *             如果提供的表达式中存在语法错误
     */
    public abstract ValueExpression createValueExpression(ELContext context,
            String expression, Class<?> expectedType);

    public abstract ValueExpression createValueExpression(Object instance,
            Class<?> expectedType);

    /**
     * 创建一个新的方法表达式实例。
     *
     * @param context            此评估的EL上下文
     * @param expression         方法表达式的String表示形式
     * @param expectedReturnType 调用方法的结果的预期类型
     * @param expectedParamTypes 输入参数的预期类型
     *
     * @return 由输入参数形成的新方法表达式。
     *
     * @throws NullPointerException
     *              如果预期的参数类型是<code> null </code>
     * @throws ELException
     *              如果提供的表达式中存在语法错误
     */
    public abstract MethodExpression createMethodExpression(ELContext context,
            String expression, Class<?> expectedReturnType,
            Class<?>[] expectedParamTypes);

    /**
     * 将提供的对象强制转换为请求的类型。
     *
     * @param obj          The object to be coerced
     * @param expectedType The type to which the object should be coerced
     *
     * @return An instance of the requested type.
     *
     * @throws ELException
     *              If the conversion fails
     */
    public abstract Object coerceToType(Object obj, Class<?> expectedType);

    /**
     * @return This default implementation returns null
     *
     * @since EL 3.0
     */
    public ELResolver getStreamELResolver() {
        return null;
    }

    /**
     * @return This default implementation returns null
     *
     * @since EL 3.0
     */
    public Map<String,Method> getInitFunctionMap() {
        return null;
    }

    /**
     * Key used to cache ExpressionFactory discovery information per class
     * loader. The class loader reference is never {@code null}, because
     * {@code null} tccl is handled separately.
     */
    private static class CacheKey {
        private final int hash;
        private final WeakReference<ClassLoader> ref;

        public CacheKey(ClassLoader cl) {
            hash = cl.hashCode();
            ref = new WeakReference<>(cl);
        }

        @Override
        public int hashCode() {
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof CacheKey)) {
                return false;
            }
            ClassLoader thisCl = ref.get();
            if (thisCl == null) {
                return false;
            }
            return thisCl == ((CacheKey) obj).ref.get();
        }
    }

    private static class CacheValue {
        private final ReadWriteLock lock = new ReentrantReadWriteLock();
        private String className;
        private WeakReference<Class<?>> ref;

        public CacheValue() {
        }

        public ReadWriteLock getLock() {
            return lock;
        }

        public String getFactoryClassName() {
            return className;
        }

        public void setFactoryClassName(String className) {
            this.className = className;
        }

        public Class<?> getFactoryClass() {
            return ref != null ? ref.get() : null;
        }

        public void setFactoryClass(Class<?> clazz) {
            ref = new WeakReference<>(clazz);
        }
    }

    /**
     * Discover the name of class that implements ExpressionFactory.
     *
     * @param tccl
     *            {@code ClassLoader}
     * @return Class name. There is default, so it is never {@code null}.
     */
    private static String discoverClassName(ClassLoader tccl) {
        String className = null;

        // First services API
        className = getClassNameServices(tccl);
        if (className == null) {
            if (IS_SECURITY_ENABLED) {
                className = AccessController.doPrivileged(
                        new PrivilegedAction<String>() {
                            @Override
                            public String run() {
                                return getClassNameJreDir();
                            }
                        }
                );
            } else {
                // Second el.properties file
                className = getClassNameJreDir();
            }
        }
        if (className == null) {
            if (IS_SECURITY_ENABLED) {
                className = AccessController.doPrivileged(
                        new PrivilegedAction<String>() {
                            @Override
                            public String run() {
                                return getClassNameSysProp();
                            }
                        }
                );
            } else {
                // Third system property
                className = getClassNameSysProp();
            }
        }
        if (className == null) {
            // Fourth - default
            className = "org.apache.el.ExpressionFactoryImpl";
        }
        return className;
    }

    private static String getClassNameServices(ClassLoader tccl) {
        InputStream is = null;

        if (tccl == null) {
            is = ClassLoader.getSystemResourceAsStream(SERVICE_RESOURCE_NAME);
        } else {
            is = tccl.getResourceAsStream(SERVICE_RESOURCE_NAME);
        }

        if (is != null) {
            String line = null;
            try (InputStreamReader isr = new InputStreamReader(is, "UTF-8");
                    BufferedReader br = new BufferedReader(isr)) {
                line = br.readLine();
                if (line != null && line.trim().length() > 0) {
                    return line.trim();
                }
            } catch (UnsupportedEncodingException e) {
                // Should never happen with UTF-8
                // If it does - ignore & return null
            } catch (IOException e) {
                throw new ELException("Failed to read " + SERVICE_RESOURCE_NAME,
                        e);
            } finally {
                try {
                    is.close();
                } catch (IOException ioe) {/*Ignore*/}
            }
        }

        return null;
    }

    private static String getClassNameJreDir() {
        File file = new File(PROPERTY_FILE);
        if (file.canRead()) {
            try (InputStream is = new FileInputStream(file)){
                Properties props = new Properties();
                props.load(is);
                String value = props.getProperty(PROPERTY_NAME);
                if (value != null && value.trim().length() > 0) {
                    return value.trim();
                }
            } catch (FileNotFoundException e) {
                // Should not happen - ignore it if it does
            } catch (IOException e) {
                throw new ELException("Failed to read " + PROPERTY_FILE, e);
            }
        }
        return null;
    }

    private static final String getClassNameSysProp() {
        String value = System.getProperty(PROPERTY_NAME);
        if (value != null && value.trim().length() > 0) {
            return value.trim();
        }
        return null;
    }

}
