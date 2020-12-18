package com.test.demo.common.vo;

import lombok.Data;

/**
 * 返回json格式数据
 **/
@Data
public class ResponseVo {
    private Integer code;
    private String desc;
    private Object data;

    private ResponseVo() {
    }

    public Integer getCode() {
        return this.code;
    }

    public ResponseVo setCode(Integer code) {
        this.code = code;
        return this;
    }

    public String getDesc() {
        return this.desc;
    }

    public ResponseVo setDesc(String desc) {
        this.desc = desc;
        return this;
    }

    public Object getData() {
        return this.data;
    }

    public ResponseVo setData(Object data) {
        this.data = data;
        return this;
    }

    public static ResponseVo BUILDER() {
        return new ResponseVo();
    }
    
}
