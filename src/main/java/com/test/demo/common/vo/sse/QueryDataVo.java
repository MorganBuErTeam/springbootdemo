package com.test.demo.common.vo.sse;

import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;

@Data
@Accessors(chain = true)
public class QueryDataVo {

    private String stockCode;
    private String stockAbbr;
    private String discloseDate;
    private String saveTime;
    private List<BulletinInfoVo> bulletinInfoList;
}
