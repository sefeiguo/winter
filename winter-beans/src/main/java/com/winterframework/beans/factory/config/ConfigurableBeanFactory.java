/*
 * Copyright (C), 1987-2099, Winter All Rights Reserved.
 */
package com.winterframework.beans.factory.config;

import com.winterframework.beans.factory.HierarchicalBeanFactory;

/**
 * 大多数bean配置接口实现的工厂。
 * 提供了设施配置bean工厂,除了bean工厂客户端方法{@link org.winterframework.beans.factory.BeanFactory}接口。
 * 
 * @author huangwh@paraview.cn
 * @since 2021/07/19
 */
public interface ConfigurableBeanFactory extends HierarchicalBeanFactory, SingletonBeanRegistry {
}
