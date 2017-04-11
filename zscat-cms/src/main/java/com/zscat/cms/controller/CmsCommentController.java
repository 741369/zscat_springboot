package com.zscat.cms.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageInfo;
import com.zscat.cms.model.CmsComment;
import com.zscat.cms.service.CmsCommentService;
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
@RequestMapping("/cms/comment")
public class CmsCommentController{
	
	@Autowired
	private CmsCommentService CmscommentService;
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
    @Menu(type = "cms" , subtype = "comment")
    public ModelAndView index(ModelMap map , HttpServletRequest request) {
    	
        ModelAndView mv =  new ModelAndView();
		mv.setViewName("/cms/comment/comment");
		PageInfo<CmsComment> commentList = CmscommentService.selectPage(getP(request), 10, new CmsComment());
		mv.addObject("commentList", commentList);
		return mv;
    }
    

    
    @RequestMapping("/delete")
    @Menu(type = "cms" , subtype = "comment")
    public String delete(HttpServletRequest request ,@Valid CmsComment comment) {
    	CmscommentService.delete(comment);
    	String msg = "cms_comment_delete" ;
    	return "redirect:/cms/comment/index.html?msg="+msg;
    }
}