package com.test.demo.common.vo.smt;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 *
 */
@Data
@Accessors(chain = true)
public class OutboundDetailsVo implements Serializable {

    private String relationworkNo; //生产任务单号
    private String productCode; //产品代码
    private String productName; //产品名称
    private String sourceOrderId;   //源单单号
    private String count;   //数量
    private String line;    //线别
    private List<OutboundDetails> outboundDetails; //投料明细
    private String makingTime;  //制单时间
    private Integer applicationformState;    //smt:收料单状态(0-待处理 1-处理中 2-已处理)
}
