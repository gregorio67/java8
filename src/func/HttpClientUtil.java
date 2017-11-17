package func;

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
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

public class HttpClientUtil {

	private static PoolingHttpClientConnectionManager connMgr = null;
	private static  SSLConnectionSocketFactory sslsf = null;
	
	public static void init() throws Exception {
		if (connMgr == null) {
			synchronized(HttpClientUtil.class) {
				// BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
				// credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials("a", "b"));
				// Trust own CA and all self-signed certs
				SSLContext sslcontext = SSLContexts.custom()
						// .loadTrustMaterial(new File("my.keystore"),"nopassword".toCharArray(), new TrustSelfSignedStrategy())
						.loadTrustMaterial(null, new TrustSelfSignedStrategy())
						// .loadTrustMaterial(new TrustStrategy() {
						// @Override
						// public boolean isTrusted(X509Certificate[] chain, String
						// authType) throws CertificateException {
						// return true;
						// }
						//
						// })
						.build();

				// Allow TLSv1 protocol only
				sslsf = new SSLConnectionSocketFactory(sslcontext,
						new String[] { "TLSv1.1", "SSLv3", "TLSv1.2" }, null, NoopHostnameVerifier.INSTANCE);

				Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder
						.<ConnectionSocketFactory>create()
						.register("http", PlainConnectionSocketFactory.getSocketFactory())
						.register("https", sslsf).build();

				PoolingHttpClientConnectionManager connMgr = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
				connMgr.setDefaultMaxPerRoute(1);
				connMgr.setMaxTotal(100);					
			}
		}
	}
	
	public static CloseableHttpClient getHttpClient() {
		CloseableHttpClient httpclient = HttpClientBuilder.create()
				.disableContentCompression()
				.disableAutomaticRetries().setSSLSocketFactory(sslsf)
//				.setDefaultCredentialsProvider(credentialsProvider)				
				.setConnectionManager(connMgr)
				.build();
		return httpclient;
	}
	
	public static <T> T requestPost(CloseableHttpClient httpclient, String Url, String reqData) throws Exception {
		
		HttpPost httpPost = new HttpPost(Url);
		httpPost.setHeader("Content-Type", "application/json");
		httpPost.setHeader("kyoboApiKey", "KVXbAdghO9CVLb9ZrrtNlN6Xy7WzGOgp");

		StringEntity stringEntity = new StringEntity(reqData);
		stringEntity.setContentType("application/json");
		httpPost.setEntity(stringEntity);

		System.out.println("Executing request " + httpPost.getRequestLine());

		CloseableHttpResponse response = httpclient.execute(httpPost);
		String responseJson = null;
		try {

			HttpEntity entity = response.getEntity();
			responseJson = EntityUtils.toString(response.getEntity());

			EntityUtils.consume(response.getEntity());

			System.out.println("----------------------------------------");
			System.out.println("content :: " + responseJson);
			System.out.println(response.getStatusLine());

			EntityUtils.consume(entity);
		} finally {
			response.close();
			httpclient.close();
		}
		return (T) responseJson;
	}

}
