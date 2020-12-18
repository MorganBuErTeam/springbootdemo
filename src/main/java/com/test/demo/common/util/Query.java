package com.test.demo.common.util;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 查询参数
 * @author cj
 **/
public class Query extends LinkedHashMap<String, Object> {
    //当前页码
    private int page = 1;
    //每页条数
    private int limit = 10;
    //true：模糊查询条件/false:精确查询条件
    private boolean isLike;
    
    //true：排序条件执行/false:排序条件不执行
    private boolean isOrderBy;
    
    private String orderBy;//排序条件
    
    //true：时间段条件执行/false:时间段条件不执行
    private boolean isQueryByDate = false;
    private String name;//时间字段的属性名称
    private Date startDate;//开始时间
    
    private Date endDate;//结束时间
    
    public Query(Map<String, Object> params){
        this.putAll(params);
        //分页参数
        if(params.get("page")!=null) {
            this.page = Integer.parseInt(params.get("page").toString());
        }
        if(params.get("limit")!=null) {
            this.limit = Integer.parseInt(params.get("limit").toString());
        }
        if(params.get("isLike")!=null) {
            this.isLike = Boolean.parseBoolean(params.get("isLike").toString());
        }
        if(params.get("isOrderBy")!=null) {
            this.isOrderBy = Boolean.parseBoolean(params.get("isOrderBy").toString());
        }
        if(params.get("orderBy")!=null) {
            this.orderBy = params.get("orderBy").toString();
        }
        if(params.get("isQueryByDate")!=null) {
            this.isQueryByDate = Boolean.parseBoolean(params.get("isQueryByDate").toString());
        }
        if(params.get("startDate")!=null) {
            this.startDate =  DateUtil.stringToDate(params.get("startDate").toString());
        }
        if(params.get("endDate")!=null) {
            this.endDate = DateUtil.stringToDate(params.get("endDate").toString());
        }
        if(params.get("name")!=null) {
            this.name = params.get("name").toString();
        }
       
        this.remove("name");
        this.remove("page");
        this.remove("limit");
        this.remove("isLike");
        this.remove("isOrderBy");
        this.remove("orderBy");
        this.remove("isQueryByDate");
        this.remove("startDate");
        this.remove("endDate");
    }

    public int getPage() {
        return page;
    }

    public int getLimit() {
        return limit;
    }

	public boolean isLike() {
		return isLike;
	}

	public boolean isOrderBy() {
		return isOrderBy;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public boolean isQueryByDate() {
		return isQueryByDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public String getName() {
		return name;
	}

}
