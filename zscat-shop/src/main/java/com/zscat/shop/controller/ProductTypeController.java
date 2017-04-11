package com.zscat.shop.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageInfo;
import com.zscat.shop.model.ProductType;
import com.zscat.shop.service.ProductTypeService;
import com.zscat.util.Menu;
import com.zscat.util.StringUtil;


/**
 * 
 * @author zsCat 2017-4-14 13:56:18
 * @Email: 951449465@qq.com
 * @version 4.0v
 */
@Controller
@RequestMapping("/shop/productType")
public class ProductTypeController{
	
	@Autowired
	private ProductTypeService ProductTypeService;
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
    @Menu(type = "shop" , subtype = "productType")
    public ModelAndView index(ModelMap map , HttpServletRequest request) {
    	
        ModelAndView mv =  new ModelAndView();
		mv.setViewName("/shop/productType/productType");
		PageInfo<ProductType> productTypeList = ProductTypeService.selectPage(getP(request), 10, new ProductType());
		mv.addObject("productTypeList", productTypeList);
		return mv;
    }
    
    @RequestMapping("/add")
    @Menu(type = "shop" , subtype = "productType")
    public ModelAndView add(ModelMap map , HttpServletRequest request) {
      
        ModelAndView mv =  new ModelAndView();
		mv.setViewName("/shop/productType/add");
		return mv;
    }
    
    @RequestMapping("/save")
    @Menu(type = "shop" , subtype = "productType")
    public String save(HttpServletRequest request ,@Valid ProductType productType) {
    	ProductTypeService.insertSelective(productType);
    	String msg = "shop_productType_delete" ;
     return "redirect:/shop/productType/index.html?msg="+msg;
    }
    
    @RequestMapping("/edit")
    @Menu(type = "shop" , subtype = "productType")
    public ModelAndView edit(ModelMap map , @Valid String id) {
    	ModelAndView mv =  new ModelAndView();
    	mv.addObject("productTypeData", ProductTypeService.selectByPrimaryKey(Long.parseLong(id)));
		mv.setViewName("/shop/productType/edit");
		return mv;
    }
    
    @RequestMapping("/update")
    @Menu(type = "shop" , subtype = "productType" , admin = true)
    public String update(HttpServletRequest request ,@Valid ProductType productType) {
    	ProductTypeService.updateByPrimaryKeySelective(productType);
    	String msg = "shop_productType_update" ;
        return "redirect:/shop/productType/index.html?msg="+msg;
    	
    }
    
    @RequestMapping("/delete")
    @Menu(type = "shop" , subtype = "productType")
    public String delete(HttpServletRequest request ,@Valid ProductType productType) {
    	ProductTypeService.delete(productType);
    	String msg = "shop_productType_delete" ;
    	return "redirect:/shop/productType/index.html?msg="+msg;
    }
}