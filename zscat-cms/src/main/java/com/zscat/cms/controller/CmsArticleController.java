package com.zscat.cms.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageInfo;
import com.zscat.cms.model.CmsArticle;
import com.zscat.cms.service.CmsArticleService;
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
@RequestMapping("/cms/article")
public class CmsArticleController{
	
	@Autowired
	private CmsArticleService CmsArticleService;
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
    @Menu(type = "cms" , subtype = "article")
    public ModelAndView index(ModelMap map , HttpServletRequest request) {
    	
        ModelAndView mv =  new ModelAndView();
		mv.setViewName("/cms/article/article");
		PageInfo<CmsArticle> articleList = CmsArticleService.selectPage(getP(request), 10, new CmsArticle());
		mv.addObject("articleList", articleList);
		return mv;
    }
    
    @RequestMapping("/add")
    @Menu(type = "cms" , subtype = "article")
    public ModelAndView add(ModelMap map , HttpServletRequest request) {
      
        ModelAndView mv =  new ModelAndView();
		mv.setViewName("/cms/article/add");
		return mv;
    }
    
    @RequestMapping("/save")
    @Menu(type = "cms" , subtype = "article")
    public String save(HttpServletRequest request ,@Valid CmsArticle article) {
    	CmsArticleService.insertSelective(article);
    	String msg = "cms_article_delete" ;
     return "redirect:/cms/article/index.html?msg="+msg;
    }
    
    @RequestMapping("/edit")
    @Menu(type = "cms" , subtype = "article")
    public ModelAndView edit(ModelMap map , @Valid String id) {
    	ModelAndView mv =  new ModelAndView();
    	mv.addObject("articleData", CmsArticleService.selectByPrimaryKey(Long.parseLong(id)));
		mv.setViewName("/cms/article/edit");
		return mv;
    }
    
    @RequestMapping("/update")
    @Menu(type = "cms" , subtype = "article" , admin = true)
    public String update(HttpServletRequest request ,@Valid CmsArticle article) {
    	CmsArticleService.updateByPrimaryKeySelective(article);
    	String msg = "cms_article_update" ;
        return "redirect:/cms/article/index.html?msg="+msg;
    	
    }
    
    @RequestMapping("/delete")
    @Menu(type = "cms" , subtype = "article")
    public String delete(HttpServletRequest request ,@Valid CmsArticle article) {
    	CmsArticleService.delete(article);
    	String msg = "cms_article_delete" ;
    	return "redirect:/cms/article/index.html?msg="+msg;
    }
}