/*
 * Copyright (C), 1987-2099, Winter All Rights Reserved.
 */
package com.winterframework.context.support;

import com.winterframework.Nullable;
import com.winterframework.beans.factory.config.ConfigurableListableBeanFactory;
import com.winterframework.beans.factory.support.DefaultListableBeanFactory;
import com.winterframework.context.weaving.ApplicationContext;

/**
 * 
 * 基类{@link com.winterframework.context.ApplicationContext}
 * 实现应该支持多个调用{@link # refresh ()},每次都创建一个新的内部工厂bean实例。
 * 通常(但不一定),这种情况下将由一组配置位置加载bean定义。
 * 
 * @author huangwh@paraview.cn
 * @since 2021/07/19
 */
public abstract class AbstractRefreshableApplicationContext extends AbstractApplicationContext {

    @Nullable
    private volatile DefaultListableBeanFactory beanFactory;

    public AbstractRefreshableApplicationContext(@Nullable ApplicationContext parent) {
        super(parent);
    }

    @Override
    public final ConfigurableListableBeanFactory getBeanFactory() {
        DefaultListableBeanFactory beanFactory = this.beanFactory;
        if (beanFactory == null) {
            throw new IllegalStateException("BeanFactory not initialized or already closed - "
                    + "call 'refresh' before accessing beans via the ApplicationContext");
        }
        return beanFactory;
    }
}
