/*
 * Copyright (C), 2008-2021, Paraview All Rights Reserved.
 */
package com.winterframework.core.annotation;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author huangwh@paraview.cn
 * @since 2021/07/05
 * 要求互为别名的属性属性值类型，默认值，都是相同的，互为别名的注解必须成对出现，比如value属性添加了@AliasFor(“path”)，那么path属性就必须添加@AliasFor(“value”)，
 * 另外还有一点，互为别名的属性必须定义默认值
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AliasFor {

    /**
     * @return
     */
    @AliasFor("attribute")
    String value() default "";

    /**
     * @return
     */
    @AliasFor("value")
    String attribute() default "";

    /**
     * @return
     */
    Class<? extends Annotation> annotation() default Annotation.class;
}
