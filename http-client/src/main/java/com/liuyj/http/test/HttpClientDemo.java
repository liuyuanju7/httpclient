package com.liuyj.http.test;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author liuyuanju1
 * @date 2018/8/3
 * @description: Httpclient 练习测试类
 */
public class HttpClientDemo {
    private static final String uri = "https://www.baidu.com/";
    private static final String charset = "UTF-8";
    @Test
    public void test1(){
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(uri);
        CloseableHttpResponse response = null;

        try {
            response = httpClient.execute(httpGet);
        } catch (IOException e) {
            e.printStackTrace();
        }

        HttpEntity entity = response.getEntity();

        System.out.println();
        try {
            System.out.println("Web Content is :" + EntityUtils.toString(entity,charset));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            response.close();
            httpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * User-Agent
     */
    @Test
    public void test2() throws Exception{
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://www.tuicool.com/");
        //设置请求头消息 如不设置 链接请求会被拒绝
        httpGet.setHeader("User-Agent",
                "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:50.0) Gecko/20100101 Firefox/50.0");
        CloseableHttpResponse response = null;
        response = httpClient.execute(httpGet);
        HttpEntity entity = response.getEntity();
     //   System.out.println("Web Content is :" + EntityUtils.toString(entity,charset));
     //   获取响应内容的content-type
        System.out.println("Content-Type : " + entity.getContentType().getValue());
     //   获取请求状态码
        System.out.println("Status : " + response.getStatusLine().getStatusCode());
        response.close();
        httpClient.close();
    }

    /**
     * RequestConfig
     */
    @Test
    public void test3() throws Exception{
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://www.tuicool.com/");
        //配置参数设置对象
        RequestConfig requestconfig = RequestConfig.custom()
                    .setConnectTimeout(5000)
                    .setSocketTimeout(5000)
                    .build();
        httpGet.setConfig(requestconfig);

        //设置请求头消息 如不设置 链接请求会被拒绝
        httpGet.setHeader("User-Agent",
                "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:50.0) Gecko/20100101 Firefox/50.0");
        CloseableHttpResponse response = null;
        response = httpClient.execute(httpGet);
        HttpEntity entity = response.getEntity();
        //   System.out.println("Web Content is :" + EntityUtils.toString(entity,charset));
        //   获取响应内容的content-type
        System.out.println("Content-Type : " + entity.getContentType().getValue());
        //   获取请求状态码
        System.out.println("Status : " + response.getStatusLine().getStatusCode());
        response.close();
        httpClient.close();
    }

    @Test
    public void test4(){
        try {
            URI testuri = new URIBuilder()
                    .setScheme("http")
                    .setHost("www.google.com")
                    .setPath("/search")
                    .setParameter("q","httpclient")
                    .setParameter("aq","f")
                    .setParameter("oq","")
                    .build();
            HttpGet httpGet = new HttpGet(testuri);
            System.out.println("uri : " + httpGet.getURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }

}
