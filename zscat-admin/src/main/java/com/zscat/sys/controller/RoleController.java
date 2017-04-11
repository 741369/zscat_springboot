package com.zscat.sys.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zscat.sys.model.SysResource;
import com.zscat.sys.model.SysRole;
import com.zscat.sys.model.SysUser;
import com.zscat.sys.model.SysUserRole;
import com.zscat.sys.service.SysResService;
import com.zscat.sys.service.SysRoleService;
import com.zscat.sys.service.SysUserService;
import com.zscat.util.Menu;
import com.zscat.util.StringUtil;



@Controller
@RequestMapping("/admin/role")
public class RoleController{
	
	@Autowired
	private SysRoleService SysRoleService;
	@Autowired
	private SysUserService SysUserService;
	@Autowired
	private com.zscat.sys.service.SysUserRoleService SysUserRoleService;
	@Autowired
	private SysResService SysResService;

    @RequestMapping("/index")
    @Menu(type = "admin" , subtype = "role")
    public ModelAndView index(ModelMap map , HttpServletRequest request , @Valid Long role) {
    	List<SysRole> roleList = SysRoleService.select(new SysRole());
    	map.addAttribute("roleList", roleList);
    	if(roleList.size() > 0){
    		SysRole roleData = null ;
    		if(!StringUtil.isBlank(role)){
    			for(SysRole data : roleList){
    				if(data.getId().equals(role)){
    					roleData = data ;
    					map.addAttribute("roleData", data);
    				}
    			}
    		}else{
    			roleData = roleList.get(0);
    			map.addAttribute("roleData",roleData );
    			
    		}
    		if(roleData!=null){
    			if(role == null){
    				map.addAttribute("userRoleList", SysRoleService.findUserByRoleId(roleData.getId()));
    			}
    			map.addAttribute("userRoleList", SysRoleService.findUserByRoleId(role));
    		}
    	}
    	return new ModelAndView("admin/role/role");
    }
    
    @RequestMapping("/add")
    @Menu(type = "admin" , subtype = "role")
    public ModelAndView add(ModelMap map , HttpServletRequest request) {
    	return new ModelAndView("admin/role/add");
    }
    
    @RequestMapping("/save")
    @Menu(type = "admin" , subtype = "role")
    public String save(HttpServletRequest request ,@Valid SysRole role) {
    	SysRole r = new SysRole();
    	r.setName(role.getName());
    	SysRole tempRole = SysRoleService.selectOne(r);
    	String msg = "admin_role_save_success" ;
    	if(tempRole != null){
    		msg =  "admin_role_save_exist";
    	}else{
    		SysRoleService.insertSelective(role) ;
    	}
    	 return "redirect:/admin/role/index.html?msg="+msg;
    }
    
    @RequestMapping("/seluser")
    @Menu(type = "admin" , subtype = "seluser" , admin = true)
    public ModelAndView seluser(ModelMap map , HttpServletRequest request , @Valid Long role) {
    	map.addAttribute("userList", SysUserService.select(new SysUser()));
    	SysRole roleData = SysRoleService.selectByPrimaryKey(role) ;
    //	map.addAttribute("userRoleList", SysUserRoleService.findByOrgiAndRole(super.getOrgi(request) , roleData)) ;
    	map.addAttribute("role", roleData) ;
        return new ModelAndView("admin/role/seluser");
    }
    
    
    @RequestMapping("/saveuser")
    @Menu(type = "admin" , subtype = "saveuser" , admin = true)
    public String saveuser(HttpServletRequest request ,@Valid Long[] users , @Valid Long role) {
    	SysRole roleData = SysRoleService.selectByPrimaryKey(role) ;
    	SysUserRole ur=new SysUserRole();
		ur.setRoleId(role);
    	List<SysUserRole> userRoleList = SysUserRoleService.select(ur);
    	
    	for(Long user : users){
    		boolean exist = false ;
    		for(SysUserRole userRole : userRoleList){
    			if(user.equals(userRole.getUserId())){
    				exist = true ; continue ;
    			}
    		}
    		if(exist == false) {
				SysUserRole userRole = new SysUserRole() ;
				userRole.setUserId(user);
				userRole.setRoleId(role);
				SysUserRoleService.insertSelective(userRole) ;
    		}
		}
    	return "redirect:/admin/role/index.html?role="+role;
   }
    
    @RequestMapping("/user/delete")
    @Menu(type = "admin" , subtype = "role")
    public String userroledelete(HttpServletRequest request ,@Valid Long id , @Valid String role) {
    	if(role!=null){
    		SysUserRoleService.deleteByPrimaryKey(id);
    	}
    	return "redirect:/admin/role/index.html?role="+role;
    }
    
    @RequestMapping("/edit")
    @Menu(type = "admin" , subtype = "role")
    public ModelAndView edit(ModelMap map , @Valid Long id) {
    	ModelAndView view = new ModelAndView();
    			view.setViewName("admin/role/edit");
    	view.addObject("roleData", SysRoleService.selectByPrimaryKey(id)) ;
        return view;
    }
    
    @RequestMapping("/update")
    @Menu(type = "admin" , subtype = "role")
    public String update(HttpServletRequest request ,@Valid SysRole role) {
    	SysRole tempRole = SysRoleService.selectByPrimaryKey(role.getId()) ;
    	String msg = "admin_role_update_success" ;
    	if(tempRole != null){
    		tempRole.setName(role.getName());
    		SysRoleService.updateByPrimaryKeySelective(tempRole) ;
    	}else{
    		msg =  "admin_role_update_not_exist";
    	}
    	 return "redirect:/admin/role/index.html?msg="+msg;
    }
    
    @RequestMapping("/delete")
    @Menu(type = "admin" , subtype = "role")
    public String delete(HttpServletRequest request ,@Valid SysRole role) {
    	String msg = "admin_role_delete" ;
    	if(role!=null){
	    	SysRoleService.deleteRoleAndUserRole(role);
    	}else{
    		msg = "admin_role_not_exist" ;
    	}
    	 return "redirect:/admin/role/index.html?msg="+msg;
    }
}