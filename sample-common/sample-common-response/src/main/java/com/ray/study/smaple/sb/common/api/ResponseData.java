package com.ray.study.smaple.sb.common.api;

/**
 * description
 *
 * @author r.shi 2020/02/25 18:05
 */
public class ResponseData<T> {
    private long code;
    private String message;
    private T data;

    protected ResponseData() {
    }

    protected ResponseData(long code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 成功返回结果
     *
     */
    public static <T> ResponseData<T> success() {
        return new ResponseData<T>(ResponseStatus.SUCCESS.getCode(), ResponseStatus.SUCCESS.getMessage(), null);
    }

    /**
     * 成功返回结果
     *
     * @param data 获取的数据
     */
    public static <T> ResponseData<T> success(T data) {
        return new ResponseData<T>(ResponseStatus.SUCCESS.getCode(), ResponseStatus.SUCCESS.getMessage(), data);
    }


    /**
     * 成功返回结果
     *
     * @param data    获取的数据
     * @param message 提示信息
     */
    public static <T> ResponseData<T> success(T data, String message) {
        return new ResponseData<T>(ResponseStatus.SUCCESS.getCode(), message, data);
    }

    /**
     * 失败返回结果
     *
     * @param errorCode 错误码
     */
    public static <T> ResponseData<T> failed(IErrorCode errorCode) {
        return new ResponseData<T>(errorCode.getCode(), errorCode.getMessage(), null);
    }

    public static <T> ResponseData<T> failed(int code, String message) {
        return new ResponseData<T>(code, message, null);
    }

    /**
     * 失败返回结果
     *
     * @param message 提示信息
     */
    public static <T> ResponseData<T> failed(String message) {
        return new ResponseData<T>(ResponseStatus.FAILED.getCode(), message, null);
    }

    /**
     * 失败返回结果
     */
    public static <T> ResponseData<T> failed() {
        return failed(ResponseStatus.FAILED);
    }

    /**
     * 参数验证失败返回结果
     */
    public static <T> ResponseData<T> validateFailed() {
        return failed(ResponseStatus.VALIDATE_FAILED);
    }

    /**
     * 参数验证失败返回结果
     *
     * @param message 提示信息
     */
    public static <T> ResponseData<T> validateFailed(String message) {
        return new ResponseData<T>(ResponseStatus.VALIDATE_FAILED.getCode(), message, null);
    }

    /**
     * 未登录返回结果
     */
    public static <T> ResponseData<T> unauthorized(T data) {
        return new ResponseData<T>(ResponseStatus.UNAUTHORIZED.getCode(), ResponseStatus.UNAUTHORIZED.getMessage(), data);
    }

    /**
     * 未授权返回结果
     */
    public static <T> ResponseData<T> forbidden(T data) {
        return new ResponseData<T>(ResponseStatus.FORBIDDEN.getCode(), ResponseStatus.FORBIDDEN.getMessage(), data);
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
