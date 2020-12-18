package com.test.demo.service;

import com.test.demo.common.data.PageBean;
import com.test.demo.common.service.BaseService;
import com.test.demo.common.util.PageCondition;
import com.test.demo.dao.MaterielMapper;
import com.test.demo.domain.MaterielMaster;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Description: 物料管理
 * @Author: zY
 * @Date: 2020/03/05 10:25
 */
public interface MaterielService extends BaseService<MaterielMapper,MaterielMaster> {

    /**
     * 导入excel
     * @param file
     * @return String
     * @throws Exception
     */
    public String importExcelInfo(MultipartFile file) throws Exception;




}
