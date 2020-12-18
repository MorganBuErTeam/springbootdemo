package com.test.demo.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.test.demo.common.data.PageBean;
import com.test.demo.common.service.impl.AbstractBaseService;
import com.test.demo.common.util.ExcelUtils;
import com.test.demo.common.util.PageCondition;
import com.test.demo.constant.Constant;
import com.test.demo.dao.MaterielMapper;
import com.test.demo.domain.MaterielMaster;
import com.test.demo.httpclient.HttpAPIService;
import com.test.demo.service.MaterielService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Description: ${description}
 * @Author: zY
 * @Date: 2020/03/05 10:26
 */
@Service
@Slf4j
public class MaterielServiceImpl extends AbstractBaseService<MaterielMapper, MaterielMaster> implements MaterielService {

    @Autowired
    private MaterielMapper materielMapper;
    @Autowired
    HttpAPIService httpAPIService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public String importExcelInfo(MultipartFile file) throws Exception {
        log.info("物料导入开始");
        String msg = null;// 返回信息
        try {
            InputStream in = file.getInputStream();     //数据导入
            List<List<String>> listob = ExcelUtils.getBankListByExcel(in, file.getOriginalFilename());
            List<MaterielMaster> entityList = new Vector<>();//新增集合
            Map<String, MaterielMaster> updateMap = new ConcurrentHashMap<String, MaterielMaster>();//更新集合
            if (listob == null) {
                return null;
            }
            int count = 300;
            int listSize = listob.get(0).size();
            int runSize = (listSize / count) + 1;
            ExecutorService executorService = Executors.newFixedThreadPool(runSize);
            CountDownLatch downLatch = new CountDownLatch(listob.get(0).size());
            for (String str : listob.get(0)) {
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        getString(entityList, updateMap, str);
                        downLatch.countDown();
                    }
                });
            }
            downLatch.await();
            if (updateMap.size() > 0) {
                System.out.println("更新条数:"+updateMap.size());
                //去除重复，map转list
                List<MaterielMaster> mapValueList = new ArrayList<MaterielMaster>(updateMap.values());
                //修改
                CountDownLatch undateDownLatch = new CountDownLatch(mapValueList.size());
                for (int i = 0; i < mapValueList.size(); i++) {
                    int finalI = i;
                    executorService.execute(new Runnable() {
                        @Override
                        public void run() {
                            updateMapMethod(mapValueList, finalI);
                            undateDownLatch.countDown();
                        }
                    });
                }
                undateDownLatch.await();
            }
            if (entityList.size() > 0) {
                System.out.println("新增条数"+entityList.size());
                //新增
                CountDownLatch insertDownLatch = new CountDownLatch(entityList.size());
                for (int i = 0; i < entityList.size(); i++) {
                    int finalI = i;
                    executorService.execute(new Runnable() {
                        @Override
                        public void run() {
                            inserListMethod(entityList, finalI);
                            insertDownLatch.countDown();
                        }
                    });
                }
                insertDownLatch.await();
            }
            executorService.shutdown();
        } catch (Exception e) {
            msg = "异常";
            log.error("导入excel失败：", e);
            throw new Exception(msg);
        }
        log.info("物料导入结束");
        return null;
    }

    public void inserListMethod(List<MaterielMaster> entityList, int finalI) {
        MaterielMaster entity = entityList.get(finalI);
        entity.setState(1).setCreateTime(new Date()); //todo wms赋值字段
        materielMapper.insertSelective(entity);
        if ("2".equals(entity.getAreaType())) {
            //新增物料存储预警表
        }
        entity.setId(0);
    }

    public void updateMapMethod(List<MaterielMaster> mapValueList, int finalI) {
        MaterielMaster entity = mapValueList.get(finalI);
        materielMapper.updateByPrimaryKeySelective(entity);
        if ("2".equals(entity.getAreaType())) {
            //更新物料存储预警表

        }
    }

    public String getString(List<MaterielMaster> entityList, Map<String, MaterielMaster> updateMap, String str) {
        boolean isAdd = true;
        String valueArr[] = str.split("￥");
        for (int j = 0; j < valueArr.length; j++) {
            valueArr[j] = valueArr[j].trim();
        }
        String valueArr1 = "";
        String valueArr2 = "";
        String valueArr3 = "";
        String valueArr4 = "";
        String valueArr5 = "";
        String valueArr6 = "";
        String valueArr7 = "";
        String valueArr8 = "";
        String valueArr9 = "";
        switch (valueArr.length) {
            case 1:
                valueArr1 = valueArr[0];
                valueArr2 = "";
                valueArr3 = "";
                valueArr4 = "";
                valueArr5 = "";
                valueArr6 = "";
                valueArr7 = "0";
                valueArr8 = "0";
                valueArr9 = "";
                break;
            case 2:
                valueArr1 = valueArr[0];
                valueArr2 = valueArr[1];
                valueArr3 = "";
                valueArr4 = "";
                valueArr5 = "";
                valueArr6 = "";
                valueArr7 = "0";
                valueArr8 = "0";
                valueArr9 = "";
                break;
            case 3:
                valueArr1 = valueArr[0];
                valueArr2 = valueArr[1];
                valueArr3 = valueArr[2];
                valueArr4 = "";
                valueArr5 = "";
                valueArr6 = "";
                valueArr7 = "0";
                valueArr8 = "0";
                valueArr9 = "";
                break;
            case 4:
                valueArr1 = valueArr[0];
                valueArr2 = valueArr[1];
                valueArr3 = valueArr[2];
                valueArr4 = valueArr[3];
                valueArr5 = "";
                valueArr6 = "";
                valueArr7 = "0";
                valueArr8 = "0";
                valueArr9 = "";
                break;
            case 5:
                valueArr1 = valueArr[0];
                valueArr2 = valueArr[1];
                valueArr3 = valueArr[2];
                valueArr4 = valueArr[3];
                valueArr5 = valueArr[4];
                valueArr6 = "";
                valueArr7 = "0";
                valueArr8 = "0";
                valueArr9 = "";
                break;
            case 6:
                valueArr1 = valueArr[0];
                valueArr2 = valueArr[1];
                valueArr3 = valueArr[2];
                valueArr4 = valueArr[3];
                valueArr5 = valueArr[4];
                valueArr6 = valueArr[5];
                valueArr7 = "0";
                valueArr8 = "0";
                valueArr9 = "";
                break;
            case 7:
                valueArr1 = valueArr[0];
                valueArr2 = valueArr[1];
                valueArr3 = valueArr[2];
                valueArr4 = valueArr[3];
                valueArr5 = valueArr[4];
                valueArr6 = valueArr[5];
                valueArr7 = valueArr[6];
                valueArr8 = "0";
                valueArr9 = "";
                break;
            case 8:
                valueArr1 = valueArr[0];
                valueArr2 = valueArr[1];
                valueArr3 = valueArr[2];
                valueArr4 = valueArr[3];
                valueArr5 = valueArr[4];
                valueArr6 = valueArr[5];
                valueArr7 = valueArr[6];
                valueArr8 = valueArr[7];
                valueArr9 = "";
                break;
            case 9:
                valueArr1 = valueArr[0];
                valueArr2 = valueArr[1];
                valueArr3 = valueArr[2];
                valueArr4 = valueArr[3];
                valueArr5 = valueArr[4];
                valueArr6 = valueArr[5];
                valueArr7 = valueArr[6];
                valueArr8 = valueArr[7];
                valueArr9 = valueArr[8];
                break;
            default:
                break;
        }
        if(valueArr.length==0){
            return null;
        }
        if(null==valueArr1 || "".equals(valueArr1)){
            return null;
        }
        MaterielMaster oldEntity = materielMapper.queryMaterielMasterByNo(valueArr1);
        if (null != oldEntity) {
            //更新
            MaterielMaster entityNew = new MaterielMaster(valueArr1, valueArr2, valueArr3, valueArr4, valueArr5, valueArr6, valueArr7, valueArr8, valueArr9);
            entityNew.setId(oldEntity.getId()).setUpdateTime(new Date());
            updateMap.put(oldEntity.getMaterielNo(), entityNew);
            isAdd = false;
        }
        if (isAdd) {
            //新增
            entityList.add(new MaterielMaster(valueArr1, valueArr2, valueArr3, valueArr4, valueArr5, valueArr6, valueArr7, valueArr8, valueArr9));
        }
        return valueArr9;
    }



}
