package com.test.demo.common.vo.sse;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Data
@Accessors(chain = true)
public class RequestQueryVo {

    private String saveDate;
}
