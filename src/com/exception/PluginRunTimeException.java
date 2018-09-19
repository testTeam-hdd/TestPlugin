package com.exception;

/**
 * dongdong Created by 上午11:06  2018/9/19
 */
public class PluginRunTimeException extends RuntimeException{
    /**
     *
     */
    private static final long serialVersionUID = 3227937873509921951L;
    /**
     * 错误文案
     */
    private final String errorMsg;
    /**
     * 错误类型
     */
    private final PluginErrorMsg errorType;

    public String getErrorMsg() {
        return errorMsg;
    }

    public PluginErrorMsg getErrorType() {
        return errorType;
    }

    public PluginRunTimeException(final PluginErrorMsg errorType) {
        super(errorType.getMsg());
        this.errorType = errorType;
        this.errorMsg = errorType.getMsg();
    }
}
