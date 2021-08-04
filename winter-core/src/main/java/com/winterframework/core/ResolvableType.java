/*
 * Copyright (C), 1987-2099, Winter All Rights Reserved.
 */
package com.winterframework.core;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

import com.winterframework.Nullable;

/**
 * @author huangwh@paraview.cn
 * @since 2021/07/26
 */
public class ResolvableType implements Serializable {

    /**
     * The underlying Java type being managed.
     */
    private final Type type;

    /**
     * Optional provider for the type.
     */
    @Nullable
    private final SerializableTypeWrapper.TypeProvider typeProvider;

    /**
     * The {@code VariableResolver} to use or {@code null} if no resolver is
     * available.
     */
    @Nullable
    private final VariableResolver variableResolver;

    /**
     * The component type for an array or {@code null} if the type should be
     * deduced.
     */
    @Nullable
    private final ResolvableType componentType;

    @Nullable
    private final Integer hash;

    @Nullable
    private Class<?> resolved;

    @Nullable
    private volatile ResolvableType superType;

    @Nullable
    private volatile ResolvableType[] interfaces;

    @Nullable
    private volatile ResolvableType[] generics;

    public ResolvableType(@Nullable Class<?> clazz) {
        this.resolved = (clazz != null ? clazz : Object.class);
        this.type = this.resolved;
        this.typeProvider = null;
        this.variableResolver = null;
        this.componentType = null;
        this.hash = null;
    }

    public static ResolvableType forClass(@Nullable Class<?> clazz) {
        return new ResolvableType(clazz);
    }

    public Class<?> toClass() {
        return resolve(Object.class);
    }

    public Class<?> resolve(Class<?> fallback) {
        return this.resolved != null ? this.resolved : fallback;
    }

    /**
     * Strategy interface used to resolve {@link TypeVariable TypeVariables}.
     */
    interface VariableResolver extends Serializable {

        /**
         * Return the source of the resolver (used for hashCode and equals).
         */
        Object getSource();

        /**
         * Resolve the specified variable.
         * 
         * @param variable
         *            the variable to resolve
         * @return the resolved variable, or {@code null} if not found
         */
        @Nullable
        ResolvableType resolveVariable(TypeVariable<?> variable);
    }
}