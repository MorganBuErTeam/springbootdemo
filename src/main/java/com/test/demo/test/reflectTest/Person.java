package com.test.demo.test.reflectTest;

/**
 * @Description: ${description}
 * @Author: zY
 * @Date: 2019/09/02 15:36
 */
public class Person {
    private int age;
    private String name;
    public Person(){

    }
    public Person(int age, String name){
        this.age = age;
        this.name = name;
    }

    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
