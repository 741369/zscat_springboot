package com.zscat.blog.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.PageInfo;
import com.zscat.blog.model.Blog;
import com.zscat.blog.model.BlogComment;
import com.zscat.blog.model.BlogLink;
import com.zscat.blog.model.BlogTemplate;
import com.zscat.blog.model.BlogType;
import com.zscat.blog.model.Blogger;
import com.zscat.blog.service.BlogCommentService;
import com.zscat.blog.service.BlogLinkService;
import com.zscat.blog.service.BlogService;
import com.zscat.blog.service.BlogTemplateService;
import com.zscat.blog.service.BlogTypeService;
import com.zscat.blog.service.BloggerService;
import com.zscat.util.PasswordEncoder;
import com.zscat.util.StringUtil;
import com.zscat.util.SysUserUtils;
	/**
	 * 
	 * @author zs 2016-5-5 11:33:51
	 * @Email: 951449465@qq.com
	 * @version 4.0v
	 *	我的blog
	 */
@Controller
@RequestMapping("front/blog")
public class IndexController {

	
	
	@Resource
	private BlogTemplateService BlogTemplateService;

	
		
	@Resource
	private BlogService blogService;
	@Resource
	private BlogCommentService BlogCommentService;
	@Resource
	private BlogTypeService BlogTypeService;
	@Resource
	private BloggerService BloggerService;
	@Resource
	private BlogLinkService BlogLinkService;
	
	@RequestMapping("/indexns")
	public ModelAndView indexns(@RequestParam(value = "pageNum",required=false,defaultValue="1")Integer pageNum,
			@RequestParam(value = "pageSize",required=false,defaultValue="12")Integer pageSize)throws Exception{
		ModelAndView mav=new ModelAndView();
		List<Blog> blogList = blogService.select(null);
		
		mav.addObject("blogList", blogList);
		mav.setViewName("blog/indexns");
		return mav;
	}
	
	
	@RequestMapping("/indexone")
	public ModelAndView index(@RequestParam(value = "pageNum",required=false,defaultValue="1")Integer pageNum,
			@RequestParam(value = "pageSize",required=false,defaultValue="12")Integer pageSize)throws Exception{
		ModelAndView mav=new ModelAndView();
		List<Blog> blogList = blogService.select(null);
		for(Blog blog:blogList){
			BlogType blogType=BlogTypeService.selectByPrimaryKey(blog.getTypeid());
			blog.setBlogType(blogType);
			String blogInfo=blog.getContent();
			Document doc=Jsoup.parse(blogInfo);
			Elements jpgs=doc.select("img[src]"); //　查找扩展名是jpg的图片
			if(jpgs!=null && jpgs.size()>0){
			Element jpg=jpgs.get(0);
				if(jpgs!=null && jpg!=null){
					String BlogLinkHref = jpg.attr("src");
					blog.setImg(BlogLinkHref);
				}else{
					blog.setImg("static/bbs/img/menu.png");
				}
			}
		}
		
		mav.addObject("blogList", blogList);
		//精心
		PageInfo<Blog> blogHotList = blogService.selectPage(1, 6, new Blog(),"clickHit desc");
		mav.addObject("blogHotList", blogHotList);
		//最新
		PageInfo<Blog> blogTopList = blogService.selectPage(1, 6, new Blog(),"releaseDate desc");
		mav.addObject("blogTopList", blogTopList);
		//随机
		PageInfo<Blog> blogRamList = blogService.selectPage(1, 6, new Blog(),"RAND()");
		mav.addObject("blogRamList", blogRamList);
		
		mav.setViewName("blog/indexone");
		return mav;
	}
	
	@RequestMapping("/about")
	public ModelAndView aboutMe(@RequestParam(value = "pageNum",required=false,defaultValue="1")Integer pageNum,
			@RequestParam(value = "pageSize",required=false,defaultValue="12")Integer pageSize)throws Exception{
		ModelAndView mav=new ModelAndView();
		List<Blog> blogList = blogService.select(null);
		
		mav.addObject("blogList", blogList);
		mav.setViewName("blog/about");
		return mav;
	}
	
	@RequestMapping("/shuo")
	public ModelAndView jilu(@RequestParam(value = "pageNum",required=false,defaultValue="1")Integer pageNum,
			@RequestParam(value = "pageSize",required=false,defaultValue="12")Integer pageSize)throws Exception{
		ModelAndView mav=new ModelAndView();
		List<Blog> blogList = blogService.select(null);
		
		mav.addObject("blogList", blogList);
		mav.setViewName("blog/shuo");
		return mav;
	}
	@RequestMapping("/daily")
	public ModelAndView daily(@RequestParam(value = "pageNum",required=false,defaultValue="1")Integer pageNum,
			@RequestParam(value = "pageSize",required=false,defaultValue="12")Integer pageSize)throws Exception{
		ModelAndView mav=new ModelAndView();
		List<Blog> blogList = blogService.select(null);
		
		mav.addObject("blogList", blogList);
		mav.setViewName("blog/daily");
		return mav;
	}
	@RequestMapping("/xc")
	public ModelAndView xc(@RequestParam(value = "pageNum",required=false,defaultValue="1")Integer pageNum,
			@RequestParam(value = "pageSize",required=false,defaultValue="12")Integer pageSize)throws Exception{
		ModelAndView mav=new ModelAndView();
//		PageInfo<CmsImg> imgList=CmsImgService.selectPage(pageNum, 10000, null);
//		mav.addObject("imgList", imgList.getList());
		mav.setViewName("blog/xc");
		return mav;
	}
	@RequestMapping("/guestbook")
	public ModelAndView guestbook(@RequestParam(value = "pageNum",required=false,defaultValue="1")Integer pageNum,
			@RequestParam(value = "pageSize",required=false,defaultValue="12")Integer pageSize)throws Exception{
		ModelAndView mav=new ModelAndView();
		List<Blog> blogList = blogService.select(null);
		
		mav.addObject("blogList", blogList);
		mav.setViewName("blog/guestbook");
		return mav;
	}
	
	
	//=====================草根
	
	@RequestMapping("/indexc")
	public ModelAndView indexc(@RequestParam(value = "pageNum",required=false,defaultValue="1")Integer pageNum,
			@RequestParam(value = "pageSize",required=false,defaultValue="12")Integer pageSize)throws Exception{
		ModelAndView mav=new ModelAndView();
		List<BlogTemplate> tempList=BlogTemplateService.select(new BlogTemplate());
		mav.addObject("tempList", tempList);
		for(BlogTemplate temp:tempList){
		
			String tempInfo=temp.getContent();
			Document doc1=Jsoup.parse(tempInfo);
			Elements jpgs1=doc1.select("img[src]"); //　查找扩展名是jpg的图片
			if(jpgs1!=null && jpgs1.size()>0){
			Element jpg1=jpgs1.get(0);
				if(jpgs1!=null && jpg1!=null){
					String BlogLinkHref = jpg1.attr("src");
					temp.setImg(BlogLinkHref);
				}else{
					temp.setImg("static/bbs/img/menu.png");
				}
			}
		}
		List<Blog> blogList = blogService.select(null);
		for(Blog blog:blogList){
			BlogType blogType=BlogTypeService.selectByPrimaryKey(blog.getTypeid());
			blog.setBlogType(blogType);
			String blogInfo=blog.getContent();
			Document doc=Jsoup.parse(blogInfo);
			Elements jpgs=doc.select("img[src]"); //　查找扩展名是jpg的图片
			if(jpgs!=null && jpgs.size()>0){
			Element jpg=jpgs.get(0);
				if(jpgs!=null && jpg!=null){
					String BlogLinkHref = jpg.attr("src");
					blog.setImg(BlogLinkHref);
				}else{
					blog.setImg("static/bbs/img/menu.png");
				}
			}
		}
		
		mav.addObject("blogList", blogList);
		mav.setViewName("blog/caogen/index");
		return mav;
	}
	@RequestMapping("/book")
	public ModelAndView book(@RequestParam(value = "pageNum",required=false,defaultValue="1")Integer pageNum,
			@RequestParam(value = "pageSize",required=false,defaultValue="12")Integer pageSize)throws Exception{
		ModelAndView mav=new ModelAndView();
		
		mav.setViewName("blog/caogen/caselist");
		return mav;
	}
	@RequestMapping("/aboutc")
	public ModelAndView aboutc(@RequestParam(value = "pageNum",required=false,defaultValue="1")Integer pageNum,
			@RequestParam(value = "pageSize",required=false,defaultValue="12")Integer pageSize)throws Exception{
		ModelAndView mav=new ModelAndView();
		
		mav.setViewName("blog/caogen/about");
		return mav;
	}
	@RequestMapping("/knowledge")
	public ModelAndView knowledge(@RequestParam(value = "pageNum",required=false,defaultValue="1")Integer pageNum,
			@RequestParam(value = "pageSize",required=false,defaultValue="12")Integer pageSize)throws Exception{
		ModelAndView mav=new ModelAndView();
		
		mav.setViewName("blog/caogen/knowledge");
		return mav;
	}
	@RequestMapping("/moodlist")
	public ModelAndView moodlist(@RequestParam(value = "pageNum",required=false,defaultValue="1")Integer pageNum,
			@RequestParam(value = "pageSize",required=false,defaultValue="12")Integer pageSize)throws Exception{
		ModelAndView mav=new ModelAndView();
		
		mav.setViewName("blog/caogen/moodlist");
		return mav;
	}
	@RequestMapping("/news/{id}")
	public ModelAndView news(@PathVariable("id") Long id)throws Exception{
		ModelAndView mav=new ModelAndView();
		Blog b=blogService.selectByPrimaryKey(id);
		mav.addObject("blog", b);
		mav.setViewName("blog/caogen/new");
		return mav;
	}
	@RequestMapping("/newlist")
	public ModelAndView newlist(@RequestParam(value = "pageNum",required=false,defaultValue="1")Integer pageNum,
			@RequestParam(value = "pageSize",required=false,defaultValue="12")Integer pageSize)throws Exception{
		ModelAndView mav=new ModelAndView();
		PageInfo<Blog> blogList = blogService.selectPage(pageNum, 10000, new Blog());
		for(Blog blog:blogList.getList()){
			String blogInfo=blog.getContent();
			Document doc=Jsoup.parse(blogInfo);
			Elements jpgs=doc.select("img[src]"); //　查找扩展名是jpg的图片
			if(jpgs!=null && jpgs.size()>0){
			Element jpg=jpgs.get(0);
				if(jpgs!=null && jpg!=null){
					String BlogLinkHref = jpg.attr("src");
					blog.setImg(BlogLinkHref);
				}else{
					blog.setImg("static/bbs/img/menu.png");
				}
			}
		}
		mav.addObject("page", blogList);
		mav.setViewName("blog/caogen/newlist");
		return mav;
	}
	@RequestMapping("/share")
	public ModelAndView share(@RequestParam(value = "pageNum",required=false,defaultValue="1")Integer pageNum,
			@RequestParam(value = "pageSize",required=false,defaultValue="12")Integer pageSize)throws Exception{
		ModelAndView mav=new ModelAndView();
		PageInfo<Blog> blogList = blogService.selectPage(pageNum, 10000, new Blog());
		mav.addObject("page", blogList);
		mav.setViewName("blog/caogen/share");
		return mav;
	}
	@RequestMapping("/template/{id}")
	public ModelAndView template(@PathVariable("id") Long id)throws Exception{
		ModelAndView mav=new ModelAndView();
		Blog b=blogService.selectByPrimaryKey(id);
		mav.addObject("template", b);
		mav.setViewName("blog/caogen/template");
		return mav;
	}
	
	
	
	//============================================社区==================
	@RequestMapping("/index")
	public ModelAndView indexs(@RequestParam(value = "pageNum",required=false,defaultValue="1")Integer pageNum,
			@RequestParam(value = "pageSize",required=false,defaultValue="12")Integer pageSize)throws Exception{
		ModelAndView mav=new ModelAndView();
	//	List<Blog> blogList = blogService.select(null);
		
				//最新
				PageInfo<Blog> blogTopList = blogService.selectPage(1, 12, new Blog(),"releaseDate desc");
				mav.addObject("blogTopList", blogTopList);
				//随机
				PageInfo<Blog> blogRamList = blogService.selectPage(1, 12, new Blog(),"RAND()");
				mav.addObject("blogRamList", blogRamList);
				mav.addObject("userList", BloggerService.selectPage(1, 12, new Blogger()));
				
				List<BlogType> typeList=BlogTypeService.select(new BlogType());
				mav.addObject("typeList", typeList);
				
				List<BlogLink> BlogLinkList=BlogLinkService.select(new BlogLink());
				mav.addObject("BlogLinkList", BlogLinkList);
				mav.setViewName("blog/community/index");
				return  mav;
		
	}
	// 首页精品课程、最新课程、全部课程
			@RequestMapping("/ajax/bna")
		public String queryCourse(HttpServletRequest request) {
				try {
					String id = request.getParameter("order");
					if (id != null && !id.equals("")) {
						Blog a=new Blog();
						a.setTypeid(Long.parseLong(id));
						// 获得精品课程、最新课程、全部课程
						List<Blog> artList = blogService.select(a);
						for(Blog blog:artList){
							String blogInfo=blog.getContent();
							Document doc=Jsoup.parse(blogInfo);
							Elements jpgs=doc.select("img[src]"); //　查找扩展名是jpg的图片
							if(jpgs!=null && jpgs.size()>0){
							Element jpg=jpgs.get(0);
								if(jpgs!=null && jpg!=null){
									String BlogLinkHref = jpg.attr("src");
									blog.setImg(BlogLinkHref);
								}else{
									blog.setImg("static/bbs/img/menu.png");
								}
							}
						}
						request.setAttribute("artList", artList);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				return "blog/community/ajax-index";
			}
	@RequestMapping("/indexs1")
	public ModelAndView indexs1(@RequestParam(value = "pageNum",required=false,defaultValue="1")Integer pageNum,
			@RequestParam(value = "pageSize",required=false,defaultValue="12")Integer pageSize)throws Exception{
		ModelAndView mav=new ModelAndView();
		Blog blog1=new Blog();
		blog1.setTypeid(3L);
		List<Blog> blogList = blogService.select(blog1);
		for(Blog blog:blogList){
			BlogType blogType=BlogTypeService.selectByPrimaryKey(blog.getTypeid());
			blog.setBlogType(blogType);
			String blogInfo=blog.getContent();
			Document doc=Jsoup.parse(blogInfo);
			Elements jpgs=doc.select("img[src]"); //　查找扩展名是jpg的图片
			if(jpgs!=null && jpgs.size()>0){
			Element jpg=jpgs.get(0);
				if(jpgs!=null && jpg!=null){
					String BlogLinkHref = jpg.attr("src");
					blog.setImg(BlogLinkHref);
				}else{
					blog.setImg("static/bbs/img/menu.png");
				}
			}
		}
			mav.addObject("blogList", blogList);
				//最新
				PageInfo<Blog> blogTopList = blogService.selectPage(1, 12, new Blog(),"releaseDate desc");
				mav.addObject("blogTopList", blogTopList);
				//随机
				PageInfo<Blog> blogRamList = blogService.selectPage(1, 12, new Blog(),"RAND()");
				mav.addObject("blogRamList", blogRamList);
				mav.addObject("userList", BloggerService.selectPage(1, 12, new Blogger()));
				
				List<BlogType> typeList=BlogTypeService.select(new BlogType());
				mav.addObject("typeList", typeList);
				
				List<BlogLink> BlogLinkList=BlogLinkService.select(new BlogLink());
				mav.addObject("BlogLinkList", BlogLinkList);
				mav.setViewName("blog/community/index1");
				return  mav;
		
	}
	
	@RequestMapping("/blogListByType/{id}")
	public ModelAndView blogListByType(@PathVariable("id") Long id)throws Exception{
		ModelAndView mav=new ModelAndView();
		Blog blog1=new Blog();
		blog1.setTypeid(id);
		List<Blog> blogList = blogService.select(blog1);
		for(Blog blog:blogList){
			BlogType blogType=BlogTypeService.selectByPrimaryKey(blog.getTypeid());
			blog.setBlogType(blogType);
			String blogInfo=blog.getContent();
			Document doc=Jsoup.parse(blogInfo);
			Elements jpgs=doc.select("img[src]"); //　查找扩展名是jpg的图片
			if(jpgs!=null && jpgs.size()>0){
			Element jpg=jpgs.get(0);
				if(jpgs!=null && jpg!=null){
					String BlogLinkHref = jpg.attr("src");
					blog.setImg(BlogLinkHref);
				}else{
					blog.setImg("static/bbs/img/menu.png");
				}
			}
		}
			mav.addObject("blogList", blogList);
				//最新
				PageInfo<Blog> blogTopList = blogService.selectPage(1, 12, new Blog(),"releaseDate desc");
				mav.addObject("blogTopList", blogTopList);
				//随机
				PageInfo<Blog> blogRamList = blogService.selectPage(1, 12, new Blog(),"RAND()");
				mav.addObject("blogRamList", blogRamList);
				mav.addObject("userList", BloggerService.selectPage(1, 12, new Blogger()));
				
				List<BlogType> typeList=BlogTypeService.select(new BlogType());
				mav.addObject("typeList", typeList);
				List<BlogLink> BlogLinkList=BlogLinkService.select(new BlogLink());
				mav.addObject("BlogLinkList", BlogLinkList);
				mav.setViewName("blog/community/index");
				return  mav;
		
	}
	@RequestMapping("/shequDetail/{id}")
	public ModelAndView shequDetail(@PathVariable("id") Long id)throws Exception{
		ModelAndView mav=new ModelAndView();
		
		//最新
		PageInfo<Blog> blogTopList = blogService.selectPage(1, 12, new Blog(),"releaseDate desc");
		mav.addObject("blogTopList", blogTopList);
		//随机
		PageInfo<Blog> blogRamList = blogService.selectPage(1, 12, new Blog(),"RAND()");
		mav.addObject("blogRamList", blogRamList);
		
		mav.addObject("userList", BloggerService.selectPage(1, 12, new Blogger()));
		Blog b=blogService.selectByPrimaryKey(id);
		mav.addObject("blog", b);
		
		BlogComment BlogComment=new BlogComment();
		BlogComment.setBlogid(id);
		PageInfo<BlogComment> commList=BlogCommentService.selectPage(1, 15, BlogComment);
		mav.addObject("commList", commList);
		mav.setViewName("blog/community/jie/detail");
		return mav;
	}
	
	@RequestMapping("/home/{id}")
	public ModelAndView home(@PathVariable("id") Long id)throws Exception{
		ModelAndView mav=new ModelAndView();
		Blog blog=new Blog();
		blog.setBloggerId(id);
		PageInfo<Blog> page=blogService.selectPage(1, 15, blog);
		mav.addObject("page", page);
		
		BlogComment BlogComment=new BlogComment();
		BlogComment.setBloggerId(id);
		PageInfo<BlogComment> commList=BlogCommentService.selectPage(1, 15, BlogComment);
		mav.addObject("commList", commList);
		mav.setViewName("blog/community/user/home");
		return mav;
	
	}
	 /**
	 * 跳转到登录页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String toLogin() {
		if( SysUserUtils.getSessionLoginUser()!= null){
			return "redirect:/front/blog/indexs";
		}
		return "/blog/community/user/login";
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
			String password,String vercode,  HttpServletRequest request) {

		Map<String, Object> msg = new HashMap<String, Object>();
		HttpSession session = request.getSession();
		username = StringUtil.trim(username);
		password = StringUtil.trim(password);
		if (!StringUtils.equalsIgnoreCase("3711111", vercode)) {
			msg.put("error", "验证码错误!");
			return msg;
		}
		Blogger user = BloggerService.checkBlogger(username, password);
		if (null != user) {
			session.setAttribute(SysUserUtils.SESSION_LOGIN_USER, user);
		} else {
			msg.put("error", "用户名或密码错误");
		}
		return msg;
	}
 
	 /**
	 * 跳转到登录页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "reg", method = RequestMethod.GET)
	public String reg() {
		if( SysUserUtils.getSessionLoginUser() != null){
			return "redirect:/front/blog/indexs";
		}
		return "/blog/community/user/reg";
	}

	@RequestMapping(value = "reg", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> reg(
			@RequestParam(value = "password",required=true)String  password,
			@RequestParam(value = "username",required=false)String username,
			@RequestParam(value = "nickname",required=false)String nickname,
			@RequestParam(value = "passwordRepeat",required=true)String passwordRepeat,
			@RequestParam(value = "vercode",required=true)String vercode,HttpServletRequest request) {
		Map<String, Object> msg = new HashMap<String, Object>();
		if (!StringUtils.equalsIgnoreCase("3711111", vercode)) {
			msg.put("error", "验证码错误!");
			return msg;
		}
		if (!StringUtils.equalsIgnoreCase(password, passwordRepeat)) {
			msg.put("error", "密码不一致!");
			return msg;
		}
		String secPwd = null ;
		Blogger m=new Blogger();
			secPwd = PasswordEncoder.encrypt(password, username);
		    m.setPassword(secPwd);
		    m.setUsername(username);
		    m.setNickname(nickname);
		try {
			int result = BloggerService.insertSelective(m);
			HttpSession session = request.getSession();
			if (result>0) {
				session.setAttribute(SysUserUtils.SESSION_LOGIN_USER, m);
			} else {
				msg.put("error", "注册失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
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
		request.getSession().invalidate();
		return "redirect:/front/blog/login";
	}

}
