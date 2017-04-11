package com.zscat.sys.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zscat.sys.service.SysUserService;
import com.zscat.util.Menu;

@Controller
public class AdminController {
	
	@Autowired
	private SysUserService SysUserService;

	
	
    @RequestMapping("/admin")
    public String index(ModelMap map ,HttpServletRequest request) {
		return "admin/index";
    }
    
    @RequestMapping("/admin/content")
    @Menu(type = "admin" , subtype = "content")
    public String content(ModelMap map , HttpServletRequest request) {
    	
    	return "admin/content";
    }

    @RequestMapping("/cms")
    public String cmsIndex(ModelMap map ,HttpServletRequest request) {
		return "cms/index";
    }
    
    @RequestMapping("/cms/content")
    @Menu(type = "cms" , subtype = "content")
    public String cmsContent(ModelMap map , HttpServletRequest request) {
    	
    	return "cms/content";
    }
}