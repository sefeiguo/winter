/*
 * Copyright (C), 1987-2099, Winter All Rights Reserved.
 */
package com.winterframework.beans.factory.support;

import com.winterframework.core.AliasRegistry;

/**
 * 接口注册持有bean定义,例如RootBeanDefinition和ChildBeanDefinition实例。
 * 通常由beanfactory实现内部使用AbstractBeanDefinition层次结构。
 * 
 * @author huangwh@paraview.cn
 * @since 2021/07/19
 */
public interface BeanDefinitionRegistry extends AliasRegistry {
}
