package com.more.transformation.util;

import com.more.transformation.constants.CommonConst;
import org.apache.commons.lang3.StringUtils;

/**
 * 枚举类 作用于对象存储类型 目前只有oss obs
 * @author zhangjb
 */
public enum StorageType {
    OSS(CommonConst.ALI, "阿里-对象存储服务"),
    OBS(CommonConst.HW, "华为-对象存储服务");

    private String code;
    private String desc;

    StorageType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static StorageType getEnumByKey(String key) {
        for (StorageType storageType : StorageType.values()) {
            if (StringUtils.equals(storageType.code,key)) {
                return storageType;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
