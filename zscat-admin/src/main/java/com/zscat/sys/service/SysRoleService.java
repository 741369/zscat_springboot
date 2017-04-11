 /*
  * Powered By zsCat, Since 2014 - 2020
  */
package com.zscat.sys.service;

import java.util.List;

import com.zscat.base.BaseService;
import com.zscat.sys.model.SysRole;
import com.zscat.sys.model.SysUser;

/**
 * 
 * @author zsCat 2017-4-14 13:56:18
 * @Email: 951449465@qq.com
 * @version 4.0v
 */
public interface SysRoleService extends BaseService<SysRole>  {

	void deleteRoleAndUserRole(SysRole role);
	public List<SysUser> findUserByRoleId(Long roleId);

}
