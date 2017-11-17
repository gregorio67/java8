package util;

import java.io.InputStream;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;

public class BaseHttpClient {

	public CloseableHttpClient getHttpClient() throws Exception {
        // Trust own CA and all self-signed certs
        SSLContext sslcontext = SSLContexts.custom()
//                .loadTrustMaterial(new File("my.keystore"), "nopassword".toCharArray(), new TrustSelfSignedStrategy())
//                .loadTrustMaterial(null, new TrustSelfSignedStrategy())
                .loadTrustMaterial(new TrustStrategy() {
					@Override
					public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
						return true;
					}
                	
                })
                .build();
        
        // Allow TLSv1 protocol only
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslcontext,
                new String[] { "TLSv1", "TLSv2" },
                null, 
                NoopHostnameVerifier.INSTANCE);
//                SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
//                SSLConnectionSocketFactory.getDefaultHostnameVerifier());
        
        CloseableHttpClient httpclient = HttpClients.custom()
                .setSSLSocketFactory(sslsf)
                .build();
        
        return httpclient;
	}
	
	public <T> T httpPost(CloseableHttpClient httpClient, String Url, String param) throws Exception {
		
        String reqData = "{}";
        try {

            HttpPost httpPost = new HttpPost("https://openapi.kyobo.co.kr:1443/v1.0/INFO/user/husermapinfo");
            httpPost.setHeader("Content-Type", "application/json");
            httpPost.setHeader("kyoboApiKey", "KVXbAdghO9CVLb9ZrrtNlN6Xy7WzGOgp");
            
            StringEntity stringEntity = new StringEntity(reqData);
            httpPost.setEntity(stringEntity);
            
            System.out.println("Executing request " + httpPost.getRequestLine());

            CloseableHttpResponse response = httpClient.execute(httpPost);
            try {
                HttpEntity entity = response.getEntity();
                long size = entity.getContentLength();
                byte[] content = new byte[(int)size];
                InputStream is = entity.getContent();
                is.read(content);

                System.out.println("----------------------------------------");
                System.out.println(response.getStatusLine());
                EntityUtils.consume(entity);
                return (T) new String(content);
                
            } finally {
                response.close();
            }
        } finally {
        	httpClient.close();
        }
    }

}
