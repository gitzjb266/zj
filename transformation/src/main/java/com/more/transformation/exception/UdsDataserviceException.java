package com.more.transformation.exception;

/**
 * 文件处理服务异常
 */
public class UdsDataserviceException extends RuntimeException{
    public UdsDataserviceException() {
        super();
    }

    public UdsDataserviceException(String message) {
        super(message);
    }

    public UdsDataserviceException(String message, Throwable cause) {
        super(message, cause);
    }

    public UdsDataserviceException(Throwable cause) {
        super(cause);
    }

    protected UdsDataserviceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
