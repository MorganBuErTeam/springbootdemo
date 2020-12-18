package com.test.demo.listener;

//import com.uv.gerry.thread.*;
import com.test.demo.thread.MyTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class Order implements CommandLineRunner {

   // @Autowired
   // private GetInitSource initSource;
   // @Autowired
   // private TaskToAGV taskToAGV;//任务下发


    @Override
    public void run(String... strings) throws Exception {
       // initSource.initSource();//获取配置数据信息
       // new Thread(taskToAGV,"任务调度").start();
    }
}
