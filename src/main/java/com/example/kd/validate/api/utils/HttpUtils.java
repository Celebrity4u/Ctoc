package com.example.kd.validate.api.utils;


import com.alibaba.fastjson.JSONObject;
import com.example.kd.validate.api.config.HTTPSConfig;
import com.example.kd.validate.api.config.SpringUtil;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.*;
import java.net.ConnectException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class HttpUtils {

	/**
	 * get
	 *
	 * @param host
	 * @param path
	 * @param method
	 * @param headers
	 * @param querys
	 * @return
	 * @throws Exception
	 */
	public static HttpResponse doGet(String host, String path, String method,
									 Map<String, String> headers,
									 Map<String, String> querys)
			throws Exception {
		HttpClient httpClient = wrapClient(host);

		HttpGet request = new HttpGet(buildUrl(host, path, querys));
		for (Map.Entry<String, String> e : headers.entrySet()) {
			request.addHeader(e.getKey(), e.getValue());
		}

		return httpClient.execute(request);
	}

	/**
	 * post form
	 *
	 * @param host
	 * @param path
	 * @param method
	 * @param headers
	 * @param querys
	 * @param bodys
	 * @return
	 * @throws Exception
	 */
	public static HttpResponse doPost(String host, String path, String method,
									  Map<String, String> headers,
									  Map<String, String> querys,
									  Map<String, String> bodys)
			throws Exception {
		HttpClient httpClient = wrapClient(host);

		HttpPost request = new HttpPost(buildUrl(host, path, querys));
		for (Map.Entry<String, String> e : headers.entrySet()) {
			request.addHeader(e.getKey(), e.getValue());
		}

		if (bodys != null) {
			List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();

			for (String key : bodys.keySet()) {
				nameValuePairList.add(new BasicNameValuePair(key, bodys.get(key)));
			}
			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(nameValuePairList, "utf-8");
			formEntity.setContentType("application/x-www-form-urlencoded; charset=UTF-8");
			request.setEntity(formEntity);
		}

		return httpClient.execute(request);
	}

	/**
	 * Post String
	 *
	 * @param host
	 * @param path
	 * @param method
	 * @param headers
	 * @param querys
	 * @param body
	 * @return
	 * @throws Exception
	 */
	public static HttpResponse doPost(String host, String path, String method,
									  Map<String, String> headers,
									  Map<String, String> querys,
									  String body)
			throws Exception {
		HttpClient httpClient = wrapClient(host);

		HttpPost request = new HttpPost(buildUrl(host, path, querys));
		for (Map.Entry<String, String> e : headers.entrySet()) {
			request.addHeader(e.getKey(), e.getValue());
		}

		if (body!=null && !"".endsWith(body)) {
			request.setEntity(new StringEntity(body, "utf-8"));
		}

		return httpClient.execute(request);
	}

	public static HttpResponse doPostBody(String host, String path, String method,
										  Map<String, String> headers,
										  Map<String, String> querys,
										  Map<String, String> bodys)
			throws Exception {
		HttpClient httpClient = wrapClient(host);

		HttpPost request = new HttpPost(buildUrl(host, path, querys));
		for (Map.Entry<String, String> e : headers.entrySet()) {
			request.addHeader(e.getKey(), e.getValue());
		}

		if (bodys != null) {
			List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();

			for (String key : bodys.keySet()) {
				nameValuePairList.add(new BasicNameValuePair(key, bodys.get(key)));
			}
			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(nameValuePairList, "utf-8");
			formEntity.setContentType("application/x-www-form-urlencoded; charset=UTF-8");
			request.setEntity(formEntity);
		}
		return httpClient.execute(request);
	}

	/**
	 * Post stream
	 *
	 * @param host
	 * @param path
	 * @param method
	 * @param headers
	 * @param querys
	 * @param body
	 * @return
	 * @throws Exception
	 */
	public static HttpResponse doPost(String host, String path, String method,
									  Map<String, String> headers,
									  Map<String, String> querys,
									  byte[] body)
			throws Exception {
		HttpClient httpClient = wrapClient(host);

		HttpPost request = new HttpPost(buildUrl(host, path, querys));
		for (Map.Entry<String, String> e : headers.entrySet()) {
			request.addHeader(e.getKey(), e.getValue());
		}

		if (body != null) {
			request.setEntity(new ByteArrayEntity(body));
		}

		return httpClient.execute(request);
	}

	/**
	 * Put String
	 * @param host
	 * @param path
	 * @param method
	 * @param headers
	 * @param querys
	 * @param body
	 * @return
	 * @throws Exception
	 */
	public static HttpResponse doPut(String host, String path, String method,
									 Map<String, String> headers,
									 Map<String, String> querys,
									 String body)
			throws Exception {
		HttpClient httpClient = wrapClient(host);

		HttpPut request = new HttpPut(buildUrl(host, path, querys));
		for (Map.Entry<String, String> e : headers.entrySet()) {
			request.addHeader(e.getKey(), e.getValue());
		}

		if (body != null && !"".equals(body)) {
			request.setEntity(new StringEntity(body, "utf-8"));
		}

		return httpClient.execute(request);
	}

	/**
	 * Put stream
	 * @param host
	 * @param path
	 * @param method
	 * @param headers
	 * @param querys
	 * @param body
	 * @return
	 * @throws Exception
	 */
	public static HttpResponse doPut(String host, String path, String method,
									 Map<String, String> headers,
									 Map<String, String> querys,
									 byte[] body)
			throws Exception {
		HttpClient httpClient = wrapClient(host);

		HttpPut request = new HttpPut(buildUrl(host, path, querys));
		for (Map.Entry<String, String> e : headers.entrySet()) {
			request.addHeader(e.getKey(), e.getValue());
		}

		if (body != null) {
			request.setEntity(new ByteArrayEntity(body));
		}

		return httpClient.execute(request);
	}

	/**
	 * Delete
	 *
	 * @param host
	 * @param path
	 * @param method
	 * @param headers
	 * @param querys
	 * @return
	 * @throws Exception
	 */
	public static HttpResponse doDelete(String host, String path, String method,
										Map<String, String> headers,
										Map<String, String> querys)
			throws Exception {
		HttpClient httpClient = wrapClient(host);

		HttpDelete request = new HttpDelete(buildUrl(host, path, querys));
		for (Map.Entry<String, String> e : headers.entrySet()) {
			request.addHeader(e.getKey(), e.getValue());
		}

		return httpClient.execute(request);
	}

	private static String buildUrl(String host, String path, Map<String, String> querys) throws UnsupportedEncodingException {
		StringBuilder sbUrl = new StringBuilder();
		sbUrl.append(host);
		if (path !=null && !"".equals(path)) {
			sbUrl.append(path);
		}
		if (null != querys) {
			StringBuilder sbQuery = new StringBuilder();
			for (Map.Entry<String, String> query : querys.entrySet()) {
				if (0 < sbQuery.length()) {
					sbQuery.append('&');
				}
				if ((query.getKey()==null || "".equals(query.getKey().toString())) && query.getValue()!=null && !"".equals(query.getValue().toString())) {
					sbQuery.append(query.getValue());
				}
				if (query.getKey()!=null && !"".equals(query.getKey().toString())) {
					sbQuery.append(query.getKey());
					if (query.getValue()!=null && !"".equals(query.getValue().toString())) {
						sbQuery.append('=');
						sbQuery.append(URLEncoder.encode(query.getValue(), "utf-8"));
					}
				}
			}
			if (0 < sbQuery.length()) {
				sbUrl.append('?').append(sbQuery);
			}
		}

		return sbUrl.toString();
	}

	private static HttpClient wrapClient(String host) {
		HttpClient httpClient = new DefaultHttpClient();
		if (host.startsWith("https://")) {
			sslClient(httpClient);
		}

		return httpClient;
	}

	private static void sslClient(HttpClient httpClient) {
		try {
			SSLContext ctx = SSLContext.getInstance("TLS");
			X509TrustManager tm = new X509TrustManager() {
				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}
				public void checkClientTrusted(X509Certificate[] xcs, String str) {

				}
				public void checkServerTrusted(X509Certificate[] xcs, String str) {

				}
			};
			ctx.init(null, new TrustManager[] { tm }, null);
			SSLSocketFactory ssf = new SSLSocketFactory(ctx);
			ssf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			ClientConnectionManager ccm = httpClient.getConnectionManager();
			SchemeRegistry registry = ccm.getSchemeRegistry();
			registry.register(new Scheme("https", 443, ssf));
		} catch (KeyManagementException ex) {
			throw new RuntimeException(ex);
		} catch (NoSuchAlgorithmException ex) {
			throw new RuntimeException(ex);
		}
	}
	public static String httpPost(String requestUrl, String sendData, String key, Boolean openMainHttps) {

		try {
			if(openMainHttps)
			{
				requestUrl= requestUrl.replaceAll("http://","https://");
			}else
			{
				requestUrl= requestUrl.replaceAll("https://","http://");
			}

			String p12Name = "ybjt.p12";
			String passWord = "ybjtadmin";
			HTTPSConfig hTTPSConfig = SpringUtil.getBean(HTTPSConfig.class);
			if(hTTPSConfig != null)
			{
				p12Name = hTTPSConfig.getKeyStore().replaceAll("classpath:","");
				passWord = hTTPSConfig.getKeyStorePassword();
			}
			SSLContext sslContext = SSLContextBuilder.create().loadTrustMaterial(new File("ssl"+ File.separator+p12Name),passWord.toCharArray()).build();
			SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);
			CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(socketFactory).build();
			if(!openMainHttps)
			{
				httpClient = HttpClients.createDefault();
			}
			HttpPost httpPost = new HttpPost(requestUrl);
			httpPost.setHeader("Content-Type","application/json;charset=UTF-8");
			httpPost.setHeader("COMPETITION-KEY",key);
			httpPost.setEntity(new StringEntity(sendData,"UTF-8"));
			CloseableHttpResponse response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			String result = EntityUtils.toString(entity);
			if(response !=null)
			{
			if(	response!=null){	response.close();};
			}
			if(httpClient !=null)
			{
			if(	httpClient!=null){	httpClient.close();};
			}
			return result;
		}catch (Exception e)
		{
          e.printStackTrace();
		}
		return "";

	}
	/**
	 * 发起https请求并获取结果
	 *
	 * @param requestUrl 请求地址
	 * @param requestMethod 请求方式（GET、POST）
	 * @param outputStr 提交的数据
	 * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
	 */
	public static JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr) {
		JSONObject jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		try {
			/*// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			javax.net.ssl.SSLSocketFactory ssf = sslContext.getSocketFactory();*/

			URL url = new URL(requestUrl);
			HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
			//httpUrlConn.setSSLSocketFactory(ssf);

			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requestMethod);
			httpUrlConn.connect();

			// 当有数据需要提交时
			if (null != outputStr) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
			if(	outputStream!=null){	outputStream.write(outputStr.getBytes("UTF-8"));};
				if(outputStream !=null)
				{
				if(	outputStream!=null){	outputStream.close();};
				}


			}

			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			if(bufferedReader !=null)
			{
			if(	bufferedReader!=null){	bufferedReader.close();};
			}


			if(inputStreamReader !=null)
			{
			if(	inputStreamReader!=null){	inputStreamReader.close();};
			}

			// 释放资源
			if(inputStream !=null)
			{
			if(	inputStream!=null){	inputStream.close();};
			}

			inputStream = null;
			httpUrlConn.disconnect();
			jsonObject = JSONObject.parseObject(buffer.toString());
		} catch (ConnectException ce) {
			ce.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonObject;
	}
}