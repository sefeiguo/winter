/*
 * Copyright (C), 1987-2099, Winter All Rights Reserved.
 */
package com.winterframework.core.env;

/**
 * @author huangwh@paraview.cn
 * @since 2021/07/19
 */
public interface Environment {
    /**
     * 合并父环境中的属性
     * 
     * @param parent
     */
    void merge(ConfigurableEnvironment parent);

    /**
     * 解析路径资源 准备替换${}参数
     * 
     * @param text
     * @return
     */
    String resolveRequiredPlaceholders(String text);
}
