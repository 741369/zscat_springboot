package com.zscat.front;



import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;



import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.github.pagehelper.PageInfo;
import com.zscat.common.utils.Collections3;
import com.zscat.shop.model.Address;
import com.zscat.shop.model.Cart;
import com.zscat.shop.model.Consult;
import com.zscat.shop.model.Favorites;
import com.zscat.shop.model.Member;
import com.zscat.shop.model.Order;
import com.zscat.shop.model.Payment;
import com.zscat.shop.model.Product;
import com.zscat.shop.model.Reply;
import com.zscat.shop.service.AddressService;
import com.zscat.shop.service.CartService;
import com.zscat.shop.service.ConsultService;
import com.zscat.shop.service.FavoriteService;
import com.zscat.shop.service.OrderService;
import com.zscat.shop.service.PaymentService;
import com.zscat.shop.service.ProductClassService;
import com.zscat.shop.service.ProductService;
import com.zscat.shop.service.ReplyService;
import com.zscat.util.IPUtils;
import com.zscat.util.SysUserUtils;
	/**
	 * 
	 * @author zsCat 2016-10-31 14:01:30
	 * @Email: 951449465@qq.com
	 * @version 4.0v
	 *	商品管理
	 */
@Controller
@RequestMapping("/person")
public class FrontPersonController {
		
	@Resource
	private ProductClassService ProductClassService;
	@Resource
	private ProductService ProductService;
	
	@Resource
	private OrderService OrderService;
	@Resource
	private AddressService AddressService;
	@Resource
	private PaymentService PaymentService;
	@Resource
	private ConsultService ConsultService;
	@Resource
	private FavoriteService FavoritesService;
	
	@Resource
	private CartService CartService;
	@Resource
	private ReplyService ReplyService;
//	@Resource
//	private ArticleService articleService;
	 @RequestMapping("")
	  public ModelAndView index() {
	        try {
	            ModelAndView model = new ModelAndView("/mall/person/index");
	            Member member=SysUserUtils.getSessionLoginUser();
	            model.addObject("member", member);
	            
	            Order o=new Order();
				o.setStatus(SysUserUtils.ORDER_TWO);
				o.setUserid(SysUserUtils.getSessionLoginUser().getId());
				List<Order> orderList=OrderService.select(o);
				model.addObject("orderList", orderList);
				
				Product p=new Product();
				p.setIscom(1);
				List<Product> goodsList=ProductService.select(p);
				model.addObject("goodsList", goodsList);
				model.addObject("goodscount", goodsList.size());
				//最新
			   Collections.sort(goodsList, new CalendarComparator());
			   model.addObject("goodsList1", goodsList);
			   List<Product> goodsList1=Collections3.copyTo(goodsList, Product.class);
			   //卖的好
			   Collections.sort(goodsList1, new SellHitComparator());
			   model.addObject("goodsList2", goodsList1);
//				 List<Article> artList=articleService.select(new Article());
//		           model.addObject("artList", artList);
	            //优惠劵
//	            Coupon Coupon=new Coupon();
//	            Coupon.setCouponLock(0);
//	            int conponCount=CouponService.selectCount(Coupon);
//	            model.addObject("conponCount", conponCount);
	            
	            return model;
	        } catch (Exception e) {
	            e.printStackTrace();
	            throw new RuntimeException("导航失败!");
	        }
	    }
	 // 自定义比较器：按销售情况排序  
	    static class SellHitComparator implements Comparator {  
	        public int compare(Object object1, Object object2) {// 实现接口中的方法  
	        	Product p1 = (Product) object1; // 强制转换  
	        	Product p2 = (Product) object2;  
	            return p2.getSellhit().compareTo(p1.getSellhit());  
	        }  
	    }  
	  
	    // 自定义比较器：按书出版时间来排序  
	    static class CalendarComparator implements Comparator {  
	        public int compare(Object object1, Object object2) {// 实现接口中的方法  
	            Product p1 = (Product) object1; // 强制转换  
	            Product p2 = (Product) object2;  
	            return p2.getCreateDate().compareTo(p1.getCreateDate());  
	        }  
	    }  
	 /**
	  * 个人信息
	  * @return
	  */
	 @RequestMapping("/information")
	  public ModelAndView information() {
		 ModelAndView model = new ModelAndView("/mall/person/information");
         Member member=SysUserUtils.getSessionLoginUser();
         model.addObject("member", member);
		 
      
		 
		 return model;
	 }
	  /**
	  * 安全管理
	  * @return
	  */
	 @RequestMapping("/safety")
	  public ModelAndView safety() {
		 ModelAndView model = new ModelAndView("/mall/person/safety");
		 
		 
		 return model;
	 }
	 /**
	     * 根据父类ID 获取到 下级城市
	     *
	     * @param @param  parentid
	     * @param @return
	     * @param @throws JsonGenerationException
	     * @param @throws JsonMappingException
	     * @param @throws Exception    设定文件
	     * @return Map<String,String>    返回类型
	     * @throws
	     * @Title: getChildArea
	     * @Description: TODO(这里用一句话描述这个方法的作用)
	     */
	
	 /**
	  * 地址管理
	  * @return
	  */
	 @RequestMapping("/address")
	  public ModelAndView address() {
		 ModelAndView model = new ModelAndView("/mall/person/address");
		 List<Address> addressList= AddressService.selectByMemberId();
		 model.addObject("page", addressList);
		 
		 return model;
	 }
	 /**
	 *
	 * @Title: saveAddress
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param @param jsondata
	 * @param @param model
	 * @param @return
	 * @param @throws Exception    设定文件
	 * @return Map<String,Object>    返回类型
	 * @throws
	 */
	@RequestMapping(value = "/saveAddress", method = RequestMethod.POST)
	public String saveAddress(@ModelAttribute Address address) throws Exception {
		address.setIsDefault("0");
		address.setMemberId(SysUserUtils.getSessionLoginUser().getId());
		AddressService.insertSelective(address);
		return "redirect:/person/address";
		
	}
	 /**
	 *
	 * @Title: saveAddress
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param @param jsondata
	 * @param @param model
	 * @param @return
	 * @param @throws Exception    设定文件
	 * @return Map<String,Object>    返回类型
	 * @throws
	 */
	@RequestMapping(value = "/saveAddress1", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, String> saveAddress1(@ModelAttribute Address address) throws Exception {
		address.setIsDefault("0");
		address.setMemberId(SysUserUtils.getSessionLoginUser().getId());
		AddressService.insertSelective(address);
		Map<String, String> map =  new HashMap<>();
		map.put("sucess", "true");
		return map;
		
	}
	 /**
		 * 
		 * @Title: updateDef
		 * @Description: 根据收货地址id和用户id修改默认收货地址
		 * @param @param addressId
		 * @param @param model
		 * @param @return
		 * @param @throws Exception 设定文件
		 * @return Map<String,String> 返回类型
		 * @throws
		 */
	 @RequestMapping(value = "/setAddressDef", method = RequestMethod.POST)
		public @ResponseBody
		Map<String, String> updateDef(@RequestParam(value = "addressId") String addressId) throws Exception {

			Map<String, String> map = new HashMap<>();
			int result = AddressService.updateDef(addressId, SysUserUtils.getSessionLoginUser().getId().toString());
			if(result == 1){
				map.put("success", "true");
			}else{
				map.put("success", "false");
			}
			return map;
		}
	 /**
	  * 快捷支付
	  * @return
	  */
	 @RequestMapping("/cardlist")
	  public ModelAndView cardlist() {
		 ModelAndView model = new ModelAndView("/mall/person/cardlist");
		 Payment Payment=new Payment();
		 Payment.setIsDel(1);
		 List<Payment> payList=PaymentService.select(Payment);
		 model.addObject("page", payList);
		 return model;
	 }
	 /**
	  * 添加银行卡
	  * @return
	  */
	 @RequestMapping("/cardmethod")
	  public ModelAndView cardmethod() {
		 ModelAndView model = new ModelAndView("/mall/person/cardmethod");
		 Payment Payment=new Payment();
		 Payment.setIsDel(1);
		 List<Payment> payList=PaymentService.select(Payment);
		 model.addObject("page", payList);
		 return model;
	 }
	 // return "redirect:/front";
	 
	 /**
	  * 订单管理
	  * @return
	  */
	 @RequestMapping("/order")
	  public ModelAndView order() {
		 ModelAndView model = new ModelAndView("/mall/person/order");
		
		 return model;
	 }
		@RequestMapping("/ajax/order")
		public String orderList(HttpServletRequest request) {
				try {
					String id = request.getParameter("order");
					if (id != null && !id.equals("")) {
						Order o=new Order();
						o.setStatus(Integer.parseInt(id));
						o.setUserid(SysUserUtils.getSessionLoginUser().getId());
						List<Order> orderList=OrderService.select(o);
					//	request.setAttribute("imgServer", "http://image.zscat.com");
						request.setAttribute("orderList", orderList);
					}
				} catch (Exception e) {

				}
				return "mall/person/ajax-order";
	}
	 
	 /**
	  * 退款管理
	  * @return
	  */
	 @RequestMapping("/change")
	  public ModelAndView change() {
		 ModelAndView model = new ModelAndView("/mall/person/change");
		 
		 
		 return model;
	 }
	 /**
	  * 评价管理
	  * @return
	  */
	 @RequestMapping("/comment")
	  public ModelAndView comment() {
		 ModelAndView model = new ModelAndView("/mall/person/comment");
		 Consult Consult=new Consult();
		 PageInfo<Consult> page=ConsultService.selectPage(1, 40, Consult);
		 model.addObject("page", page);
		 return model;
	 }
	 /**
	  * 我的积分
	  * @return
	  */
	 @RequestMapping("/points")
	  public ModelAndView points() {
		 ModelAndView model = new ModelAndView("/mall/person/points");
		 
		 
		 return model;
	 }
	 
	 /**
	  * 优惠劵
	  * @return
	  */
	 @RequestMapping("/coupon")
	  public ModelAndView coupon() {
		 ModelAndView model = new ModelAndView("/mall/person/coupon");
		 
		 
		 return model;
	 }
	 /**
	  * 红包
	  * @return
	  */
	 @RequestMapping("/bonus")
	  public ModelAndView bonus() {
		 ModelAndView model = new ModelAndView("/mall/person/bonus");
		 
		 
		 return model;
	 }
	 /**
	  * 账户余额
	  * @return
	  */
	 @RequestMapping("/walletlist")
	  public ModelAndView walletlist() {
		 ModelAndView model = new ModelAndView("/mall/person/walletlist");
		 
		 
		 return model;
	 }
	 /**
	  * 账单明细
	  * @return
	  */
	 @RequestMapping("/bill")
	  public ModelAndView bill() {
		 ModelAndView model = new ModelAndView("/mall/person/bill");
		 
		 
		 return model;
	 }
	 /**
	  * 收藏
	  * @return
	  */
	 @RequestMapping("/collection")
	  public ModelAndView collection() {
		 ModelAndView model = new ModelAndView("/mall/person/collection");
		 Favorites fa=new Favorites();
		 fa.setMemberId(SysUserUtils.getSessionLoginUser().getId());
//		 PageInfo<Product> page= ProductService.selectFavoritePageInfo(1, 30, fa);
//		 model.addObject("page", page);
		 return model;
	 }
	 /**
	  * 足迹
	  * @return
	  */
	 @RequestMapping("/foot")
	  public ModelAndView foot(HttpServletRequest req) {
		 ModelAndView model = new ModelAndView("/mall/person/foot");
		  String ip=IPUtils.getClientAddress(req);
//		    RedisUtils  RedisUtils=new RedisUtils();
//			Map<String,String> map=RedisUtils.hgetall(Constant.SHOPPING_HISTORY+ip);
//			List<Object> ProductList=JsonUtils.readJsonList(JsonUtils.toJsonStr(map), Product.class);
//			for (int i=0;i<l.size();i++){
//				String json=l.get(i);
//				ProductList.add(JsonUtils.fromJson(json, Product.class));
//			}
		//	model.addObject("ProductList",ProductList);
		 return model;
	 }
	 /**
	  * 商品咨询
	  * @return
	  */
	 @RequestMapping("/consultation")
	  public ModelAndView consultation() {
		 ModelAndView model = new ModelAndView("/mall/person/consultation");
		 
		 
		 return model;
	 }
	 /**
	  * 意见反馈
	  * @return
	  */
	 @RequestMapping("/suggest")
	  public ModelAndView suggest() {
		 ModelAndView model = new ModelAndView("/mall/person/suggest");
		 
		 
		 return model;
	 }
	 /**
	  * 我的消息
	  * @return
	  */
	 @RequestMapping("/news")
	  public ModelAndView news() {
		 ModelAndView model = new ModelAndView("/mall/person/news");
		 
		 
		 return model;
	 }
	/**
	 * 购物车
	 * @return
	 */
	 @RequestMapping("/cartList")
	  public ModelAndView cartList() {
		 ModelAndView model = new ModelAndView("/mall/cartList");
		 
		 List<Cart> cartList=CartService.selectOwnCart();
		 model.addObject("cartList", cartList);
		 return model;
	 }
	 /**
	  * 立即购买
	  * @param id
	  * @return
	  * @throws Exception
	  */
	 @RequestMapping("/LikBuy/{ProductId}")
		public ModelAndView ProductDetail(@PathVariable("ProductId") Long ProductId,
				 HttpSession session)throws Exception{
			ModelAndView mav=new ModelAndView();
//			 Product goods=ProductService.selectByPrimaryKey(ProductId);
//			Cart cart=new Cart();
//		 	cart.setGoodsid(ProductId);
//		 	cart.setUserid(SysUserUtils.getSessionLoginUser().getId());
//		 	Cart check=CartService.selectOne(cart);
//		 	Map<String, String> map = Maps.newHashMap();
//		 	int result=0;
//		 	if(check==null){
//		 		cart.setCount(1);
//		 		cart.setGoodsname(goods.getTitle());
//			 	cart.setPrice(goods.getPrices());
//				 result = CartService.insertSelective(cart);
//		 	}else{
//		 		check.setCount(check.getCount()+1);
//		 		result=CartService.updateByPrimaryKeySelective(check);
//		 	}
			
			Cart cart1 = new Cart();
			cart1.setUserid(SysUserUtils.getSessionLoginUser().getId());
			List<Cart> cartList=CartService.select(cart1);
			mav.addObject("cartList", cartList);
			
			 List<Address> addressList= AddressService.selectByMemberId();
			 mav.addObject("addressList", addressList);
			 
			 Payment Payment=new Payment();
			 Payment.setIsDel(1);
			 List<Payment> payList=PaymentService.select(Payment);
			 mav.addObject("payList", payList);
			 
			mav.setViewName("mall/LikBuy");
			return mav;
		}
	 /**
	  * 提交订单
	  * @param id
	  * @return
	  * @throws Exception
	  */
	 @RequestMapping("submitOrder")
		public ModelAndView submitOrder(@RequestParam(value = "cartIds") String[] cartIds,
				@RequestParam(value = "addressid") Long addressid,
				@RequestParam(value = "paymentid") Long paymentid,
				@RequestParam(value = "paymentid",defaultValue="无留言") String usercontent
				)throws Exception{
			ModelAndView mav=new ModelAndView();
			Order order=OrderService.insertOrder(cartIds,addressid,paymentid,usercontent);
			 Payment Payment=new Payment();
			 Payment.setIsDel(1);
			 List<Payment> payList=PaymentService.select(Payment);
			 mav.addObject("payList", payList);
			 if(order==null){
				 mav.setViewName("mall/forwad");
			 }else{
				 mav.setViewName("mall/success");
			 }
			
			mav.addObject("order", order);
			return mav;
		}
	 
	 
	 /**
	  * 查看已买到的宝贝
	  * @param id
	  * @return
	  * @throws Exception
	  */
	 @RequestMapping("/selledProduct/{id}")
		public ModelAndView selledProduct(@PathVariable("id") Long id)throws Exception{
			ModelAndView mav=new ModelAndView();
			Product b=ProductService.selectByPrimaryKey(id);
			mav.addObject("Product", b);
			mav.setViewName("mall/person/order");
			return mav;
		}
	 /**
	  *交易详情
	  * @param id
	  * @return
	  * @throws Exception
	  */
	 @RequestMapping("/orderDetail/{id}")
		public ModelAndView orderDetail(@PathVariable("id") Long id)throws Exception{
			ModelAndView mav=new ModelAndView();
			Product b=ProductService.selectByPrimaryKey(id);
			mav.addObject("Product", b);
			mav.setViewName("mall/person/orderinfo");
			return mav;
		}
	 /**
	  * 物流
	  * @param id
	  * @return
	  * @throws Exception
	  */
	 @RequestMapping("/logistics/{id}")
		public ModelAndView logistics(@PathVariable("id") Long id)throws Exception{
			ModelAndView mav=new ModelAndView();
			Product b=ProductService.selectByPrimaryKey(id);
			mav.addObject("Product", b);
			mav.setViewName("mall/person/orderinfo");
			return mav;
		}
	 /**
		 * 
		 * @Title: addRePly
		 * @Description:添加评论
		 * @param @param addressId
		 * @param @param model
		 * @param @return
		 * @param @throws Exception 设定文件
		 * @return Map<String,String> 返回类型
		 * @throws
		 */
	 @RequestMapping(value = "/addRePly", method = RequestMethod.POST)
		public @ResponseBody
		Map<String, String> addRePly(@RequestParam(value = "goodsid") Long goodsid,
				@RequestParam(value = "content") String content) throws Exception {
		 	Reply r=new Reply();
		 	r.setContent(content);r.setCreatedate(new Date());
		 	r.setGoodsid(goodsid);r.setStatus(1);r.setUsername(SysUserUtils.getSessionLoginUser().getUsername());
		 	r.setUserid(SysUserUtils.getSessionLoginUser().getId());
		 	
			Map<String, String> map = new HashMap<>();
			int result = ReplyService.insertSelective(r);
			if(result == 1){
				map.put("success", "true");
			}else{
				map.put("success", "false");
			}
			return map;
		}
	 /**
	  * 加入购物车
	  * @param goodsid
	  * @param content
	  * @return
	  * @throws Exception
	  */
	 @RequestMapping(value = "/addCart", method = RequestMethod.POST)
		public @ResponseBody
		Map<String, String> addCart(@RequestParam(value = "goodsid") Long goodsid) throws Exception {
		 Product goods=ProductService.selectByPrimaryKey(goodsid);
		 	Cart cart=new Cart();
		 	cart.setGoodsid(goodsid);
		 	cart.setUserid(SysUserUtils.getSessionLoginUser().getId());
		 	Cart check=CartService.selectOne(cart);
		 	Map<String, String> map = new HashMap<>();
		 	int result=0;
		 	if(check==null){
		 		cart.setCount(1);
		 		cart.setGoodsname(goods.getTitle());
		 		cart.setImg(goods.getImg());
			 	cart.setPrice(goods.getPrices());
				 result = CartService.insertSelective(cart);
		 	}else{
		 		check.setCount(check.getCount()+1);
		 		result=CartService.updateByPrimaryKeySelective(check);
		 	}
		 	
			if(result == 1){
				map.put("success", "true");
			}else{
				map.put("success", "false");
			}
			return map;
		}
	 /**
	  * 删除购物车
	  * @param id
	  * @return
	  * @throws Exception
	  */
	 @RequestMapping(value = "/deleteCart", method = RequestMethod.POST)
		public @ResponseBody
		Map<String, String> deleteCart(@RequestParam(value = "id") Long id) throws Exception {
		 	CartService.deleteByPrimaryKey(id);
		 	Map<String, String> map = new HashMap<>();
				map.put("success", "true");
			 return map;
		}
	 /**
	  * 删除订单，修改状态为9
	  * @param id
	  * @return
	  * @throws Exception
	  */
	 @RequestMapping(value = "/deleteOrder", method = RequestMethod.POST)
		public @ResponseBody
		Map<String, String> deleteOrder(@RequestParam(value = "id") Long id) throws Exception {
		 	Order o=new Order();
		 	o.setStatus(SysUserUtils.ORDER_NiNe);
		 	o.setId(id);
		 	OrderService.updateByPrimaryKeySelective(o);
		 	Map<String, String> map = new HashMap<>();
				map.put("success", "true");
			 return map;
		}
}
