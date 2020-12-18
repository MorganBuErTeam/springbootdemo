package com.test.demo.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description: 保养信息
 * @Author: zY
 * @Date: 2020/01/06 9:45
 */
@Data
@Accessors(chain=true)
public class UpKeepInfo implements Serializable {

    private Integer id;

    private String deviceName; //设备名称

    private String numDay;  //多少天

    private Date createDate; //创建时间


}
