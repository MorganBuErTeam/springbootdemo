package com.test.demo.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import lombok.Data;

@Data
@Table(name = "t_user")
public class User implements Serializable {
	@Id
	private int id;// 编号（自增）

	@Column(name = "acount_number") 
	private String acountNumber;// 登录账号

	@Column(name = "pwd")
	private int pwd;// 用户密码,六位

	@Column(name = "u_type")
	private int type;// 用户类型1：普通用户；2：管理员

	@Column(name = "creat_time")
	private Date creatTime;// 纪录时间
	
	@Column(name = "create_user")
	private int createUser;// 操作人
	
	@Column(name = "update_time")
	private Date updateTime;// 更新时间
	
	@Column(name = "update_user")
	private int updateUser;// 操作人
	
	@Column(name = "state") 
	private String state;// 01-无效,02-有效
	
	@Column(name = "name") 
	private String name;// 名称
}
