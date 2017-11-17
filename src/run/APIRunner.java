package run;

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

public class APIRunner {

	public static void main(String[] args) throws Exception {

		// BasicCredentialsProvider credentialsProvider = new
		// BasicCredentialsProvider();
		// credentialsProvider.setCredentials(AuthScope.ANY, new
		// UsernamePasswordCredentials("a", "b"));
		// Trust own CA and all self-signed certs
		SSLContext sslcontext = SSLContexts.custom()
				// .loadTrustMaterial(new File("my.keystore"),
				// "nopassword".toCharArray(), new TrustSelfSignedStrategy())
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
		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext,
				new String[] { "TLSv1.1", "SSLv3", "TLSv1.2" }, null, NoopHostnameVerifier.INSTANCE);
		// SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
		// SSLConnectionSocketFactory.getDefaultHostnameVerifier());

		Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder
				.<ConnectionSocketFactory>create()
				.register("http", PlainConnectionSocketFactory.getSocketFactory())
				.register("https", sslsf).build();

		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
		cm.setDefaultMaxPerRoute(1);
		cm.setMaxTotal(100);

		CloseableHttpClient httpclient = HttpClientBuilder.create()
				.disableContentCompression()
				.disableAutomaticRetries().setSSLSocketFactory(sslsf)
				// .setDefaultCredentialsProvider(credentialsProvider)
				.setConnectionManager(cm)
				// .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
				.build();

		// CloseableHttpClient httpclient = HttpClients.custom()
		// .setSSLSocketFactory(sslsf)
		// .build();

		String reqData = "{\"dataBody\" : {\"PE601UM_I_PRTY_REG_NO\":\"6302022521947\"}}";
		try {

			HttpPost httpPost = new HttpPost("https://openapi.kyobo.co.kr:1443/v1.0/PAY/loan/amount");
			httpPost.setHeader("Content-Type", "application/json");
			httpPost.setHeader("kyoboApiKey", "KVXbAdghO9CVLb9ZrrtNlN6Xy7WzGOgp");

			StringEntity stringEntity = new StringEntity(reqData);
			stringEntity.setContentType("application/json");
			httpPost.setEntity(stringEntity);

			System.out.println("Executing request " + httpPost.getRequestLine());

			CloseableHttpResponse response = httpclient.execute(httpPost);
			try {

				HttpEntity entity = response.getEntity();
				String responseJson = EntityUtils.toString(response.getEntity());

				EntityUtils.consume(response.getEntity());

				System.out.println("----------------------------------------");
				System.out.println("content :: " + responseJson);
				System.out.println(response.getStatusLine());

				EntityUtils.consume(entity);
			} finally {
				response.close();
			}

		} finally {
			httpclient.close();
		}
	}
}
