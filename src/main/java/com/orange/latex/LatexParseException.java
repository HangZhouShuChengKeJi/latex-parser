package com.orange.latex;

/**
 * 解析异常
 *
 * @author 小天
 * @date 2020/12/9 10:29
 */
public class LatexParseException extends RuntimeException {

    public LatexParseException() {
    }

    public LatexParseException(String message) {
        super(message);
    }

    public LatexParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public LatexParseException(Throwable cause) {
        super(cause);
    }

    public LatexParseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
