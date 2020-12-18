package com.test.demo.service.impl;

import com.test.demo.common.vo.WmsRequest;
import com.test.demo.common.vo.WmsResponse;
import com.test.demo.service.WebServiceAgvService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.jws.WebService;

/**
 * @Description: ${description}
 * @Author: zY
 * @Date: 2019/03/27 16:50
 */
@WebService(targetNamespace="http://service.demo.Test.com/",
        endpointInterface = "com.test.demo.service.WebServiceAgvService")
@Component
public class WmsToAgvServiceImpl implements WebServiceAgvService {
    Logger logger = LoggerFactory.getLogger(WmsToAgvServiceImpl.class);

//    @Resource
//    private WmsInfoMapper wmsInfoMapper;

    @Override
    public WmsResponse wmsInfo(WmsRequest wmsRequest) {  //解析ms信息,持久化日志信息
        WmsResponse response=new WmsResponse();
        /////////
        return response;
    }

}
