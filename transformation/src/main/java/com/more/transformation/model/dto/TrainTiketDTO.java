package com.more.transformation.model.dto;

import lombok.Data;

/**
 * @author lzy
 * @date 2021/7/7 8:56
 * 火车票
 */
@Data
public class TrainTiketDTO {
    // 乘车日期时间
    private String date;
    // 始发站点
    private String departureStation;
    // 目的站点
    private String destination;
    // 座位席别
    private String level;
    // 乘车人姓名
    private String name;
    // 车次号
    private String number;
    // 票价
    private Float price;
    // 座位车厢及座次号
    private String seat;

    private String base64;
}
