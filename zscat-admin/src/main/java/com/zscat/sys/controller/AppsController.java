package com.zscat.sys.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zscat.sys.model.SysUser;
import com.zscat.sys.service.SysUserService;
import com.zscat.util.Menu;

@Controller
public class AppsController {
	
	@Autowired
	private SysUserService SysUserService;
	

	@RequestMapping({"/apps/content"})
	@Menu(type="apps", subtype="content")
	public ModelAndView content(HttpServletRequest request){
		ModelAndView mv =  new ModelAndView();
		mv.setViewName("apps/index");
		List<SysUser> userList = SysUserService.select(new SysUser());
		mv.addObject("page", userList);
		return mv;
	}
	
	
	
	@RequestMapping({"/apps/onlineuser"})
	@Menu(type="apps", subtype="onlineuser")
	public  ModelAndView onlineuser(ModelMap map , HttpServletRequest request){
		ModelAndView mv =  new ModelAndView();
		mv.setViewName("apps/index");
		List<SysUser> userList = SysUserService.select(new SysUser());
		mv.addObject("page", userList);
		return mv;
	}
	
	
	
}
