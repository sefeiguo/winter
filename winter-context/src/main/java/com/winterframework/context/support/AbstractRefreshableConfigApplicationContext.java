/*
 * Copyright (C), 2008-2021, Paraview All Rights Reserved.
 */
package com.winterframework.context.support;

import com.winterframework.beans.factory.BeanNameAware;
import com.winterframework.beans.factory.InitializingBean;

/**
 * @author huangwh@paraview.cn
 * @since 2021/07/19
 */
public abstract class AbstractRefreshableConfigApplicationContext extends AbstractRefreshableApplicationContext
        implements
            BeanNameAware,
            InitializingBean {
    @Override
    public void setBeanName(String name) {

    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
