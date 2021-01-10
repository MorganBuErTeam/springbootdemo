package com.test.demo.controller;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
@RequestMapping("config")
public class NacosController {

    @Value("${useLocalCache:true}")
    private boolean useLocalCache;

    public void setUseLocalCache(boolean useLocalCache) {
        this.useLocalCache = useLocalCache;
    }

    @RequestMapping(value = "/get", method = GET)
    @ResponseBody
    public boolean get() {
        return useLocalCache;
    }
}
