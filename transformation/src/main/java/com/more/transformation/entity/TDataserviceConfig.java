package com.more.transformation.entity;

import java.util.Date;
import java.io.Serializable;

/**
 * (TDataserviceConfig)实体类
 *
 * @author makejava
 * @since 2021-08-05 09:02:01
 */
public class TDataserviceConfig implements Serializable {
    private static final long serialVersionUID = -98950278166311949L;
    /**
     * 主键
     */
    private String id;
    /**
     * 服务名称
     */
    private String serviceName;
    /**
     * 1 阿里 2华为
     */
    private String type;

    private Date createTime;

    private Date updateTime;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

}
