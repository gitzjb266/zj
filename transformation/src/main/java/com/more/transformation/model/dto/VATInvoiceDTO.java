package com.more.transformation.model.dto;

import lombok.Data;

/**
 * @author lzy
 * @date 2021/7/7 9:05
 * 增值税发票
 */
@Data
public class VATInvoiceDTO {
    // 购买方地址、电话
    private String payerAddress;
    // 销售方纳税人识别号
    private String payeeRegisterNo;
    // 销售方开户行及账号
    private String payeeBankName;
    // 发票号码
    private String invoiceNo;
    // 购买方纳税人识别号
    private String payerRegisterNo;
    // 复核人
    private String checker;
    // 合计税额
    private String taxAmount;
    // 开票日期
    private String invoiceDate;
    // 合计金额
    private String withoutTaxAmount;
    // 价税合计(小写)
    private String invoiceAmount;
    // 校验码
    private String antiFakeCode;
    // 购买方名称
    private String payerName;
    // 收款人
    private String Payee;
    // 价税合计(大写)
    private String sumAmount;
    // 购买方开户行及账号
    private String payerBankName;
    // 开票人
    private String clerk;
    // 销售方名称
    private String payeeName;
    // 销售方地址、电话
    private String payeeAddress;
    // 发票代码
    private String invoiceCode;

    private String base64;
}
