package com.wwf.common.bean;

import java.io.Serializable;
import java.util.List;

public class BaseBean<T> implements Serializable{
    /**
     * code : 200
     * data : {"is_can_receive":false,"activity_img_url":"http://res.webi.com.cn/webi_app/newcomer_package.png"}
     * msg :"未知错误"
     */
    private int code;
    private T data;
    private List<T>  dataList;
    private String msg;

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
