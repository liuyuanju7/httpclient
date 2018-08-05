package com.liuyj.http.test;

import com.liuyj.http.HttpAPIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liuyuanju1
 * @date 2018/8/5
 * @description: httpAPIService 测试
 */

@RestController
@RequestMapping("/httpclient")
public class HttpApiServiceTest {

    @Autowired
    private HttpAPIService httpAPIService;

    @RequestMapping("/testGet")
    public String testGet() throws Exception{
        String url = "http://www.baidu.com";
        String result = httpAPIService.doGet(url);

        System.out.println(result);
        return result;
    }

}
