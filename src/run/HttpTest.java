package run;

import java.io.InputStream;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;



public class HttpTest {

	public static void main(String...strings) throws Exception {
		  HttpClientBuilder b = HttpClientBuilder.create();
		  
		    // setup a Trust Strategy that allows all certificates.
		    //
		    SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
		        public boolean isTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
		            return true;
		        }
		    }).build();
		    
		    b.setSslcontext( sslContext);
		 
		    // don't check Hostnames, either.
		    //      -- use SSLConnectionSocketFactory.getDefaultHostnameVerifier(), if you don't want to weaken
//		    HostnameVerifier hostnameVerifier = SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;
		    HostnameVerifier hostnameVerifier =  NoopHostnameVerifier.INSTANCE;		 
		    // here's the special part:
		    //      -- need to create an SSL Socket Factory, to use our weakened "trust strategy";
		    //      -- and create a Registry, to register it.
		    //
		    SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(sslContext, hostnameVerifier);
		    Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
		            .register("http", PlainConnectionSocketFactory.getSocketFactory())
		            .register("https", sslSocketFactory)
		            .build();
		 
		    // now, we create connection-manager using our Registry.
		    //      -- allows multi-threaded use
		    PoolingHttpClientConnectionManager connMgr = new PoolingHttpClientConnectionManager( socketFactoryRegistry);
		    b.setConnectionManager( connMgr);
		 
		    // finally, build the HttpClient;
		    //      -- done!
		    CloseableHttpClient client = b.build();
		    
            HttpPost httpPost = new HttpPost("https://openapi.kyobo.co.kr:1443/v1.0/PAY/loan/amount");
            httpPost.setHeader("Content-Type", "application/json");
            httpPost.setHeader("kyoboApiKey", "KVXbAdghO9CVLb9ZrrtNlN6Xy7WzGOgp");
            String reqData = "{\"dataBody\" : {\"PE601UM_I_PRTY_REG_NO\":\"6302022521947\"}}";
            StringEntity stringEntity = new StringEntity(reqData);
            stringEntity.setContentType("application/json");
            httpPost.setEntity(stringEntity);
            
            System.out.println("Executing request " + httpPost.getRequestLine());

            CloseableHttpResponse response = client.execute(httpPost);
            
            HttpEntity entity = response.getEntity();
            InputStream is = entity.getContent();
            long size = entity.getContentLength(); 
            byte[] content = new byte[8192]; 
           
            is.read(content);
            
            System.out.println("content :: " + new String(content, "UTF-8"));

            System.out.println("----------------------------------------");
            System.out.println(response.getStatusLine());
            EntityUtils.consume(entity);
	}
}
