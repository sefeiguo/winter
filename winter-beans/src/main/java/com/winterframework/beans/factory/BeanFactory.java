/*
 * Copyright (C), 2008-2021, Paraview All Rights Reserved.
 */
package com.winterframework.beans.factory;

import com.winterframework.beans.BeansException;

/**
 * @author huangwh@paraview.cn
 * @since 2021/07/12
 */
public interface BeanFactory {

    <T> T getBean(Class<T> requiredType) throws BeansException;
}
