/*
 * Copyright (C), 2008-2021, Paraview All Rights Reserved.
 */
package com.winterframework.context;

/**
 * @author huangwh@paraview.cn
 * @since 2021/07/12
 */
@FunctionalInterface
public interface ApplicationEventPublisher {

    default void publishEvent(ApplicationEvent event) {
        publishEvent((Object) event);
    }

    void publishEvent(Object event);
}
