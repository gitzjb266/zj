package com.more.transformation.model.dto;

import lombok.Data;

/**
 * @author lzy
 * @date 2021/7/6 14:41
 * 营业执照
 */
@Data
public class BizLicenseDTO {
    // 公司地址
    private String address;
    // 经营范围
    private String business;
    // 注册资本
    private String capital;
    // 注册日期
    private String establishDate;
    // 法人
    private String legalPerson;
    // 公司名称
    private String name;
    // 统一社会信用代码
    private String registerNumber;
    // 公司类型
    private String type;
    // 营业期限
    private String validPeriod;

    private String base64;

}
