package com.more.transformation.constants;

/**
 * 文件名：StructureRegContants.java
 * 说明：篇章结构抽取正则常量
 * (?!.*(\t)) 表示字符串中不能包含制表符
 * 作者：陈江海
 * 创建时间：2019-09-26
 * 版权所有：福建亿榕信息技术有限公司
 */
public class StructureRegContants {
    public final static String FIRST_LEVEL_REG1 = "第[一二三四五六七八九十百零]{1,5}章\\b{0,10}.{2,20}[\\n]";//第一章
    public final static String FIRST_LEVEL_REG2 = "[一二三四五六七八九十百零]{1,5}、\\b{0,10}.{2,20}[\\n]";//一、
    public final static String FIRST_LEVEL_REG4 = "第[0-9]{1,5}章\\b{0,10}.{2,20}[\\s\\t\\n]";//第1章
    //含义：数字开头后跟 去除某些特殊字符的其他字符 最后为换行符号。例如：1.舆情监测\n 或者 1. 舆情监测\n
    public final static String FIRST_LEVEL_REG5 = "[0-9]{1,3}[\\.．]{0,1}\\b{1,3}[^0-9\\)\\n）\\(\\:：；。]{2,20}[^\\:\\n。][\\n]";

    public final static String[] FIRST_LEVLE_REG_ARR = new String[]{FIRST_LEVEL_REG1, FIRST_LEVEL_REG2, FIRST_LEVEL_REG4, FIRST_LEVEL_REG5};


    public final static String SECOND_LEVEL_REG1 = "[\\(（][一二三四五六七八九十百零]{1,5}[\\)）][0-9a-z\\u4e00-\\u9fa5]{2,20}[\\n]";//（一）
    //例如：2.1 综合分析\n (注意，中间不能出现 \n ()等字符 ) 结尾只匹配一个\n
    public final static String SECOND_LEVEL_REG3 = "[0-9]{1,3}[\\.][0-9]{1,2}[\\.]{0,1}\\b{1,10}[^0-9\\)\\n）\\(\\:：；。]{2,30}[^\\:\\n。][\\n]";


    public final static String[] SECOND_LEVLE_REG_ARR = new String[]{SECOND_LEVEL_REG1, SECOND_LEVEL_REG3
    };


    //4.2.2 简报模板操作说明\n (注意，中间不能出现 \n ()等字符 )
    public final static String THIRD_LEVEL_REG1 = "[0-9]{1,3}[\\.][0-9]{1,3}[\\.][0-9]{1,3}[\\.]{0,1}\\b{0,10}[^0-9\\)\\n）\\(\\:：；。]{2,30}[^\\:\\n。][\\n]";
    //1.竣工图编制不准确
    public final static String THIRD_LEVEL_REG2 = "[0-9]{1,2}[\\.]\\b{1,2}[^0-9\\)\\n）\\(\\:：；。]{2,20}[^\\:\\n。][\\n]";
    public final static String[] THIRD_LEVLE_REG_ARR = new String[]{THIRD_LEVEL_REG1, THIRD_LEVEL_REG2};


    public final static String[] FOURTH_LEVLE_REG_ARR = new String[]{};
}
