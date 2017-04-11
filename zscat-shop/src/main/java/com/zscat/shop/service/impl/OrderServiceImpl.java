 /*
  * Powered By zsCat, Since 2014 - 2020
  */
package com.zscat.shop.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zscat.common.base.ServiceMybatis;
import com.zscat.common.utils.RandomString;
import com.zscat.shop.mapper.CartMapper;
import com.zscat.shop.mapper.GoodsOrderMapper;
import com.zscat.shop.mapper.OrderLogMapper;
import com.zscat.shop.mapper.OrderMapper;
import com.zscat.shop.model.Cart;
import com.zscat.shop.model.GoodsOrder;
import com.zscat.shop.model.Order;
import com.zscat.shop.model.OrderLog;
import com.zscat.shop.service.OrderService;
import com.zscat.util.SysUserUtils;

/**
 * 
 * @author zsCat 2017-4-14 13:56:18
 * @Email: 951449465@qq.com
 * @version 4.0v
 */
@Service
public class OrderServiceImpl extends ServiceMybatis<Order> implements OrderService {

	@Resource
	private OrderMapper OrderMapper;
	
	@Resource
	private GoodsOrderMapper GoodsOrderMapper;
	@Resource
	private  CartMapper CartMapper;
	@Resource
	private OrderLogMapper  OrderLogMapper;
	@Override
	public Order insertOrder(String[] cartIds,Long addressid, Long paymentid, String usercontent) {
		Order order=new Order();
		if(cartIds!=null && cartIds.length>0){
			int count=0;
			BigDecimal total=BigDecimal.ZERO;
			for(String cartId:cartIds){
				Cart cart=CartMapper.selectByPrimaryKey(Long.parseLong(cartId));
				if(cart==null){
					return null;
				}
				count+=cart.getCount();
				 total =total.add(BigDecimal.valueOf(Double.valueOf(cart.getPrice())).multiply(BigDecimal.valueOf(cart.getCount())));
				GoodsOrder go=new GoodsOrder();
				go.setGoodsid(cart.getGoodsid());
				go.setOrderid(order.getId());
				GoodsOrderMapper.insertSelective(go);
				CartMapper.deleteByPrimaryKey(cart);
			}
			order.setOrdersn(RandomString.generateRandomString(8));
			order.setCreatedate(new Date());
			order.setStatus(1);
			order.setUserid(SysUserUtils.getSessionLoginUser().getId());
			order.setUsername(SysUserUtils.getSessionLoginUser().getUsername());
			order.setPaymentid(paymentid);
			order.setUsercontent(usercontent);
			order.setAddressid(addressid);
			//order.setOrderTotalPrice();
			OrderMapper.insertSelective(order);
			
			OrderLog log=new OrderLog();
			log.setOrderId(order.getId());
			log.setOrderState("1");
			log.setStateInfo("提交订单");
			log.setCreateTime(new Date().getTime());
			log.setOperator(SysUserUtils.getSessionLoginUser().getUsername());
			OrderLogMapper.insertSelective(log);
			
			order.setTotalcount(count);
			order.setTotalprice(total);
			OrderMapper.updateByPrimaryKey(order);
		}
		return order;
		
	}

 
    
}
