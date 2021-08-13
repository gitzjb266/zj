package com.more.adapter.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lzy
 * @date 2021/7/6 17:38
 * 转换策略
 */
public class CommonConst {
    // 营业执照
    public static final String BIZ_LICENSE = "BIZ_LICENSE";
    // 身份证
    public static final String ID_CARD = "ID_CARD";
    // 银行卡
    public static final String BANK_CARD = "BANK_CARD";
    // 火车票
    public static final String TRAIN_TIKET = "TRAIN_TIKET";
    // 通用文字
    public static final String GENERAL_CHARACTER  = "GENERAL_CHARACTER";
    // 增值税发票
    public static final String VAT_INVOICE = "VAT_INVOICE";

    // 阿里云
    public static final String ALI = "1";
    // 华为云
    public static final String HW = "2";
    // 阿里身份证人脸面
    public static final String FACE = "face";
    // 华为身份证人脸面
    public static final String FRONT = "front";
    // 身份证国徽面
    public static final String BACK = "back";

    //语音短消息识别
    public static final String SHORT_MSG = "short_msg";

    //录音文件提取
    public static final String SOUND_MSG_PICKUP = "sound_msg_pickup";

    //录音文件识别(两个功能一样但是要区分页面)
    public static final String SOUND_MSG = "sound_msg";


    public static Map<String,String> serviceMap = new HashMap<String,String>() {{
        put(BIZ_LICENSE, "营业执照");
        put(ID_CARD, "身份证");
        put(BANK_CARD, "银行卡");
        put(TRAIN_TIKET, "火车票");
        put(GENERAL_CHARACTER, "通用文字");
        put(VAT_INVOICE, "增值税发票");
        put(SHORT_MSG, "语音短消息识别");
        put(SOUND_MSG_PICKUP, "录音文件提取");
        put(SOUND_MSG, "录音文件识别");
    }};
}
