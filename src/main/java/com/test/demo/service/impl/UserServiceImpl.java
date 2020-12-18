package com.test.demo.service.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.demo.common.service.impl.AbstractBaseService;
import com.test.demo.common.util.CookieUtils;
import com.test.demo.common.util.ResponseVoUtil;
import com.test.demo.common.vo.ResponseVo;
import com.test.demo.dao.UserMapper;
import com.test.demo.domain.User;
import com.test.demo.service.UserService;

@Service
public class UserServiceImpl extends AbstractBaseService<UserMapper, User> implements UserService {
	Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserMapper userMapper;

	@Override
	public ResponseVo login(String acountNumber, String pwd, HttpSession httpSession, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			if (StringUtils.isNotBlank(acountNumber) && StringUtils.isNotBlank(pwd)) {
				// 查询用户信息
				 int type= userMapper.queryUserNameAndPassword(acountNumber, pwd);
				 
				if (type != 0) {// 登录成功
					CookieUtils.saveCookie(request, response, "CookieType", type+"");
					return ResponseVoUtil.successResult("登录成功", "../infoSystem/taskInfo.html", type+"");
				} else {// 登录失败
					return ResponseVoUtil.failResult("登录失败");
				}
			}
		} catch (Exception e) {
			logger.error("用户信息查询是失败" + e.getMessage());
		}
		return ResponseVoUtil.failResult("登录失败");// 登录失败
	}

	@Override
	public void insert(User entity) {

	}

	@Override
	public void insertSelective(User entity) {

	}
}
