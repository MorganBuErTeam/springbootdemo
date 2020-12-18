package com.test.demo.common.vo;

import lombok.Data;

/**
 * @Description: ${description}
 * @Author: zY
 * @Date: 2019/03/28 20:21
 */
@Data
public class WebServiceResult {
    private String message ;
    private String orderNo;
    private String stationNo ;
    private String supplierId ;
    private String vinNo ;
    private boolean success  ;
}
