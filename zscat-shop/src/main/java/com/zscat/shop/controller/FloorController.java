package com.zscat.shop.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageInfo;
import com.zscat.shop.model.Floor;
import com.zscat.shop.service.FloorService;
import com.zscat.util.Menu;
import com.zscat.util.StringUtil;


/**
 * 
 * @author zsCat 2017-4-14 13:56:18
 * @Email: 951449465@qq.com
 * @version 4.0v
 */
@Controller
@RequestMapping("/shop/floor")
public class FloorController{
	
	@Autowired
	private FloorService FloorService;
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
    @Menu(type = "shop" , subtype = "floor")
    public ModelAndView index(ModelMap map , HttpServletRequest request) {
    	
        ModelAndView mv =  new ModelAndView();
		mv.setViewName("/shop/floor/floor");
		PageInfo<Floor> floorList = FloorService.selectPage(getP(request), 10, new Floor());
		mv.addObject("floorList", floorList);
		return mv;
    }
    
    @RequestMapping("/add")
    @Menu(type = "shop" , subtype = "floor")
    public ModelAndView add(ModelMap map , HttpServletRequest request) {
      
        ModelAndView mv =  new ModelAndView();
		mv.setViewName("/shop/floor/add");
		return mv;
    }
    
    @RequestMapping("/save")
    @Menu(type = "shop" , subtype = "floor")
    public String save(HttpServletRequest request ,@Valid Floor floor) {
    	FloorService.insertSelective(floor);
    	String msg = "shop_floor_delete" ;
     return "redirect:/shop/floor/index.html?msg="+msg;
    }
    
    @RequestMapping("/edit")
    @Menu(type = "shop" , subtype = "floor")
    public ModelAndView edit(ModelMap map , @Valid String id) {
    	ModelAndView mv =  new ModelAndView();
    	mv.addObject("floorData", FloorService.selectByPrimaryKey(Long.parseLong(id)));
		mv.setViewName("/shop/floor/edit");
		return mv;
    }
    
    @RequestMapping("/update")
    @Menu(type = "shop" , subtype = "floor" , admin = true)
    public String update(HttpServletRequest request ,@Valid Floor floor) {
    	FloorService.updateByPrimaryKeySelective(floor);
    	String msg = "shop_floor_update" ;
        return "redirect:/shop/floor/index.html?msg="+msg;
    	
    }
    
    @RequestMapping("/delete")
    @Menu(type = "shop" , subtype = "floor")
    public String delete(HttpServletRequest request ,@Valid Floor floor) {
    	FloorService.delete(floor);
    	String msg = "shop_floor_delete" ;
    	return "redirect:/shop/floor/index.html?msg="+msg;
    }
}