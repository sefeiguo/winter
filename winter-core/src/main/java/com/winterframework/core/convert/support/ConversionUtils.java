/*
 * Copyright (C), 1987-2099, Winter All Rights Reserved.
 */
package com.winterframework.core.convert.support;

import com.winterframework.Nullable;
import com.winterframework.core.convert.ConversionFailedException;
import com.winterframework.core.convert.TypeDescriptor;
import com.winterframework.core.convert.converter.GenericConverter;

/**
 * @author huangwh@paraview.cn
 * @since 2021/07/27
 */
public class ConversionUtils {

    @Nullable
    public static Object invokeConverter(GenericConverter converter, @Nullable Object source, TypeDescriptor sourceType,
            TypeDescriptor targetType) {

        try {
            return converter.convert(source, sourceType, targetType);
        } catch (ConversionFailedException ex) {
            throw ex;
        } catch (Throwable ex) {
            throw new ConversionFailedException(sourceType, targetType, source, ex);
        }
    }
}