package com.test.demo.httpclient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;

@Component
public class HttpAPIService {
	Logger logger = LoggerFactory.getLogger(HttpAPIService.class);
	@Autowired
	private CloseableHttpClient httpClient;

	@Autowired
	private RequestConfig config;

	/**
	 * 不带参数的get请求，如果状态码为200，则返回body，如果不为200，则返回null
	 *
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public String doGet(String url) throws Exception {

		// 声明 http get 请求
		try {
			HttpGet httpGet = new HttpGet(url);
			// 装载配置信息
			httpGet.setConfig(config);
			// 发起请求
			CloseableHttpResponse response = httpClient.execute(httpGet);
			//注意:"UTF-8",解决中文问题
			String res=EntityUtils.toString(response.getEntity(), "UTF-8");
			// 判断状态码是否为200
			if (response.getStatusLine().getStatusCode() == 200) {
				// 返回响应体的内容
				return res;
			}
		} catch (Exception e) {
			logger.info("请求mes异常:"+e.getMessage());
			HttpGet httpGet = new HttpGet(url);
			httpGet.setConfig(config);
			CloseableHttpResponse response = httpClient.execute(httpGet);
			String res=EntityUtils.toString(response.getEntity(), "UTF-8");
			if (response.getStatusLine().getStatusCode() == 200) {
				return res;
			}
		}
		return null;
	}

	/**
	 * 带参数的get请求，如果状态码为200，则返回body，如果不为200，则返回null
	 *
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public String doGet(String url, Map<String, Object> map) throws Exception {
		URIBuilder uriBuilder = new URIBuilder(url);

		if (map != null) {
			// 遍历map,拼接请求参数
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				uriBuilder.setParameter(entry.getKey(), entry.getValue().toString());
			}
		}

		// 调用不带参数的get请求
		return this.doGet(uriBuilder.build().toString());

	}

	/**
	 * 带参数的post请求
	 *
	 * @param url
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public String doPost(String url, Map<String, Object> map) throws Exception {
		// 声明httpPost请求
		HttpPost httpPost = new HttpPost(url);
		// 加入配置信息
		httpPost.setConfig(config);

		// 判断map是否为空，不为空则进行遍历，封装from表单对象
		if (map != null) {
			List<NameValuePair> list = new ArrayList<NameValuePair>();
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				list.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
			}
			// 构造from表单对象
			UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(list, "UTF-8");

			// 把表单放到post里
			httpPost.setEntity(urlEncodedFormEntity);
		}
		// 发起请求
		CloseableHttpResponse response = httpClient.execute(httpPost);
		String result = null;
		if (response != null && response.getStatusLine().getStatusCode() == 200) {
			result = EntityUtils.toString(response.getEntity(), "UTF-8");
		}
		return result;
	}

	/*
	 * 入参说明
	 *
	 * param url 请求地址
	 * param jsonObject 请求的json数据
	 *
	 */
	public String httpPostWithJsonObject(String url,JSONObject jsonObject) throws IOException{
		// 声明httpPost请求
		HttpPost httpPost = new HttpPost(url);
		// 加入配置信息
		httpPost.setConfig(config);
		String response = null;
		//注意:"UTF-8",解决中文问题
		StringEntity s = new StringEntity(jsonObject.toString(),"UTF-8");
		s.setContentEncoding("UTF-8");
		s.setContentType("application/json");//发送json数据需要设置contentType
		httpPost.setEntity(s);
		HttpResponse res = httpClient.execute(httpPost);
		if(res != null && res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			response = JSONObject.toJSONString(EntityUtils.toString(res.getEntity()));
		}
		return response;
	}

	/**
	 *
	 * @param url
	 * @param json
	 * @return
	 * @throws IOException
	 */
	public String httpPostWithJson(String url, String json) throws IOException {
		String returnValue = null;
		ResponseHandler<String> responseHandler = new BasicResponseHandler();

		HttpPost httpPost = new HttpPost(url);

		//第三步：给httpPost设置JSON格式的参数
		StringEntity requestEntity = new StringEntity(json,"utf-8");
		requestEntity.setContentEncoding("UTF-8");
		httpPost.setHeader("Content-type", "application/json");
		httpPost.setEntity(requestEntity);

		//第四步：发送HttpPost请求，获取返回值
		returnValue = httpClient.execute(httpPost,responseHandler); //调接口获取返回值时，必须用此方法

		//第五步：处理返回值
		return returnValue;
	}
}
