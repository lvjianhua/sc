package org.sc.common.exception;

/**
 * Created by Sonic Wang on 2017/3/24.
 */
public class OceanRuntimeException extends RuntimeException {
    public OceanRuntimeException() {
    }

    public OceanRuntimeException(String message) {
        super(message);
    }

    public OceanRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public OceanRuntimeException(Throwable cause) {
        super(cause);
    }

    public OceanRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
