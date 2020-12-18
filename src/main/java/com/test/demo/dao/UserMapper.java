package com.test.demo.dao;


import com.test.demo.domain.User;
import tk.mybatis.mapper.common.Mapper;

public interface UserMapper extends Mapper<User> {
	
	public int queryUserNameAndPassword(String acountNumber, String pwd);
}
