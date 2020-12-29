package com.test.demo.common.vo.sse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class WebServiceResponse<T> {

    public static final int SUCCESS_STATUS=1;
    public static final int EXCEPTION_STATUS=0;

    /**
     * HTTP请求返回数据的状态信息 1.成功； 0及以下.失败
     */
    private int status;

    /**
     * 返回的消息状态
     */
    private String message;

    /**
     * 返回的数据信息
     */
    private T data;

}
