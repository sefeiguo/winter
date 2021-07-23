/*
 * Copyright (C), 1987-2099, Winter All Rights Reserved.
 */
package com.winterframework.stereotype;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.winterframework.core.annotation.AliasFor;

/**
 * @author huangwh@paraview.cn
 * @since 2021/07/05
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Controller {

    /**
     * @return
     */
    @AliasFor(annotation = Component.class)
    String value() default "";
}
