/*
 * Copyright (C), 1987-2099, Winter All Rights Reserved.
 */
package com.winterframework.beans.factory;

/**
 * 标记超接口表明一个bean是合格的通知的Spring容器特定的框架对象通过回叫形式的方法。
 * 但实际的方法签名是由个人个子通常由一个void-returning方法接受一个参数。
 * <p>
 * 注意,仅仅实现{@link Aware}没有提供默认功能。相反,必须显式地进行处理
 * 例如在一个{@link org.winterframework.beans.factory.config.BeanPostProcessor}。
 * 指{@link org.winterframework.context.support.ApplicationContextAwareProcessor}
 * 例如处理特定{@code *知道}接口回调。
 * </p>
 * 
 * @author huangwh@paraview.cn
 * @since 2021/07/19
 */
public interface Aware {
}
