/*
 * Copyright (C), 1987-2099, Winter All Rights Reserved.
 */
package com.winterframework.core.convert.converter;

import java.util.Set;

import com.winterframework.Nullable;
import com.winterframework.core.convert.TypeDescriptor;

/**
 * @author huangwh@paraview.cn
 * @since 2021/07/26
 */
public interface GenericConverter {

    @Nullable
    Object convert(@Nullable Object source, TypeDescriptor sourceType, TypeDescriptor targetType);

    Set<ConvertiblePair> getConvertibleTypes();
}