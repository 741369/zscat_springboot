 /*
  * Powered By zsCat, Since 2014 - 2020
  */
package com.zscat.shop.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.zscat.common.base.ServiceMybatis;
import com.zscat.shop.model.Member;
import com.zscat.shop.service.MemberService;
import com.zscat.util.PasswordEncoder;

/**
 * 
 * @author zsCat 2017-4-14 13:56:18
 * @Email: 951449465@qq.com
 * @version 4.0v
 */
@Service
public class MemberServiceImpl extends ServiceMybatis<Member> implements MemberService {

	@Override
	public Member checkUser(String username, String password) {
		Member sysUser = new Member();
		String secPwd = PasswordEncoder.encrypt(password, username);
		sysUser.setUsername(username);
		sysUser.setPassword(secPwd);
		List<Member> users = this.select(sysUser);
		if(users != null && users.size() == 1 && users.get(0) != null) {
			return users.get(0);
		}
		return null;
	}

  
    
}
