/*
 * Copyright (C), 1987-2099, Winter All Rights Reserved.
 */
package com.winterframework.core.convert.support;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.CopyOnWriteArraySet;

import com.winterframework.Nullable;
import com.winterframework.core.convert.ConversionFailedException;
import com.winterframework.core.convert.TypeDescriptor;
import com.winterframework.core.convert.converter.ConditionalConverter;
import com.winterframework.core.convert.converter.ConditionalGenericConverter;
import com.winterframework.core.convert.converter.ConvertiblePair;
import com.winterframework.core.convert.converter.GenericConverter;
import com.winterframework.util.Assert;
import com.winterframework.util.ClassUtils;
import com.winterframework.util.ConcurrentReferenceHashMap;
import com.winterframework.util.StringUtils;

/**
 * @author huangwh@paraview.cn
 * @since 2021/07/26
 */
public class GenericConversionService implements ConfigurableConversionService {

    private final Map<ConverterCacheKey, GenericConverter> converterCache = new ConcurrentReferenceHashMap<>(64);

    /**
     * Used as a cache entry when no converter is available. This converter is never
     * returned.
     */
    private static final GenericConverter NO_MATCH = new NoOpConverter("NO_MATCH");

    private final Converters converters = new Converters();

    @Override
    public <T> T convert(Object source, Class<T> targetType) {
        Assert.notNull(targetType, "Target type to convert to cannot be null");
        return (T) convert(source, TypeDescriptor.forObject(source), TypeDescriptor.valueOf(targetType));
    }

    @Override
    @Nullable
    public Object convert(@Nullable Object source, @Nullable TypeDescriptor sourceType, TypeDescriptor targetType) {
        Assert.notNull(targetType, "Target type to convert to cannot be null");
        if (sourceType == null) {
            Assert.isTrue(source == null, "Source must be [null] if source type == [null]");
            return handleResult(null, targetType, convertNullSource(null, targetType));
        }
        if (source != null && !sourceType.getObjectType().isInstance(source)) {
            throw new IllegalArgumentException("Source to convert from must be an instance of [" + sourceType + "]; instead it was a ["
                    + source.getClass().getName() + "]");
        }
        GenericConverter converter = getConverter(sourceType, targetType);
        if (converter != null) {
            Object result = ConversionUtils.invokeConverter(converter, source, sourceType, targetType);
            return handleResult(sourceType, targetType, result);
        }
        return handleConverterNotFound(source, sourceType, targetType);
    }

    private Object handleConverterNotFound(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        return null;
    }

    @Nullable
    protected Object convertNullSource(@Nullable TypeDescriptor sourceType, TypeDescriptor targetType) {
        if (targetType.getObjectType() == Optional.class) {
            return Optional.empty();
        }
        return null;
    }

    @Nullable
    private Object handleResult(@Nullable TypeDescriptor sourceType, TypeDescriptor targetType, @Nullable Object result) {
        if (result == null) {
            assertNotPrimitiveTargetType(sourceType, targetType);
        }
        return result;
    }

    private void assertNotPrimitiveTargetType(@Nullable TypeDescriptor sourceType, TypeDescriptor targetType) {
        if (targetType.isPrimitive()) {
            throw new ConversionFailedException(sourceType, targetType, null,
                    new IllegalArgumentException("A null value cannot be assigned to a primitive type"));
        }
    }

    @Nullable
    protected GenericConverter getConverter(TypeDescriptor sourceType, TypeDescriptor targetType) {
        ConverterCacheKey key = new ConverterCacheKey(sourceType, targetType);
        GenericConverter converter = this.converterCache.get(key);
        if (converter != null) {
            return (converter != NO_MATCH ? converter : null);
        }

        converter = this.converters.find(sourceType, targetType);
        if (converter == null) {
            converter = getDefaultConverter(sourceType, targetType);
        }

        if (converter != null) {
            this.converterCache.put(key, converter);
            return converter;
        }

        this.converterCache.put(key, NO_MATCH);
        return null;
    }

    private GenericConverter getDefaultConverter(TypeDescriptor sourceType, TypeDescriptor targetType) {
        return null;
    }

    private static final class ConverterCacheKey implements Comparable<ConverterCacheKey> {

        private final TypeDescriptor sourceType;

        private final TypeDescriptor targetType;

        public ConverterCacheKey(TypeDescriptor sourceType, TypeDescriptor targetType) {
            this.sourceType = sourceType;
            this.targetType = targetType;
        }

        @Override
        public boolean equals(@Nullable Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof ConverterCacheKey)) {
                return false;
            }
            ConverterCacheKey otherKey = (ConverterCacheKey) other;
            return (this.sourceType.equals(otherKey.sourceType)) && this.targetType.equals(otherKey.targetType);
        }

        @Override
        public int hashCode() {
            return (this.sourceType.hashCode() * 29 + this.targetType.hashCode());
        }

        @Override
        public String toString() {
            return ("ConverterCacheKey [sourceType = " + this.sourceType + ", targetType = " + this.targetType + "]");
        }

        @Override
        public int compareTo(ConverterCacheKey other) {
            int result = this.sourceType.getResolvableType().toString().compareTo(other.sourceType.getResolvableType().toString());
            if (result == 0) {
                result = this.targetType.getResolvableType().toString().compareTo(other.targetType.getResolvableType().toString());
            }
            return result;
        }
    }

    /**
     * Manages all converters registered with the service.
     */
    private static class Converters {

        private final Set<GenericConverter> globalConverters = new CopyOnWriteArraySet<>();

        private final Map<ConvertiblePair, ConvertersForPair> converters = new ConcurrentHashMap<>(256);

        public void add(GenericConverter converter) {
            Set<ConvertiblePair> convertibleTypes = converter.getConvertibleTypes();
            if (convertibleTypes == null) {
                Assert.state(converter instanceof ConditionalConverter, "Only conditional converters may return null convertible types");
                this.globalConverters.add(converter);
            } else {
                for (ConvertiblePair convertiblePair : convertibleTypes) {
                    getMatchableConverters(convertiblePair).add(converter);
                }
            }
        }

        private ConvertersForPair getMatchableConverters(ConvertiblePair convertiblePair) {
            return this.converters.computeIfAbsent(convertiblePair, k -> new ConvertersForPair());
        }

        public void remove(Class<?> sourceType, Class<?> targetType) {
            this.converters.remove(new ConvertiblePair(sourceType, targetType));
        }

        /**
         * Find a {@link GenericConverter} given a source and target type.
         * <p>
         * This method will attempt to match all possible converters by working through
         * the class and interface hierarchy of the types.
         * 
         * @param sourceType
         *            the source type
         * @param targetType
         *            the target type
         * @return a matching {@link GenericConverter}, or {@code null} if none found
         */
        @Nullable
        public GenericConverter find(TypeDescriptor sourceType, TypeDescriptor targetType) {
            // Search the full type hierarchy
            List<Class<?>> sourceCandidates = getClassHierarchy(sourceType.getType());
            List<Class<?>> targetCandidates = getClassHierarchy(targetType.getType());
            for (Class<?> sourceCandidate : sourceCandidates) {
                for (Class<?> targetCandidate : targetCandidates) {
                    ConvertiblePair convertiblePair = new ConvertiblePair(sourceCandidate, targetCandidate);
                    GenericConverter converter = getRegisteredConverter(sourceType, targetType, convertiblePair);
                    if (converter != null) {
                        return converter;
                    }
                }
            }
            return null;
        }

        @Nullable
        private GenericConverter getRegisteredConverter(TypeDescriptor sourceType, TypeDescriptor targetType,
                ConvertiblePair convertiblePair) {

            // Check specifically registered converters
            ConvertersForPair convertersForPair = this.converters.get(convertiblePair);
            if (convertersForPair != null) {
                GenericConverter converter = convertersForPair.getConverter(sourceType, targetType);
                if (converter != null) {
                    return converter;
                }
            }
            // Check ConditionalConverters for a dynamic match
            for (GenericConverter globalConverter : this.globalConverters) {
                if (((ConditionalConverter) globalConverter).matches(sourceType, targetType)) {
                    return globalConverter;
                }
            }
            return null;
        }

        /**
         * Returns an ordered class hierarchy for the given type.
         * 
         * @param type
         *            the type
         * @return an ordered list of all classes that the given type extends or
         *         implements
         */
        private List<Class<?>> getClassHierarchy(Class<?> type) {
            List<Class<?>> hierarchy = new ArrayList<>(20);
            Set<Class<?>> visited = new HashSet<>(20);
            addToClassHierarchy(0, ClassUtils.resolvePrimitiveIfNecessary(type), false, hierarchy, visited);
            boolean array = type.isArray();

            int i = 0;
            while (i < hierarchy.size()) {
                Class<?> candidate = hierarchy.get(i);
                candidate = (array ? candidate.getComponentType() : ClassUtils.resolvePrimitiveIfNecessary(candidate));
                Class<?> superclass = candidate.getSuperclass();
                if (superclass != null && superclass != Object.class && superclass != Enum.class) {
                    addToClassHierarchy(i + 1, candidate.getSuperclass(), array, hierarchy, visited);
                }
                addInterfacesToClassHierarchy(candidate, array, hierarchy, visited);
                i++;
            }

            if (Enum.class.isAssignableFrom(type)) {
                addToClassHierarchy(hierarchy.size(), Enum.class, array, hierarchy, visited);
                addToClassHierarchy(hierarchy.size(), Enum.class, false, hierarchy, visited);
                addInterfacesToClassHierarchy(Enum.class, array, hierarchy, visited);
            }

            addToClassHierarchy(hierarchy.size(), Object.class, array, hierarchy, visited);
            addToClassHierarchy(hierarchy.size(), Object.class, false, hierarchy, visited);
            return hierarchy;
        }

        private void addInterfacesToClassHierarchy(Class<?> type, boolean asArray, List<Class<?>> hierarchy, Set<Class<?>> visited) {

            for (Class<?> implementedInterface : type.getInterfaces()) {
                addToClassHierarchy(hierarchy.size(), implementedInterface, asArray, hierarchy, visited);
            }
        }

        private void addToClassHierarchy(int index, Class<?> type, boolean asArray, List<Class<?>> hierarchy, Set<Class<?>> visited) {

            if (asArray) {
                type = Array.newInstance(type, 0).getClass();
            }
            if (visited.add(type)) {
                hierarchy.add(index, type);
            }
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("ConversionService converters =\n");
            for (String converterString : getConverterStrings()) {
                builder.append('\t').append(converterString).append('\n');
            }
            return builder.toString();
        }

        private List<String> getConverterStrings() {
            List<String> converterStrings = new ArrayList<>();
            for (ConvertersForPair convertersForPair : this.converters.values()) {
                converterStrings.add(convertersForPair.toString());
            }
            Collections.sort(converterStrings);
            return converterStrings;
        }
    }

    /**
     * Manages converters registered with a specific {@link ConvertiblePair}.
     */
    private static class ConvertersForPair {

        private final Deque<GenericConverter> converters = new ConcurrentLinkedDeque<>();

        public void add(GenericConverter converter) {
            this.converters.addFirst(converter);
        }

        @Nullable
        public GenericConverter getConverter(TypeDescriptor sourceType, TypeDescriptor targetType) {
            for (GenericConverter converter : this.converters) {
                if (!(converter instanceof ConditionalGenericConverter)
                        || ((ConditionalGenericConverter) converter).matches(sourceType, targetType)) {
                    return converter;
                }
            }
            return null;
        }

        @Override
        public String toString() {
            return StringUtils.collectionToCommaDelimitedString(this.converters);
        }
    }
}