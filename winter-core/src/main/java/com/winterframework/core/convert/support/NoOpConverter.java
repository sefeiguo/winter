package com.winterframework.core.convert.support;

import java.util.Set;

import com.winterframework.Nullable;
import com.winterframework.core.convert.TypeDescriptor;
import com.winterframework.core.convert.converter.ConvertiblePair;
import com.winterframework.core.convert.converter.GenericConverter;

/**
 * Internal converter that performs no operation.
 */
public class NoOpConverter implements GenericConverter {

    private final String name;

    public NoOpConverter(String name) {
        this.name = name;
    }

    @Override
    @Nullable
    public Set<ConvertiblePair> getConvertibleTypes() {
        return null;
    }

    @Override
    @Nullable
    public Object convert(@Nullable Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        return source;
    }

    @Override
    public String toString() {
        return this.name;
    }
}