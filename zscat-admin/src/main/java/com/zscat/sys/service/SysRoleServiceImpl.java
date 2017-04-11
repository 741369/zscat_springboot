 /*
  * Powered By zsCat, Since 2014 - 2020
  */
package com.zscat.sys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zscat.base.ServiceMybatis;
import com.zscat.sys.mapper.SysRoleMapper;
import com.zscat.sys.mapper.SysUserRoleMapper;
import com.zscat.sys.model.SysRole;
import com.zscat.sys.model.SysUser;
import com.zscat.sys.model.SysUserRole;

/**
 * 
 * @author zsCat 2017-4-14 13:56:18
 * @Email: 951449465@qq.com
 * @version 4.0v
 */
@Service
public class SysRoleServiceImpl extends ServiceMybatis<SysRole> implements SysRoleService {

    @Autowired
    private SysRoleMapper SysRoleMapper;
    @Autowired
    private  SysUserRoleMapper SysUserRoleMapper;
    
	@Override
	public void deleteRoleAndUserRole(SysRole role) {
		SysUserRole ur=new SysUserRole();
		ur.setRoleId(role.getId());
		SysUserRoleMapper.delete(ur);
		SysRoleMapper.delete(role);
		
	}

	@Override
	public List<SysUser> findUserByRoleId(Long roleId){
		return SysRoleMapper.findUserByRoleId(roleId);
	}
    
}
