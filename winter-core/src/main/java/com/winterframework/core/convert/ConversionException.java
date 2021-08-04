/*
 * Copyright (C), 1987-2099, Winter All Rights Reserved.
 */
package com.winterframework.core.convert;

import com.winterframework.core.NestedRuntimeException;

/**
 * @author huangwh@paraview.cn
 * @since 2021/07/27
 */
public abstract class ConversionException extends NestedRuntimeException {

    public ConversionException(String message, Throwable cause) {
        super(message, cause);
    }
}