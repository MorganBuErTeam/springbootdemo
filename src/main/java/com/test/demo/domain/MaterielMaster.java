package com.test.demo.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @Description: 物料基础信息表
 * @Author: zY
 * @Date: 2020/03/04 14:17
 */
@Data
@Table(name="t_materiel")
@Accessors(chain = true)
public class MaterielMaster implements Serializable {

    @Id
    @GeneratedValue(generator = "JDBC")
    private  int id;    //编号（自增）

    @Column(name="area_type")
    private String areaType;//库区类型:1.虚拟库 2.立体库 3.电子标签库

    @Column(name="materiel_no")
    private String materielNo;    //物料号

    @Column(name="materiel_name")
    private String materielName;    //物料名称

    @Column(name="materiel_names")
    private String materielNames;    //物料全名

    @Column(name="model")
    private String model;    //规格型号

    @Column(name="attribute")
    private String attribute;    //物料属性

    @Column(name="measm_sheet")
    private String measmSheet;    //基本计量单 pcs

    @Column(name="bin_location")
    private String binLocation; //储位

    @Column(name="default_warehouse")
    private String defaultWarehouse;    //默认仓库 配件库（汽车空调）配件库（调光电机）

    @Column(name="fullbox_count")
    private String fullboxCount;    //满箱数量

    @Column(name="weight")
    private String weight;    //重量/KG

    @Column(name="frequency")
    private String frequency;   //频次

    @Column(name="attrition_rate")
    private String attritionRate; //损耗率

    @Column(name="safety_number")
    private Integer safetyNumber;    //库存安全数

    @Column(name="state")
    private Integer state;    //状态 1.正常 2下架

    @Column(name="create_time")
    private Date createTime;    //创建时间

    @Column(name="update_time")
    private Date updateTime;    //更新时间

    @Column(name="create_user")
    private Integer createUser;  //创建用户 (1：系统管理员；2：普通用户)

    @Column(name="update_user")
    private Integer updateUser;  //更新用户  (1：系统管理员；2：普通用户)

    @Column(name="memo")
    private String memo; //备注

    public MaterielMaster(String materielNo, String  materielName, String model ,
                          String  attribute, String  measmSheet, String  fullboxCount, String  weight, String  frequency, String areaType) {
        super();
        this.materielNo =  materielNo;
        this.materielName =materielName;
        this.model = model ;
        this.attribute=  attribute;
        this.measmSheet = measmSheet ;
        this.fullboxCount =fullboxCount;
        this.weight = weight;
        this.frequency =frequency  ;
        this.areaType=areaType;

    }

    public MaterielMaster() {
        super();
    }


}
