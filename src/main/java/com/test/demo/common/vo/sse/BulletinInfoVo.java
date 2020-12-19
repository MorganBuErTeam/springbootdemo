package com.test.demo.common.vo.sse;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
@Data
@Accessors(chain = true)
public class BulletinInfoVo {

    private String bulletinId;
    private String bulletinTitle;
    private List<QueryAttachVo> attachList;
    private String auditorName;
    private String publishMedia;
}
