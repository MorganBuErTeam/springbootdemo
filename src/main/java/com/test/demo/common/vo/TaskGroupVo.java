package com.test.demo.common.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor //无参构造
@AllArgsConstructor//有参构造
public class TaskGroupVo {



    private String taskName;//任务名称

    private String taskType;//任务类型
}
