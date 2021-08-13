package com.more.transformation.util;

import java.util.List;

/**
 * 文件名：CommonUtil.java
 * 说明：
 * 作者：陈江海
 * 创建时间：2019-11-18
 * 版权所有：福建亿榕信息技术有限公司
 */
public class CommonUtil {
    public static boolean isEmpty(List list) {
        return null == list || list.size() == 0;
    }

    public static boolean isNotEmpty(List list) {
        return !isEmpty(list);
    }
}
