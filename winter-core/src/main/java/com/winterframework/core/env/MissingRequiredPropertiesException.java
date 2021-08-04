/*
 * Copyright (C), 1987-2099, Winter All Rights Reserved.
 */
package com.winterframework.core.env;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author huangwh@paraview.cn
 * @since 2021/08/05
 */
public class MissingRequiredPropertiesException extends IllegalArgumentException {

    private final Set<String> missingRequiredProperties = new LinkedHashSet<>();
    /**
     * 
     * @param key
     */
    void addMissingRequiredProperty(String key) {
        this.missingRequiredProperties.add(key);
    }

    /**
     * 
     * @return
     */
    public Set<String> getMissingRequiredProperties() {
        return this.missingRequiredProperties;
    }
}