package com.more.transformation.util;


import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * 超类 配置初始化数据
 *  @author zhangjb
 */
public class BaseParam {


    public static String PARENT_PATH = "";

    public static String APP_KEY="";
    public static String ACCESS_KEY_ID = "";
    public static String ACCESS_KEY_SECRET = "";

    /**
     * OSS 桶名称
     */
    public static String OSS_VT = "";

    /**
     * OBS 桶名称
     */
    public static String OBS_VT = "";
    public static String AK = "";
    public static String SK = "";
    @PostConstruct
    private void init(){
        //----------------------------华为配置----------------------------------
        AK = "fMNneR3AVLKBtViG";
        SK = "fMNneR3AVLKBtViG";

//        ACCESS_KEY_ID = paramFeignService.findParams("aliyun.accessKeyId.temp");
//        ACCESS_KEY_SECRET = paramFeignService.findParams("aliyun.accessKeySecret.temp");
//
//
//        PARENT_PATH =  paramFeignService.findParams("parent_path");
//
//        APP_KEY =  paramFeignService.findParams("aliyun.appkey");
//
//        OBS_VT =  paramFeignService.findParams("obs.vt");
//
//        OSS_VT =  paramFeignService.findParams("oss.vt");

    }

    public static final String FORMAT="pcm";
    /**
     * 填写Bucket所在地域对应的Endpoint。以华东1（杭州）为例，Endpoint填写为https://oss-cn-hangzhou.aliyuncs.com。
     */
    public static final String END_POINT = "https://oss-cn-hangzhou.aliyuncs.com";
    public static String REGION = "cn-north-4";    // region, such as ap-southeast-3
    public static final String OBS_END_POINT = "https://obs."+REGION+".myhuaweicloud.com";
    public static String PROJECT_ID = "0cc9f964e980f2422fe3c017f0920ecf"; // project_id, refer to https://support.huaweicloud.com/intl/en-us/api-sis/sis_03_0008.html
    public static String AUDIO_FORMAT_WAV = "wav";
    public static String AUDIO_FORMAT_MP3 = "mp3";
    public static String PROPERTY_16 = "chinese_16k_common";
    public static String PROPERTY_8 = "chinese_8k_common";


}
