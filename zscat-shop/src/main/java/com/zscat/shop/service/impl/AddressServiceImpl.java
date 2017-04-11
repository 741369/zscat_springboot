 /*
  * Powered By zsCat, Since 2014 - 2020
  */
package com.zscat.shop.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.zscat.common.base.ServiceMybatis;
import com.zscat.shop.model.Address;
import com.zscat.shop.service.AddressService;

/**
 * 
 * @author zsCat 2017-4-14 13:56:18
 * @Email: 951449465@qq.com
 * @version 4.0v
 */
@Service
public class AddressServiceImpl extends ServiceMybatis<Address> implements AddressService {

	@Override
	public List<Address> selectByMemberId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateDef(String addressId, String string) {
		// TODO Auto-generated method stub
		return 0;
	}

  
    
}
