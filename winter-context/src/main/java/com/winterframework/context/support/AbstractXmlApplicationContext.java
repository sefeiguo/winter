/*
 * Copyright (C), 1987-2099, Winter All Rights Reserved.
 */
package com.winterframework.context.support;

import com.winterframework.Nullable;
import com.winterframework.context.weaving.ApplicationContext;

/**
 * @author huangwh@paraview.cn
 * @since 2021/07/19
 */
public abstract class AbstractXmlApplicationContext extends AbstractRefreshableConfigApplicationContext {

    public AbstractXmlApplicationContext(@Nullable ApplicationContext parent) {
        super(parent);
    }
}
