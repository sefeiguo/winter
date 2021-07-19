/*
 * Copyright (C), 2008-2021, Paraview All Rights Reserved.
 */
package com.winterframework.core.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author huangwh@paraview.cn
 * @since 2021/07/12
 */
public interface InputStreamSource {

    /**
     * Return an {@link InputStream} for the content of an underlying resource.
     *
     * @return
     * @throws IOException
     */
    InputStream getInputStream() throws IOException;
}
