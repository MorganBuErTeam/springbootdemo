package com.test.demo.constant;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @description: 常量类
 **/
@Component
public class Constant {
    public static final String BYTE="byte";
    public static final int SELFMOTION=3;
    public static final int[] SITARGS= {111,113,115};
    public static final SimpleDateFormat SDF = new SimpleDateFormat("MM-dd HH:mm:ss");


}

