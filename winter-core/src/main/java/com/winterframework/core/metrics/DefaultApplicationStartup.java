/*
 * Copyright (C), 1987-2099, Winter All Rights Reserved.
 */
package com.winterframework.core.metrics;

/**
 * @author huangwh@paraview.cn
 * @since 2021/08/04
 */
public class DefaultApplicationStartup implements ApplicationStartup {

    @Override
    public DefaultStartupStep start(String name) {
        return new DefaultStartupStep();
    }

    static class DefaultStartupStep implements StartupStep {

    }
}