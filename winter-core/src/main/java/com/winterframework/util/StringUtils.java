/*
 * Copyright (C), 1987-2099, Winter All Rights Reserved.
 */
package com.winterframework.util;

import java.util.Collection;
import java.util.Iterator;

import com.winterframework.Nullable;

/**
 * @author huangwh@paraview.cn
 * @since 2021/07/25
 */
public abstract class StringUtils {

    /**
     * 子字符串是否在父字符串中
     * 
     * @param str
     *            父字符串
     * @param index
     *            起始位置
     * @param substring
     *            子字符串
     * @return
     */
    public static boolean substringMatch(CharSequence str, int index, String substring) {
        if (index + substring.length() > str.length()) {
            return false;
        }
        for (int i = 0; i < substring.length(); i++) {
            if (str.charAt(index + i) != substring.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    public static String collectionToCommaDelimitedString(@Nullable Collection<?> coll) {
        return collectionToDelimitedString(coll, ",");
    }

    public static String collectionToDelimitedString(Collection<?> coll, String delim) {
        return collectionToDelimitedString(coll, delim, "", "");
    }

    public static String collectionToDelimitedString(Collection<?> coll, String delim, String prefix, String suffix) {
        if (CollectionUtils.isEmpty(coll)) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        final Iterator<?> iterator = coll.iterator();
        while (iterator.hasNext()) {
            sb.append(prefix).append(iterator.next()).append(suffix);
            if (iterator.hasNext()) {
                sb.append(delim);
            }
        }
        return sb.toString();
    }
}