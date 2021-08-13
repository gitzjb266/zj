package com.more.transformation.api.v1;

import com.obs.services.ObsClient;

import java.io.File;

/**
 * @author skymeng
 * @version 1.0.0
 * @ClassName test.java
 * @Description TODO
 * @createTime 2021年07月02日 10:52:00
 */
public class test {
    public static void main(String[] args) {
        String endPoint = "https://obs.cn-north-4.myhuaweicloud.com";
        String ak = "UA2AG9ANDYKVIARWWUAL";
        String sk = "YsLot7ReRXl2GRwJLQGCk8gi0Q00gsuwqOjTFF4F";
// 创建ObsClient实例
        ObsClient obsClient = new ObsClient(ak, sk, endPoint);


        obsClient.putObject("zjbcs002", "zjbcs2/zjb16/16k.wav", new File("D:\\16k16bit.wav"));
    }
}
