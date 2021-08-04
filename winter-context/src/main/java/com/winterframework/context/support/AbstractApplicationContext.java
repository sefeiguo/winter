/*
 * Copyright (C), 1987-2099, Winter All Rights Reserved.
 */
package com.winterframework.context.support;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

import com.winterframework.Nullable;
import com.winterframework.beans.BeansException;
import com.winterframework.beans.factory.config.ConfigurableListableBeanFactory;
import com.winterframework.context.weaving.ApplicationContext;
import com.winterframework.context.weaving.ConfigurableApplicationContext;
import com.winterframework.core.env.ConfigurableEnvironment;
import com.winterframework.core.env.Environment;
import com.winterframework.core.env.StandardEnvironment;
import com.winterframework.core.io.DefaultResourceLoader;
import com.winterframework.core.io.PathMatchingResourcePatternResolver;
import com.winterframework.core.io.Resource;
import com.winterframework.core.io.ResourcePatternResolver;
import com.winterframework.core.metrics.ApplicationStartup;
import com.winterframework.core.metrics.StartupStep;
import com.winterframework.util.ObjectUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * 抽象的实现{@link com.winterframework.context.ApplicationContext}接口。
 * 没有授权的存储类型用于配置;简单的*实现共同语境的功能。 使用模板方法设计模式,*要求具体的子类实现抽象方法。
 * <p>
 * 纯BeanFactory相比,ApplicationContext应该是*检测特殊bean中定义其内部bean工厂:因此,该类自动注册
 * </p>
 * 
 * @author huangwh@paraview.cn
 * @since 2021/07/19
 */
@Slf4j
public abstract class AbstractApplicationContext extends DefaultResourceLoader implements ConfigurableApplicationContext {

    /**
     * Parent context.
     */
    @Nullable
    private ApplicationContext parent;

    /** ResourcePatternResolver used by this context. */
    private ResourcePatternResolver resourcePatternResolver;

    /** Application startup metrics. **/
    private ApplicationStartup applicationStartup = ApplicationStartup.DEFAULT;

    @Nullable
    private ConfigurableEnvironment environment;
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

    /**
     * 启动时间毫秒
     */
    private long startupDate;

    /** Synchronization monitor for the "refresh" and "destroy". */
    private final Object startupShutdownMonitor = new Object();

    public AbstractApplicationContext(ApplicationContext parent) {
        this();
        setParent(parent);
    }

    public AbstractApplicationContext() {
        this.resourcePatternResolver = getResourcePatternResolver();
    }

    protected ResourcePatternResolver getResourcePatternResolver() {
        return new PathMatchingResourcePatternResolver(this);
    }

    public void setParent(@Nullable ApplicationContext parent) {
        this.parent = parent;
        if (parent != null) {
            Environment parentEnvironment = parent.getEnvironment();
            if (parentEnvironment instanceof ConfigurableEnvironment) {
                getEnvironment().merge((ConfigurableEnvironment) parentEnvironment);
            }
        }
    }

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
    public ConfigurableEnvironment getEnvironment() {
        if (this.environment == null) {
            this.environment = createEnvironment();
        }
        return this.environment;
    }

    protected ConfigurableEnvironment createEnvironment() {
        return new StandardEnvironment();
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

    @Override
    public void refresh() {
        synchronized (this.startupShutdownMonitor) {
            StartupStep contextRefresh = this.applicationStartup.start("spring.context.refresh");

            // 准备启动上下文 refresh.
            prepareRefresh();

        }
    }

    protected void prepareRefresh() {
        // 初始化启动时间
        this.startupDate = System.currentTimeMillis();
        this.closed.set(false);
        this.active.set(true);

        if (log.isDebugEnabled()) {
            if (log.isTraceEnabled()) {
                log.trace("Refreshing " + this);
            } else {
                log.debug("Refreshing " + getDisplayName());
            }
        }

        // 初始化配置文件的容器环境.
        initPropertySources();

        // 验证所需的所有属性标记为可解析
        getEnvironment().validateRequiredProperties();
    }

    /**
     * 用实际的实例替换任何存在属性资源
     */
    protected void initPropertySources() {
        // 提供给之类实现 默认没有实现
    }

}
