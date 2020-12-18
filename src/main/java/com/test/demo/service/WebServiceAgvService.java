package com.test.demo.service;



import com.test.demo.common.vo.WmsRequest;
import com.test.demo.common.vo.WmsResponse;

import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 * @Description: ${description}
 * @Author: zY
 * @Date: 2019/03/27 16:47
 */
@WebService
public interface WebServiceAgvService {
    @WebMethod  //把此方法发布
    public WmsResponse wmsInfo(WmsRequest wmsRequest);
}
