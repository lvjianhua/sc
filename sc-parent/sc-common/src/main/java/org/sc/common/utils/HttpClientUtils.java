package org.sc.common.utils;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.*;
import java.net.URI;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * HttpClient工具类
 * @author Webb Dong
 *
 */
public class HttpClientUtils {
	/**
	 * GET方式请求
	 * @param url
	 * @param param
	 * @return
	 */
	public static String doGet(String url, Map<String, String> param) {
		// 创建Httpclient对象
		CloseableHttpClient httpclient = HttpClients.createDefault();

		String resultString = "";
		CloseableHttpResponse response = null;
		try {
			// 创建uri
			URIBuilder builder = new URIBuilder(url);
			if (param != null) {
				for (String key : param.keySet()) {
					builder.addParameter(key, param.get(key));
				}
			}
			URI uri = builder.build();

			// 创建http GET请求
			HttpGet httpGet = new HttpGet(uri);

			// 执行请求
			response = httpclient.execute(httpGet);
			// 判断返回状态是否为200
			if (response.getStatusLine().getStatusCode() == 200) {
				resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			try {
				if (response != null) {
					response.close();
				}
				httpclient.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return resultString;
	}

	public static String doGet(String url) {
		return doGet(url, null);
	}

	/**
	 * POST方式请求
	 * @param url
	 * @param param
	 * @return
	 */
	public static String doPost(String url, Map<String, String> param, Map<String, String> headers) {
		// 创建Httpclient对象
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		String resultString = null;
		try {
			// 创建Http Post请求
			HttpPost httpPost = new HttpPost(url);
			// 创建参数列表
			if (param != null) {
				List<NameValuePair> paramList = new ArrayList<>();
				for (String key : param.keySet()) {
					paramList.add(new BasicNameValuePair(key, param.get(key)));
				}
				// 模拟表单
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList);
				httpPost.setEntity(entity);
			}
			if (headers != null) {
				for (String key : headers.keySet()) {
					httpPost.setHeader(key, headers.get(key));
				}
			}
			// 执行http请求
			response = httpClient.execute(httpPost);
			resultString = EntityUtils.toString(response.getEntity(), "utf-8");
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			try {
				response.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		return resultString;
	}

	public static String doPost(String url) {
		return doPost(url, null, null);
	}
	
	public static String doPost(String url, Map<String, String> headers) {
		return doPost(url, null, headers);
	}

	public static String doPostXml(String url, String xml) {
		return doPostXml(url, xml, null);
	}

	public static String doPostXml(String url, String xml, Map<String, String> headers) {
		// 创建Httpclient对象
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		String resultString = null;
		try {
			// 创建Http Post请求
			HttpPost httpPost = new HttpPost(url);
			if (headers != null) {
				for (Map.Entry<String, String> entry : headers.entrySet()) {
					httpPost.setHeader(entry.getKey(), entry.getValue());
				}
			}
			// 创建请求内容
			StringEntity entity = new StringEntity(xml, ContentType.APPLICATION_XML);
			httpPost.setEntity(entity);
			// 执行http请求
			response = httpClient.execute(httpPost);
			resultString = EntityUtils.toString(response.getEntity(), "utf-8");
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			try {
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		return resultString;
	}

	public static String doPostXmlSSL(String url, String xml, File p12File, String p12Pwd) throws FileNotFoundException {
		return doPostXmlSSL(url, xml, p12File, p12Pwd, null);
	}

	public static String doPostXmlSSL(String url, String xml, File p12File, String p12Pwd, Map<String, String> headers) throws FileNotFoundException {
		return doPostXmlSSL(url, xml, new FileInputStream(p12File), p12Pwd, headers);
	}

	public static String doPostXmlSSL(String url, String xml, InputStream instream, String p12Pwd) {
		return doPostXmlSSL(url, xml, instream, p12Pwd, null);
	}

	public static String doPostXmlSSL(String url, String xml, InputStream instream, String p12Pwd, Map<String, String> headers) {
		SSLConnectionSocketFactory sslsf = null;
		try {
			KeyStore keyStore;
			try {
				keyStore  = KeyStore.getInstance("PKCS12");
				keyStore.load(instream, p12Pwd.toCharArray());//"123456"指的是商户号
			} finally {
				instream.close();
			}
			SSLContext sslcontext = SSLContexts.custom()
					.loadKeyMaterial(keyStore, p12Pwd.toCharArray())// 这里也是写密码的
					.build();
			sslsf = new SSLConnectionSocketFactory(
					sslcontext, new String[] { "TLSv1" }, null,
					SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		// 创建Httpclient对象
		CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
		CloseableHttpResponse response = null;
		String resultString = null;

		try {
			// 创建Http Post请求
			HttpPost httpPost = new HttpPost(url);
			if (headers != null) {
				for (Map.Entry<String, String> entry : headers.entrySet()) {
					httpPost.setHeader(entry.getKey(), entry.getValue());
				}
			}
			// 创建请求内容
			StringEntity entity = new StringEntity(xml, ContentType.APPLICATION_XML);
			httpPost.setEntity(entity);
			// 执行http请求
			response = httpClient.execute(httpPost);
			resultString = EntityUtils.toString(response.getEntity(), "utf-8");
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			try {
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		return resultString;
	}
	
	public static String doPostJson(String url, String json) {
		return doPostJson(url, json, null);
	}
	
	public static String doPostJson(String url, String json, Map<String, String> headers) {
		// 创建Httpclient对象
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		String resultString = null;
		try {
			// 创建Http Post请求
			HttpPost httpPost = new HttpPost(url);
			if (headers != null) {
				for (Map.Entry<String, String> entry : headers.entrySet()) {
					httpPost.setHeader(entry.getKey(), entry.getValue());
				}
			}
			// 创建请求内容
			StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
			httpPost.setEntity(entity);
			// 执行http请求
			response = httpClient.execute(httpPost);
			resultString = EntityUtils.toString(response.getEntity(), "utf-8");
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			try {
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		return resultString;
	}
	
	public static String doDelete(String url, Map<String, String> headers) {
		// 创建Httpclient对象
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		String resultString = null;
		try {
			// 创建Http Post请求
			HttpDelete httpDelete = new HttpDelete(url);
			if (headers != null) {
				for (String key : headers.keySet()) {
					httpDelete.setHeader(key, headers.get(key));
				}
			}
			// 执行http请求
			response = httpClient.execute(httpDelete);
			resultString = EntityUtils.toString(response.getEntity(), "utf-8");
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			try {
				response.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		return resultString;
	}
	
	private HttpClientUtils() {}
}
