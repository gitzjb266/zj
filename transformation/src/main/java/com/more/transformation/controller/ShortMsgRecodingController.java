package com.more.transformation.controller;

import com.alibaba.fastjson.JSONPath;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;

import com.more.transformation.constants.CommonConst;
import com.more.transformation.entity.TDataserviceConfig;
import com.more.transformation.service.TDataserviceConfigService;
import com.more.transformation.util.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;

/**
 * 短语音识别
 * @author zhangjb
 */
@Slf4j
@RestController
@Api("语音短消息识别")
@RequestMapping("sisShort")
public class ShortMsgRecodingController extends BaseParam {

    @Autowired
    private TDataserviceConfigService dataservice;

    @PostMapping("shortMsgRecording")
    @ApiOperation(value="智能语音识别",notes="语音短消息识别")
    public R upload(MultipartFile file){
        ResultBean rb = CheckUpload.getInstance().beforeCheck(file);
        if(!rb.resultFlag){
            return R.failed(rb.getMsg());
        }
        QueryWrapper<TDataserviceConfig> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("service_name", CommonConst.SHORT_MSG);
        String storageType = dataservice.getOne(queryWrapper).getType();

        String filePath = rb.getResultMap().get("filePath");
        String originalFilename = rb.getResultMap().get("originalFilename");
        String msg = null;
        switch (StorageType.getEnumByKey(storageType)){
            case OBS:
                msg = ObsShortUtil.shortObs(filePath,originalFilename);
                break;
            case OSS:
                msg = OssShortUtil.shortOss(filePath);
               // msg = shortOss(filePath, FORMAT, 16000, true, true, false);
                break;
            default:
                msg = "不支持的存储类型";
                break;
        }
        return R.ok(msg);
    }

    @Deprecated
    @ApiOperation(value="智能语音识别",notes="录音文件识别极速版")
    public static String shortOss(String fileName, String format, int sampleRate,
                        boolean enablePunctuationPrediction,
                        boolean enableInverseTextNormalization,
                        boolean enableVoiceDetection) {

        /**
         *  录音文件识别极速版 :
         * https://help.aliyun.com/document_detail/187161.html?spm=a2c4g.11186623.6.609.14d27219RSbLKG
         * 设置HTTP RESTful POST请求：
         * 1.使用HTTP协议。
         * 2.语音识别服务域名：nls-gateway.cn-shanghai.aliyuncs.com。
         * 3.语音识别接口请求路径：/stream/v1/asr。
         * 4.设置必选请求参数：appkey、format、sample_rate。
         * 5.设置可选请求参数：enable_punctuation_prediction、enable_inverse_text_normalization、enable_voice_detection。
         */
        String url = "http://nls-gateway.cn-shanghai.aliyuncs.com/stream/v1/asr";
        String request = url;
        request = request + "?appkey=" + APP_KEY;
        request = request + "&format=" + format;
        request = request + "&sample_rate=" + sampleRate;
        if (enablePunctuationPrediction) {
            request = request + "&enable_punctuation_prediction=" + true;
        }
        if (enableInverseTextNormalization) {
            request = request + "&enable_inverse_text_normalization=" + true;
        }
        if (enableVoiceDetection) {
            request = request + "&enable_voice_detection=" + true;
        }

        /**
         * 设置HTTP头部字段：
         * 1.鉴权参数。
         * 2.Content-Type：application/octet-stream。
         */
        String token = TokenUtils.getOssToken(ACCESS_KEY_ID,ACCESS_KEY_SECRET);
        HashMap<String, String> headers = new HashMap<>();
        headers.put("X-NLS-Token", token);
        headers.put("Content-Type", "application/octet-stream");

        /**
         * 发送HTTP POST请求，返回服务端的响应。
         */
        String response = HttpUtil.sendPostFile(request, headers, fileName);

        if (response != null) {
            log.info("Response: ",response);
            String result = JSONPath.read(response, "result").toString();
            log.info("识别结果：" + result);
            return result;
        }
        else {
            log.info("识别失败!");
            return "识别失败!";
        }
    }
}