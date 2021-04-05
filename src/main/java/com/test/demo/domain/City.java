package com.test.demo.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author zf
 * @since 2021-04-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class City implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer cid;

    private String city;

    private Integer pid;


    public City() {
    }

    public City(String city) {
        this.city = city;
    }


}
