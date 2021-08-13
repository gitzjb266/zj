package com.more.transformation.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
//import com.aliyun.oss.OSS;
//import com.aliyun.oss.OSSClientBuilder;
import com.more.transformation.service.SoundRecordingService;
import com.more.transformation.util.*;
import com.obs.services.ObsClient;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

@Service
@Slf4j
public class SoundRecordingServiceImpl implements SoundRecordingService {
    @Override
    public String saveFile(String storageType, String originalFilename, String filePath) throws FileNotFoundException {
        {
            String msg = null;
            switch (StorageType.getEnumByKey(storageType)){
                case OSS:
               //     OSS ossClient = new OSSClientBuilder().build(BaseParam.END_POINT, BaseParam.ACCESS_KEY_ID, BaseParam.ACCESS_KEY_SECRET);
//                    InputStream inputStream = new FileInputStream(filePath);
//                    ossClient.putObject(BaseParam.OSS_VT, BaseParam.PARENT_PATH+originalFilename, inputStream);
//                    ossClient.shutdown();
//
//                    String fileLink = BaseParam.END_POINT.replace("oss-cn-hangzhou",BaseParam.OSS_VT+".oss-cn-hangzhou")+"/"+BaseParam.PARENT_PATH+originalFilename;
//                    log.info("阿里ossUrl:{}",fileLink);
//                    OssSoundUtil osu = new OssSoundUtil();
//                    String taskId = osu.submitFileTransRequest(BaseParam.APP_KEY, fileLink);
//                    if (taskId != null) {
//                        log.info("录音文件识别请求成功，task_id: " + taskId);
//                    }else {
//                        log.info("录音文件识别请求失败！");
//                        msg = "录音文件识别请求失败！";
//                    }
//                    // 第二步：根据任务ID轮询识别结果。
//                    String result = osu.getFileTransResult(taskId);
//                    if (result != null) {
//                        log.info("录音文件识别结果查询成功：" + result);
//                        SoundBean soundBean = JSON.parseObject(result, new TypeReference<SoundBean>() {});
//                        List<Sentences> stl = soundBean.getSentences();
//                        StringBuilder sb = new StringBuilder();
//                        for(Sentences st:stl){
//                            sb.append(st.getText());
//                        }
//                        msg = sb.toString();
//                    }
//                    else {
//                        log.info("录音文件识别结果查询失败！");
//                        msg = "录音文件识别结果查询失败！";
//                    }
                    break;
                case OBS:
                    ObsClient obsClient = new ObsClient(BaseParam.AK, BaseParam.SK, BaseParam.OBS_END_POINT);
                    obsClient.putObject(BaseParam.OBS_VT, BaseParam.PARENT_PATH+originalFilename, new File(filePath));

                    String obsUrl = BaseParam.OBS_END_POINT.replace("obs."+BaseParam.REGION,BaseParam.OBS_VT+".obs."+BaseParam.REGION)+"/"+BaseParam.PARENT_PATH+originalFilename;
                    ObsSoundUtil obu = new ObsSoundUtil();
                    msg = obu.obsSound(obsUrl);
                    break;
                default:
                    msg = "不支持的存储类型";
                    break;
            }
            return msg;
        }
    }
}
