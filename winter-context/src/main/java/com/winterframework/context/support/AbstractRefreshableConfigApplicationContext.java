/*
 * Copyright (C), 1987-2099, Winter All Rights Reserved.
 */
package com.winterframework.context.support;

import com.winterframework.Nullable;
import com.winterframework.beans.factory.BeanNameAware;
import com.winterframework.beans.factory.InitializingBean;
import com.winterframework.context.weaving.ApplicationContext;

/**
 * @author huangwh@paraview.cn
 * @since 2021/07/19
 */
public abstract class AbstractRefreshableConfigApplicationContext extends AbstractRefreshableApplicationContext
        implements
            BeanNameAware,
            InitializingBean {
    public AbstractRefreshableConfigApplicationContext(@Nullable ApplicationContext parent) {
        super(parent);
    }

    @Override
    public void setBeanName(String name) {

    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
