
package com.test.demo;

import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;

import javax.annotation.PostConstruct;


/**
 * 启动类
 */
@SpringBootApplication
@ComponentScan(basePackages={"com.test.demo"})
@NacosPropertySource(dataId = "nacostest", autoRefreshed = true)
public class Application {
    //@EnableScheduling 定时任务注解
//    @EnableJms    //启动消息队列
	public static void main(String[] args) {
        new SpringApplicationBuilder(Application.class).run(args);
    }



    @PostConstruct
    public void run(){
        System.out.println("程序启动时执行");
    }



}
