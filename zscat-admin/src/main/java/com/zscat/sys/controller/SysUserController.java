package com.zscat.sys.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;




import com.github.pagehelper.PageInfo;
import com.zscat.sys.model.SysUser;
import com.zscat.sys.service.SysUserService;
import com.zscat.util.Menu;
import com.zscat.util.StringUtil;


/**
 *
 * @author 程序猿DD
 * @version 1.0.0
 * @blog http://blog.didispace.com
 *
 */
@Controller
@RequestMapping("/admin/user")
public class SysUserController{
	
	@Autowired
	private SysUserService SysUserService;
	public int getP(HttpServletRequest request) {
		int page = 0;
		String p = request.getParameter("p") ;
		if(!StringUtil.isBlank(p) && p.matches("[\\d]*")){
			page = Integer.parseInt(p) ;
			if(page > 0){
				page = page ;
			}
		}
		return page;
	}
    @RequestMapping("/index")
    @Menu(type = "admin" , subtype = "user")
    public ModelAndView index(ModelMap map , HttpServletRequest request) {
    	
        ModelAndView mv =  new ModelAndView();
		mv.setViewName("/admin/user/user");
		PageInfo<SysUser> userList = SysUserService.selectPage(getP(request), 10, new SysUser());
		mv.addObject("userList", userList);
		return mv;
    }
    
    @RequestMapping("/add")
    @Menu(type = "admin" , subtype = "user")
    public ModelAndView add(ModelMap map , HttpServletRequest request) {
      
        ModelAndView mv =  new ModelAndView();
		mv.setViewName("/admin/user/add");
		return mv;
    }
    
    @RequestMapping("/save")
    @Menu(type = "admin" , subtype = "user")
    public String save(HttpServletRequest request ,@Valid SysUser user) {
    	SysUserService.insertSelective(user);
    	String msg = "admin_user_delete" ;
     return "redirect:/admin/user/index.html?msg="+msg;
    }
    
    @RequestMapping("/edit")
    @Menu(type = "admin" , subtype = "user")
    public ModelAndView edit(ModelMap map , @Valid String id) {
    	ModelAndView mv =  new ModelAndView();
    	mv.addObject("userData", SysUserService.selectByPrimaryKey(Long.parseLong(id)));
		mv.setViewName("/admin/user/edit");
		return mv;
    }
    
    @RequestMapping("/update")
    @Menu(type = "admin" , subtype = "user" , admin = true)
    public String update(HttpServletRequest request ,@Valid SysUser user) {
    	SysUserService.updateByPrimaryKeySelective(user);
    	String msg = "admin_user_delete" ;
        return "redirect:/admin/user/index.html?msg="+msg;
    	/*User tempUser = userRepository.getOne(user.getId()) ;
    	User exist = userRepository.findByUsername(user.getUsername()) ;
    	if(exist==null || exist.getId().equals(user.getId())){
	    	if(tempUser != null){
	    		tempUser.setUname(user.getUname());
	    		tempUser.setUsername(user.getUsername());
	    		tempUser.setEmail(user.getEmail());
	    		tempUser.setMobile(user.getMobile());
	    		tempUser.setAgent(user.isAgent());
	    		tempUser.setOrgi(super.getOrgi(request));
	    		if(!StringUtils.isBlank(user.getPassword())){
	    			tempUser.setPassword(UKTools.md5(user.getPassword()));
	    		}
	    		if(tempUser.getCreatetime() == null){
	    			tempUser.setCreatetime(new Date());
	    		}
	    		tempUser.setUpdatetime(new Date());
	    		userRepository.save(tempUser) ;
	    	}
    	}
    	return request(super.createRequestPageTempletResponse("redirect:/admin/user/index.html"));*/
    }
    
    @RequestMapping("/delete")
    @Menu(type = "admin" , subtype = "user")
    public String delete(HttpServletRequest request ,@Valid SysUser user) {
    	SysUserService.delete(user);
    	String msg = "admin_user_delete" ;
    	/*
    	if(user!=null){
	    	List<UserRole> userRole = userRoleRes.findByOrgiAndUser(super.getOrgi(request), user) ;
	    	userRoleRes.delete(userRole);	//删除用户的时候，同时删除用户对应的
	    	user = userRepository.getOne(user.getId()) ;
	    	user.setDatastatus(true);
	    	userRepository.save(user) ;
    	}else{
    		msg = "admin_user_not_exist" ;
    	}
    	return request(super.createRequestPageTempletResponse("redirect:/admin/user/index.html?msg="+msg));*/
    	 return "redirect:/admin/user/index.html?msg="+msg;
    }
}