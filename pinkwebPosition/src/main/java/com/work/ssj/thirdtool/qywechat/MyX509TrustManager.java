package com.work.ssj.thirdtool.qywechat;

import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;

public class MyX509TrustManager implements X509TrustManager  {
	
	      public void checkClientTrusted(X509Certificate[] chain, String authType) {
		  }
 
		  public void checkServerTrusted(X509Certificate[] chain, String authType) {
		  }
 
		  public X509Certificate[] getAcceptedIssuers()
		  {
		    return null;
		  }

}
