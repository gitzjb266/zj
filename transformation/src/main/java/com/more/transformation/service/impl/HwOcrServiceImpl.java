package com.more.transformation.service.impl;

import cn.hutool.http.HttpStatus;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.more.transformation.constants.CommonConst;
import com.more.transformation.model.dto.*;
import com.more.transformation.service.HwOcrService;
import com.more.transformation.util.FileUtil;
import com.more.transformation.util.HWOcrClientAKSK;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


/**
 * @author lzy
 * @date 2021/7/8 15:05
 */
@Service
@Slf4j
public class HwOcrServiceImpl implements HwOcrService {


    private String AK; //AK from authentication
    private String SK; //SK from authentication
    private static final String REGION_NAME = "cn-north-4"; //region name of the service
    
    private static final String HTTP_URI = "/v1.0/ocr/";

    // 身份正接口名
    public static final String IDCARD = "id-card";
    // 银行卡接口名
    public static final String BANKCARD = "bankcard";
    // 通用文字接口名
    public static final String GENERAL_TEXT = "general-text";
    //营业执照接口名
    public static final String BIZ_LICENSE = "business-license";
    // 增值税发票接口名
    public static final String VAT_INVOICE = "vat-invoice";
    // 火车票接口名
    public static final String TRAIN_TIKET = "train-ticket";

    private void init(){
        AK = "fMNneR3AVLKBtViG";
        SK = "fMNneR3AVLKBtViG";
        log.info("AK================" + AK);
        log.info("SK================" + SK);
    }

    @Override
    public IDCardDTO recognizeIdCard(MultipartFile file, String side) throws Exception {
        String base64Str = FileUtil.imgToBase64(file);
        String url = HTTP_URI + IDCARD;
        JSONObject obj = queryByAkSK(base64Str, url, side, false, false);
        IDCardDTO idcard = new IDCardDTO();
        if(CommonConst.FRONT.equals(side)) {
            idcard.setBirthDate(obj.getString("birth"));
            idcard.setGender(obj.getString("sex"));
            idcard.setAddress(obj.getString("address"));
            idcard.setNationality(obj.getString("ethnicity"));
            idcard.setName(obj.getString("name"));
            idcard.setIDNumber(obj.getString("number"));
            idcard.setFaceBase64(base64Str);
        } else {
            idcard.setEndDate(obj.getString("valid_from"));
            idcard.setIssue(obj.getString("issue"));
            idcard.setStartDate(obj.getString("valid_to"));
            idcard.setBackBase64(base64Str);
        }
        return idcard;
    }

    @Override
    public BizLicenseDTO recognizeBizLicense(MultipartFile file) throws Exception {
        String base64Str = FileUtil.imgToBase64(file);
        String url = HTTP_URI + BIZ_LICENSE;
        JSONObject obj = queryByAkSK(base64Str, url, null, false, false);
        BizLicenseDTO bizLicense = new BizLicenseDTO();
        bizLicense.setAddress(obj.getString("address"));
        bizLicense.setBusiness(obj.getString("business_scope"));
        bizLicense.setCapital(obj.getString("registered_capital"));
        bizLicense.setEstablishDate(obj.getString("found_date"));
        bizLicense.setLegalPerson(obj.getString("legal_representative"));
        bizLicense.setName(obj.getString("name"));
        bizLicense.setRegisterNumber(obj.getString("registration_number"));
        bizLicense.setType(obj.getString("type"));
        bizLicense.setValidPeriod(obj.getString("business_term"));
        bizLicense.setBase64(base64Str);
        return bizLicense;
    }

    @Override
    public BankCardDTO recognizeBankCard(MultipartFile file) throws Exception {
        String base64Str = FileUtil.imgToBase64(file);
        String url = HTTP_URI + BANKCARD;
        JSONObject obj = queryByAkSK(base64Str, url, null, false, false);
        BankCardDTO bankCardDTO = new BankCardDTO();
        bankCardDTO.setBankName(obj.getString("bank_name"));
        bankCardDTO.setCardNumber(obj.getString("card_number"));
        bankCardDTO.setValidDate(obj.getString("expiry_date"));
        bankCardDTO.setBase64(base64Str);

        return bankCardDTO;
    }

    @Override
    public TrainTiketDTO recognizeTrainTiket(MultipartFile file) throws Exception {
        String base64Str = FileUtil.imgToBase64(file);
        String url = HTTP_URI + TRAIN_TIKET;
        JSONObject obj = queryByAkSK(base64Str, url, null, false, false);

        TrainTiketDTO trainTiketDTO = new TrainTiketDTO();
        trainTiketDTO.setDate(obj.getString("departure_time"));
        trainTiketDTO.setDepartureStation(obj.getString("departure_station"));
        trainTiketDTO.setDestination(obj.getString("destination_station"));
        trainTiketDTO.setLevel(obj.getString("seat_category"));
        trainTiketDTO.setName(obj.getString("name"));
        trainTiketDTO.setNumber(obj.getString("train_number"));
        trainTiketDTO.setPrice(obj.getFloat("ticket_price"));
        trainTiketDTO.setSeat(obj.getString("seat_number"));
        trainTiketDTO.setBase64(base64Str);

        return trainTiketDTO;
    }

    @Override
    public VATInvoiceDTO recognizeVatInvoice(MultipartFile file) throws Exception {
        String base64Str = FileUtil.imgToBase64(file);
        String url = HTTP_URI + VAT_INVOICE;
        JSONObject obj = queryByAkSK(base64Str, url, null, true, false);

        VATInvoiceDTO vatInvoiceDTO = new VATInvoiceDTO();
        vatInvoiceDTO.setPayerAddress(obj.getString("buyer_address"));
        vatInvoiceDTO.setPayeeRegisterNo(obj.getString("seller_id"));
        vatInvoiceDTO.setPayeeBankName(obj.getString("seller_bank"));
        vatInvoiceDTO.setInvoiceNo(obj.getString("number"));
        vatInvoiceDTO.setPayerRegisterNo(obj.getString("buyer_id"));
        vatInvoiceDTO.setChecker(obj.getString("reviewer"));
        vatInvoiceDTO.setTaxAmount(obj.getString("subtotal_tax"));
        vatInvoiceDTO.setInvoiceDate(obj.getString("issue_date"));
        vatInvoiceDTO.setWithoutTaxAmount(obj.getString("subtotal_amount"));
        vatInvoiceDTO.setInvoiceAmount(obj.getString("total"));
        vatInvoiceDTO.setAntiFakeCode(obj.getString("check_code"));
        vatInvoiceDTO.setPayerName(obj.getString("buyer_name"));
        vatInvoiceDTO.setPayee(obj.getString("receiver"));
        vatInvoiceDTO.setSumAmount(obj.getString("total_in_words"));
        vatInvoiceDTO.setPayerBankName(obj.getString("buyer_bank"));
        vatInvoiceDTO.setClerk(obj.getString("issuer"));
        vatInvoiceDTO.setPayeeName(obj.getString("seller_name"));
        vatInvoiceDTO.setPayeeAddress(obj.getString("buyer_address"));
        vatInvoiceDTO.setInvoiceCode(obj.getString("code"));
        vatInvoiceDTO.setBase64(base64Str);

        return vatInvoiceDTO;
    }

    @Override
    public CharacterDTO recognizeCharacter(MultipartFile file) throws Exception {
        String base64Str = FileUtil.imgToBase64(file);
        String url = HTTP_URI + GENERAL_TEXT;
        JSONObject obj = queryByAkSK(base64Str, url, null, false, true);

        CharacterDTO characterDTO = new CharacterDTO();
        List<CharacterDTO.CommonText> list = new ArrayList<>();

        JSONArray jsonArray = obj.getJSONArray("words_block_list");
        if(!jsonArray.isEmpty()) {
            jsonArray.forEach(item -> {
                CharacterDTO.CommonText commonText = new CharacterDTO.CommonText();
                commonText.setText(((JSONObject) item).getString("words"));
                list.add(commonText);
            });
        }
        characterDTO.setResults(list);
        characterDTO.setBase64(base64Str);
        return characterDTO;
    }

    /**
     * 以AK SK认证的方式请求华为云ocr
     * @param base64Img 转base64的图片
     * @param url 请求路径
     * @param side 身份正的正反面 front：身份证人像面，back：身份证国徽面
     * @param advancedMode 是否返回增值税发票全部字段
     * @param detectDirection 是否校正通用文字图片角度
     * @return
     * @throws UnsupportedOperationException
     */
    public JSONObject queryByAkSK(String base64Img, String url,
                                  String side, Boolean advancedMode, Boolean detectDirection) throws UnsupportedOperationException {
        if(AK == null || SK == null) {
            init();
        }
        JSONObject params = new JSONObject();
        params.put("image", base64Img);
        JSONObject result = new JSONObject();
        if(StringUtils.isNotBlank(side)) {
            params.put("side", side);
        }
        if(advancedMode) {
            params.put("advanced_mode", true);
        }
        if(detectDirection) {
            params.put("detect_direction", true);
        }
        try {
            HWOcrClientAKSK ocrClient=new HWOcrClientAKSK(REGION_NAME, AK, SK);
            HttpResponse response=ocrClient.RequestOcrServiceBase64(url, params);
            String content = IOUtils.toString(response.getEntity().getContent(), StandardCharsets.UTF_8);
            JSONObject jsonObject = JSONObject.parseObject(content);
            if(response.getStatusLine().getStatusCode() == HttpStatus.HTTP_OK) {
                result = jsonObject.getJSONObject("result");
            } else {
                log.error(jsonObject.getString("error_msg"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
