/*
 * Copyright (C), 1987-2099, Winter All Rights Reserved.
 */
package com.winterframework.core.env;

import com.winterframework.Nullable;

/**
 * @author huangwh@paraview.cn
 * @since 2021/07/19
 */
public interface PropertyResolver {

    String resolvePlaceholders(String text);

    /**
     * 解决$ {…}占位符在给定的文本,取而代之的是相应的属性值是通过{@link # getProperty}来解决。
     * 不肯舍弃的占位符没有默认值会导致抛出一个IllegalArgumentException。
     * 
     * @param text
     * @return
     */
    String resolveRequiredPlaceholders(String text);

    /**
     * 获取资源属性
     * 
     * @param key
     * @return
     */
    String getProperty(String key);

    @Nullable
    <T> T getProperty(String key, Class<T> targetType);
}
