package com.test.demo.controller;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.test.demo.common.controller.BaseController;
import com.test.demo.common.util.CookieUtils;
import com.test.demo.common.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.test.demo.domain.User;
import com.test.demo.service.UserService;
  /**
   * 用户信息
   * @author Administrator
   *
   */
@RestController
@RequestMapping("/userInfo")
public class UserController extends BaseController<UserService,User> {
	
	 @Autowired
	 private UserService userService;
	 
	
	
	  
	 /**
	  * 登录
	  * @param acountNumber 用户名
	  * @param pwd  密码
	  * @param httpSession
	  * @param request
	  * @param response
	  * @return
	  */
 	 @PostMapping("/login")
	 public ResponseVo login(@RequestParam("acountNumber")String acountNumber, @RequestParam("pwd") String pwd, HttpSession httpSession, HttpServletRequest request,
							 HttpServletResponse response){
		     
		    	 return this.userService.login(acountNumber,pwd,httpSession,request,response);
	  }
	  
	 /**
	  * 获取存储在cookie中的用户名
	  * @param request
	  * @param type
	  * @return
	  */
	 @PostMapping("/getLoginInfo")
	 public String getLoginInfo(HttpServletRequest request,int type){
		 return CookieUtils.getCookie(request, "CookieName");
	 }
	  
	 /**
	  * 清空cookie值
	  * @param request
	  * @param response
	  * @param type
	  */
	 @PostMapping("/clearCookie")
	 public void clearCookie(HttpServletRequest request,HttpServletResponse response,int type){
		  CookieUtils.saveCookie(request,response,"CookieName",null);
	 }
}
