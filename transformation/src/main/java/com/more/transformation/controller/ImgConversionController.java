package com.more.transformation.controller;

import com.aliyun.tea.TeaException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;

import com.more.transformation.constants.CommonConst;
import com.more.transformation.entity.TDataserviceConfig;
import com.more.transformation.model.dto.*;
import com.more.transformation.service.AliOcrService;
import com.more.transformation.service.HwOcrService;
import com.more.transformation.service.TDataserviceConfigService;
import com.more.transformation.util.FileTypesUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lzy
 * @date 2021/7/5 17:36
 */

@RestController
@RequestMapping("ocr")
@Slf4j
public class ImgConversionController {
    @Autowired
    private AliOcrService aliOcrService;

    @Autowired
    private TDataserviceConfigService dataservice;

    @Autowired
    private HwOcrService hwOcrService;

    @RequestMapping("bizLicense")
    @ApiOperation("营业执照")
    public R bizLicense(@RequestParam("file") MultipartFile file) throws Exception {
        if(file == null) {
            return R.failed("文件不能为空！");
        }
        QueryWrapper<TDataserviceConfig> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("service_name", CommonConst.BIZ_LICENSE);
        BizLicenseDTO res;
        try {
            if(CommonConst.ALI.equals(dataservice.getOne(queryWrapper).getType())) {
                res = aliOcrService.recognizeBizLicense(file);
            } else {
                res = hwOcrService.recognizeBizLicense(file);
            }
        } catch (TeaException e) {
            String message = e.getMessage().substring(e.getMessage().indexOf(",") + 1,
                    e.getMessage().indexOf("request"));
            log.error("营业执照转换失败：" + message);
            return R.failed(message);
        }
        return R.ok(res);
    }

    @RequestMapping("bankCard")
    @ApiOperation("银行卡")
    public R bankCard(@RequestParam("file") MultipartFile file) throws Exception {
        if(file == null) {
            return R.failed("文件不能为空！");
        }

        QueryWrapper<TDataserviceConfig> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("service_name", CommonConst.BANK_CARD);
        BankCardDTO res;
        try {
            TDataserviceConfig config = dataservice.getOne(queryWrapper);
            if(CommonConst.ALI.equals(config.getType())) {
                res = aliOcrService.recognizeBankCard(file);
            } else {
                res = hwOcrService.recognizeBankCard(file);
            }
        } catch (TeaException e) {
            String message = e.getMessage().substring(e.getMessage().indexOf(",") + 1,
                    e.getMessage().indexOf("request"));
            log.error("火车票转换失败：" + message);
            return R.failed("转换失败！");
        }
        return R.ok(res);
    }

    @RequestMapping("trainTiket")
    @ApiOperation("火车票")
    public R trainTiket(@RequestParam("file") MultipartFile file) throws Exception{
        if(file == null) {
            return R.failed("文件不能为空！");
        }
        QueryWrapper<TDataserviceConfig> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("service_name", CommonConst.TRAIN_TIKET);
        TDataserviceConfig config = dataservice.getOne(queryWrapper);
        TrainTiketDTO res;
        try {
            if(CommonConst.ALI.equals(config.getType())) {
                res = aliOcrService.recognizeTrainTiket(file);
            } else {
                res = hwOcrService.recognizeTrainTiket(file);
            }
        } catch (TeaException e) {
            String message = e.getMessage().substring(e.getMessage().indexOf(",") + 1,
                    e.getMessage().indexOf("request"));
            log.error("火车票转换失败：" + message);
            return R.failed("转换失败！");
        }
        return R.ok(res);
    }

    @RequestMapping("vatInvoice")
    @ApiOperation("增值税发票")
    public R vatInvoice(@RequestParam("file") MultipartFile file) throws Exception {
        if(file == null) {
            return R.failed("文件不能为空！");
        }
        QueryWrapper<TDataserviceConfig> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("service_name", CommonConst.VAT_INVOICE);

        VATInvoiceDTO res;
        try {
            TDataserviceConfig config = dataservice.getOne(queryWrapper);
            if(CommonConst.ALI.equals(config.getType())) {
                res = aliOcrService.recognizeVatInvoice(file, FileTypesUtil.getFileTypeByStream(file.getBytes()));
            } else {
                res = hwOcrService.recognizeVatInvoice(file);
            }
        } catch (TeaException e) {
            String message = e.getMessage().substring(e.getMessage().indexOf(",") + 1,
                    e.getMessage().indexOf("request"));
            log.error("增值税发票转换失败：" + message);
            return R.failed("转换失败！");
        }
        return R.ok(res);
    }

    @RequestMapping("character")
    @ApiOperation("通用文字")
    public R character(@RequestParam("file") MultipartFile file) throws Exception {
        if(file == null) {
            return R.failed("文件不能为空！");
        }

        QueryWrapper<TDataserviceConfig> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("service_name", CommonConst.GENERAL_CHARACTER);
        TDataserviceConfig config = dataservice.getOne(queryWrapper);
        CharacterDTO characterDTO;
        try {
            if(CommonConst.ALI.equals(config.getType())) {
                characterDTO = aliOcrService.recognizeCharacter(file);
            } else {
                characterDTO = hwOcrService.recognizeCharacter(file);
            }
        } catch (TeaException e) {
            String message = e.getMessage().substring(e.getMessage().indexOf(",") + 1,
                    e.getMessage().indexOf("request"));
            log.error("通用文字转换失败：" + message);
            return R.failed("转换失败！");
        }
        return R.ok(characterDTO);
    }

    @RequestMapping("IDCard")
    @ApiOperation("身份证")
    public R IDCard(MultipartFile face, MultipartFile back) throws Exception {
        if(face == null && back == null) {
            return R.failed("文件不能为空！");
        }

        QueryWrapper<TDataserviceConfig> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("service_name", CommonConst.ID_CARD);
        String serviceName = dataservice.getOne(queryWrapper).getType();

        Map<String,Object> map = new HashMap<>();
        try {
            if(CommonConst.ALI.equals(serviceName)) {
                if(face != null) {
                    IDCardDTO res = aliOcrService.recognizeIdCard(face, CommonConst.FACE);
                    map.put(CommonConst.FACE, res);
                }
                if(back != null) {
                    IDCardDTO res = aliOcrService.recognizeIdCard(back, CommonConst.BACK);
                    map.put(CommonConst.BACK, res);
                }
            } else {
                if(face != null) {
                    IDCardDTO res = hwOcrService.recognizeIdCard(face, CommonConst.FRONT);
                    map.put(CommonConst.FACE, res);
                }
                if(back != null) {
                    IDCardDTO res = hwOcrService.recognizeIdCard(back, CommonConst.BACK);
                    map.put(CommonConst.BACK, res);
                }
            }
        } catch (TeaException e) {
            String message = e.getMessage().substring(e.getMessage().indexOf(",") + 1,
                    e.getMessage().indexOf("request"));
            log.error("身份证转换失败：" + message);
            return R.failed("转换失败！");
        }
        return R.ok(map);
    }


}
