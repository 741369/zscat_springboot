package com.zscat.shop.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageInfo;
import com.zscat.shop.model.OrderLog;
import com.zscat.shop.service.OrderLogService;
import com.zscat.util.Menu;
import com.zscat.util.StringUtil;


/**
 * 
 * @author zsCat 2017-4-14 13:56:18
 * @Email: 951449465@qq.com
 * @version 4.0v
 */
@Controller
@RequestMapping("/shop/orderLog")
public class OrderLogController{
	
	@Autowired
	private OrderLogService OrderLogService;
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
    @Menu(type = "shop" , subtype = "orderLog")
    public ModelAndView index(ModelMap map , HttpServletRequest request) {
    	
        ModelAndView mv =  new ModelAndView();
		mv.setViewName("/shop/orderLog/orderLog");
		PageInfo<OrderLog> orderLogList = OrderLogService.selectPage(getP(request), 10, new OrderLog());
		mv.addObject("orderLogList", orderLogList);
		return mv;
    }
    
    @RequestMapping("/add")
    @Menu(type = "shop" , subtype = "orderLog")
    public ModelAndView add(ModelMap map , HttpServletRequest request) {
      
        ModelAndView mv =  new ModelAndView();
		mv.setViewName("/shop/orderLog/add");
		return mv;
    }
    
    @RequestMapping("/save")
    @Menu(type = "shop" , subtype = "orderLog")
    public String save(HttpServletRequest request ,@Valid OrderLog orderLog) {
    	OrderLogService.insertSelective(orderLog);
    	String msg = "shop_orderLog_delete" ;
     return "redirect:/shop/orderLog/index.html?msg="+msg;
    }
    
    @RequestMapping("/edit")
    @Menu(type = "shop" , subtype = "orderLog")
    public ModelAndView edit(ModelMap map , @Valid String id) {
    	ModelAndView mv =  new ModelAndView();
    	mv.addObject("orderLogData", OrderLogService.selectByPrimaryKey(Long.parseLong(id)));
		mv.setViewName("/shop/orderLog/edit");
		return mv;
    }
    
    @RequestMapping("/update")
    @Menu(type = "shop" , subtype = "orderLog" , admin = true)
    public String update(HttpServletRequest request ,@Valid OrderLog orderLog) {
    	OrderLogService.updateByPrimaryKeySelective(orderLog);
    	String msg = "shop_orderLog_update" ;
        return "redirect:/shop/orderLog/index.html?msg="+msg;
    	
    }
    
    @RequestMapping("/delete")
    @Menu(type = "shop" , subtype = "orderLog")
    public String delete(HttpServletRequest request ,@Valid OrderLog orderLog) {
    	OrderLogService.delete(orderLog);
    	String msg = "shop_orderLog_delete" ;
    	return "redirect:/shop/orderLog/index.html?msg="+msg;
    }
}