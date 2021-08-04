/*
 * Copyright (C), 1987-2099, Winter All Rights Reserved.
 */
package com.winterframework.core;

import java.io.Serializable;
import java.lang.reflect.Type;

import com.winterframework.Nullable;

/**
 * @author huangwh@paraview.cn
 * @since 2021/07/26
 */
final class SerializableTypeWrapper {

    @SuppressWarnings("serial")
    interface TypeProvider extends Serializable {

        /**
         * Return the (possibly non {@link Serializable}) {@link Type}.
         */
        @Nullable
        Type getType();

        /**
         * Return the source of the type, or {@code null} if not known.
         * <p>
         * The default implementations returns {@code null}.
         */
        @Nullable
        default Object getSource() {
            return null;
        }
    }
}