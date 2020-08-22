package com.message.board.common;

import com.alibaba.fastjson.annotation.JSONField;
import jdk.nashorn.internal.ir.annotations.Ignore;

import java.util.List;

/**
 * @author cnxqin
 * @desc
 * @date 2020/06/14 18:34
 */
public class ResultInfo<T> {

    private boolean success;
    private String msg;
    private T data;
    private List<T> list;

    @JSONField(serialize=false)
    private boolean json;

    @JSONField(serialize=false)
    private String forwordUrl;

    public ResultInfo(){
        this.success = false;
        this.json = true;
        this.forwordUrl = "/";
    }

    public ResultInfo(boolean success, boolean json) {
        this.success = success;
        this.json = json;
        this.forwordUrl = "/";
    }

    public ResultInfo(boolean success, boolean json, String forwordUrl) {
        this.success = success;
        this.json = json;
        this.forwordUrl = forwordUrl;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public boolean isJson() {
        return json;
    }

    public void setJson(boolean json) {
        this.json = json;
    }

    public String getForwordUrl() {
        return forwordUrl;
    }

    public void setForwordUrl(String forwordUrl) {
        this.forwordUrl = forwordUrl;
    }
}
