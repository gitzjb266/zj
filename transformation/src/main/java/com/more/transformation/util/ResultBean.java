package com.more.transformation.util;

import java.util.Map;

/**
 * 相应实体类：目前作用在文件上传后承接返回值
 * @author zhangjb
 */
public class ResultBean {
    public String msg;
    public boolean resultFlag;
    public Map<String,String> resultMap;

    public ResultBean(boolean resultFlag) {
        this.resultFlag = resultFlag;
    }

    public boolean isResultFlag() {
        return resultFlag;
    }

    public void setResultFlag(boolean resultFlag) {
        this.resultFlag = resultFlag;
    }

    public Map<String, String> getResultMap() {
        return resultMap;
    }

    public void setResultMap(Map<String, String> resultMap) {
        this.resultMap = resultMap;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
