package com.more.transformation.util;


import com.alibaba.fastjson.JSONObject;

import com.more.transformation.constants.StructureRegContants;
import com.more.transformation.model.Chapter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件名：StructureExtractClient.java
 * 说明：抽取篇章结构客户端
 * 作者：陈江海
 * 创建时间：2019-09-24
 * 版权所有：福建亿榕信息技术有限公司
 */
@Slf4j
public class StructureExtractClient {

    private final static char[] ILLEGAL_ARR = new char[]{(int) 9, (int) 10};

    /**
     * 抽取文档结构
     * 有如下几种情况
     * 模式1. 第一章 -> 第一条 -> （一）
     * 模式2. 一、 ->（一） -> 1.    。
     *
     * @param file:txt文件
     * @return 文档结构
     * @throws IOException
     */
    public static JSONObject extract(File file) throws IOException {
        return extract(file, null);
    }

    /**
     * 指定正则抽取篇章结构，最多支持三层结构
     *
     * @param file
     * @param regList
     * @return
     * @throws IOException
     */
    public static JSONObject extract(File file, List<String> regList) throws IOException {
        String content = FileUtils.readFileToString(file, Charset.forName("utf-8"));
        return extract(content, regList);
    }

    public static JSONObject extract(String content) {
        return extract(content, null);
    }

    public static JSONObject extract(String content, List<String> regList) {
        List<Chapter> chapterList = extractFirstLevelChapter(content, regList);
        JSONObject obj = new JSONObject();
        obj.put("chapters", chapterList);
        return obj;
    }

    private static List<Chapter> extractFirstLevelChapter(String content) {
        return extractFirstLevelChapter(content, null);
    }


    /**
     * 抽取第一层级的章节信息
     * 如：第一章/第二章
     * 特点：以换行符号结束
     * \s：匹配任何空白字符，包括空格、制表符、换页符等。与 [ \f\n\r\t\v] 等效。
     * 字边界：\b
     *
     * @param content
     * @return
     * @throws IOException
     */
    private static List<Chapter> extractFirstLevelChapter(String content, List<String> regList) {
        int level = 1;
        List<Chapter> chapterList = new ArrayList<>();
        if (null != regList && regList.size() >= level && StringUtils.isNotEmpty(regList.get(level - 1))) {
            chapterList.addAll(extractChapterByList(content, regList.get(level - 1), level));
        } else {
            chapterList.addAll(extractChapterWithRegs(content, StructureRegContants.FIRST_LEVLE_REG_ARR, level));
        }
        for (Chapter chapter : chapterList) {
            String newContent = chapter.getContent().substring(chapter.getChapterName().length() - 1);//截取了标题后再往下搜索
            List<Chapter> childList = extractSecondLevelChapter(newContent, regList);
            chapter.setChildChapterList(childList);
        }
        return chapterList;
    }

    private static List<Chapter> extractChapterWithRegs(String content, String[] regArr, int level) {
        List<Chapter> chapterList = new ArrayList<>();
        int startIndex = Integer.MAX_VALUE;
        //选择最小匹配到的开始索引
        for (String reg : regArr) {
            List<Chapter> tmpList = extractChapterByList(content, reg, level);
            if (CommonUtil.isEmpty(tmpList)) {
                continue;
            }
            if (startIndex > tmpList.get(0).getStartIndex()) {
                startIndex = tmpList.get(0).getStartIndex();
                chapterList.clear();
                chapterList.addAll(tmpList);
            }
        }
        return chapterList;
    }


    /**
     * 抽取第二层级的章节信息
     * 如：第一条/第二条
     * <p>
     * 如果第二层级同时有多个正则有结果，则以匹配到到第一条数据startIndex最小者为主
     *
     * @param content
     * @return
     * @throws IOException
     */
    private static List<Chapter> extractSecondLevelChapter(String content, List<String> regList) {

        int level = 2;
        List<Chapter> chapterList = new ArrayList<>();
        if (null != regList && regList.size() >= level && StringUtils.isNotEmpty(regList.get(level - 1))) {
            chapterList.addAll(extractChapterByList(content, regList.get(level - 1), level));
        } else {
            chapterList.addAll(extractChapterWithRegs(content, StructureRegContants.SECOND_LEVLE_REG_ARR, level));
        }

        for (Chapter chapter : chapterList) {
            String newContent = chapter.getContent().substring(chapter.getChapterName().length() - 1);//截取了标题后再往下搜索
            List<Chapter> childList = extractThirdLevelChapter(newContent, regList);
            chapter.setChildChapterList(childList);
        }
        return chapterList;
    }


    private static List<Chapter> extractThirdLevelChapter(String content, List<String> regList) {
        int level = 3;
        List<Chapter> chapterList = new ArrayList<>();
        if (null != regList && regList.size() >= level && StringUtils.isNotEmpty(regList.get(level - 1))) {
            chapterList.addAll(extractChapterByList(content, regList.get(level - 1), level));
        } else {
            chapterList.addAll(extractChapterWithRegs(content, StructureRegContants.THIRD_LEVLE_REG_ARR, level));
        }

        for (Chapter chapter : chapterList) {
            String newContent = chapter.getContent().substring(chapter.getChapterName().length() - 1);//截取了标题后再往下搜索
            List<Chapter> childList = extractFourthevelChapter(newContent, regList);
            chapter.setChildChapterList(childList);
        }
        return chapterList;
    }

    private static List<Chapter> extractFourthevelChapter(String content, List<String> regList) {
        int level = 4;
        List<Chapter> chapterList = new ArrayList<>();
        if (null != regList && regList.size() >= level && StringUtils.isNotEmpty(regList.get(level - 1))) {
            chapterList.addAll(extractChapterByList(content, regList.get(level - 1), level));
        } else {
            chapterList.addAll(extractChapterWithRegs(content, StructureRegContants.FOURTH_LEVLE_REG_ARR, level));
        }
        return chapterList;
    }

    /**
     * 过滤章节列表
     * 1.前面一个字符必须是回车或者换行
     * 2.内容不能包含ascii码表的 9或者10 （水平定位符或换行键）
     *
     * @param chapterList
     * @return
     */
    private static List<Chapter> filter(List<Chapter> chapterList, String content, int level) {
        List<Chapter> newList = new ArrayList<>();
        boolean hasRemove = true;
        for (Chapter chapter : chapterList) {
            if (chapter.getChapterName().length() >= 50) {
                hasRemove = true;
                continue;
            }

            // 如果是一级目录，校验内容长度
            if (level == 1) {
                //截取除去标题后的长度，判断是否有有效字符
                String truelyContent = chapter.getContent().substring(chapter.getChapterName().length());
                if (!validFirstChapterContent(truelyContent)) {
                    log.info("章节没有有效内容,层级：【" + chapter.getLevel() + "】内容：" + chapter.getContent());
                    hasRemove = true;
                    continue;
                }
            }

            if (chapter.getStartIndex() > 0) {
                char pre = content.charAt(chapter.getStartIndex() - 1);
                if ((int) pre != 10 && (int) pre != 32) {
                    log.info("章节不符合前置条件,层级：【" + chapter.getLevel() + "】内容：" + chapter.getContent());
                    hasRemove = true;
                    continue;
                }
                if (containIllegalChar(chapter.getChapterName().substring(1, chapter.getChapterName().length() - 1))) {
                    log.info("章节包含非法字符，层级：【" + chapter.getLevel() + "】内容：" + chapter.getChapterName());
                    hasRemove = true;
                    continue;
                }
                //章节没有实际内容的，不抽取

                newList.add(chapter);
            }
        }
        //重新设置内容(错误的匹配会导致内容错误)
        if (hasRemove) {
            for (int i = 0; i < newList.size(); i++) {
                Chapter cur = newList.get(i);
                String chapterContent = "";
                if (i == newList.size() - 1) {
                    chapterContent = content.substring(cur.getStartIndex());
                } else {
                    Chapter next = newList.get(i + 1);
                    chapterContent = content.substring(cur.getStartIndex(), next.getStartIndex());
                }
                cur.setContent(chapterContent);
            }
        }
        return newList;
    }

    //中文正则
    private static String VALID_CHINESE_REG = "[\\u4e00-\\u9fa5]{1,}";

    /**
     * 校验level 1章节的内容合法性
     *
     * @param content
     * @return
     */
    private static boolean validFirstChapterContent(String content) {
        content = content.replaceAll("\\n", "");
        content = content.replaceAll("\\t", "");
        if (content.length() == 0) {
            return false;
        }
        if (content.length() > 30) {
            return true;
        }
        List<MatchResult> matchList = RegUtil.reg(VALID_CHINESE_REG, content);
        return CommonUtil.isNotEmpty(matchList);
    }

    private static boolean containIllegalChar(String content) {
        boolean ret = false;
        for (char c : ILLEGAL_ARR) {
            if (content.contains(c + "")) {
                ret = true;
                break;
            }
        }
        return ret;
    }

    /**
     * 通过正则抽取章节列表
     *
     * @param content
     * @param reg
     * @return
     * @throws IOException
     */
    public static List<Chapter> extractChapterByList(String content, String reg, int level) {
        List<MatchResult> matchList = RegUtil.reg(reg, content);
        List<Chapter> chapterList = new ArrayList<>();
        for (int i = 0; i < matchList.size(); i++) {
            MatchResult cur = matchList.get(i);
            String chapterContent = "";
            if (i == matchList.size() - 1) {
                chapterContent = content.substring(cur.getStart());
            } else {
                MatchResult next = matchList.get(i + 1);
                chapterContent = content.substring(cur.getStart(), next.getStart());
            }
            Chapter chapter = new Chapter(chapterContent, cur.getStart(), cur.getEnd(), cur.getValue(), cur.getValue());
            chapter.setLevel(level);
            chapterList.add(chapter);
        }
        return filter(chapterList, content, level);
    }

    public static String stringToAscii(String value) {
        StringBuffer sbu = new StringBuffer();
        char[] chars = value.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (i != chars.length - 1) {
                sbu.append((int) chars[i]).append(",");
            } else {
                sbu.append((int) chars[i]);
            }
        }
        return sbu.toString();
    }

}
