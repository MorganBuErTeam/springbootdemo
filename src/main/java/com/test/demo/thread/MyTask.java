package com.test.demo.thread;

import java.util.Date;

import com.test.demo.constant.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MyTask {
    Logger logger = LoggerFactory.getLogger(MyTask.class);
    @Scheduled(initialDelay = 1000, fixedDelay = 1000)
    public void firstTask() {  //在tomcat启动完毕后延迟1秒钟开始运行,之后每隔1秒运行一次
        logger.info("{}:这是第1个定时任务", Constant.SDF.format(new Date()));
    }
    @Scheduled(cron = "*/5 * * * * ?")
    public void secondTask() { //每隔5秒钟运行一次
        logger.info("{}:这是第2个定时任务", Constant.SDF.format(new Date()));
    }

    //上面我们演示的是最简单的定时任务,这种任务有着固定的频率或间隔, 当然在实际开发中我们也会碰到定时规则比较复杂的情况, 这时候我们就需要用到强大的cron表达式了。
    //cron表达式是一个字符串, 用来定义复杂的定时规则, 由七部分组成, 每部分中间用空格隔开, 每部分的含义如下表所示:
    //组成部分	含义	取值范围
    //第一部分	Seconds (秒) 	0－59
    //第二部分	Minutes(分)	0－59
    //第三部分	Hours(时)	0-23
    //第四部分	Day-of-Month(天)	1-31
    //第五部分	Month(月)	0-11或JAN-DEC
    //第六部分	Day-of-Week(星期)	1-7(1表示星期日)或SUN-SAT
    //第七部分	Year(年) 可选	1970-2099
    //另外, cron表达式还可以包含一些特殊符号来定义更加灵活的定时规则, 如下表所示:
    //符号	含义
    //?	表示不确定的值,任意的一天
    //*	表示整个时间段
    //,	设置多个值,例如”26,29,33”表示在26分,29分和33分各自运行一次任务
    //-	设置取值范围,例如”5-20”，表示从5分到20分钟每分钟运行一次任务
    ///	设置频率或间隔,如"1/15"表示从1分开始,每隔15分钟运行一次任务
    //L	用于每月，或每周，表示每月的最后一天，或每个月的最后星期几,例如"6L"表示"每月的最后一个星期五"
    //W	表示离给定日期最近的工作日,例如"15W"放在每月（day-of-month）上表示"离本月15日最近的工作日"
    //#	表示该月第几个周X。例如”6#3”表示该月第3个周五
    //为了让大家更熟悉cron表达式的用法, 接下来我们给大家列举了大量的例子, 如下表所示:
    //cron表达式	含义
    //*/5 * * * * ?	每隔5秒运行一次任务
    //0 0 23 * * ?	每天23点运行一次任务
    //0 0 1 1 * ?	每月1号凌晨1点运行一次任务
    //0 0 23 L * ?	每月最后一天23点运行一次任务
    //0 26,29,33 * * * ?	在26分、29分、33分运行一次任务
    //0 0 0,13,18,21 * * ?	每天的0点、13点、18点、21点都运行一次任务
    //0 0/30 9-17 * * ?	朝九晚五工作时间内每半小时运行一次任务
    //0 15 10 ? * 6#3	每月的第三个星期五上午10:15运行一次任务



}
