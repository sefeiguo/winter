/*
 * Copyright (C), 1987-2099, Winter All Rights Reserved.
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

    /**
     * 声明周期启动
     */
    void start();

    /**
     * 声明周期停止
     */
    void stop();

    /**
     * 声明周期正在运行
     */
    void isRunning();
}
