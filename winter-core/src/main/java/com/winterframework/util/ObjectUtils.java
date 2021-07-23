/*
 * Copyright (C), 1987-2099, Winter All Rights Reserved.
 */
package com.winterframework.util;

import com.winterframework.Nullable;

/**
 * @author huangwh@paraview.cn
 * @since 2021/07/19
 */
public abstract class ObjectUtils {

    public static final String DELIMITER = "@";

    public static String identityToString(@Nullable Object obj) {
        if (obj == null) {
            return "";
        }
        return String.join(DELIMITER, obj.getClass().getName(), getIdentityHexString(obj));
    }

    private static String getIdentityHexString(Object obj) {
        return Integer.toHexString(System.identityHashCode(obj));
    }
}
