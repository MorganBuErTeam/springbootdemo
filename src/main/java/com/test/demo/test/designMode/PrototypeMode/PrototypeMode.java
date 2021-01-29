package com.test.demo.test.designMode.PrototypeMode;

/**
 * 原型模式:
 *      就是讲一个对象作为原型，使用clone()方法来创建新的实例。
 */
public class PrototypeMode implements Cloneable{

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    protected Object clone()   {
        try {
            //此处使用的是浅拷贝
            return super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }finally {
            return null;
        }
    }

    public static void main ( String[] args){
        PrototypeMode pro = new PrototypeMode();
        PrototypeMode pro1 = (PrototypeMode)pro.clone();
    }
}
