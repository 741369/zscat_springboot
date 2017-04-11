package com.zscat.cms.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageInfo;
import com.zscat.cms.model.CmsLink;
import com.zscat.cms.service.CmsLinkService;
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
@RequestMapping("/cms/link")
public class CmsLinkController{
	
	@Autowired
	private CmsLinkService CmslinkService;
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
    @Menu(type = "cms" , subtype = "link")
    public ModelAndView index(ModelMap map , HttpServletRequest request) {
    	
        ModelAndView mv =  new ModelAndView();
		mv.setViewName("/cms/link/link");
		PageInfo<CmsLink> linkList = CmslinkService.selectPage(getP(request), 10, new CmsLink());
		mv.addObject("linkList", linkList);
		return mv;
    }
    
    @RequestMapping("/add")
    @Menu(type = "cms" , subtype = "link")
    public ModelAndView add(ModelMap map , HttpServletRequest request) {
      
        ModelAndView mv =  new ModelAndView();
		mv.setViewName("/cms/link/add");
		return mv;
    }
    
    @RequestMapping("/save")
    @Menu(type = "cms" , subtype = "link")
    public String save(HttpServletRequest request ,@Valid CmsLink link) {
    	CmslinkService.insertSelective(link);
    	String msg = "cms_link_delete" ;
     return "redirect:/cms/link/index.html?msg="+msg;
    }
    
    @RequestMapping("/edit")
    @Menu(type = "cms" , subtype = "link")
    public ModelAndView edit(ModelMap map , @Valid String id) {
    	ModelAndView mv =  new ModelAndView();
    	mv.addObject("linkData", CmslinkService.selectByPrimaryKey(Long.parseLong(id)));
		mv.setViewName("/cms/link/edit");
		return mv;
    }
    
    @RequestMapping("/update")
    @Menu(type = "cms" , subtype = "link" , admin = true)
    public String update(HttpServletRequest request ,@Valid CmsLink link) {
    	CmslinkService.updateByPrimaryKeySelective(link);
    	String msg = "cms_link_update" ;
        return "redirect:/cms/link/index.html?msg="+msg;
    	
    }
    
    @RequestMapping("/delete")
    @Menu(type = "cms" , subtype = "link")
    public String delete(HttpServletRequest request ,@Valid CmsLink link) {
    	CmslinkService.delete(link);
    	String msg = "cms_link_delete" ;
    	return "redirect:/cms/link/index.html?msg="+msg;
    }
}