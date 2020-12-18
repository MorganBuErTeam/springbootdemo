package com.test.demo.test;

/**
 * @Description: ${description}
 * @Author: zY
 * @Date: 2019/12/12 13:49
 */
public enum EnumTest {
    USER(1), ADMIN(2);

    private int id;

    private EnumTest(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
