/*
 * Copyright (C), 1987-2099, Winter All Rights Reserved.
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
