package com.test.demo.service;


import com.test.demo.common.service.BaseService;
import com.test.demo.dao.MesInfoMapper;
import com.test.demo.domain.MesInfo;

public interface HttpClientService extends BaseService<MesInfoMapper,MesInfo> {
	
	public MesInfo requestMesToGetJob() throws Exception;

}
