/*
 * Copyright (C), 1987-2099, Winter All Rights Reserved.
 */
package com.winterframework.core.env;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import com.winterframework.Nullable;

/**
 * @author huangwh@paraview.cn
 * @since 2021/07/25
 */
public interface PropertySources extends Iterable<PropertySource<?>> {

    /**
     * 返回一个属性资源流
     * 
     * @return
     */
    default Stream<PropertySource<?>> stream() {
        return StreamSupport.stream(spliterator(), false);
    }

    /**
     * 根据属性名称返回是否已经被包含
     * 
     * @param name
     * @return
     */
    boolean contains(String name);

    /**
     * 根据名称返回一个属性资源
     * 
     * @param name
     * @return
     */
    @Nullable
    PropertySource<?> get(String name);
}