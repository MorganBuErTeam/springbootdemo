package com.test.demo.common.vo.smt;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 *
 */
@Data
@Accessors(chain = true)
public class SmtInfoVo {
    private String rackNumber;//料架编号
    private String communicationStatus;//通讯状态
    private String equipmentStatus;//设备状态
}
