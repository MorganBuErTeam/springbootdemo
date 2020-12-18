package com.test.demo.test;

/**
 * @Description: 单例
 * @Author: zY
 * @Date: 2020/04/16 14:08
 */
public class SingleObject {

    //创建一个对象
    private static SingleObject instance=new SingleObject();
    //让构造函数为私有的,这样改类就不会被实例化
    private SingleObject(){}
    //获取唯一可用的对象
    public static SingleObject getInstance(){
        return instance;
    }
    public void showMessage(){
        System.out.println("这是一个单例对象!");
    }

    public static void main(String[] args) {
        //不合法的构造函数
        //编译时错误:构造函数 SingleObject() 是私有的
        //SingleObject object=new SingleObject();
        //获取唯一可用的对象
        SingleObject object=SingleObject.getInstance();
        //显示消息
        object.showMessage();
    }
}
