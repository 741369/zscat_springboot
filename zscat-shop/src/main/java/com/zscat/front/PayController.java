package com.zscat.front;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jdom.JDOMException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;




import com.mysql.jdbc.log.LogUtils;
import com.zscat.common.utils.Utils;
import com.zscat.shop.model.Order;
import com.zscat.shop.model.OrderLog;
import com.zscat.shop.service.FloorService;
import com.zscat.shop.service.MemberService;
import com.zscat.shop.service.OrderLogService;
import com.zscat.shop.service.OrderService;
import com.zscat.shop.service.ProductClassService;
import com.zscat.shop.service.ProductService;
import com.zscat.shop.service.ProductTypeService;
import com.zscat.util.Symphonys;
import com.zscat.util.SysUserUtils;
import com.zscat.util.pay.CommUtil;
import com.zscat.util.pay.WxCommonUtil;
import com.zscat.util.pay.alipay.config.AlipayConfig;
import com.zscat.util.pay.alipay.services.AlipayService;
import com.zscat.util.pay.alipay.util.AlipayNotify;

	/**
	 * 
	 * @author zsCat 2016-10-31 14:01:30
	 * @Email: 951449465@qq.com
	 * @version 4.0v
	 *	商品管理
	 */
@Controller
public class PayController {

	@Resource
	private ProductClassService ProductClassService;
	@Resource
	private ProductService ProductService;
	@Resource
	private MemberService MemberService;
	@Resource
	private  FloorService floorService;
	
	@Resource
	private ProductTypeService ProductTypeService;
	@Resource
	private OrderService OrderService;
	@Resource
	private OrderLogService OrderLogService;
	/**
	 * 微信扫码支付
	 * 
	 * @param request
	 * @param response
	 * @param order_id
	 * @return
	 */
	@RequestMapping({ "/wechat/wxcodepay" })
	public void wxcodepay(HttpServletRequest request, HttpServletResponse response, Long order_id,String payType) {

		String UNI_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
		Order of=OrderService.selectByPrimaryKey(order_id);
		String returnhtml = null;
		if (of.getStatus() == SysUserUtils.ORDER_ONE) {
		if("wxcodepay".equals(payType)){
			of.setPaymentid(1L);
		}
			
			String codeUrl = "";// 微信返回的二维码地址信息

			SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
			parameters.put("appid", Symphonys.get("Weixin_appId"));// 公众账号id
			parameters.put("mch_id", Symphonys.get("Weixin_partnerId"));// 商户号
			parameters.put("nonce_str", WxCommonUtil.createNoncestr());// 随机字符串
			parameters.put("body", Symphonys.get("goodsdesc"));// 商品描述
			parameters.put("out_trade_no", of.getId()+"");// 商户订单号
			//parameters.put("total_fee", of.getTotalprice().multiply(new BigDecimal(100)).setScale(0).toString());// 总金额
			parameters.put("total_fee", "1");// 总金额
			parameters.put("spbill_create_ip", WxCommonUtil.localIp());// 终端IP.Native支付填调用微信支付API的机器IP。
			// 支付成功后回调的action，与JSAPI相同
			// parameters.put("notify_url", basePath + NOTIFY_URL);//
			// 支付成功后回调的action
			parameters.put("notify_url", Utils.getURL(request) + "/wechat/paynotify");// 支付成功后回调的action，与JSAPI相同
			parameters.put("trade_type", "NATIVE");// 交易类型
			parameters.put("product_id", order_id+"");// 商品ID。商品号要唯一,trade_type=NATIVE，此参数必传。此id为二维码中包含的商品ID，商户自行定义
			// String sign = WxPayUtil.createSign2("UTF-8", parameters,
			// API_KEY);
			String sign = WxCommonUtil.createSignMD5("UTF-8", parameters, Symphonys.get("Weixin_paySignKey"));
			parameters.put("sign", sign);// 签名
			String requestXML = WxCommonUtil.getRequestXml(parameters);
			System.out.println("requestXML" + requestXML);
			String result = WxCommonUtil.httpsRequestString(UNI_URL, "POST", requestXML);// WxCommonUtil.httpsRequest(WxConstants.UNIFIED_ORDER_URL,
																							// "POST",
																							// requestXML);
			// System.out.println(" 微信支付二维码生成" + result);
			Map<String, String> map = new HashMap<String, String>();
			try {
				map = WxCommonUtil.doXMLParse(result);
				System.out.println("------------------code_url=" + map.get("code_url") + ";      result_code="
						+ map.get("code_url") + "------------------------------");
			} catch (Exception e) {
				System.out.println("doXMLParse()--error");
			}
			String returnCode = map.get("return_code");
			String resultCode = map.get("result_code");

			if (returnCode.equalsIgnoreCase("SUCCESS") && resultCode.equalsIgnoreCase("SUCCESS")) {
				codeUrl = map.get("code_url");
				// 拿到codeUrl，生成二维码图片TODO
			//	byte[] imgs = QRCodeEncoderHandler.createQRCode(codeUrl);

				String urls = request.getRealPath("/") +  "upload" 
						+ java.io.File.separator + "weixin_qr" + java.io.File.separator + "wxpay"
						+ java.io.File.separator;
				File file = new File(urls, order_id + ".png");
				if (!file.exists()) {
					if (!file.getParentFile().exists()) {
						file.getParentFile().mkdirs();
					}
					try {
						file.createNewFile();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				// 图片的实际路径
				String imgfile = urls + order_id + ".png";
//TODO
			//	QRCodeEncoderHandler.saveImage(imgs, imgfile, "png");

				// 图片的网路路径
				String imgUrl = Utils.getURL(request) + "/" +  "upload" 
						+ "/weixin_qr/wxpay/" + order_id + ".png";
				//String imgUrl = Utils.getURL(request)+"/static/shen.png";
			//	LogUtils.BUSSINESS_LOG.info("图片的网路路径imgurl={}", imgUrl);
 
				returnhtml = "<img src='" + imgUrl + "' style='width:200px;height:200px;'/>";

			} else {
				returnhtml = "支付状态不正确";
			}
		}
		response.setContentType("text/plain");
		response.setHeader("Cache-Control", "no-cache");
		response.setCharacterEncoding("UTF-8");
		try {
			PrintWriter writer = response.getWriter();
			writer.print(returnhtml);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// return returnhtml;
	} 
	
	/***
     * 付款成功回调处理，你必须要返回SUCCESS信息给微信服务器，告诉微信服务器我已经收到支付成功的后台通知了。
     * 不然的话，微信会一直调用该回调地址，当达到8次的时候还是没有收到SUCCESS的返回，微信服务器则认为此订单支付失败。
     * <p>
     * 该回调地址是异步的。 这里可以处理数据库中的订单状态。
     * <p>
     * 作者: YUKE 日期：2016年1月14日 上午9:25:29
     *
     * @param response
     * @throws IOException
     * @throws JDOMException
     */
    @RequestMapping({"/wechat/paynotify"})
    public String notify_success(HttpServletRequest request, HttpServletResponse response)
            throws IOException, JDOMException {

        InputStream inStream = request.getInputStream();
        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outSteam.write(buffer, 0, len);
        }
        System.out.println("~~~~~~~~~~~~~~~~付款成功，收到微信回调~~~~~~~~~");
        outSteam.close();
        inStream.close();

        /** 支付成功后，微信回调返回的信息 */
        String result = new String(outSteam.toByteArray(), "utf-8");
        Map<String, String> map = WxCommonUtil.doXMLParse(result);

//        for (Object keyValue : map.keySet()) {
//            /** 输出返回的订单支付信息 */
//            logger.info(keyValue + "=" + map.get(keyValue));
//        }
        // 支付签名验证
        /*
         * if (verifyNotify(notifyMethod, request)) {
		 * 
		 * }
		 */
        String order_no = map.get("product_id");
        Order order = null;
        order = OrderService.selectByPrimaryKey(Long.parseLong(order_no));

        if (map.get("result_code").toString().equalsIgnoreCase("SUCCESS")) {
            
         order.setStatus(SysUserUtils.ORDER_TWO);
           OrderService.updateByPrimaryKeySelective(order);
           OrderLog log=new OrderLog();
			log.setOrderId(order.getId());
			log.setOrderState("2");
			log.setStateInfo("微信扫码支付成功");
			log.setCreateTime(new Date().getTime());
			log.setOperator(SysUserUtils.getSessionLoginUser().getUsername());
			OrderLogService.insertSelective(log);
            // 告诉微信服务器，我收到信息了，不要在调用回调方法(/pay)了
            System.out.println("-------------" + WxCommonUtil.setXML("SUCCESS", "OK"));
            response.getWriter().write(WxCommonUtil.setXML("SUCCESS", "OK"));
        } else {
            System.out.println("------微信异步回调失败-------");
        }
        return "";
    }

    /**
     * 微信公众号支付前台页面回调
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping({"/wechat/paysuccess"})
    public ModelAndView wxpaySuccess(HttpServletRequest request, HttpServletResponse response, Long order_id) {
        ModelAndView mv =new ModelAndView();
        mv.setViewName("wap/paysuccess.html");
        	
        mv.addObject("obj", OrderService.selectByPrimaryKey(order_id));
        return mv;
    }

    @RequestMapping("/order_pay")
	public ModelAndView order_pay(HttpServletRequest request, HttpServletResponse response, String payType,
			String order_id) {
    	 ModelAndView mv =new ModelAndView();
        
		Order of = OrderService.selectByPrimaryKey(Long.parseLong(order_id));
		if (of.getStatus() == SysUserUtils.ORDER_ONE) {
			if (CommUtil.null2String(payType).equals("")) {
				
				 mv.setViewName("error");
				mv.addObject("op_title", "支付方式错误！");
				mv.addObject("url", CommUtil.getURL(request) + "/front");

			} else {
				 mv.setViewName("mall/pay");
				 mv.addObject("url", CommUtil.getURL(request) + "/front");
				of.setPaymentid(2L);
				OrderService.updateByPrimaryKeySelective(of);
//				if (payType.equals("balance")) {
//					mv = new JModelAndView("balance_pay.html", this.configService.getSysConfig(),
//							this.userConfigService.getUserConfig(), 1, request, response);
//				} else if (payType.equals("outline")) {
//					mv = new JModelAndView("outline_pay.html", this.configService.getSysConfig(),
//							this.userConfigService.getUserConfig(), 1, request, response);
//					String pay_session = CommUtil.randomString(32);
//					request.getSession(false).setAttribute("pay_session", pay_session);
//					mv.addObject("paymentTools", this.paymentTools);
//					mv.addObject("store_id",
//							this.orderFormService.getObjById(CommUtil.null2Long(order_id)).getStore().getId());
//					mv.addObject("pay_session", pay_session);
//				} else if (payType.equals("payafter")) {
//					mv = new JModelAndView("payafter_pay.html", this.configService.getSysConfig(),
//							this.userConfigService.getUserConfig(), 1, request, response);
//					String pay_session = CommUtil.randomString(32);
//					request.getSession(false).setAttribute("pay_session", pay_session);
//					mv.addObject("paymentTools", this.paymentTools);
//					mv.addObject("store_id",
//							this.orderFormService.getObjById(CommUtil.null2Long(order_id)).getStore().getId());
//					mv.addObject("pay_session", pay_session);
//				} else {
//					mv = new JModelAndView("line_pay.html", this.configService.getSysConfig(),
//							this.userConfigService.getUserConfig(), 1, request, response);
//					mv.addObject("payType", payType);
//					mv.addObject("url", CommUtil.getURL(request));
//					mv.addObject("payTools", this.payTools);
//					mv.addObject("type", "goods");
//					mv.addObject("payment_id", of.getPayment().getId());
//				}
//				mv.addObject("order_id", of.getOrder_id());
				mv.addObject("order_id", of.getId());
			}
		} else {
			 mv.setViewName("error");
			mv.addObject("op_title", "该订单不能进行付款！");
			mv.addObject("url", CommUtil.getURL(request) + "/front");
		}
		return mv;
	}
    @RequestMapping({ "/order_finish" })
	public ModelAndView order_finish(HttpServletRequest request, HttpServletResponse response, String order_id) {
    	 ModelAndView mv =new ModelAndView();
         mv.setViewName("mall/order_finish.html");
		 Order obj=OrderService.selectByPrimaryKey(Long.parseLong(order_id));
		mv.addObject("obj", obj);
		return mv;
	}
    
    // 支付宝
 // alipay------------------------
    @RequestMapping({ "/alipay" })
 	public @ResponseBody void genericAlipay(HttpServletResponse response,HttpServletRequest request,String id) {
 		String result = "";
 		Order of = OrderService.selectByPrimaryKey(Long.parseLong(id));

 		AlipayConfig config = new AlipayConfig();

 			config.setKey(Symphonys.get("alipay_key"));
 			config.setPartner(Symphonys.get("alipay_partner"));

 	//	config.setSeller_email(payment.getSeller_email());
 		config.setNotify_url(CommUtil.getURL(request) +"/alipay_notify");
 		config.setReturn_url(CommUtil.getURL(request) +"/aplipay_return");

 		String type="goods";
 			String out_trade_no = "";
 			if (type.equals("goods")) {
 			//	out_trade_no = of.getId().toString();
 				out_trade_no = of.getId().toString();
 			}
 			

 			String subject = "";
 			
 				subject = Symphonys.get("goodsdesc");

 		 	String body = type;
 
 			String total_fee = CommUtil.null2String(of.getTotalprice());
 			

 			String paymethod = "";

 			String defaultbank = "";

 			String anti_phishing_key = "";

 			String exter_invoke_ip = "";

 			String extra_common_param = type;

 			String buyer_email = "";

 			String show_url = "";

 			String royalty_type = "10";

 			
 			Map sParaTemp = new HashMap();
 			sParaTemp.put("payment_type", "1");
 			sParaTemp.put("out_trade_no", out_trade_no);
 			sParaTemp.put("subject", subject);
 			//sParaTemp.put("body", bodys.substring(0, bodys.length()-1));
 			//sParaTemp.put("body", bodys.toString());
 			sParaTemp.put("body", "支付宝pc支付,"+type);
 			sParaTemp.put("total_fee", "1");
 			sParaTemp.put("show_url", show_url);
 			sParaTemp.put("paymethod", paymethod);
 			sParaTemp.put("defaultbank", defaultbank);
 			sParaTemp.put("anti_phishing_key", anti_phishing_key);
 			sParaTemp.put("exter_invoke_ip", exter_invoke_ip);
 			sParaTemp.put("extra_common_param", extra_common_param);
 			sParaTemp.put("buyer_email", buyer_email);
// 			if ((type.equals("goods")) && (sys_config.getAlipay_fenrun() == 1)) {
// 				sParaTemp.put("royalty_type", royalty_type);
// 				sParaTemp.put("royalty_parameters", royalty_parameters);
// 			}

 			result = AlipayService.create_direct_pay_by_user(config, sParaTemp);
 	
 		
 		//	result = AlipayService.trade_create_by_buyer(config, sParaTemp);
 			response.setContentType("text/plain");
 			response.setHeader("Cache-Control", "no-cache");
 			response.setCharacterEncoding("UTF-8");
 			try {
 				PrintWriter writer = response.getWriter();
 				writer.print(result);
 			} catch (IOException e) {
 				e.printStackTrace();
 			}
 		
 	}
    /**
     * 支付宝后台台回调
     */
    @RequestMapping({"/alipay/alipay_notify"})
    public void alipayNotify(HttpServletRequest request, HttpServletResponse respons) throws Exception {
        // 获取支付宝POST过来反馈信息
        Map<String, String> params = new HashMap<String, String>();
        Map requestParams = request.getParameterMap();
        System.out.println(request.getQueryString());
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                // valueStr = (i == values.length - 1) ? valueStr + values[i] :
                // valueStr + values[i] + ",";
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i];
            }
            // 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
            // valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
            params.put(name, valueStr);
        }
        // 商户订单号
        String out_trade_no = request.getParameter("out_trade_no");

        Order order=OrderService.selectByPrimaryKey(Long.parseLong(out_trade_no));

        // 支付宝交易号
        String trade_no = request.getParameter("trade_no");

        // 交易状态
        String trade_status = request.getParameter("trade_status");

        AlipayConfig config = new AlipayConfig();

        config.setKey(Symphonys.get("alipay_key"));
			config.setPartner(Symphonys.get("alipay_partner"));
       // config.setSeller_email(order.getPayment().getSeller_email());

        config.setTransport("https");

        config.setNotify_url(CommUtil.getURL(request) + "/alipay_notify");
        config.setReturn_url(CommUtil.getURL(request) + "/aplipay_return");

        boolean check = AlipayNotify.verifyWap(config, params);
        check = true;
        if (check) {// 验证成功

            if (((trade_status.equals("WAIT_SELLER_SEND_GOODS")) || (trade_status.equals("TRADE_FINISHED"))
                    || (trade_status.equals("TRADE_SUCCESS")))) {
                
               
            }
            try {
                respons.getWriter().println("success");
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {// 验证失败
            try {
                respons.getWriter().println("fail");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 支付宝前台回调
     */
    @RequestMapping({"/alipay/alipay_retrun"})
    public void alipayRetrun(HttpServletRequest request, HttpServletResponse respons) {
        // 获取支付宝GET过来反馈信息
        Map<String, String> params = new HashMap<String, String>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            // 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
            // valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }

        // 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
        // 商户订单号
        // 商户订单号
        String out_trade_no = request.getParameter("out_trade_no");

        System.out.println("-----------------支付宝回调-----------------out_trade_no=" + out_trade_no);

        Order order = OrderService.selectByPrimaryKey(Long.parseLong(out_trade_no));

        // 支付宝交易号
        String trade_no = request.getParameter("trade_no");

        // 交易状态
        String trade_status = request.getParameter("trade_status");

        AlipayConfig config = new AlipayConfig();

        // 通过RSA校验,可以注释掉，通md5的校验打开
        // config.setSeller_email(order.getPayment().getSeller_email());
        // config.setKey(order.getPayment().getSafeKey());
      //  config.setPartner(order.getPayment().getPartner());

        config.setNotify_url(CommUtil.getURL(request) + "/alipay/alipay_notify");
        config.setReturn_url(
                CommUtil.getURL(request) + "/alipay/aplipay_return");

        // 计算得出通知验证结果
        // boolean verify_result = AlipayNotify.verify(config, params);
        boolean verify_result = true;

        if (verify_result) {// 验证成功

            if (trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")) {

                try {
                    respons.sendRedirect(
                            CommUtil.getURL(request) + "/mall/paysuccess");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } else {
            // 该页面可做页面美工编辑
            try {
                respons.getWriter().println("验证失败");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    /**
     * 支付宝前台页面回调显示
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping({"/alipay/paysuccess.htm"})
    public ModelAndView account_password(HttpServletRequest request, HttpServletResponse response) {
    	 ModelAndView mv =new ModelAndView();
         mv.setViewName("wap/paysuccess.html");
         	
      //   mv.addObject("obj", OrderService.selectByPrimaryKey(order_id));
         return mv;
    }

}
