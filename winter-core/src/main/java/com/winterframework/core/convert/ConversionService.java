/*
 * Copyright (C), 1987-2099, Winter All Rights Reserved.
 */
package com.winterframework.core.convert;

import com.winterframework.Nullable;

/**
 * @author huangwh@paraview.cn
 * @since 2021/07/26
 */
public interface ConversionService {

    <T> T convert(Object value, Class<T> targetValueType);

    @Nullable
    Object convert(@Nullable Object source, @Nullable TypeDescriptor sourceType, TypeDescriptor targetType);
}