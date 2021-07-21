/*
 * Copyright (C), 2008-2021, Paraview All Rights Reserved.
 */
package com.winterframework.beans.factory;

/**
 * 接口由bean实现,需要反应一旦他们所有的属性设置了一个{@link BeanFactory}:例如执行自定义初始化,或者仅仅是检查所有强制性属性设置。
 * <p>
 * 另一种实现{@code InitializingBean}是指定一个自定义的init方法,例如在一个XML
 * bean定义。所有bean的列表生命周期方法,请参见{@link BeanFactory BeanFactory javadocs}。
 * </p>
 * 
 * @author huangwh@paraview.cn
 * @since 2021/07/19
 */
public interface InitializingBean {

    /**
     * 接口由bean实现,需要反应一旦他们所有的属性设置了一个{@link BeanFactory}:例如执行自定义初始化,或者仅仅是检查所有强制性属性设置。
     * <p>
     * 另一种实现{@code InitializingBean}是指定一个自定义的init方法,例如在一个XML
     * bean定义。所有bean的列表生命周期方法,请参见{@link BeanFactory BeanFactory javadocs}。
     * </p>
     * 
     * @throws Exception
     */
    void afterPropertiesSet() throws Exception;
}
