/*
 * Copyright (C), 2008-2021, Paraview All Rights Reserved.
 */
package com.winterframework.stereotype;

/**
 * @author huangwh@paraview.cn
 * @since 2021/07/05
 */
public @interface Controller {

    //@AliasFor(annotation = Component.class)
    String value() default "";
}
