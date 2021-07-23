/*
 * Copyright (C), 1987-2099, Winter All Rights Reserved.
 */
package com.winterframework.context.weaving;

import com.winterframework.beans.factory.HierarchicalBeanFactory;
import com.winterframework.beans.factory.ListableBeanFactory;
import com.winterframework.context.ApplicationEventPublisher;
import com.winterframework.context.MessageSource;
import com.winterframework.core.env.EnvironmentCapable;
import com.winterframework.core.io.ResourcePatternResolver;

/**
 * @author huangwh@paraview.cn
 * @since 2021/07/19
 */
public interface ApplicationContext
        extends
            EnvironmentCapable,
            ListableBeanFactory,
            HierarchicalBeanFactory,
            MessageSource,
            ApplicationEventPublisher,
            ResourcePatternResolver {

}
