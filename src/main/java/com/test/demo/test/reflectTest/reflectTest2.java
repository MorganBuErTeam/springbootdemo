package com.test.demo.test.reflectTest;

import com.test.demo.domain.Task;

import java.lang.reflect.Field;
import java.util.Date;

/**
 * @Description: ${description}
 * @Author: zY
 * @Date: 2020/01/14 10:43
 */
public class reflectTest2 {

    public static void main(String[] args) {
        Task agvplCparamInfo;//小车实时信息
        Task tempAgvRealInfo;//记录上一次小车实时信息
        agvplCparamInfo = new Task();
        agvplCparamInfo.setTaskName("3");
        agvplCparamInfo.setState(3);
        tempAgvRealInfo = (Task) agvplCparamInfo.clone();

        Class clazz = agvplCparamInfo.getClass();
        Class clazzTwo = tempAgvRealInfo.getClass();
        Field[] fields = clazz.getDeclaredFields();
        Field[] fieldsTwo = clazzTwo.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            //将所有属性的访问权限设为true。
            //因为JavaBean中所有的属性访问权限都是private，所以无法直接获得所有属性，
            field.setAccessible(true);
            Field fieldTwo = fieldsTwo[i];
            fieldTwo.setAccessible(true);
            try {
                // 获取指定对象的当前字段的值
                Object fieldVal = field.get(agvplCparamInfo);
                Object fieldValTwo = field.get(tempAgvRealInfo);
                if(!fieldVal.equals(fieldValTwo)){
                    System.out.println(field.getName());
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private void changeAgvLogInfo() {
            /*equals比较对象值,==比较对象引用地址*/
//        if (!agvplCparamInfo.equals(tempAgvRealInfo)) {
            /*info.clone  实现接口,重写方法*/
//            tempAgvRealInfo = (AGVPLCparamInfo) agvplCparamInfo.clone();
//            logger.info("{}:{}号车,在{},实时的状态是\n{}", Constant.SDF.format(new Date()),
//                    tempAgvRealInfo.getAgv_no(), tempAgvRealInfo.getAddress(), tempAgvRealInfo.toString());
            //持久化实时信息

//        }
    }


}
