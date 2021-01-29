package com.test.demo.test.designMode.DecoratorMode;

/**
 * 装饰模式
 *      给类对象增加新的功能，装饰方法与具体的内部逻辑无关
 */
public class DecoratorMode implements Source{

    private Source source;

    public void decotatel(){
        System.out.println("decotatel");
    }

    @Override
    public void method() {
        decotatel();
        source.method();
    }

}
