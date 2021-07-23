/*
 * Copyright (C), 1987-2099, Winter All Rights Reserved.
 */
package com.winterframework.core.io;

import java.io.IOException;

/**
 * @author huangwh@paraview.cn
 * @since 2021/07/12
 */
public interface ResourcePatternResolver extends ResourceLoader {

    /**
     * @param locationPattern
     * @return
     * @throws IOException
     */
    Resource[] getResources(String locationPattern) throws IOException;
}
