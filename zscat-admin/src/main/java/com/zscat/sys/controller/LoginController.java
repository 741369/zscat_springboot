package com.zscat.sys.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zscat.sys.model.SysResource;
import com.zscat.sys.model.SysUser;
import com.zscat.sys.service.SysResService;
import com.zscat.sys.service.SysUserService;
import com.zscat.sys.util.Constant;
import com.zscat.sys.util.SysUserUtils;
import com.zscat.util.StringUtil;


@Controller
public class LoginController {

	@Resource
	private SysResService sysResourceService;
	@Resource
	private SysUserService sysUserService;
	
	
	/**
	 * 管理主页
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/index")
	public String toIndex(Model model, HttpServletRequest request) {
		request.getSession().removeAttribute("code"); // 清除code
		if( SysUserUtils.getSessionLoginUser() == null){
			return "login";
		}
	//	model.addAttribute("pmenuList", sysResourceService.selectTop(0L));
	//	model.addAttribute("menuLists", SysUserUtils.getUserMenus());
//		String id = request.getParameter("order");
//		if (id != null && !id.equals("")) {
//			SysResource record=new SysResource();
//			record.setParentId(Long.parseLong(id));
//			List<SysResource> resourceList=sysResourceService.select(record, "sort");
//			
//			request.setAttribute("menuList", resourceList);
//		}else{
//			SysResource record=new SysResource();
//			record.setParentId(Long.parseLong("188"));
//			List<SysResource> resourceList=sysResourceService.select(record, "sort");
//			
//			request.setAttribute("menuList", resourceList);
//		}
		return "index";
	}

	/**
	 * 跳转到登录页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String toLogin() {
		if( SysUserUtils.getSessionLoginUser() != null ){
			return "redirect:/index";
		}
		return "login";
	}
	
	/**
	 * 登录验证
	 * 
	 * @param username
	 * @param password
	 * @param code
	 * @return
	 */
	@RequestMapping(value = "login", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> checkLogin(String username,
			String password, String code, HttpServletRequest request) {

		Map<String, Object> msg = new HashMap<String, Object>();
		HttpSession session = request.getSession();
		code = StringUtil.trim(code);
		username = StringUtil.trim(username);
		password = StringUtil.trim(password);
		Object scode = session.getAttribute("code");
		String sessionCode = null;
		if (scode != null)
			sessionCode = scode.toString();
		if (!StringUtil.equalsIgnoreCase(code, sessionCode)) {
			msg.put("error", "验证码错误");
			return msg;
		}
		SysUser user = sysUserService.checkUser(username, password);
		if (null != user) {
			
			session.setAttribute(Constant.SESSION_LOGIN_USER, user);
			
			//缓存用户
		//	SysUserUtils.cacheLoginUser(user);
			
			//设置并缓存用户认证
		//	SysUserUtils.setUserAuth();
			
		
		} else {
			msg.put("error", "用户名或密码错误");
		}
		return msg;
	}

	/**
	 * 用户退出
	 * 
	 * @return 跳转到登录页面
	 */
	@RequestMapping("logout")
	public String logout(HttpServletRequest request) {
	//	SysUserUtils.clearCacheUser(SysUserUtils.getSessionLoginUser().getId());
		request.getSession().invalidate();
		return "redirect:/login";
	}
	
	@RequestMapping("notauth")
	public String notAuth(){
		return "notauth";
	}
	
	@RequestMapping("notlogin")
	public String notLogin(){
		return "notlogin";
	}

}
