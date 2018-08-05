package com.liuyj.http;

import com.google.common.collect.Lists;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author liuyuanju1
 * @date 2018/8/5
 * @description: http 服务封装
 */
@Component
public class HttpAPIService {

    @Autowired
    private CloseableHttpClient httpClient;

    @Autowired
    private RequestConfig config;

    private static final int SUCCESS_CODE = 200;

    private static final String CHATSET = "UTF-8";

    /**
     * 不带参数的get请求
     * @param url 请求地址
     * @return 如果状态码为200，则返回body 否则返回null
     * @throws Exception
     */
    public String doGet(String url) throws Exception{
        // 声明 http get 请求
        HttpGet httpGet = new HttpGet(url);

        // 装载配置信息
        httpGet.setConfig(config);

        // 发起请求
        CloseableHttpResponse response = this.httpClient.execute(httpGet);

        // 判断状态码是否为 200
        if(response.getStatusLine().getStatusCode() == SUCCESS_CODE){
            //返回响应体内容
            return EntityUtils.toString(response.getEntity(),CHATSET);
        }
        return null;
    }

    /**
     * 带参数的get请求
     * @param url 请求地址
     * @param paramMap 参数map
     * @return 如果状态码为200，则返回body 否则返回null
     * @throws Exception
     */
    public String doGet(String url, Map<String, Object> paramMap) throws Exception{
        URIBuilder uriBuilder = new URIBuilder(url);

        if(paramMap != null){
            //遍历请求参数集合 拼接请求参数
            paramMap.entrySet().forEach(param -> {
                uriBuilder.setParameter(param.getKey(),param.getValue().toString());
            });
        }
        //调用不带参数的get请求
        return this.doGet(uriBuilder.build().toString());
    }

    /**
     * 带参数的 post请求
     * @param url 请求地址
     * @param paramMap 参数map
     * @return httpresult 结果对象
     * @throws Exception
     */
    public HttpResult doPost(String url, Map<String, Object> paramMap) throws Exception{
        // 声明httppost 请求
        HttpPost httpPost = new HttpPost(url);
        // 加入配置信息
        httpPost.setConfig(config);

        //判断参数 paramMap 是否为空，不为空只鞥进行遍历 封装from表单对象
        if(paramMap != null){
            List<NameValuePair> pairList = Lists.newArrayList();
            paramMap.entrySet().forEach(param ->
                    pairList.add(new BasicNameValuePair(param.getKey(),param.getValue().toString()))
            );
            // 构造form 表单对象
            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(pairList,CHATSET);

            // 把表单放入post请求中
            httpPost.setEntity(urlEncodedFormEntity);
        }

        // 发起请求
        CloseableHttpResponse response = this.httpClient.execute(httpPost);
        // 返回结果
        return new HttpResult(response.getStatusLine().getStatusCode(), EntityUtils.toString(
                response.getEntity(),CHATSET
        ));
    }

    /**
     * 不带参数的 post 请求
     * @param url 请求参数
     * @return httpresult 结果对象
     * @throws Exception
     */
    public HttpResult doPost(String url) throws Exception{
        return this.doPost(url,null);
    }
}
