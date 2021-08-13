package com.more.transformation.model.dto;

import lombok.Data;

/**
 * @author lzy
 * @date 2021/7/6 17:19
 * 银行卡
 */
@Data
public class BankCardDTO {
    // 银行名称
    private String bankName;
    // 卡号
    private String cardNumber;
    // 过期日期
    private String validDate;

    private String base64;
}
