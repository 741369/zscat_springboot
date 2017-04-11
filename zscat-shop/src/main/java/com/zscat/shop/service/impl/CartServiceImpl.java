 /*
  * Powered By zsCat, Since 2014 - 2020
  */
package com.zscat.shop.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zscat.common.base.ServiceMybatis;
import com.zscat.shop.mapper.CartMapper;
import com.zscat.shop.model.Cart;
import com.zscat.shop.service.CartService;
import com.zscat.util.SysUserUtils;

/**
 * 
 * @author zsCat 2017-4-14 13:56:18
 * @Email: 951449465@qq.com
 * @version 4.0v
 */
@Service
public class CartServiceImpl extends ServiceMybatis<Cart> implements CartService {
	@Resource
	private CartMapper CartMapper;

	 @Override
		public List<Cart> selectOwnCart() {
			if(SysUserUtils.getSessionLoginUser()!=null){
				Cart cart=new Cart();
				cart.setUserid(SysUserUtils.getSessionLoginUser().getId());
				return CartMapper.select(cart);
			}
			return null;
			
		}
		@Override
		public int selectOwnCartCount() {
			if(SysUserUtils.getSessionLoginUser()!=null){
				Cart cart=new Cart();
				cart.setUserid(SysUserUtils.getSessionLoginUser().getId());
				return CartMapper.selectCount(cart);
			}
			return 0;
			
		}

  
    
}
