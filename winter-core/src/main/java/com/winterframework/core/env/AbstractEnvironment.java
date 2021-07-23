/*
 * Copyright (C), 1987-2099, Winter All Rights Reserved.
 */
package com.winterframework.core.env;

/**
 * @author huangwh@paraview.cn
 * @since 2021/07/22
 */
public abstract class AbstractEnvironment implements ConfigurableEnvironment {
    @Override
    public void merge(ConfigurableEnvironment parent) {

    }

    @Override
    public String resolveRequiredPlaceholders(String text) {
        return null;
    }
}
