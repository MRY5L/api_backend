package com.mry5l.apiclientsdk.common;

/**
 * 返回工具类
 */
public class ResultUtils {

    /**
     * 成功
     *
     * @param data ...
     * @param <T>  ...
     * @return ...
     */
    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(0, data, "ok");
    }

    /**
     * 失败
     *
     * @param errorCode ...
     * @param <T>       ...
     * @return ...
     */
    public static <T> BaseResponse<T> error(ErrorCode errorCode) {
        return new BaseResponse<>(errorCode);
    }

    /**
     * 失败
     *
     * @param code    ...
     * @param message ...
     * @param <T>     ...
     * @return ...
     */
    public static <T> BaseResponse<T> error(int code, String message) {
        return new BaseResponse<>(code, null, message);
    }

    /**
     * 失败
     *
     * @param errorCode ...
     * @param message   ...
     * @param <T>       ...
     * @return ...
     */
    public static <T> BaseResponse<T> error(ErrorCode errorCode, String message) {
        return new BaseResponse<>(errorCode.getCode(), null, message);
    }
}
