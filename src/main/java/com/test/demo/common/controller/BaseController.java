package com.test.demo.common.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.test.demo.common.service.BaseService;
import com.test.demo.common.vo.ResponseVo;

import java.util.Map;

/**
 * 通用Controller
 **/
public class BaseController<Service extends BaseService,Entity> {

    @Autowired
    protected Service baseService;





}
