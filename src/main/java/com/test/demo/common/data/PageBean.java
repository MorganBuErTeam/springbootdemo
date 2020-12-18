package com.test.demo.common.data;

import lombok.Data;

import java.util.List;

/**
 * @author cj
 **/
@Data
public class PageBean<T> {

    List<T> rows;

    long total;

    public PageBean(List<T> rows , long total){
        this.rows = rows;
        this.total = total;
    }
}
