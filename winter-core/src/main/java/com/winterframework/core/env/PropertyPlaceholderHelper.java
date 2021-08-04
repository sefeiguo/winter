/*
 * Copyright (C), 1987-2099, Winter All Rights Reserved.
 */
package com.winterframework.core.env;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.winterframework.Nullable;
import com.winterframework.util.Assert;
import com.winterframework.util.StringUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * @author huangwh@paraview.cn
 * @since 2021/07/25
 */
@Slf4j
public class PropertyPlaceholderHelper {

    private static final Map<String, String> WELL_KNOWN_SIMPLE_PREFIXES = new HashMap<>(4);

    static {
        WELL_KNOWN_SIMPLE_PREFIXES.put("}", "{");
        WELL_KNOWN_SIMPLE_PREFIXES.put("]", "[");
        WELL_KNOWN_SIMPLE_PREFIXES.put(")", "(");
    }

    private final String placeholderPrefix;

    private final String placeholderSuffix;

    private final String simplePrefix;

    @Nullable
    private final String valueSeparator;

    private final boolean ignoreUnresolvablePlaceholders;

    @Nullable
    private PropertyPlaceholderHelper strictHelper;

    public PropertyPlaceholderHelper(String placeholderPrefix, String placeholderSuffix, String valueSeparator,
            boolean ignoreUnresolvablePlaceholders) {
        Assert.notNull(placeholderPrefix, "placeholderPrefix must not be null");
        Assert.notNull(placeholderSuffix, "placeholderSuffix must not be null");

        this.placeholderPrefix = placeholderPrefix;
        this.placeholderSuffix = placeholderSuffix;

        String simpleProfixForSuffix = WELL_KNOWN_SIMPLE_PREFIXES.get(this.placeholderSuffix);
        if (simpleProfixForSuffix != null && this.placeholderPrefix.endsWith(this.placeholderSuffix)) {
            this.simplePrefix = simpleProfixForSuffix;
        } else {
            this.simplePrefix = this.placeholderPrefix;
        }

        this.valueSeparator = valueSeparator;
        this.ignoreUnresolvablePlaceholders = ignoreUnresolvablePlaceholders;
    }

    public String replacePlaceholders(String value, PlaceholderResolver placeholderResolver) {
        Assert.notNull(value, "value must not be null");
        return parseStringValue(value, placeholderResolver, null);
    }

    /**
     * Strategy interface used to resolve replacement values for placeholders
     * contained in Strings.
     */
    @FunctionalInterface
    public interface PlaceholderResolver {

        /**
         * Resolve the supplied placeholder name to the replacement value.
         * 
         * @param placeholderName
         *            the name of the placeholder to resolve
         * @return the replacement value, or {@code null} if no replacement is to be
         *         made
         */
        @Nullable
        String resolvePlaceholder(String placeholderName);
    }

    protected String parseStringValue(String value, PlaceholderResolver placeholderResolver, @Nullable Set<String> visitedPlaceholders) {

        int startIndex = value.indexOf(this.placeholderPrefix);
        if (startIndex == -1) {
            return value;
        }

        StringBuilder result = new StringBuilder(value);
        while (startIndex != -1) {
            int endIndex = findPlaceholderEndIndex(result, startIndex);
            if (endIndex != -1) {
                String placeholder = result.substring(startIndex + this.placeholderPrefix.length(), endIndex);
                String originalPlaceholder = placeholder;
                if (visitedPlaceholders == null) {
                    visitedPlaceholders = new HashSet<>(4);
                }
                if (!visitedPlaceholders.add(originalPlaceholder)) {
                    throw new IllegalArgumentException(
                            "Circular placeholder reference '" + originalPlaceholder + "' in property definitions");
                }
                // Recursive invocation, parsing placeholders contained in the placeholder key.
                placeholder = parseStringValue(placeholder, placeholderResolver, visitedPlaceholders);
                // Now obtain the value for the fully resolved key...
                String propVal = placeholderResolver.resolvePlaceholder(placeholder);
                if (propVal == null && this.valueSeparator != null) {
                    int separatorIndex = placeholder.indexOf(this.valueSeparator);
                    if (separatorIndex != -1) {
                        String actualPlaceholder = placeholder.substring(0, separatorIndex);
                        String defaultValue = placeholder.substring(separatorIndex + this.valueSeparator.length());
                        propVal = placeholderResolver.resolvePlaceholder(actualPlaceholder);
                        if (propVal == null) {
                            propVal = defaultValue;
                        }
                    }
                }
                if (propVal != null) {
                    // Recursive invocation, parsing placeholders contained in the
                    // previously resolved placeholder value.
                    propVal = parseStringValue(propVal, placeholderResolver, visitedPlaceholders);
                    result.replace(startIndex, endIndex + this.placeholderSuffix.length(), propVal);
                    if (log.isTraceEnabled()) {
                        log.trace("Resolved placeholder '" + placeholder + "'");
                    }
                    startIndex = result.indexOf(this.placeholderPrefix, startIndex + propVal.length());
                } else if (this.ignoreUnresolvablePlaceholders) {
                    // Proceed with unprocessed value.
                    startIndex = result.indexOf(this.placeholderPrefix, endIndex + this.placeholderSuffix.length());
                } else {
                    throw new IllegalArgumentException(
                            "Could not resolve placeholder '" + placeholder + "'" + " in value \"" + value + "\"");
                }
                visitedPlaceholders.remove(originalPlaceholder);
            } else {
                startIndex = -1;
            }
        }
        return result.toString();
    }

    private int findPlaceholderEndIndex(CharSequence buf, int startIndex) {
        int index = startIndex + this.placeholderPrefix.length();
        int withinNestedPlaceholder = 0;
        while (index < buf.length()) {
            if (StringUtils.substringMatch(buf, index, this.placeholderSuffix)) {
                if (withinNestedPlaceholder > 0) {
                    withinNestedPlaceholder--;
                    index = index + this.placeholderSuffix.length();
                } else {
                    return index;
                }
            } else if (StringUtils.substringMatch(buf, index, this.simplePrefix)) {
                withinNestedPlaceholder++;
                index = index + this.simplePrefix.length();
            } else {
                index++;
            }
        }
        return -1;
    }
}