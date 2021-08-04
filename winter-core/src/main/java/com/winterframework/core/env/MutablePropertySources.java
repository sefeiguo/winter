/*
 * Copyright (C), 1987-2099, Winter All Rights Reserved.
 */
package com.winterframework.core.env;

import java.util.Iterator;

/**
 * @author huangwh@paraview.cn
 * @since 2021/07/25
 */
public class MutablePropertySources implements PropertySources {
    public MutablePropertySources() {
    }

    @Override
    public boolean contains(String name) {
        return false;
    }

    @Override
    public PropertySource<?> get(String name) {
        return null;
    }

    @Override
    public Iterator<PropertySource<?>> iterator() {
        return null;
    }
}