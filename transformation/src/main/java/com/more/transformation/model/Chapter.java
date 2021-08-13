package com.more.transformation.model;

import lombok.Data;

import java.util.List;

/**
 * 文件名：Chapter.java
 * 说明：章节
 * 作者：陈江海
 * 创建时间：2019-09-24
 * 版权所有：福建亿榕信息技术有限公司
 */
@Data
public class Chapter {

    private String content;//该章节完整内容
    private int startIndex;//在全文中的开始索引
    private int endIndex;//在全文中的结束索引
    private int level;//层级
    private String chapterName;//章节名
    private String simpleChapterName;//简要章节名
    private List<Chapter> childChapterList;

    public Chapter(String content, int startIndex, int endIndex, String chapterName, String simpleChapterName) {
        this.content = content;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.chapterName = chapterName;
        this.simpleChapterName = simpleChapterName;
    }
}
