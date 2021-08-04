/*
 * Copyright (C), 1987-2099, Winter All Rights Reserved.
 */
package com.winterframework.core.env;

import com.winterframework.Nullable;

import lombok.extern.slf4j.Slf4j;

/**
 * @author huangwh@paraview.cn
 * @since 2021/07/25
 */
@Slf4j
public class PropertySourcesPropertyResolver extends AbstractPropertyResolver {

    @Nullable
    private final PropertySources propertySources;

    public PropertySourcesPropertyResolver(PropertySources propertySources) {
        this.propertySources = propertySources;
    }

    @Override
    protected String getPropertyAsRawString(String key) {
        return getProperty(key, String.class, false);
    }

    private <T> T getProperty(String key, Class<T> targetValueType, boolean resolveNestedPlaceholders) {
        if (this.propertySources == null) {
            if (log.isTraceEnabled()) {
                log.trace("Could not find key '{}' in any property source", key);
            }
            return null;
        }
        for (PropertySource<?> propertySource : this.propertySources) {
            if (log.isTraceEnabled()) {
                log.trace("Searching for key '{}' in PropertySource '{}'", key, propertySource.getName());
            }
            Object value = propertySource.getProperty(key);
            if (value != null) {
                if (resolveNestedPlaceholders && value instanceof String) {
                    value = resolveNestedPlaceholders((String) value);
                }
                logKeyFound(key, propertySource, value);
                return convertValueIfNecessary(value, targetValueType);
            }
        }
        return null;
    }

    protected void logKeyFound(String key, PropertySource<?> propertySource, Object value) {
        if (log.isDebugEnabled()) {
            log.debug("Found key '{}' in PropertySource '" + propertySource.getName() + "' with value of type {}", propertySource.getName(),
                    value.getClass().getSimpleName());
        }
    }
}