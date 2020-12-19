package com.test.demo.common.vo.sse;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class ComparisonVo {

    private String stockCode;
    private String stockAbbr;
    private String discloseDate;
    private String receiptNumber;
    private String issueNumber;
    private boolean isAccordance;
    private List<ComparisonBulletinInfoVo> receiptBulletinInfoList;
    private List<ComparisonBulletinInfoVo> issueBulletinInfoList;
}
