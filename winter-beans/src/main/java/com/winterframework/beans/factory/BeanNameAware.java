/*
 * Copyright (C), 1987-2099, Winter All Rights Reserved.
 */
package com.winterframework.beans.factory;

/**
 *
 * 接口由bean实现,要意识到自己的在一个bean工厂bean的名字。
 * 请注意,这不是通常推荐对象取决于它的bean的名字,这代表了一种潜在的脆弱的依赖外部配置,以及一个可能不必要的依赖Spring API。
 * 
 * @author huangwh@paraview.cn
 * @since 2021/07/19
 */
public interface BeanNameAware extends Aware {

    /**
     * 设置bean的名称在这个bean创建bean工厂。
     * <p>
     * 调用后正常的bean属性但在人口init调如{@link InitializingBean # afterPropertiesSet ()}或一个定制的init方法。
     * </p>
     * 
     * @param name
     */
    void setBeanName(String name);
}
