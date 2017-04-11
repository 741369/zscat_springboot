package com.zscat.cms.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageInfo;
import com.zscat.cms.model.CmsSite;
import com.zscat.cms.service.CmsSiteService;
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
@RequestMapping("/cms/site")
public class CmsSiteController{
	
	@Autowired
	private CmsSiteService CmssiteService;
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
    @Menu(type = "cms" , subtype = "site")
    public ModelAndView index(ModelMap map , HttpServletRequest request) {
    	
        ModelAndView mv =  new ModelAndView();
		mv.setViewName("/cms/site/site");
		PageInfo<CmsSite> siteList = CmssiteService.selectPage(getP(request), 10, new CmsSite());
		mv.addObject("siteList", siteList);
		return mv;
    }
    
    @RequestMapping("/add")
    @Menu(type = "cms" , subtype = "site")
    public ModelAndView add(ModelMap map , HttpServletRequest request) {
      
        ModelAndView mv =  new ModelAndView();
		mv.setViewName("/cms/site/add");
		return mv;
    }
    
    @RequestMapping("/save")
    @Menu(type = "cms" , subtype = "site")
    public String save(HttpServletRequest request ,@Valid CmsSite site) {
    	CmssiteService.insertSelective(site);
    	String msg = "cms_site_delete" ;
     return "redirect:/cms/site/index.html?msg="+msg;
    }
    
    @RequestMapping("/edit")
    @Menu(type = "cms" , subtype = "site")
    public ModelAndView edit(ModelMap map , @Valid String id) {
    	ModelAndView mv =  new ModelAndView();
    	mv.addObject("siteData", CmssiteService.selectByPrimaryKey(Long.parseLong(id)));
		mv.setViewName("/cms/site/edit");
		return mv;
    }
    
    @RequestMapping("/update")
    @Menu(type = "cms" , subtype = "site" , admin = true)
    public String update(HttpServletRequest request ,@Valid CmsSite site) {
    	CmssiteService.updateByPrimaryKeySelective(site);
    	String msg = "cms_site_update" ;
        return "redirect:/cms/site/index.html?msg="+msg;
    	
    }
    
    @RequestMapping("/delete")
    @Menu(type = "cms" , subtype = "site")
    public String delete(HttpServletRequest request ,@Valid CmsSite site) {
    	CmssiteService.delete(site);
    	String msg = "cms_site_delete" ;
    	return "redirect:/cms/site/index.html?msg="+msg;
    }
}