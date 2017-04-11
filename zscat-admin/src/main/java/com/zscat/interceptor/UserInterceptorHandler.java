package com.zscat.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.autoconfigure.web.BasicErrorController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.zscat.sys.model.SysUser;
import com.zscat.sys.util.Constant;
import com.zscat.util.Menu;



public class UserInterceptorHandler extends HandlerInterceptorAdapter {
	
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
    	boolean filter = false; 
        SysUser user = (SysUser) request.getSession(true).getAttribute(Constant.SESSION_LOGIN_USER) ;
        HandlerMethod  handlerMethod = (HandlerMethod ) handler ;
        Menu menu = handlerMethod.getMethod().getAnnotation(Menu.class) ;
        if(user != null || (menu!=null && menu.access()) || handlerMethod.getBean() instanceof BasicErrorController){
        	filter = true;
        }
        
        if(!filter){
        	response.sendRedirect("/login.html");
        }
        return filter ; 
    }

    public void postHandle(HttpServletRequest arg0, HttpServletResponse response, Object arg2,
            ModelAndView view) throws Exception {
    	SysUser user = (SysUser) arg0.getSession().getAttribute(Constant.SESSION_LOGIN_USER) ;
    	if( view!=null){
	    	if(user!=null){
				view.addObject("user", user) ;
				view.addObject("schema",arg0.getScheme()) ;
				view.addObject("hostname",arg0.getServerName()) ;
				view.addObject("port",arg0.getServerPort()) ;
				
				HandlerMethod  handlerMethod = (HandlerMethod ) arg2 ;
				Menu menu = handlerMethod.getMethod().getAnnotation(Menu.class) ;
				if(menu!=null){
					view.addObject("subtype", menu.subtype()) ;
					view.addObject("maintype", menu.type()) ;
					view.addObject("typename", menu.name()) ;
				}
				//view.addObject("orgi", user.getOrgi()) ;
			}
	    //	view.addObject("webimport",UKDataContext.getWebIMPort()) ;
	    //	view.addObject("sessionid", UKTools.getContextID(arg0.getSession().getId())) ;
	    	
	    //	view.addObject("models", UKDataContext.model) ;
			/**
			 * WebIM共享用户
			 */
	    	SysUser imUser = (SysUser) arg0.getSession().getAttribute(Constant.SESSION_LOGIN_USER) ;
			if(imUser == null && view!=null){
				imUser = new SysUser();
				imUser.setUsername("guest") ;
				imUser.setId(1L) ;
				//imUser.setSessionid(imUser.getId()) ;
				view.addObject("imuser", imUser) ;
			}
			
			if(arg0.getParameter("msg") != null){
				view.addObject("msg", arg0.getParameter("msg")) ;
			}
		//	view.addObject("uKeFuDic", UKeFuDic.getInstance()) ;	//处理系统 字典数据 ， 通过 字典code 获取
    	}
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
    }

}