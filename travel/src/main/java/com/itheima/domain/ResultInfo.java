package com.itheima.domain;

import java.io.Serializable;
import java.util.Objects;

/**
 * 用于封装后端返回前端数据对象
 */
public class ResultInfo implements Serializable {
    private boolean flag;//true代表成功,false代表失败
    private Object data;//后端返回结果数据对象
    private String msg;//消息描述

    //无参构造方法
    public ResultInfo() {
    }
    public ResultInfo(boolean flag) {
        this.flag = flag;
    }
    /**
     * 有参构造方法
     * @param flag
     * @param msg
     */
    public ResultInfo(boolean flag, String msg) {
        this.flag = flag;
        this.msg = msg;
    }
    /**
     * 有参构造方法
     * @param flag
     * @param data
     * @param msg
     */
    public ResultInfo(boolean flag, Object data, String msg) {
        this.flag = flag;
        this.data = data;
        this.msg = msg;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
