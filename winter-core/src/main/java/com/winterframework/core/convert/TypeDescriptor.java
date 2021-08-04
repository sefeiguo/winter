/*
 * Copyright (C), 1987-2099, Winter All Rights Reserved.
 */
package com.winterframework.core.convert;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.winterframework.Nullable;
import com.winterframework.core.ResolvableType;
import com.winterframework.util.ClassUtils;
import com.winterframework.util.ObjectUtils;

/**
 * @author huangwh@paraview.cn
 * @since 2021/07/26
 */
public class TypeDescriptor implements Serializable {

    private static final Map<Class<?>, TypeDescriptor> COMMON_TYPES_CACHE = new HashMap<>(32);

    private static final Annotation[] EMPTY_ANNOTATION_ARRAY = new Annotation[0];

    private static final Class<?>[] CACHED_COMMON_TYPES = {boolean.class, Boolean.class, byte.class, Byte.class, char.class,
            Character.class, double.class, Double.class, float.class, Float.class, int.class, Integer.class, long.class, Long.class,
            short.class, Short.class, String.class, Object.class};

    static {
        for (Class<?> preCachedClass : CACHED_COMMON_TYPES) {
            COMMON_TYPES_CACHE.put(preCachedClass, valueOf(preCachedClass));
        }
    }

    private final Class<?> type;

    private final ResolvableType resolvableType;

    private final AnnotatedElementAdapter annotatedElement;

    /**
     * Create a new type descriptor from a {@link ResolvableType}.
     * <p>
     * This constructor is used internally and may also be used by subclasses that
     * support non-Java languages with extended type systems. It is public as of
     * 5.1.4 whereas it was protected before.
     * 
     * @param resolvableType
     *            the resolvable type
     * @param type
     *            the backing type (or {@code null} if it should get resolved)
     * @param annotations
     *            the type annotations
     * @since 4.0
     */
    public TypeDescriptor(ResolvableType resolvableType, @Nullable Class<?> type, @Nullable Annotation[] annotations) {
        this.resolvableType = resolvableType;
        this.type = (type != null ? type : resolvableType.toClass());
        this.annotatedElement = new AnnotatedElementAdapter(annotations);
    }

    @Nullable
    public static TypeDescriptor forObject(@Nullable Object source) {
        return (source != null ? valueOf(source.getClass()) : null);
    }

    public static TypeDescriptor valueOf(Class<?> type) {
        if (type == null) {
            type = Object.class;
        }
        TypeDescriptor desc = COMMON_TYPES_CACHE.get(type);
        return (desc != null ? desc : new TypeDescriptor(ResolvableType.forClass(type), null, null));
    }

    public Class<?> getObjectType() {
        return ClassUtils.resolvePrimitiveIfNecessary(getType());
    }

    public Class<?> getType() {
        return this.type;
    }

    public Object getResolvableType() {
        return this.resolvableType;
    }

    /**
     * 决定如果指定的{}@code类对象代表一个原始类型。
     * 
     * @return 这种类型是一个原始类型吗?
     */
    public boolean isPrimitive() {
        return getType().isPrimitive();
    }

    /**
     * Adapter class for exposing a {@code TypeDescriptor}'s annotations as an
     */
    private class AnnotatedElementAdapter implements AnnotatedElement, Serializable {

        @Nullable
        private final Annotation[] annotations;

        public AnnotatedElementAdapter(@Nullable Annotation[] annotations) {
            this.annotations = annotations;
        }

        @Override
        public boolean isAnnotationPresent(Class<? extends Annotation> annotationClass) {
            for (Annotation annotation : getAnnotations()) {
                if (annotation.annotationType() == annotationClass) {
                    return true;
                }
            }
            return false;
        }

        @Override
        @Nullable
        @SuppressWarnings("unchecked")
        public <T extends Annotation> T getAnnotation(Class<T> annotationClass) {
            for (Annotation annotation : getAnnotations()) {
                if (annotation.annotationType() == annotationClass) {
                    return (T) annotation;
                }
            }
            return null;
        }

        @Override
        public Annotation[] getAnnotations() {
            return (this.annotations != null ? this.annotations.clone() : EMPTY_ANNOTATION_ARRAY);
        }

        @Override
        public Annotation[] getDeclaredAnnotations() {
            return getAnnotations();
        }

        public boolean isEmpty() {
            return ObjectUtils.isEmpty(this.annotations);
        }

        @Override
        public boolean equals(@Nullable Object other) {
            return (this == other || (other instanceof AnnotatedElementAdapter
                    && Arrays.equals(this.annotations, ((AnnotatedElementAdapter) other).annotations)));
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(this.annotations);
        }

        @Override
        public String toString() {
            return TypeDescriptor.this.toString();
        }
    }
}