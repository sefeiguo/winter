/*
 * Copyright (C), 1987-2099, Winter All Rights Reserved.
 */
package com.winterframework.core.env;

/**
 * @author huangwh@paraview.cn
 * @since 2021/07/25
 */
public abstract class PropertySource<T> {

    protected final String name;

    protected final T source;

    public PropertySource(String name, T source) {
        this.name = name;
        this.source = source;
    }

    public PropertySource(String name) {
        this(name, (T) new Object());
    }

    public String getName() {
        return this.name;
    }

    public abstract T getProperty(String key);
}