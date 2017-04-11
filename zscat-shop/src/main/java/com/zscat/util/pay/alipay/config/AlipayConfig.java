 package com.zscat.util.pay.alipay.config;
 
 public class AlipayConfig
 {
   private String partner = "";
 
   private String key = "";
 
   private String seller_email = "";
 
   private String notify_url = "";
 
   private String return_url = "";
 
   private String log_path = "D:\\alipay_log_" + System.currentTimeMillis() + 
     ".txt";
 
   private String input_charset = "utf-8";
 
   private String sign_type = "MD5";
 
   private String transport = "http";
 
	// 商户的私钥,需要PKCS8格式，RSA公私钥生成：https://doc.open.alipay.com/doc2/detail.htm?spm=a219a.7629140.0.0.nBDxfy&treeId=58&articleId=103242&docType=1
	//public static String private_key = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBANSWE16Htx6Y9nuSrci+1PwvjONjlQQK5BOMuGOnv9IM85HfX6gaomAwXwaJlnvBI05KJOw+bPaEc7EqnKnl6WynBuLalbvH8xPnsCp4Nl4lfAzl1jAveV3lQNIka6sLDc+Fsj90lZwPbYEc532IWdzF2nGtDZzdeUzR8O0Cj50DAgMBAAECgYBMAfya59tJTVeThzYreE+ee0Hca1Z0Q5CYjM/AkdG7wHW1yi/hYuR7j8/p/neXd86vx0K8VPWQjnru9VVeSZ3TW8DS5clFlmDPBrZPt96vR78dmt2c4C0HcyQo5VSmDjb/XQYPlaS08yR9iP/r1ydDCTDVvYDggQwvjJQpEgzgQQJBAP+Xhzx4pP1cz8ToQpz5/6Bwi4it2OVOO7axI/0eAmHh6taZPVtesH8onV8HUljqLE+1VI+j9QiQKHcAFSQD9T0CQQDU7PgVBaDj3RcKr+MoFFk0kLwTh7L/mApRvqiLJKFgDF4g7EumfxX0gNcCKg7KMOkuK8SYUJfQ7SfuLCCbcX8/AkAKQMTfCyPrOf7nsj2B2JGFmR4v1HF/+Vxx3rdH3x1PWeW2yBzq/mhsIW/2sO9/u50dcXO782LP/iBxbaJreHEtAkEAnX+vvfUM3qAitCAIehQbLBvHUvb0e8WeFHtgNpRg3ign1FiVWNH2joZjBFHR4NOvGBz97eDne36BI9b2Q6T/hwJBAPcAdO9Kd3M3reiI7Flhtsps769w6K5h+trplkC2ZBdJHMEpMLHzJtdYJW9i9tc7C7FbX8dRvk/66hl7kEXM9Y4=";
    	public static String private_key = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAPGiPR5/D1o3tHEP/xF96oy1mCw/mP9AaR9lwdXPEf7MutRnRpkpXGdGifoMiHzBAfsNigrTKWMwZ0g+/lRt6v002uHrVx1o/44GK0rotpBRwyQoHZDEnk6gdCcuxwOy+/0ew4i1uxF72ITUd1b4tbk6viHZYAO1JQMH0c/gRUHrAgMBAAECgYBf8VFfWIgaBGBLPFTE90A4Wqpvd1kikMqswtSyNqQojdXpHJC1o+yivSlv9aYEEwbcTkXvgmS+k6uOziRiFbrlxZpmbCaZYP0UqqccVoo/V9pQIGfgxlQWcUUhVlPqTltmjNlb17nMICR7NRBh7w+WWd8Bdh4BSER8To5dbIJrIQJBAP0iSvYleeozSK2hlvp9MFep8dAdshtpg4izqk2aBqlbJcixHNtGYQdMj1j98VsZQryDCNuS2bp2sLBA5ZSCpCMCQQD0XpzV3DYpZqtOx+1eA/TmqYmZJUL3JxdrxWuhHoA7cy9IsYMKT4D1dKZP23hkR/EoDstrPiC6nok/H07jlEOZAkAWbOlTMTLBytVPfJdRjbGwsn+Xvmm+//QFMlWOFm0sp8SLKRWzX2LNML0qVyUAuxYseTdZrILZgBZbJwSvyXsdAkEAwa1zFDTSYIHU6D2grdDo+yAHxG854fUDpRD1Ot+XtoaWRUEboMJebSwk3EoP4zr5sUv4gWjeO8qG8JZ5yfmqkQJBAJaADuDdwEf7v/zYu+XWR4DFIFFxfOpuaTAHlh4MU5OHkWXXsawdZ16OaVp3/F9SUA/XCXEQIOPJjNRJezbNU7U=";
    	// 支付宝的公钥,查看地址：https://b.alipay.com/order/pidAndKey.htm
	public static String ali_public_key = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDxoj0efw9aN7RxD/8RfeqMtZgsP5j/QGkfZcHVzxH+zLrUZ0aZKVxnRon6DIh8wQH7DYoK0yljMGdIPv5Uber9NNrh61cdaP+OBitK6LaQUcMkKB2QxJ5OoHQnLscDsvv9HsOItbsRe9iE1HdW+LW5Or4h2WADtSUDB9HP4EVB6wIDAQAB";
 
   public static String getPrivate_key() {
     return private_key;
   }
 
   public static void setPrivate_key(String private_key) {
	   private_key = private_key;
   }
 
   public static String getAli_public_key() {
     return ali_public_key;
   }
 
   public static void setAli_public_key(String ali_public_key) {
     ali_public_key = ali_public_key;
   }
 
   public String getPartner() {
     return this.partner;
   }
 
   public void setPartner(String partner) {
     this.partner = partner;
   }
 
   public String getKey() {
     return this.key;
   }
 
   public void setKey(String key) {
     this.key = key;
   }
 
   public String getSeller_email() {
     return this.seller_email;
   }
 
   public void setSeller_email(String seller_email) {
     this.seller_email = seller_email;
   }
 
   public String getNotify_url() {
     return this.notify_url;
   }
 
   public void setNotify_url(String notify_url) {
     this.notify_url = notify_url;
   }
 
   public String getReturn_url() {
     return this.return_url;
   }
 
   public void setReturn_url(String return_url) {
     this.return_url = return_url;
   }
 
   public String getLog_path() {
     return this.log_path;
   }
 
   public void setLog_path(String log_path) {
     this.log_path = log_path;
   }
 
   public String getInput_charset() {
     return this.input_charset;
   }
 
   public void setInput_charset(String input_charset) {
     this.input_charset = input_charset;
   }
 
   public String getSign_type() {
     return this.sign_type;
   }
 
   public void setSign_type(String sign_type) {
     this.sign_type = sign_type;
   }
 
   public String getTransport() {
     return this.transport;
   }
 
   public void setTransport(String transport) {
     this.transport = transport;
   }
 }


 
 
 