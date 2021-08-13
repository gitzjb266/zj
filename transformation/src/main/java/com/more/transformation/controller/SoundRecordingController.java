package com.more.transformation.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;

import com.more.transformation.constants.CommonConst;
import com.more.transformation.entity.TDataserviceConfig;
import com.more.transformation.service.SoundRecordingService;
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

import java.io.IOException;

/**
 * 录音文件提取
 * @author zhangjb
 */
@Slf4j
@RestController
@Api(tags = {"录音文件提取","录音文件识别"})
@RequestMapping("sisSound")
public class SoundRecordingController extends BaseParam {

    @Autowired
    private TDataserviceConfigService dataservice;

    @Autowired
    private SoundRecordingService srcs;

    @PostMapping("/soundRecordingPickUp")
    @ApiOperation(value="智能语音识别",notes="录音文件提取")
    public R soundRecordingPickUp(MultipartFile file){
        String msg = "";
        try {
            ResultBean rb = CheckUpload.getInstance().beforeCheck(file);
            if(!rb.resultFlag){
                return R.failed(rb.getMsg());
            }
            QueryWrapper<TDataserviceConfig> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("service_name", CommonConst.SOUND_MSG_PICKUP);
            String storageType = dataservice.getOne(queryWrapper).getType();
            String originalFilename = rb.getResultMap().get("originalFilename");
            String filePath = rb.getResultMap().get("filePath");
            msg = srcs.saveFile(storageType,originalFilename,filePath);
        } catch (IOException e) {
            e.printStackTrace();
            log.error(e.getMessage(),e);
            msg =  "失败:"+e.getMessage();
        }
        return R.ok(msg);
    }

    @PostMapping("/soundRecording")
    @ApiOperation(value="智能语音识别",notes="录音文件识别")
    public R soundRecording(MultipartFile file){
        String msg = "";
        try {
            ResultBean rb = CheckUpload.getInstance().beforeCheck(file);
            if(!rb.resultFlag){
                return R.failed(rb.getMsg());
            }
            QueryWrapper<TDataserviceConfig> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("service_name", CommonConst.SOUND_MSG);
            String storageType = dataservice.getOne(queryWrapper).getType();
            String originalFilename = rb.getResultMap().get("originalFilename");
            String filePath = rb.getResultMap().get("filePath");
            msg = srcs.saveFile(storageType,originalFilename,filePath);
        } catch (IOException e) {
            e.printStackTrace();
            log.error(e.getMessage(),e);
            msg =  "失败:"+e.getMessage();
        }
        return R.ok(msg);
    }
}