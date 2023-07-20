package com.versionone.demo1server.utils;

/**
 * 返回结果
 */
public enum ResultCode implements IErrorCode {

    SUCCESS(200,"操作成功"),
    FAILED(500,"操作失败"),
    SIGN_FAILED(500,"用户已存在"),
    ADD_SUCCESS(200,"添加成功"),
    UPDATE_SUCCESS(200,"修改成功"),
    DELETE_SUCCESS(200,"删除成功"),
    VALIDATE_FAILED(404, "参数检验失败"),
    UNAUTHORIZED(401, "暂未登录或token已经过期"),
    FORBIDDEN(403, "没有相关权限");

    private long code;
    private String message;

   private ResultCode(long code, String message){
      this.code = code;
      this.message = message;
   }

    @Override
    public long getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
