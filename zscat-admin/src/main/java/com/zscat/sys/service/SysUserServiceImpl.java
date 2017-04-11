 /*
  * Powered By zsCat, Since 2014 - 2020
  */
package com.zscat.sys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zscat.base.ServiceMybatis;
import com.zscat.sys.mapper.SysUserMapper;
import com.zscat.sys.model.SysUser;
import com.zscat.util.PasswordEncoder;

/**
 * 
 * @author zsCat 2017-4-14 13:56:18
 * @Email: 951449465@qq.com
 * @version 4.0v
 */
@Service
public class SysUserServiceImpl extends ServiceMybatis<SysUser> implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

	@Override
	public SysUser checkUser(String username, String password) {
		SysUser sysUser = new SysUser();
		String secPwd = PasswordEncoder.encrypt(password, username);
		sysUser.setUsername(username);
		sysUser.setPassword(secPwd);
		List<SysUser> users = this.select(sysUser);
		if(users != null && users.size() == 1 && users.get(0) != null) {
			return users.get(0);
		}
		return null;
	}
	public static void main(String[] args) {
		String secPwd = PasswordEncoder.encrypt("admin", "admin");
		System.out.println(secPwd);
	}
    
}
