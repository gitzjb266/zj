package com.more.transformation.util;

import lombok.Data;

/**
 * 文件名：MatchResult.java
 * 说明：匹配结果
 * 作者：陈江海
 * 创建时间：2019-09-24
 * 版权所有：福建亿榕信息技术有限公司
 */
@Data
public class MatchResult {
    private String value;
    private int start;
    private int end;

    public MatchResult(String value, int start, int end) {
        this.value = value;
        this.start = start;
        this.end = end;
    }
}
