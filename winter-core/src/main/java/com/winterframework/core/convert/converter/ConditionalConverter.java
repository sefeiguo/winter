/*
 * Copyright (C), 1987-2099, Winter All Rights Reserved.
 */
package com.winterframework.core.convert.converter;

import com.winterframework.core.convert.TypeDescriptor;

/**
 * @author huangwh@paraview.cn
 * @since 2021/07/27
 */
public interface ConditionalConverter {
    /**
     * 
     * @param sourceType
     * @param targetType
     * @return
     */
    boolean matches(TypeDescriptor sourceType, TypeDescriptor targetType);
}