package org.coin.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.http.HttpMethod;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HttpClientUtil {

	private static PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();

	private static RequestConfig requestConfig = null;

	static {
		// max 10 connections
		connManager.setMaxTotal(10);
		// timeout after 10s
		requestConfig = RequestConfig.custom().setConnectTimeout(10 * 1000).setSocketTimeout(10 * 1000).build();
	}

	/**
	 * execute url by httpClient for form
	 * 
	 * @param originalUrl
	 * @param jsonString
	 * @param httpMethod
	 * @return
	 * @throws Exception
	 */
	public static String executeByHttpClientForJson(String originalUrl, String json, Map<String, String> headerMap,
			HttpMethod httpMethod, boolean sslFlag) throws Exception {
		// ssl
		SSLContext sslContext = SSLContext.getInstance("TLS");
		sslContext.init(null, getTrustManager(), null);
		//
		HttpClientBuilder httpClientBuilder = HttpClients.custom().setDefaultRequestConfig(requestConfig);
		CloseableHttpClient client = null;
		if (sslFlag) {
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext,
					NoopHostnameVerifier.INSTANCE);
			Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
					.register("https", sslsf).build();
			HttpClientConnectionManager ccm1 = new BasicHttpClientConnectionManager(registry);
			client = httpClientBuilder.setSSLSocketFactory(sslsf).setConnectionManager(ccm1)
					.setSSLHostnameVerifier(new NoopHostnameVerifier()).build();
		} else {
			client = httpClientBuilder.setConnectionManager(connManager)
					.setSSLHostnameVerifier(new NoopHostnameVerifier()).build();
		}
		String result = "";
		HttpUriRequest request = null;
		try {
			request = getHttpRequestForJson(originalUrl, httpMethod, json);
			// add header
			if (headerMap != null) {
				for (Entry<String, String> entry : headerMap.entrySet()) {
					request.addHeader(entry.getKey(), entry.getValue());
				}
			}
			HttpResponse response = client.execute(request);
			HttpEntity entity = response.getEntity();
			// Get the response
			BufferedReader rd = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));
			String line = "";
			StringBuffer sb = new StringBuffer();
			while ((line = rd.readLine()) != null) {
				sb.append(line);
			}
			result = sb.toString();
		} catch (Exception e) {
			log.error("execute url error!!!", e);
			throw e;
		}
		return result;
	}

	/**
	 * trust all host for certificate
	 */
	private static TrustManager[] getTrustManager() {
		// Create a trust manager that does not validate certificate chains
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			public X509Certificate[] getAcceptedIssuers() {
				return new X509Certificate[] {};
			}

			public void checkClientTrusted(X509Certificate[] chain, String authType) {

			}

			public void checkServerTrusted(X509Certificate[] chain, String authType) {

			}
		} };
		//
		return trustAllCerts;
	}

	/**
	 * get mapping request for json
	 */
	private static HttpUriRequest getHttpRequestForJson(String url, HttpMethod method, String jsonString) {
		HttpUriRequest request = null;
		try {
			if (method.toString().equals("GET")) {
				request = new HttpGet(url);
			}
			if (method.toString().equals("POST") && StringUtils.isNotBlank(url)) {
				HttpPost post = new HttpPost(url);
				post.setEntity(new StringEntity(jsonString, "UTF-8"));
				request = (HttpUriRequest) post;
			}
			if (method.toString().equals("PUT") && StringUtils.isNotBlank(url)) {
				HttpPut put = new HttpPut(url);
				put.setEntity(new StringEntity(jsonString, "UTF-8"));
				request = (HttpUriRequest) put;
			}
		} catch (Exception e) {
			log.error("get http request error!!!", e);
		}
		return request;
	}

	public static void main(String[] args) throws Exception {
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "application/json");
		System.out.println(executeByHttpClientForJson("https://api.coindesk.com/v1/bpi/currentprice.json", null,
				headers, HttpMethod.GET, true));
	}

}
