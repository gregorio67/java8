package func;

import java.util.concurrent.Callable;

import org.apache.http.impl.client.CloseableHttpClient;

public class APITask<T> implements Callable<T> {

	private String url = null;
	private String reqData = null;
	
	public APITask(String url, String reqData) {
		this.url = url;
		this.reqData = reqData;
	}
	@Override
	public T call() throws Exception {
		
		HttpClientUtil.init();
		CloseableHttpClient httpclient = HttpClientUtil.getHttpClient();
		return HttpClientUtil.requestPost(httpclient, url, reqData);
	}
}
