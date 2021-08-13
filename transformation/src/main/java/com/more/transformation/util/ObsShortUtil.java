package com.more.transformation.util;

import com.huawei.sis.bean.AuthInfo;
import com.huawei.sis.bean.SisConfig;
import com.huawei.sis.bean.SisConstant;
import com.huawei.sis.bean.request.AsrCustomShortRequest;
import com.huawei.sis.bean.response.AsrCustomShortResponse;
import com.huawei.sis.client.AsrCustomizationClient;
import com.huawei.sis.exception.SisException;
import com.huawei.sis.util.IOUtils;
import com.huawei.sis.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * obs 短语音识别
 * @author zhangjb
 */
@Slf4j
public class ObsShortUtil {
    /**
     * set the parameter, all the parameters are optional
     *
     * @param request sentence transcription request
     */
    private static void setShortParameter(AsrCustomShortRequest request) {
        request.setAddPunc("yes");
    }

    /**
     * set config, all the parameters are optional
     *
     * @return SisConfig
     */
    private static SisConfig getConfig() {
        SisConfig config = new SisConfig();
        config.setConnectionTimeout(SisConstant.DEFAULT_CONNECTION_TIMEOUT);
        return config;
    }

    /**
     * sentence transcription demo。
     */
    public static String shortObs(String path,String originalFilename) {
        String msg = null;
        String data = null;
        String audioFormat = null;
        AsrCustomizationClient asr = null;
        try {
            SisConfig config = getConfig();
            asr = new AsrCustomizationClient(new AuthInfo(BaseParam.AK, BaseParam.SK, BaseParam.REGION, BaseParam.PROJECT_ID), config);
            data = IOUtils.getEncodeDataByPath(path);
            if(originalFilename.endsWith(BaseParam.AUDIO_FORMAT_WAV)){
                audioFormat = BaseParam.AUDIO_FORMAT_WAV;
            }else{
                audioFormat = BaseParam.AUDIO_FORMAT_MP3;
            }

            AsrCustomShortRequest request = new AsrCustomShortRequest(data, audioFormat, BaseParam.PROPERTY_16);
            setShortParameter(request);
            AsrCustomShortResponse response = asr.getAsrShortResponse(request);
            log.info("OBS一句话语音识别结果："+ JsonUtils.obj2Str(response, true));
            msg = response.getResult().getText();
        } catch (SisException e) {
            e.printStackTrace();
            msg = "error_code:" + e.getErrorCode() + "error_msg:" + e.getErrorMsg();
            log.error("error_code:" + e.getErrorCode() + "\nerror_msg:" + e.getErrorMsg());

            if("SIS.0301".equals(e.getErrorCode())){
                log.info("一句话语音识别格式为SIS.0301 系统自动采用"+BaseParam.PROPERTY_8+",进行识别");
                AsrCustomShortRequest request = new AsrCustomShortRequest(data, audioFormat, BaseParam.PROPERTY_8);
                setShortParameter(request);
                try{
                    AsrCustomShortResponse response = asr.getAsrShortResponse(request);
                    log.info("OBS一句话语音识别结果："+ JsonUtils.obj2Str(response, true));
                    msg = response.getResult().getText();
                } catch (SisException e1) {
                    e1.printStackTrace();
                    msg = "error_code:" + e1.getErrorCode() + "error_msg:" + e1.getErrorMsg();
                    log.info("error_code:" + e1.getErrorCode() + "\nerror_msg:" + e1.getErrorMsg());
                }
            }
        }
        return msg;
    }
}
