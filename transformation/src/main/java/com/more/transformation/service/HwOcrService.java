package com.more.transformation.service;


import com.more.transformation.model.dto.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author lzy
 * @date 2021/7/5 17:42
 */
public interface HwOcrService {
    IDCardDTO recognizeIdCard(MultipartFile file, String side) throws Exception;

    BizLicenseDTO recognizeBizLicense(MultipartFile file) throws Exception;

    BankCardDTO recognizeBankCard(MultipartFile file) throws Exception;

    TrainTiketDTO recognizeTrainTiket(MultipartFile file) throws Exception;

    VATInvoiceDTO recognizeVatInvoice(MultipartFile file) throws Exception;

    CharacterDTO recognizeCharacter(MultipartFile file) throws Exception;
}
