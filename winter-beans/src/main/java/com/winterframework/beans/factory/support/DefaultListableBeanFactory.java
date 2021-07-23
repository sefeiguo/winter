/*
 * Copyright (C), 1987-2099, Winter All Rights Reserved.
 */
package com.winterframework.beans.factory.support;

import java.io.Serializable;

import com.winterframework.beans.BeansException;
import com.winterframework.beans.factory.config.ConfigurableListableBeanFactory;

/**
 * @author huangwh@paraview.cn
 * @since 2021/07/20
 */
public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory
        implements
            ConfigurableListableBeanFactory,
            BeanDefinitionRegistry,
            Serializable {
    @Override
    public <T> T getBean(Class<T> requiredType) throws BeansException {
        return null;
    }
}
