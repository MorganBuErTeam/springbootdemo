package com.test.demo.common.vo.smt;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 */
@Data
public class OutboundDetails implements Serializable {

    @Id
    @GeneratedValue(generator = "JDBC")
    @ApiModelProperty(value="主键id")
    private  int id;

    @Column(name="order_no")
    @ApiModelProperty(value="申请单号")
    private String orderNo;

    @Column(name="materiel_no")
    @ApiModelProperty(value="物料号")
    private String materielNo;

    @Column(name="materiel_name")
    @ApiModelProperty(value="物料名称")
    private String materielName;

    @Column(name="materiel_barCode")
    @ApiModelProperty(value="物料条码")
    private String materielBarCode;  //todo

    @Column(name="bom_count")
    @ApiModelProperty(value="Bom用量")
    private String bomCount;  //todo

    @Column(name="loss_rate")
    @ApiModelProperty(value="损耗率")
    private String lossRate;  //todo

    @Column(name="bin_location")
    @ApiModelProperty(value="储位")
    private String binLocation;  //todo

    @Column(name="attribute")
    @ApiModelProperty(value="属性")
    private String attribute;//自制/外购

    @Column(name="model")
    @ApiModelProperty(value="规格型号")
    private String model;

    @Column(name="storehouse_count")
    @ApiModelProperty(value="库存数量")
    private Integer storehouseCount;

    @Column(name="warehousing_batch")
    @ApiModelProperty(value="入库批次")
    private String warehousingBatch;

    @Column(name="storehouse")
    @ApiModelProperty(value="所在库位")
    private String storeHouse;

    @Column(name="outbound_count")
    @ApiModelProperty(value="出库数量")
    private Integer outboundCount;   //todo smt投料数量 count

    @Column(name="already_outbound_count")
    @ApiModelProperty(value="已出库数量")
    private Integer alreadyOutboundCount;

    @Column(name="wait_outbound_count")
    @ApiModelProperty(value="待出库数量")
    private Integer waitOutboundCount;
    
    @Column(name="measm_sheet")
    @ApiModelProperty(value="基本计量单")
    private String measmSheet;
    
    @Column(name="stock_count")
    @ApiModelProperty(value="库存数量")
    private Integer stockCount;
    
    @Column(name="weight")
    @ApiModelProperty(value="承重范围/KG")
    private Integer weight;
    
    @Column(name="frequency")
    @ApiModelProperty(value="频次")
    private String frequency;
    
    @Column(name="state")
    @ApiModelProperty(value="状态")
    private Integer state;//1.待出库 2.出库中 3.已出库
    
    @Column(name="createtime")
    private Date createTime;

    @Column(name="updatetime")
    private Date updateTime;

    @Column(name="memo")
    @ApiModelProperty(value="备注")
    private String memo;

}
