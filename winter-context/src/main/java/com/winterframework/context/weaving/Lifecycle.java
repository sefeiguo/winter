/*
 * Copyright (C), 2008-2021, Paraview All Rights Reserved.
 */
package com.winterframework.context.weaving;

/**
 * <p>
 * 一个公共接口定义的方法来启动/停止生命周期控制. 具体典型用例是控制异步处理。
 * </p>
 * <b>注:此接口并不意味着特定auto-startup语义。*考虑实现{@link SmartLifecycle}。</b>
 * 
 * @author huangwh@paraview.cn
 * @since 2021/07/19
 */
public interface Lifecycle {

    void start();

    void stop();

    void isRunning();
}
