package com.zscat.shop.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageInfo;
import com.zscat.shop.model.Product;
import com.zscat.shop.service.ProductService;
import com.zscat.util.Menu;
import com.zscat.util.StringUtil;


/**
 * 
 * @author zsCat 2017-4-14 13:56:18
 * @Email: 951449465@qq.com
 * @version 4.0v
 */
@Controller
@RequestMapping("/shop/product")
public class ProductController{
	
	@Autowired
	private ProductService ProductService;
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
    @Menu(type = "shop" , subtype = "product")
    public ModelAndView index(ModelMap map , HttpServletRequest request) {
    	
        ModelAndView mv =  new ModelAndView();
		mv.setViewName("/shop/product/product");
		PageInfo<Product> productList = ProductService.selectPage(getP(request), 10, new Product());
		mv.addObject("productList", productList);
		return mv;
    }
    
    @RequestMapping("/add")
    @Menu(type = "shop" , subtype = "product")
    public ModelAndView add(ModelMap map , HttpServletRequest request) {
      
        ModelAndView mv =  new ModelAndView();
		mv.setViewName("/shop/product/add");
		return mv;
    }
    
    @RequestMapping("/save")
    @Menu(type = "shop" , subtype = "product")
    public String save(HttpServletRequest request ,@Valid Product product) {
    	ProductService.insertSelective(product);
    	String msg = "shop_product_delete" ;
     return "redirect:/shop/product/index.html?msg="+msg;
    }
    
    @RequestMapping("/edit")
    @Menu(type = "shop" , subtype = "product")
    public ModelAndView edit(ModelMap map , @Valid String id) {
    	ModelAndView mv =  new ModelAndView();
    	mv.addObject("productData", ProductService.selectByPrimaryKey(Long.parseLong(id)));
		mv.setViewName("/shop/product/edit");
		return mv;
    }
    
    @RequestMapping("/update")
    @Menu(type = "shop" , subtype = "product" , admin = true)
    public String update(HttpServletRequest request ,@Valid Product product) {
    	ProductService.updateByPrimaryKeySelective(product);
    	String msg = "shop_product_update" ;
        return "redirect:/shop/product/index.html?msg="+msg;
    	
    }
    
    @RequestMapping("/delete")
    @Menu(type = "shop" , subtype = "product")
    public String delete(HttpServletRequest request ,@Valid Product product) {
    	ProductService.delete(product);
    	String msg = "shop_product_delete" ;
    	return "redirect:/shop/product/index.html?msg="+msg;
    }
}