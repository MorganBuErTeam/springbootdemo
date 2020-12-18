package com.test.demo.test;

/**
 * @Description: 单例模式
 * @Author: zY
 * @Date: 2020/01/02 10:21
 */
public class Singleton {
    //1.私有的构造方法  让无参构造变成私有 保证外面改变不可随便创建
    private Singleton(){};
    //2. 私有的静态的当前类对象作为属性 volatile强制制止指令重排
    private volatile static  Singleton singleton = new Singleton();// 饿汉式 立即加载
    //  private static Singleton singleton; // 懒汉式加载
    // 3.公开的静态的方法返回当前对象
    public Singleton getSingleton(){
        if(singleton==null){
            synchronized (this){
                if(singleton==null){
                    singleton =  new Singleton(); //  懒汉式加载
                }
                return singleton;
            }
        }
        return null;
    }
    //饿汉式(立即加载):对象启动时就加载  优点:不会产生 空指针异常   缺点:会给服务器带来压力
    //懒汉式(延迟加载) :需要的时候再去加载  优点:不会浪费空间             缺点: 可能会产生空指针
    //生命周期托管式:对象加载过程交给别人
}
