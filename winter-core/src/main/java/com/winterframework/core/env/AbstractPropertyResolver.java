/*
 * Copyright (C), 1987-2099, Winter All Rights Reserved.
 */
package com.winterframework.core.env;

import com.winterframework.Nullable;
import com.winterframework.core.convert.ConversionService;
import com.winterframework.core.convert.support.ConfigurableConversionService;
import com.winterframework.core.convert.support.DefaultConversionService;
import com.winterframework.util.ClassUtils;
import com.winterframework.util.SystemPropertyUtils;

/**
 * @author huangwh@paraview.cn
 * @since 2021/07/25
 */
public abstract class AbstractPropertyResolver implements ConfigurablePropertyResolver {

    private String placeholderPrefix = SystemPropertyUtils.PLACEHOLDER_PREFIX;

    private String placeholderSuffix = SystemPropertyUtils.PLACEHOLDER_SUFFIX;

    @Nullable
    private String valueSeparator = SystemPropertyUtils.VALUE_SEPARATOR;

    private boolean ignoreUnresolvableNestedPlaceholders = false;

    @Nullable
    private PropertyPlaceholderHelper strictHelper;

    @Nullable
    private PropertyPlaceholderHelper nonStrictHelper;

    @Nullable
    private volatile ConfigurableConversionService conversionService;

    @Override
    public String resolvePlaceholders(String text) {
        if (this.nonStrictHelper == null) {
            this.nonStrictHelper = createPlaceholderHelper(true);
        }
        return doResolvePlaceholders(text, this.nonStrictHelper);
    }

    @Override
    public String resolveRequiredPlaceholders(String text) {
        if (this.strictHelper == null) {
            this.strictHelper = createPlaceholderHelper(false);
        }
        return doResolvePlaceholders(text, this.strictHelper);
    }

    protected String doResolvePlaceholders(String text, PropertyPlaceholderHelper helper) {
        return helper.replacePlaceholders(text, this::getPropertyAsRawString);
    }

    private PropertyPlaceholderHelper createPlaceholderHelper(boolean ignoreUnresolvablePlaceholders) {
        return new PropertyPlaceholderHelper(this.placeholderPrefix, this.placeholderSuffix, this.valueSeparator,
                ignoreUnresolvablePlaceholders);
    }

    @Nullable
    protected abstract String getPropertyAsRawString(String key);

    protected String resolveNestedPlaceholders(String value) {
        if (value.isEmpty()) {
            return value;
        }
        return (this.ignoreUnresolvableNestedPlaceholders ? resolvePlaceholders(value) : resolveRequiredPlaceholders(value));
    }

    protected <T> T convertValueIfNecessary(Object value, @Nullable Class<T> targetValueType) {
        if (targetValueType == null) {
            return (T) value;
        }
        ConversionService conversionServiceToUse = this.conversionService;
        if (conversionServiceToUse == null) {
            if (ClassUtils.isAssignableValue(targetValueType, value)) {
                return (T) value;
            }
            conversionServiceToUse = DefaultConversionService.getSharedInstance();
        }
        return conversionServiceToUse.convert(value, targetValueType);
    }
}