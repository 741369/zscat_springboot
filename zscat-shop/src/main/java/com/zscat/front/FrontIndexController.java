package com.zscat.front;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageInfo;
import com.zscat.shop.model.Floor;
import com.zscat.shop.model.Member;
import com.zscat.shop.model.Product;
import com.zscat.shop.model.ProductClass;
import com.zscat.shop.model.ProductType;
import com.zscat.shop.service.FloorService;
import com.zscat.shop.service.MemberService;
import com.zscat.shop.service.ProductClassService;
import com.zscat.shop.service.ProductService;
import com.zscat.shop.service.ProductTypeService;
import com.zscat.util.PasswordEncoder;
import com.zscat.util.StringUtil;
import com.zscat.util.SysUserUtils;




	/**
	 * 
	 * @author zsCat 2016-10-31 14:01:30
	 * @Email: 951449465@qq.com
	 * @version 4.0v
	 *	商品管理
	 */
@Controller
@RequestMapping("/front")
public class FrontIndexController {

	@Resource
	private ProductClassService ProductClassService;
	@Resource
	private ProductService ProductService;
	@Resource
	private MemberService MemberService;
	@Resource
	private  FloorService floorService;
//	@Resource
//	private ArticleService articleService;
	
	@Resource
	private ProductTypeService ProductTypeService;
	
	 @RequestMapping("")
	  public ModelAndView index() {
	        try {
	            ModelAndView model = new ModelAndView("/mall/index");
	            Product goods=new Product();
	            PageInfo<Product> page = ProductService.selectPage(1, 4, goods,"orderby desc");
	            model.addObject("page", page);
	            ProductClass gc=new ProductClass();
	            gc.setParentId(1L);
	            List<ProductClass> gcList=ProductClassService.selectPage(1, 15, gc).getList();
	            model.addObject("spList", floorService.select(new Floor()));
	            model.addObject("gcList", gcList);
//	            List<Article> artList=articleService.select(new Article());
//	            model.addObject("artList", artList);
	            List<Member> useList=MemberService.select(new Member());
		        model.addObject("useList", useList);
		        
		        List<ProductType> typeList=ProductTypeService.select(new ProductType());
		        model.addObject("typeList", typeList);
	            return model;
	        } catch (Exception e) {
	            e.printStackTrace();
	            throw new RuntimeException("导航失败!");
	        }
	    }
	 @RequestMapping("/index")
	  public ModelAndView index1() {
	        try {
	            ModelAndView model = new ModelAndView("/mall/home3");
	            Product goods=new Product();
	            PageInfo<Product> page = ProductService.selectPage(1, 4, goods,"orderby desc");
	            model.addObject("page", page);
	            ProductClass gc=new ProductClass();
	            gc.setParentId(1L);
	            List<ProductClass> gcList=ProductClassService.selectPage(1, 15, gc).getList();
	            model.addObject("spList", floorService.select(new Floor()));
	            model.addObject("gcList", gcList);
//	            List<Article> artList=articleService.select(new Article());
//	            model.addObject("artList", artList);
	            List<Member> useList=MemberService.select(new Member());
		        model.addObject("useList", useList);
		        
		        List<ProductType> typeList=ProductTypeService.select(new ProductType());
		        model.addObject("typeList", typeList);
		        
		        PageInfo<Product> xinpin = ProductService.selectPage(1, 24, goods,"create_date desc");
	            model.addObject("xinpin", xinpin);
	            return model;
	        } catch (Exception e) {
	            e.printStackTrace();
	            throw new RuntimeException("导航失败!");
	        }
	    }
	 @RequestMapping("/information/{createBy}")
	  public ModelAndView information(@PathVariable("createBy") Long createBy) {
		 ModelAndView model = new ModelAndView("/mall/person/information");
        Member member=MemberService.selectByPrimaryKey(createBy);
        model.addObject("member", member);
		 return model;
	 }
	   /**
		 * 跳转到登录页面
		 * 
		 * @return
		 */
		@RequestMapping(value = "login", method = RequestMethod.GET)
		public String toLogin() {
			if( SysUserUtils.getSessionLoginUser() != null){
				return "redirect:/front";
			}
			return "/mall/login";
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
				String password,  HttpServletRequest request) {

			Map<String, Object> msg = new HashMap<String, Object>();
			HttpSession session = request.getSession();
			//code = StringUtils.trim(code);
			username = StringUtil.trim(username);
			password = StringUtil.trim(password);
			//Object scode = session.getAttribute("code");
			String sessionCode = null;
//			if (scode != null)
//				sessionCode = scode.toString();
//			if (!StringUtils.equalsIgnoreCase(code, sessionCode)) {
//				msg.put("error", "验证码错误");
//				return msg;
//			}
			Member user = MemberService.checkUser(username, password);
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
				return "redirect:/front";
			}
			return "/mall/register";
		}
	
		@RequestMapping(value = "reg", method = RequestMethod.POST)
		public @ResponseBody Map<String, Object> reg(
				@RequestParam(value = "password",required=true)String  password,
				@RequestParam(value = "username",required=true)String  username,
				@RequestParam(value = "email",required=false)String email,
				@RequestParam(value = "phone",required=false)String phone,
				@RequestParam(value = "passwordRepeat",required=true)String passwordRepeat,HttpServletRequest request) {
			Map<String, Object> msg = new HashMap<String, Object>();
			if (!StringUtil.equalsIgnoreCase(password, passwordRepeat)) {
				msg.put("error", "密码不一致!");
				return msg;
			}
			String secPwd = null ;
			Member m=new Member();
				m.setUsername(username);
				secPwd = PasswordEncoder.encrypt(password, username);
			    m.setPassword(secPwd);
			    m.setStatus(1);m.setAddtime(new Date());
			    m.setTruename(m.getUsername());
			try {
				
				int result = MemberService.insertSelective(m);
				
				MemberService.insertSelective(m);
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
			return "redirect:/front/login";
		}
	
		@RequestMapping("/search/{createBy}")
		  public ModelAndView search(@PathVariable("createBy") Long createBy) {
			 ModelAndView model = new ModelAndView("/mall/search");
			 Product p=new Product();
			 p.setCreateBy(createBy);
	        PageInfo<Product> page=ProductService.selectPage(1, 15, p, "orderby desc");
	        model.addObject("page", page);
	        p.setCreateBy(2L);
	        PageInfo<Product> page1=ProductService.selectPage(1, 15, p, "orderby desc");
	        model.addObject("page1", page1);
			 return model;
		 }
		@RequestMapping("/memberList")
		  public ModelAndView memberList() {
			 ModelAndView model = new ModelAndView("/mall/member");
			 List<Member> page=MemberService.select(new Member());
	        model.addObject("page", page);
			 return model;
		 }
		@RequestMapping("/type")
		  public ModelAndView type() {
			 ModelAndView model = new ModelAndView("/mall/type");
			 ProductClass gc=new ProductClass();
	            gc.setParentId(1L);
	            List<ProductClass> gcList=ProductClassService.selectPage(1, 15, gc).getList();
	            model.addObject("gcList", gcList);
			 return model;
		 }
	 
}
