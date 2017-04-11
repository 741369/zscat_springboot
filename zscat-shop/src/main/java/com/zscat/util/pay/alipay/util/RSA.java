 package com.zscat.util.pay.alipay.util;
 
 import java.io.ByteArrayInputStream;
 import java.io.ByteArrayOutputStream;
 import java.io.InputStream;
 import java.security.KeyFactory;
 import java.security.PrivateKey;
 import java.security.PublicKey;
 import java.security.Signature;
 import java.security.spec.PKCS8EncodedKeySpec;
 import java.security.spec.X509EncodedKeySpec;
 import javax.crypto.Cipher;
 
 public class RSA
 {
   public static final String SIGN_ALGORITHMS = "SHA1WithRSA";
 
   public static String sign(String content, String privateKey, String input_charset)
   {
     try
     {
       PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(
         Base64Wap.decode(privateKey));
       KeyFactory keyf = KeyFactory.getInstance("RSA");
       PrivateKey priKey = keyf.generatePrivate(priPKCS8);
 
       Signature signature = 
         Signature.getInstance("SHA1WithRSA");
 
       signature.initSign(priKey);
       signature.update(content.getBytes(input_charset));
 
       byte[] signed = signature.sign();
 
       return Base64Wap.encode(signed);
     } catch (Exception e) {
       e.printStackTrace();
     }
 
     return null;
   }
 
   public static boolean verify(String content, String sign, String ali_public_key, String input_charset)
   {
     try
     {
       KeyFactory keyFactory = KeyFactory.getInstance("RSA");
       byte[] encodedKey = Base64Wap.decode(ali_public_key);
       PublicKey pubKey = keyFactory
         .generatePublic(new X509EncodedKeySpec(encodedKey));
 
       Signature signature = 
         Signature.getInstance("SHA1WithRSA");
 
       signature.initVerify(pubKey);
       signature.update(content.getBytes(input_charset));
 
       boolean bverify = signature.verify(Base64Wap.decode(sign));
       return bverify;
     }
     catch (Exception e) {
       e.printStackTrace();
     }
 
     return false;
   }
 
   public static String decrypt(String content, String private_key, String input_charset)
     throws Exception
   {
     PrivateKey prikey = getPrivateKey(private_key);
 
     Cipher cipher = Cipher.getInstance("RSA");
     cipher.init(2, prikey);
 
     InputStream ins = new ByteArrayInputStream(Base64Wap.decode(content));
     ByteArrayOutputStream writer = new ByteArrayOutputStream();
 
     byte[] buf = new byte[''];
     int bufl;
     while ((bufl = ins.read(buf)) != -1)
     {
       //int bufl;
       byte[] block = null;
 
       if (buf.length == bufl) {
         block = buf;
       } else {
         block = new byte[bufl];
         for (int i = 0; i < bufl; i++) {
           block[i] = buf[i];
         }
       }
 
       writer.write(cipher.doFinal(block));
     }
 
     return new String(writer.toByteArray(), input_charset);
   }
 
   public static PrivateKey getPrivateKey(String key)
     throws Exception
   {
     byte[] keyBytes = Base64Wap.decode(key);
 
     PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
 
     KeyFactory keyFactory = KeyFactory.getInstance("RSA");
 
     PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
 
     return privateKey;
   }
   
   
	/**
	* RSA签名
	* @param content 待签名数据
	* @param privateKey 商户私钥
	* @param input_charset 编码格式
	* @return 签名值
	*/
	public static String signWap(String content, String privateKey, String input_charset)
	{
       try 
       {
       	PKCS8EncodedKeySpec priPKCS8 	= new PKCS8EncodedKeySpec( Base64Wap.decode(privateKey) ); 
       	KeyFactory keyf 				= KeyFactory.getInstance("RSA");
       	PrivateKey priKey 				= keyf.generatePrivate(priPKCS8);

           java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);

           signature.initSign(priKey);
           signature.update( content.getBytes(input_charset) );

           byte[] signed = signature.sign();
           
           return Base64Wap.encode(signed);
       }
       catch (Exception e) 
       {
       	e.printStackTrace();
       }
       
       return null;
   }
	
	/**
	* RSA验签名检查
	* @param content 待签名数据
	* @param sign 签名值
	* @param ali_public_key 支付宝公钥
	* @param input_charset 编码格式
	* @return 布尔值
	*/
	public static boolean verifyWap(String content, String sign, String ali_public_key, String input_charset) {
		try {
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
	        byte[] encodedKey = Base64Wap.decode(ali_public_key);
	        PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
		
			java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);
			
			signature.initVerify(pubKey);
			signature.update( content.getBytes(input_charset) );
			
			boolean bverify = signature.verify( Base64Wap.decode(sign) );
			return bverify;
			
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	* 解密
	* @param content 密文
	* @param private_key 商户私钥
	* @param input_charset 编码格式
	* @return 解密后的字符串
	*/
	public static String decryptWap(String content, String private_key, String input_charset) throws Exception {
       PrivateKey prikey = getPrivateKeyWap(private_key);

       Cipher cipher = Cipher.getInstance("RSA");
       cipher.init(Cipher.DECRYPT_MODE, prikey);

       InputStream ins = new ByteArrayInputStream(Base64Wap.decode(content));
       ByteArrayOutputStream writer = new ByteArrayOutputStream();
       //rsa解密的字节大小最多是128，将需要解密的内容，按128位拆开解密
       byte[] buf = new byte[128];
       int bufl;

       while ((bufl = ins.read(buf)) != -1) {
           byte[] block = null;

           if (buf.length == bufl) {
               block = buf;
           } else {
               block = new byte[bufl];
               for (int i = 0; i < bufl; i++) {
                   block[i] = buf[i];
               }
           }

           writer.write(cipher.doFinal(block));
       }

       return new String(writer.toByteArray(), input_charset);
   }

	
	/**
	* 得到私钥
	* @param key 密钥字符串（经过base64编码）
	* @throws Exception
	*/
	public static PrivateKey getPrivateKeyWap(String key) throws Exception {

		byte[] keyBytes;
		
		keyBytes = Base64Wap.decode(key);
		
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
		
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		
		PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
		
		return privateKey;
	}
	
 }


 
 
 