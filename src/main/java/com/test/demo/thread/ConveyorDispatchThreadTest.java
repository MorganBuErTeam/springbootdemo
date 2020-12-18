package com.test.demo.thread;

import com.test.demo.dao.TaskMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @Description: 单元测试
 * @Author: zY
 * @Date: 2020/05/22 10:55
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ConveyorDispatchThreadTest {

    @Autowired
    private TaskMapper taskMapper;

    @Before
    public void setUp() throws Exception {
        System.out.println("开始");
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("结束");
    }

    @Test
    public void persistenceWarhouseData() {

        //逻辑处理
        taskMapper.selectConfirBargeCount();



    }


}