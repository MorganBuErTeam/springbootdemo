package com.test.demo.controller;

import com.test.demo.annotation.KthLog;
import com.test.demo.domain.Task;
import com.test.demo.domain.UpKeepInfo;
import com.test.demo.service.TaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/test")
@Slf4j
@Api(value = "演示",description = "用来演示Swagger的一些注解")
public class TestMethodController {

    @Autowired
    private TaskService taskService;

    @PostMapping(value = "/test")
    @ApiOperation(value="测试自定义注解")
    @KthLog
    public UpKeepInfo test(@RequestBody Task task) {
        UpKeepInfo upKeepInfo=new UpKeepInfo().setCreateDate(new Date()).setDeviceName("名称").setNumDay("2020");
        return upKeepInfo;
    }


}
