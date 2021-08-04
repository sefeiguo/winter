/*
 * Copyright (C), 1987-2099, Winter All Rights Reserved.
 */
package com.winterframework.core.env;

/**
 * @author huangwh@paraview.cn
 * @since 2021/07/2;
 */
public interface ConfigurablePropertyResolver extends PropertyResolver {

    /**
     * 验证必要属性的正确性
     */
    void validateRequiredProperties();
}
