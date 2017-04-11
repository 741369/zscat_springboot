package com.zscat.sys.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.zscat.sys.model.SysUser;

public class SysUserUtils {


	/**
	 * session中的用户
	 */
	public static SysUser getSessionLoginUser(){
		return (SysUser) getSession().getAttribute(Constant.SESSION_LOGIN_USER);
	}
	
	/**
	 * 得到当前session
	 */
	public static HttpSession getSession() {
		HttpSession session = getCurRequest().getSession();
		return session;
	}
	/**
	 * @Title: getCurRequest
	 * @Description:(获得当前的request) 
	 * @param:@return 
	 * @return:HttpServletRequest
	 */
	public static HttpServletRequest getCurRequest(){
		RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
		if(requestAttributes != null && requestAttributes instanceof ServletRequestAttributes){
			ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes)requestAttributes;
			return servletRequestAttributes.getRequest();
		}
		return null;
	}
}
