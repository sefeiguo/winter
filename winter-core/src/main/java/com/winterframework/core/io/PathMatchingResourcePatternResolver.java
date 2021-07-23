/*
 * Copyright (C), 1987-2099, Winter All Rights Reserved.
 */
package com.winterframework.core.io;

import java.io.IOException;

import com.winterframework.util.Assert;

/**
 * @author huangwh@paraview.cn
 * @since 2021/07/21
 */
public class PathMatchingResourcePatternResolver implements ResourcePatternResolver {

    private final ResourceLoader resourceLoader;

    public PathMatchingResourcePatternResolver(ResourceLoader resourceLoader) {
        Assert.notNull(resourceLoader, "ResourceLoader must not be null");
        this.resourceLoader = resourceLoader;
    }

    @Override
    public Resource getResource(String location) {
        return null;
    }

    @Override
    public Resource[] getResources(String locationPattern) throws IOException {
        return new Resource[0];
    }
}
