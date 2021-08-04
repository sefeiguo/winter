/*
 * Copyright (C), 1987-2099, Winter All Rights Reserved.
 */
package com.winterframework.context.support;

import java.io.IOException;

import com.winterframework.Nullable;
import com.winterframework.context.weaving.ApplicationContext;
import com.winterframework.core.io.Resource;
import com.winterframework.util.Assert;
import com.winterframework.util.ObjectUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * @author huangwh@paraview.cn
 * @since 2021/07/19
 */
@Slf4j
public class ClassPathXmlApplicationContext extends AbstractXmlApplicationContext {

    @Nullable
    private String[] configLocations;

    /**
     * 创建一个新的ClassPathXmlApplicationContext,加载定义从给定的XML文件,并自动刷新上下文。
     * 
     * @param configLocation
     */
    public ClassPathXmlApplicationContext(String configLocation) {
        this(new String[]{configLocation}, true, null);
    }

    public ClassPathXmlApplicationContext(String[] configLocations, boolean refresh, @Nullable ApplicationContext parent) {
        super(parent);
        setConfigLocations(configLocations);
        if (refresh) {
            log.info("{} start refresh method", ObjectUtils.identityToString(this));
            refresh();
        }
    }

    private void setConfigLocations(@Nullable String... locations) {
        if (locations != null) {
            Assert.noNullElements(locations, "Config locations must not be null");
            this.configLocations = new String[locations.length];
            for (int i = 0; i < locations.length; i++) {
                this.configLocations[i] = resolvePath(locations[i]).trim();
            }
        } else {
            this.configLocations = null;
        }
    }

    /**
     * 解析路径 替换${}
     * 
     * @param path
     * @return
     */
    protected String resolvePath(String path) {
        return getEnvironment().resolveRequiredPlaceholders(path);
    }

    @Override
    public Resource[] getResources(String locationPattern) throws IOException {
        return new Resource[0];
    }
}
