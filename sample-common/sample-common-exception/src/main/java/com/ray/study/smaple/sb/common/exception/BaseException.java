package com.ray.study.smaple.sb.common.exception;


import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

/**
 * description
 *
 * @author r.shi 2020/02/25 18:05
 */
@Getter
@Setter
public class BaseException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String msg;

    private HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

    public BaseException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public BaseException(String msg, HttpStatus status) {
        super(msg);
        this.msg = msg;
        this.status = status;
    }

    public BaseException(String msg, HttpStatus status, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.status = status;
    }

}
