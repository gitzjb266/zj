package com.more.transformation.model.dto;

import lombok.Data;

/**
 * @author lzy
 * @date 2021/7/7 9:24
 * 身份证
 */
@Data
public class IDCardDTO {
    // 出生日期
    private String birthDate;
    // 性别
    private String gender;
    // 地址
    private String address;
    // 民族
    private String nationality;
    //  姓名
    private String name;
    // 身份证号
    private String iDNumber;
    // 有效期结束日期
    private String endDate;
    // 签发机关
    private String issue;
    // 有效期起始日期
    private String startDate;

    private String faceBase64;

    private String backBase64;
}
