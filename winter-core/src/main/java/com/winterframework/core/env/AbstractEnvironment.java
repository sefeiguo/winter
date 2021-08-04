/*
 * Copyright (C), 1987-2099, Winter All Rights Reserved.
 */
package com.winterframework.core.env;

/**
 * @author huangwh@paraview.cn
 * @since 2021/07/22
 */
public abstract class AbstractEnvironment implements ConfigurableEnvironment {

    private final MutablePropertySources propertySources;

    private final ConfigurablePropertyResolver propertyResolver;

    public AbstractEnvironment() {
        this(new MutablePropertySources());
    }

    public AbstractEnvironment(MutablePropertySources propertySources) {
        this.propertySources = propertySources;
        this.propertyResolver = createPropertyResolver(propertySources);
        customizePropertySources(propertySources);
    }

    protected void customizePropertySources(MutablePropertySources propertySources) {

    }

    protected ConfigurablePropertyResolver createPropertyResolver(MutablePropertySources propertySources) {
        return new PropertySourcesPropertyResolver(propertySources);
    }

    @Override
    public void merge(ConfigurableEnvironment parent) {

    }

    @Override
    public String resolvePlaceholders(String text) {
        return null;
    }

    @Override
    public String resolveRequiredPlaceholders(String text) {
        return this.propertyResolver.resolveRequiredPlaceholders(text);
    }
}
