package com.zscat.shop.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageInfo;
import com.zscat.shop.model.ProductClass;
import com.zscat.shop.service.ProductClassService;
import com.zscat.util.Menu;
import com.zscat.util.StringUtil;


/**
 * 
 * @author zsCat 2017-4-14 13:56:18
 * @Email: 951449465@qq.com
 * @version 4.0v
 */
@Controller
@RequestMapping("/shop/productClass")
public class ProductClassController{
	
	@Autowired
	private ProductClassService ProductClassService;
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
    @Menu(type = "shop" , subtype = "productClass")
    public ModelAndView index(ModelMap map , HttpServletRequest request) {
    	
        ModelAndView mv =  new ModelAndView();
		mv.setViewName("/shop/productClass/productClass");
		PageInfo<ProductClass> productClassList = ProductClassService.selectPage(getP(request), 10, new ProductClass());
		mv.addObject("productClassList", productClassList);
		return mv;
    }
    
    @RequestMapping("/add")
    @Menu(type = "shop" , subtype = "productClass")
    public ModelAndView add(ModelMap map , HttpServletRequest request) {
      
        ModelAndView mv =  new ModelAndView();
		mv.setViewName("/shop/productClass/add");
		return mv;
    }
    
    @RequestMapping("/save")
    @Menu(type = "shop" , subtype = "productClass")
    public String save(HttpServletRequest request ,@Valid ProductClass productClass) {
    	ProductClassService.insertSelective(productClass);
    	String msg = "shop_productClass_delete" ;
     return "redirect:/shop/productClass/index.html?msg="+msg;
    }
    
    @RequestMapping("/edit")
    @Menu(type = "shop" , subtype = "productClass")
    public ModelAndView edit(ModelMap map , @Valid String id) {
    	ModelAndView mv =  new ModelAndView();
    	mv.addObject("productClassData", ProductClassService.selectByPrimaryKey(Long.parseLong(id)));
		mv.setViewName("/shop/productClass/edit");
		return mv;
    }
    
    @RequestMapping("/update")
    @Menu(type = "shop" , subtype = "productClass" , admin = true)
    public String update(HttpServletRequest request ,@Valid ProductClass productClass) {
    	ProductClassService.updateByPrimaryKeySelective(productClass);
    	String msg = "shop_productClass_update" ;
        return "redirect:/shop/productClass/index.html?msg="+msg;
    	
    }
    
    @RequestMapping("/delete")
    @Menu(type = "shop" , subtype = "productClass")
    public String delete(HttpServletRequest request ,@Valid ProductClass productClass) {
    	ProductClassService.delete(productClass);
    	String msg = "shop_productClass_delete" ;
    	return "redirect:/shop/productClass/index.html?msg="+msg;
    }
}