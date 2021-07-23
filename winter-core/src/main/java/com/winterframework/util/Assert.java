/*
 * Copyright (C), 1987-2099, Winter All Rights Reserved.
 */
package com.winterframework.util;

import com.winterframework.Nullable;

/**
 * @author huangwh@paraview.cn
 * @since 2021/07/21
 */
public abstract class Assert {

    /**
     * 检查一个对象是否为null
     * 
     * @param object
     *            检查对象
     * @param message
     *            假如对象为null 则抛出异常信息
     */
    public static void notNull(@Nullable Object object, String message) {
        if (object == null) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 检查数据是否为null
     * 
     * @param array
     * @param message
     */
    public static void noNullElements(Object[] array, String message) {
        if (array == null) {
            return;
        }
        for (Object element : array) {
            if (element == null) {
                throw new IllegalArgumentException(message);
            }
        }
    }
}
