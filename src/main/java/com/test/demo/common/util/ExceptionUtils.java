package com.test.demo.common.util;

import com.test.demo.common.vo.sse.WebServiceResponse;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
public class ExceptionUtils {

    public static WebServiceResponse exceptionHandler(Exception ex) {
        log.error(ex.getMessage(),ex);

        WebServiceResponse webServiceResponse=new WebServiceResponse()
                .setStatus(0)
                .setMessage(ex.getMessage());
        log.error(webServiceResponse.toString());

        return webServiceResponse;
    }

}
