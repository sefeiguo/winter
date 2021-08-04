/*
 * Copyright (C), 1987-2099, Winter All Rights Reserved.
 */
package com.winterframework.core.env;

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
}
