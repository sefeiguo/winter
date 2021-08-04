/*
 * Copyright (C), 1987-2099, Winter All Rights Reserved.
 */
package com.winterframework.util;

import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;

/**
 * @author huangwh@paraview.cn
 * @since 2021/07/26
 */
public abstract class ClassUtils {

    /**
     * Map 是包装类型为key 原始类型为value 例如: Integer.class -> int.class.
     */
    private static final Map<Class<?>, Class<?>> PRIMITIVE_WRAPPER_TYPE_MAP = new IdentityHashMap<>(8);

    /**
     * Map 是原始类型为key 包装类型为value 例如: int.class -> Integer.class.
     */
    private static final Map<Class<?>, Class<?>> PRIMITIVE_TYPE_TO_WRAPPER_MAP = new IdentityHashMap<>(8);

    /**
     * Map with common Java language class name as key and corresponding Class as
     * value. Primarily for efficient deserialization of remote invocations.
     */
    private static final Map<String, Class<?>> COMMON_CLASS_CACHE = new HashMap<>(64);

    static {
        // 初始化基础类型
        registerPrimitiveWrapperTypeMap();

        // Map entry iteration is less expensive to initialize than forEach with lambdas
        for (Map.Entry<Class<?>, Class<?>> entry : PRIMITIVE_WRAPPER_TYPE_MAP.entrySet()) {
            PRIMITIVE_TYPE_TO_WRAPPER_MAP.put(entry.getValue(), entry.getKey());
            registerCommonClasses(entry.getKey());
        }
    }

    /**
     * 注册基础类型和封装类型map
     */
    private static void registerPrimitiveWrapperTypeMap() {
        PRIMITIVE_WRAPPER_TYPE_MAP.put(Boolean.class, boolean.class);
        PRIMITIVE_WRAPPER_TYPE_MAP.put(Byte.class, byte.class);
        PRIMITIVE_WRAPPER_TYPE_MAP.put(Character.class, char.class);
        PRIMITIVE_WRAPPER_TYPE_MAP.put(Double.class, double.class);
        PRIMITIVE_WRAPPER_TYPE_MAP.put(Float.class, float.class);
        PRIMITIVE_WRAPPER_TYPE_MAP.put(Integer.class, int.class);
        PRIMITIVE_WRAPPER_TYPE_MAP.put(Long.class, long.class);
        PRIMITIVE_WRAPPER_TYPE_MAP.put(Short.class, short.class);
        PRIMITIVE_WRAPPER_TYPE_MAP.put(Void.class, void.class);
    }

    /**
     * 注册基础类型在ClassUtils 缓存
     * 
     * @param commonClasses
     */
    private static void registerCommonClasses(Class<?>... commonClasses) {
        for (Class<?> clazz : commonClasses) {
            COMMON_CLASS_CACHE.put(clazz.getName(), clazz);
        }
    }

    /**
     * 判断类的类型和值的类型是否为同一类型
     * 
     * @param type
     * @param value
     * @param <T>
     * @return
     */
    public static <T> boolean isAssignableValue(Class<T> type, Object value) {
        Assert.notNull(type, "Type must not be null");
        return (value != null ? isAssignable(type, value.getClass()) : !type.isPrimitive());
    }

    /**
     * 判断两个类是否相等 包括基础类型和封装类型
     * 
     * @param lhsType
     * @param rhsType
     * @return
     */
    public static boolean isAssignable(Class<?> lhsType, Class<?> rhsType) {
        Assert.notNull(lhsType, "Left-hand side type must not be null");
        Assert.notNull(rhsType, "Right-hand side type must not be null");
        if (lhsType.isAssignableFrom(rhsType)) {
            return true;
        }
        if (lhsType.isPrimitive()) {
            Class<?> resolvedPrimitive = PRIMITIVE_WRAPPER_TYPE_MAP.get(rhsType);
            return (lhsType == resolvedPrimitive);
        } else {
            Class<?> resolvedWrapper = PRIMITIVE_TYPE_TO_WRAPPER_MAP.get(rhsType);
            return (resolvedWrapper != null && lhsType.isAssignableFrom(resolvedWrapper));
        }
    }

    /**
     * 判断类是否为封装类型
     * 
     * @param clazz
     * @return
     */
    public static Class<?> resolvePrimitiveIfNecessary(Class<?> clazz) {
        Assert.notNull(clazz, "clazz must not be null");
        return (clazz.isPrimitive() && clazz != void.class ? PRIMITIVE_TYPE_TO_WRAPPER_MAP.get(clazz) : clazz);
    }
}