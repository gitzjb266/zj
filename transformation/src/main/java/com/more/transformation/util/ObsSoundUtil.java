package com.more.transformation.util;

import com.huawei.sis.bean.AuthInfo;
import com.huawei.sis.bean.SisConfig;
import com.huawei.sis.bean.SisConstant;
import com.huawei.sis.bean.base.AsrcLongSentence;
import com.huawei.sis.bean.request.AsrCustomLongRequest;
import com.huawei.sis.bean.response.AsrCustomLongResponse;
import com.huawei.sis.client.AsrCustomizationClient;
import com.huawei.sis.exception.SisException;
import com.huawei.sis.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 录音文件识别OBS
 * 错误码 : https://support.huaweicloud.com/api-sis/sis_03_0045.html
 * 源码：https://support.huaweicloud.com/sdkreference-sis/sis_05_0045.html
 * @author zhangjb
 */
@Slf4j
public class ObsSoundUtil {
    private static final int SLEEP_TIME = 500;
    private static final int MAX_POLLING_NUMS = 1000;

    /**
     *   todo 请正确填写音频格式和模型属性字符串
     *   1. 音频格式一定要相匹配.
     *      例如obs url是xx.wav, 则在录音文件识别格式是auto。
     *      例如音频是pcm格式，并且采样率为8k，则格式填写pcm8k16bit。
     *      如果返回audio_format is invalid 说明该文件格式不支持。
     *
     *   2. 音频采样率要与属性字符串的采样率要匹配。
     *      例如格式选择pcm16k16bit，属性字符串却选择chinese_8k_common, 则属于采样率不匹配
     *      例如wav本身是16k采样率，属性选择chinese_8k_common, 同样属于采样率不匹配
     *
     *   3. 用户可以通过使用热词，识别专业术语，增加语句识别准确率。
     */
    private String obsAudioFormat = "auto";   // 文件格式，如auto等
    private String obsProperty = "chinese_8k_common";      // 属性字符串，如chinese_8k_common等
    /**
     * 设置录音文件识别参数，所有参数均有默认值，不配置也可使用
     *
     * @param request 录音文件识别请求
     */
    private void setLongParameter(AsrCustomLongRequest request) {
        // 设置否是添加标点，yes 或no， 默认是no
        request.setAddPunc("yes");
        // 设置是否将语音中的数字转写为阿拉伯数字，yes或no，默认yes
        request.setDigitNorm("no");
        // 设置声道，MONO/LEFT_AGENT/RIGHT_AGENT, 默认是单声道MONO
        request.setChannel("MONO");
        // 设置是否需要分析，默认为false。当前仅支持8k采样率音频。当其设置为true时，话者分离、情绪检测，速度、声道才生效。
        request.setNeedAnalysis(true);
        // 设置是否需要话者分离，若是，则识别结果包含role，默认true
        request.setDirization(true);
        // 设置是否需要情绪检测，默认true。
        request.setEmotion(true);
        // 设置是否需要速度。默认true。
        request.setSpeed(true);
        // 设置回调地址，设置后音频转写结果将直接发送至回调地址。请务必保证地址可联通,不支持ip地址。
        // request.setCallbackUrl("");
        // 设置热词id，不使用则不用填写
        // request.setVocabularyId("");
    }

    /**
     * 定义config，所有参数可选，设置超时时间等。
     *
     * @return SisConfig
     */
    private SisConfig getConfig() {
        SisConfig config = new SisConfig();
        // 设置连接超时，默认10000ms
        config.setConnectionTimeout(SisConstant.DEFAULT_CONNECTION_TIMEOUT);
        // 设置读取超时，默认10000ms
        config.setReadTimeout(SisConstant.DEFAULT_READ_TIMEOUT);
        return config;
    }


    /**
     * 录音文件识别demo
     */
    public String obsSound(String obsUrl) {
        String msg = null;
        try {
            // 1. 初始化AsrCustomizationClient
            // 定义authInfo，根据ak，sk，region,projectId.
            AuthInfo authInfo = new AuthInfo(BaseParam.AK, BaseParam.SK, BaseParam.REGION, BaseParam.PROJECT_ID);
            // 设置config，主要与超时有关
            SisConfig config = getConfig();
            // 根据authInfo和config，构造AsrCustomizationClient
            AsrCustomizationClient asr = new AsrCustomizationClient(authInfo, config);

            // 2. 生成请求
            AsrCustomLongRequest request = new AsrCustomLongRequest(obsUrl, obsAudioFormat, obsProperty);
            // 设置请求参数，所有参数均为可选
            setLongParameter(request);

            // 3. 提交任务，获取jobId
            String jobId = asr.submitJob(request);

            // 4 轮询jobId，获取最终结果。
            int count = 0;
            int successFlag = 0;
            AsrCustomLongResponse response = null;
            while (count < MAX_POLLING_NUMS) {
                log.info("正在进行第" + count + "次尝试");
                response = asr.getAsrLongResponse(jobId);
                String status = response.getStatus();
                if (status.equals("FINISHED")) {
                    successFlag = 1;
                    break;
                } else if (status.equals("ERROR")) {
                    log.info("执行失败, 无法根据jobId获取结果");
                    msg = "执行失败, 无法根据jobId获取结果";
                }
                try {
                    Thread.sleep(SLEEP_TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                count++;
            }
            // 打印结果
            if (successFlag == 0) {
                log.info("已进行" + count + "次尝试，无法获取识别结果。 jobId为 " + jobId);
                msg = "已进行" + count + "次尝试，无法获取识别结果。 jobId为 " + jobId;
            }

            StringBuilder sb = new StringBuilder();
            if ("FINISHED".equals(response.getStatus())){
                log.info("OBS录音文件识别结果："+ JsonUtils.obj2Str(response, true));
                List<AsrcLongSentence> resultList = response.getSentenceList();
                for(AsrcLongSentence as:resultList){
                    sb.append(as.getResult().getText());
                }
            }
            msg = sb.toString();
            //msg = JsonUtils.obj2Str(response, true);
        } catch (SisException e) {
            e.printStackTrace();
            log.info("error_code:" + e.getErrorCode() + "\nerror_msg:" + e.getErrorMsg());
            msg = "error_code:" + e.getErrorCode() + "\nerror_msg:" + e.getErrorMsg();
        }finally{
            return msg;
        }
    }
}
