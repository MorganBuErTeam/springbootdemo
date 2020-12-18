package com.test.demo.test.IniAnalysis;

import com.sun.scenario.effect.Offset;
import lombok.Data;

import java.io.IOException;
import java.util.HashMap;

/**
 * @Author: zpyu521
 * @Date: 2019/9/12
 * @Description:
 * @Version: 1.0
 */
@Data
public class Test01 {

    private int iNo;
    private Offset offsets[];
    private String arr[][];

    public static void main(String[] args) throws IOException {
        System.out.println(System.getProperty("user.dir"));
        ReadProperty rp = new ReadProperty("src\\main\\resources\\Test.ini");
        HashMap<String, String> shuzu1 = rp.GetAllSectionValues("shuzu1");
    }

}
