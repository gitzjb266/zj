package com.more.transformation.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 文件名：RegUtil.java
 * 说明：正则帮助类
 * 作者：陈江海
 * 创建时间：2019-09-24
 * 版权所有：福建亿榕信息技术有限公司
 */
public class RegUtil {
    public static void main(String[] args) {
//        String text = "This is the text which is to be searched for occurrences of the word 'is'.";
//        String patternString = "is";
//        reg(patternString, text).forEach(f -> System.out.println(f));


    }

    /**
     * 匹配正则，并返回匹配结果
     * @param regStr
     * @param text
     * @return
     */
    public static List<MatchResult> reg(String regStr, String text) {
        Pattern pattern = Pattern.compile(regStr);
        Matcher matcher = pattern.matcher(text);
        List<MatchResult> resultList = new ArrayList<>();
        while (matcher.find()) {
            MatchResult result = new MatchResult(matcher.group(), matcher.start(), matcher.end());
            resultList.add(result);
        }
        return resultList;
    }


}
