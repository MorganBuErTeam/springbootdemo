package com.test.demo.exception.handler;

import com.test.demo.common.util.ExceptionUtils;
import com.test.demo.common.vo.sse.WebServiceResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public WebServiceResponse exceptionHandler(Exception ex){
        return ExceptionUtils.exceptionHandler(ex);
    }
}
