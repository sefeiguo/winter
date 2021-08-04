/*
 * Copyright (C), 1987-2099, Winter All Rights Reserved.
 */
package com.winterframework.util;

import java.util.Collection;

/**
 * @author huangwh@paraview.cn
 * @since 2021/07/27
 */
public abstract class CollectionUtils {

    public static boolean isEmpty(Collection<?> collection) {
        return (collection == null || collection.isEmpty());
    }
}