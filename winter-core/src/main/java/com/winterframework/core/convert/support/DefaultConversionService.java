/*
 * Copyright (C), 1987-2099, Winter All Rights Reserved.
 */
package com.winterframework.core.convert.support;

import com.winterframework.Nullable;
import com.winterframework.core.convert.ConversionService;

/**
 * @author huangwh@paraview.cn
 * @since 2021/07/26
 */
public class DefaultConversionService extends GenericConversionService {
    @Nullable
    private static volatile DefaultConversionService sharedInstance;

    public static ConversionService getSharedInstance() {
        DefaultConversionService cs = sharedInstance;
        if (cs != null) {
            return cs;
        }
        synchronized (DefaultConversionService.class) {
            cs = sharedInstance;
            if (cs == null) {
                cs = new DefaultConversionService();
                sharedInstance = cs;
            }
            return cs;
        }
    }
}