package com.zscat.blog.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageInfo;
import com.zscat.blog.model.Blog;
import com.zscat.blog.model.BlogComment;
import com.zscat.blog.model.BlogType;
import com.zscat.blog.service.BlogCommentService;
import com.zscat.blog.service.BlogService;
import com.zscat.blog.service.BlogTypeService;
import com.zscat.blog.service.BloggerService;
import com.zscat.util.IPUtils;
import com.zscat.util.StringUtil;
import com.zscat.util.SysUserUtils;

@Controller
@RequestMapping("person")
public class PersonController {

	@Resource
	private BlogService blogService;
	@Resource
	private BlogCommentService BlogCommentService;
	@Resource
	private BlogTypeService BlogTypeService;
	@Resource
	private BloggerService BloggerService;
	
	@RequestMapping("/index")
	public ModelAndView index()throws Exception{
		ModelAndView mav=new ModelAndView();
		mav.addObject("test", "shenzhuan");
		mav.setViewName("blog/community/user/index");
		return mav;
	
	}
	/**
	 * 发布问题
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/adds")
	public ModelAndView adds()throws Exception{
		ModelAndView mav=new ModelAndView();
		List<BlogType> typeList=BlogTypeService.select(new BlogType());
		mav.addObject("typeList", typeList);
		mav.setViewName("blog/community/jie/add");
		return mav;
	}
	@RequestMapping("/saves")
	public String saves(String vercode,String title,String content,Long typeid,HttpServletRequest req)throws Exception{
		if (!StringUtil.equalsIgnoreCase("3711111", vercode)) {
			return "redirect:/front/blog/adds";
		}
		Blog blog=new Blog();
		blog.setTitle(title);
		blog.setState(0);
		blog.setContent(content);
		blog.setTypeid(typeid);
		if(SysUserUtils.getSessionLoginUser()!=null){
			blog.setBloggerId(SysUserUtils.getSessionLoginUser().getId());
		}else{
			blog.setBloggerId(1L);
		}
		
		blog.setReleasedate(new Date());
		blog.setSummary(title);
		blogService.insertSelective(blog);
		return "redirect:/front/blog/indexs";
		
	}
	@RequestMapping("/activate")
	public ModelAndView activate()throws Exception{
		ModelAndView mav=new ModelAndView();
		mav.addObject("test", "shenzhuan");
		mav.setViewName("blog/community/user/activate");
		return mav;
	
	}
	@RequestMapping("/question")
	public ModelAndView question()throws Exception{
		ModelAndView mav=new ModelAndView();
		mav.addObject("test", "shenzhuan");
		mav.setViewName("blog/community/user/question");
		return mav;
	
	}
	@RequestMapping("/message")
	public ModelAndView message()throws Exception{
		ModelAndView mav=new ModelAndView();
		mav.addObject("test", "shenzhuan");
		mav.setViewName("blog/community/user/message");
		return mav;
	
	}
	@RequestMapping("/home")
	public ModelAndView home()throws Exception{
		ModelAndView mav=new ModelAndView();
		Blog blog=new Blog();
		blog.setBloggerId(SysUserUtils.getSessionLoginUser().getId());
		PageInfo<Blog> page=blogService.selectPage(1, 15, blog);
		mav.addObject("page", page);
		
		BlogComment BlogComment=new BlogComment();
		BlogComment.setBloggerId(SysUserUtils.getSessionLoginUser().getId());
		PageInfo<BlogComment> commList=BlogCommentService.selectPage(1, 15, BlogComment);
		mav.addObject("commList", commList);
		mav.setViewName("blog/community/user/home");
		return mav;
	
	}
	@RequestMapping("/set")
	public ModelAndView set()throws Exception{
		ModelAndView mav=new ModelAndView();
		mav.addObject("test", "shenzhuan");
		mav.setViewName("blog/community/user/set");
		return mav;
	
	}
	@RequestMapping("/reply")
	public String reply(String content,String blogtitle,
			Long blogid,HttpServletRequest req)throws Exception{
		BlogComment com=new BlogComment();
		com.setCommentdate(new Date());
	//	com.setBloggerId(SysUserUtils.getSessionLoginUser().getId());
		com.setContent(content);
		com.setBlogid(blogid);
		com.setTitle(blogtitle);
		com.setUserip(IPUtils.getClientAddress(req));
	//	com.setBlogger(SysUserUtils.getSessionLoginUser().getName());
		BlogCommentService.insertSelective(com);
		return "redirect:/front/blog/shequDetail/"+blogid;
	
	}
	
	
}
