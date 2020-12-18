package com.test.demo.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.test.demo.common.service.BaseService;
import com.test.demo.common.vo.ResponseVo;
import com.test.demo.dao.UserMapper;
import com.test.demo.domain.User;


public interface UserService extends BaseService<UserMapper,User>{
    /**
    * @Description: 用户登录
    * @Param: String userAcountNumber(用户账号), String userPwd(密码)
    * 
    */
   public ResponseVo login(String acountNumber, String pwd,HttpSession httpSession,HttpServletRequest request,
                     HttpServletResponse response);
    
   
   
}
