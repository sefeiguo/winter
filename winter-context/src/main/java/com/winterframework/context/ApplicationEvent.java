/*
 * Copyright (C), 1987-2099, Winter All Rights Reserved.
 */
package com.winterframework.context;

import java.util.EventObject;

/**
 * @author huangwh@paraview.cn
 * @since 2021/07/12
 */
public abstract class ApplicationEvent extends EventObject {

    private final Long timestamp;

    /**
     * Constructs a prototypical Event.
     *
     * @param source
     *            The object on which the Event initially occurred.
     * @throws IllegalArgumentException
     *             if source is null.
     */
    public ApplicationEvent(Object source) {
        super(source);
        this.timestamp = System.currentTimeMillis();
    }

    public final Long getTimestamp() {
        return this.timestamp;
    }
}
