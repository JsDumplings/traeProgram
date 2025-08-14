package com.example.demo;

public class ResponseResult {
    private int code;
    private Object data;
    private String message;

    public ResponseResult(int code, Object data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static ResponseResult success(Object data) {
        return new ResponseResult(200, data, "请求成功");
    }

    public static ResponseResult success() {
        return new ResponseResult(200, null, "请求成功");
    }

    public static ResponseResult error(int code, String message) {
        return new ResponseResult(code, null, message);
    }
}