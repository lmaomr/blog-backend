package cn.lmao.blogbackend.exception;

import cn.lmao.blogbackend.model.enums.ExceptionCodeMsg;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

    private final int code;
    private final String msg;

    public CustomException(ExceptionCodeMsg exceptionCodeMsg) {
        super();
        this.code = exceptionCodeMsg.getCode();
        this.msg = exceptionCodeMsg.getMsg();
    }

    public CustomException(int code, String msg) {
        super();
        this.code = code;
        this.msg = msg;
    }

}
