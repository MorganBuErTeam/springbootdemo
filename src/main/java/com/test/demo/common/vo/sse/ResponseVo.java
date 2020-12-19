package com.test.demo.common.vo.sse;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ResponseVo {

    private String status;

    private String message;

    private Object data;

}
