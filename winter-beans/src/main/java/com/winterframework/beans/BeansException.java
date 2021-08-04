/*
 * Copyright (C), 1987-2099, Winter All Rights Reserved.
 */
package com.winterframework.beans;

import com.winterframework.core.NestedRuntimeException;

/**
 * @author huangwh@paraview.cn
 * @since 2021/07/19
 */
public class BeansException extends NestedRuntimeException {
    public BeansException(String message, Throwable cause) {
        super(message, cause);
    }
}
