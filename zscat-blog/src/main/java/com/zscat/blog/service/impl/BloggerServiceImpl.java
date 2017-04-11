 /*
  * Powered By zsCat, Since 2014 - 2020
  */
package com.zscat.blog.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.zscat.base.ServiceMybatis;
import com.zscat.blog.model.Blogger;
import com.zscat.blog.service.BloggerService;
import com.zscat.util.PasswordEncoder;

/**
 * 
 * @author zsCat 2017-4-14 13:56:18
 * @Email: 951449465@qq.com
 * @version 4.0v
 */
@Service
public class BloggerServiceImpl extends ServiceMybatis<Blogger> implements BloggerService {

	@Override
	public Blogger checkBlogger(String username, String password) {
		Blogger sysUser = new Blogger();
		String secPwd = PasswordEncoder.encrypt(password, username);
		sysUser.setUsername(username);
		sysUser.setPassword(secPwd);
		List<Blogger> users = this.select(sysUser);
		if(users != null && users.size() == 1 && users.get(0) != null) {
			return users.get(0);
		}
		return null;
	}

 

    
}
