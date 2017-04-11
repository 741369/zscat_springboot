package com.zscat.shop.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageInfo;
import com.zscat.shop.model.Order;
import com.zscat.shop.service.OrderService;
import com.zscat.util.Menu;
import com.zscat.util.StringUtil;


/**
 * 
 * @author zsCat 2017-4-14 13:56:18
 * @Email: 951449465@qq.com
 * @version 4.0v
 */
@Controller
@RequestMapping("/shop/order")
public class OrderController{
	
	@Autowired
	private OrderService OrderService;
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
    @Menu(type = "shop" , subtype = "order")
    public ModelAndView index(ModelMap map , HttpServletRequest request) {
    	
        ModelAndView mv =  new ModelAndView();
		mv.setViewName("/shop/order/order");
		PageInfo<Order> orderList = OrderService.selectPage(getP(request), 10, new Order());
		mv.addObject("orderList", orderList);
		return mv;
    }
    
    @RequestMapping("/add")
    @Menu(type = "shop" , subtype = "order")
    public ModelAndView add(ModelMap map , HttpServletRequest request) {
      
        ModelAndView mv =  new ModelAndView();
		mv.setViewName("/shop/order/add");
		return mv;
    }
    
    @RequestMapping("/save")
    @Menu(type = "shop" , subtype = "order")
    public String save(HttpServletRequest request ,@Valid Order order) {
    	OrderService.insertSelective(order);
    	String msg = "shop_order_delete" ;
     return "redirect:/shop/order/index.html?msg="+msg;
    }
    
    @RequestMapping("/edit")
    @Menu(type = "shop" , subtype = "order")
    public ModelAndView edit(ModelMap map , @Valid String id) {
    	ModelAndView mv =  new ModelAndView();
    	mv.addObject("orderData", OrderService.selectByPrimaryKey(Long.parseLong(id)));
		mv.setViewName("/shop/order/edit");
		return mv;
    }
    
    @RequestMapping("/update")
    @Menu(type = "shop" , subtype = "order" , admin = true)
    public String update(HttpServletRequest request ,@Valid Order order) {
    	OrderService.updateByPrimaryKeySelective(order);
    	String msg = "shop_order_update" ;
        return "redirect:/shop/order/index.html?msg="+msg;
    	
    }
    
    @RequestMapping("/delete")
    @Menu(type = "shop" , subtype = "order")
    public String delete(HttpServletRequest request ,@Valid Order order) {
    	OrderService.delete(order);
    	String msg = "shop_order_delete" ;
    	return "redirect:/shop/order/index.html?msg="+msg;
    }
}