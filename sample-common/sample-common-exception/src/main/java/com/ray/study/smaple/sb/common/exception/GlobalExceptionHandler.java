package com.ray.study.smaple.sb.common.exception;


import com.ray.study.smaple.sb.common.api.ResponseData;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * description
 *
 * @author r.shi 2020/02/26 18:12
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseData handleRRException(BaseException e) {
        return ResponseData.failed(e.getStatus().value(), e.getMessage());
    }

}
