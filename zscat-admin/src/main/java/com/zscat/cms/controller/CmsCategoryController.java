package com.zscat.cms.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageInfo;
import com.zscat.cms.model.CmsCategory;
import com.zscat.cms.service.CmsCategoryService;
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
@RequestMapping("/cms/category")
public class CmsCategoryController{
	
	@Autowired
	private CmsCategoryService CmscategoryService;
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
    @Menu(type = "cms" , subtype = "category")
    public ModelAndView index(ModelMap map , HttpServletRequest request) {
    	
        ModelAndView mv =  new ModelAndView();
		mv.setViewName("/cms/category/category");
		PageInfo<CmsCategory> categoryList = CmscategoryService.selectPage(getP(request), 10, new CmsCategory());
		mv.addObject("categoryList", categoryList);
		return mv;
    }
    
    @RequestMapping("/add")
    @Menu(type = "cms" , subtype = "category")
    public ModelAndView add(ModelMap map , HttpServletRequest request) {
      
        ModelAndView mv =  new ModelAndView();
		mv.setViewName("/cms/category/add");
		return mv;
    }
    
    @RequestMapping("/save")
    @Menu(type = "cms" , subtype = "category")
    public String save(HttpServletRequest request ,@Valid CmsCategory category) {
    	CmscategoryService.insertSelective(category);
    	String msg = "cms_category_delete" ;
     return "redirect:/cms/category/index.html?msg="+msg;
    }
    
    @RequestMapping("/edit")
    @Menu(type = "cms" , subtype = "category")
    public ModelAndView edit(ModelMap map , @Valid String id) {
    	ModelAndView mv =  new ModelAndView();
    	mv.addObject("categoryData", CmscategoryService.selectByPrimaryKey(Long.parseLong(id)));
		mv.setViewName("/cms/category/edit");
		return mv;
    }
    
    @RequestMapping("/update")
    @Menu(type = "cms" , subtype = "category" , admin = true)
    public String update(HttpServletRequest request ,@Valid CmsCategory category) {
    	CmscategoryService.updateByPrimaryKeySelective(category);
    	String msg = "cms_category_update" ;
        return "redirect:/cms/category/index.html?msg="+msg;
    	
    }
    
    @RequestMapping("/delete")
    @Menu(type = "cms" , subtype = "category")
    public String delete(HttpServletRequest request ,@Valid CmsCategory category) {
    	CmscategoryService.delete(category);
    	String msg = "cms_category_delete" ;
    	return "redirect:/cms/category/index.html?msg="+msg;
    }
}