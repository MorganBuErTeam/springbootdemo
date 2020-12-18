package com.test.demo.dao;

import com.test.demo.common.util.PageCondition;
import com.test.demo.domain.MaterielMaster;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @Description: ${description}
 * @Author: zY
 * @Date: 2020/03/05 10:23
 */
public interface MaterielMapper extends Mapper<MaterielMaster> {


    public MaterielMaster queryMaterielMasterByNo(@Param("materielNo") String materielNo);




}
