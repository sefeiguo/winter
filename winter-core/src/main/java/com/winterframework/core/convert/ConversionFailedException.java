/*
 * Copyright (C), 1987-2099, Winter All Rights Reserved.
 */
package com.winterframework.core.convert;

import com.winterframework.Nullable;
import com.winterframework.util.ObjectUtils;

/**
 * @author huangwh@paraview.cn
 * @since 2021/07/27
 */
public class ConversionFailedException extends ConversionException {

    @Nullable
    private final TypeDescriptor sourceType;

    private final TypeDescriptor targetType;

    @Nullable
    private final Object value;

    /**
     * Create a new conversion exception.
     * 
     * @param sourceType
     *            the value's original type
     * @param targetType
     *            the value's target type
     * @param value
     *            the value we tried to convert
     * @param cause
     *            the cause of the conversion failure
     */
    public ConversionFailedException(@Nullable TypeDescriptor sourceType, TypeDescriptor targetType, @Nullable Object value,
            Throwable cause) {

        super(String.format("Failed to convert from type [%s] to type [%s] for value '%s'", sourceType, targetType,
                ObjectUtils.nullSafeToString(value)), cause);
        this.sourceType = sourceType;
        this.targetType = targetType;
        this.value = value;
    }

    /**
     * Return the source type we tried to convert the value from.
     */
    @Nullable
    public TypeDescriptor getSourceType() {
        return this.sourceType;
    }

    /**
     * Return the target type we tried to convert the value to.
     */
    public TypeDescriptor getTargetType() {
        return this.targetType;
    }

    /**
     * Return the offending value.
     */
    @Nullable
    public Object getValue() {
        return this.value;
    }
}