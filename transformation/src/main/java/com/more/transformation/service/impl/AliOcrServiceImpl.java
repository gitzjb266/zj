package com.more.transformation.service.impl;

import com.aliyun.ocr20191230.Client;
import com.aliyun.ocr20191230.models.*;
import com.aliyun.teaopenapi.models.Config;
import com.aliyun.teautil.models.RuntimeOptions;

import com.more.transformation.model.dto.*;
import com.more.transformation.service.AliOcrService;
import com.more.transformation.util.FileUtil;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import static org.apache.commons.beanutils.BeanUtils.*;

/**
 * @author lzy
 * @date 2021/7/5 17:47
 * 阿里ocr识别
 */
@Service
@Slf4j
public class AliOcrServiceImpl implements AliOcrService {

    private Client ocrClient;
    private RuntimeOptions runtime;

    private void init() {
        try{
            Config config = new Config();
//            config.type = "access_key";    TODO
            config.regionId = "cn-shanghai";
            config.accessKeyId = "fMNneR3AVLKBtViG";
            config.accessKeySecret = "fMNneR3AVLKBtViG";
            log.info("aliyun-ak==========" + config.accessKeyId);
            log.info("aliyun-sk==========" + config.accessKeySecret);
            config.endpoint = "ocr.cn-shanghai.aliyuncs.com";
            ocrClient = new Client(config);
            runtime = new RuntimeOptions();
        } catch (Exception e) {
            log.error("初始化阿里云ocrClient异常:" + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public IDCardDTO recognizeIdCard(MultipartFile file, String side) throws Exception {
        if(ocrClient == null) {
            init();
        }
        RecognizeIdentityCardAdvanceRequest request = new RecognizeIdentityCardAdvanceRequest();
        request.imageURLObject = file.getInputStream();
        RecognizeIdentityCardResponse response = null;
        request.setSide(side);
        request.side = side;
        response = ocrClient.recognizeIdentityCardAdvance(request, runtime);
        IDCardDTO idCardDTO = new IDCardDTO();
        if("face".equals(side)) {
            copyProperties(idCardDTO, response.body.getData().getFrontResult());
            idCardDTO.setFaceBase64(FileUtil.imgToBase64(file));
        } else {
            copyProperties(idCardDTO, response.body.getData().getBackResult());
            idCardDTO.setBackBase64(FileUtil.imgToBase64(file));
        }
        return idCardDTO;
    }

    @Override
    public BizLicenseDTO recognizeBizLicense(MultipartFile file) throws Exception {
        if(ocrClient == null) {
            init();
        }
        RecognizeBusinessLicenseAdvanceRequest request = new RecognizeBusinessLicenseAdvanceRequest();
        request.imageURLObject = file.getInputStream();
        RecognizeBusinessLicenseResponse response = null;
        response = ocrClient.recognizeBusinessLicenseAdvance(request, runtime);
        BizLicenseDTO bizLicense = new BizLicenseDTO();
        copyProperties(bizLicense, response.getBody().getData());
        bizLicense.setBase64(FileUtil.imgToBase64(file));
        return bizLicense;
    }

    @Override
    public BankCardDTO recognizeBankCard(MultipartFile file) throws Exception {
        if(ocrClient == null) {
            init();
        }
        RecognizeBankCardAdvanceRequest request = new RecognizeBankCardAdvanceRequest();
        request.imageURLObject = file.getInputStream();
        RecognizeBankCardResponse response = ocrClient.recognizeBankCardAdvance(request, runtime);
        BankCardDTO bankCardDTO = new BankCardDTO();
        copyProperties(bankCardDTO, response.getBody().getData());
        bankCardDTO.setBase64(FileUtil.imgToBase64(file));
        return bankCardDTO;
    }

    @Override
    public TrainTiketDTO recognizeTrainTiket(MultipartFile file) throws Exception {
        if(ocrClient == null) {
            init();
        }
        RecognizeTrainTicketAdvanceRequest request = new RecognizeTrainTicketAdvanceRequest();
        request.imageURLObject = file.getInputStream();
        RecognizeTrainTicketResponse response = ocrClient.recognizeTrainTicketAdvance(request, runtime);
        TrainTiketDTO trainTiketDTO = new TrainTiketDTO();
        copyProperties(trainTiketDTO, response.getBody().getData());
        trainTiketDTO.setBase64(FileUtil.imgToBase64(file));
        return trainTiketDTO;
    }

    @Override
    public VATInvoiceDTO recognizeVatInvoice(MultipartFile file, String fileType) throws Exception {
        if(ocrClient == null) {
            init();
        }
        VATInvoiceDTO vatInvoice = new VATInvoiceDTO();
        RecognizeVATInvoiceAdvanceRequest request = new RecognizeVATInvoiceAdvanceRequest();
        request.fileURLObject = file.getInputStream();
        request.fileType = fileType;
        RecognizeVATInvoiceResponse response = ocrClient.recognizeVATInvoiceAdvance(request, runtime);
        copyProperties(vatInvoice, response.body.data.content);
        vatInvoice.setBase64(FileUtil.imgToBase64(file));
        return vatInvoice;
    }

    @Override
    public CharacterDTO recognizeCharacter(MultipartFile file) throws Exception{
        if(ocrClient == null) {
            init();
        }
        CharacterDTO characterDTO = new CharacterDTO();
        RecognizeCharacterAdvanceRequest request = new RecognizeCharacterAdvanceRequest();
        request.imageURLObject = file.getInputStream();
        request.minHeight = 10;
        request.outputProbability = true;
        RecognizeCharacterResponse response = ocrClient.recognizeCharacterAdvance(request, runtime);
        copyProperties(characterDTO, response.body.data);
        characterDTO.setBase64(FileUtil.imgToBase64(file));
        return characterDTO;
    }
}
