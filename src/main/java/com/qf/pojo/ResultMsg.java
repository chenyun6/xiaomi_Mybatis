package com.qf.pojo;

public class ResultMsg {
    private String message;
    private boolean success=true;

    public ResultMsg(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    public ResultMsg() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
    public static ResultMsg success(String message){
        return new ResultMsg(message, true);
    }
    public static ResultMsg faliure(String message){
        return new ResultMsg(message, false);
    }
}
