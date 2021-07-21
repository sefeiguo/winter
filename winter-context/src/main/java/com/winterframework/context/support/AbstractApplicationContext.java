/*
 * Copyright (C), 2008-2021, Paraview All Rights Reserved.
 */
package com.winterframework.context.support;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

import com.winterframework.beans.BeansException;
import com.winterframework.beans.factory.config.ConfigurableListableBeanFactory;
import com.winterframework.context.weaving.ConfigurableApplicationContext;
import com.winterframework.core.env.Environment;
import com.winterframework.core.io.DefaultResourceLoader;
import com.winterframework.core.io.Resource;
import com.winterframework.util.ObjectUtils;

/**
 * 抽象的实现{@link org.winterframework.context.ApplicationContext}接口。
 * 没有授权的存储类型用于配置;简单的*实现共同语境的功能。 使用模板方法设计模式,*要求具体的子类实现抽象方法。
 * <p>
 * 纯BeanFactory相比,ApplicationContext应该是*检测特殊bean中定义其内部bean工厂:因此,该类自动注册
 * </p>
 * 
 * @author huangwh@paraview.cn
 * @since 2021/07/19
 */
public abstract class AbstractApplicationContext extends DefaultResourceLoader implements ConfigurableApplicationContext {

    /**
     * Flag 表明这种情况下是否当前活动。
     */
    private final AtomicBoolean active = new AtomicBoolean();

    /**
     * Flag 表明这种情况下是否已经被关闭了.
     */
    private final AtomicBoolean closed = new AtomicBoolean();

    /** Display name. */
    private String displayName = ObjectUtils.identityToString(this);

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void isRunning() {

    }

    @Override
    public void publishEvent(Object event) {

    }

    @Override
    public Environment getEnvironment() {
        return null;
    }

    @Override
    public Resource[] getResources(String locationPattern) throws IOException {
        return new Resource[0];
    }

    @Override
    public void close() throws IOException {

    }

    @Override
    public <T> T getBean(Class<T> requiredType) throws BeansException {
        assertBeanFactoryActive();
        return getBeanFactory().getBean(requiredType);
    }

    protected abstract ConfigurableListableBeanFactory getBeanFactory();

    protected void assertBeanFactoryActive() {
        if (!this.active.get()) {
            if (this.closed.get()) {
                throw new IllegalStateException(getDisplayName() + " has been closed already");
            } else {
                throw new IllegalStateException(getDisplayName() + " has not been refreshed yet");
            }
        }
    }

    protected String getDisplayName() {
        return this.displayName;
    }

}
