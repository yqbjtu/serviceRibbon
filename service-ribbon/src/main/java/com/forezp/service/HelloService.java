package com.forezp.service;


import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by fangzhipeng on 2017/4/6.
 */
@Service
public class HelloService {

    @Autowired
    RestTemplate restTemplate;

    public String hiService(String name) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=UTF-8");
        //设置参数
        Map<String, String> hashMap = new LinkedHashMap<String, String>();
        hashMap.put("alert", "1234556");
        hashMap.put("appKey", "2023b334c43876569a7b3d04");
        hashMap.put("ids", "140fe1da9ea0a8457f5");
        hashMap.put("masterSecret", "b052c6a253ef8dc27424b225");
        hashMap.put("messageId", "as,as");
        hashMap.put("pushHostName", "https://api.jpush.cn");
        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<Map<String, String>>(hashMap, headers);
        //执行请求
        RestTemplate restTemplate = new RestTemplate();
        String url= "https://iottest.c.citic:8443/pushapi/service/push/pushByIDs";
//        url= "https://iottest.c.citic:8443/dpmapi/service/v2/apps/1000/groups/1000/categories";

//        ResponseEntity<JSONObject> resp =
//                restTemplate.exchange(url, HttpMethod.POST, requestEntity, JSONObject.class);
//        JSONObject body = resp.getBody();
        //restTemplate.getForObject("http://SERVICE-HI/hi?name="+name,String.class);

        HttpPost postMethod = new HttpPost(url);
        postMethod.addHeader("Content-Type", "application/json; charset=UTF-8");

        String str = "{\"alert\":\"Warning 01\", " +
                "\"appKey\": \"202sss3d04\"," +
                "\"ids\": \"140fessss57f5,3600sss457e147\"," +
                "\"masterSecret\": \"b052c6a253ef8dc27424b225\"," +
                "\"messageId\": \"a1,b2,c3" + name +"\"," +
                "\"pushHostName\": \"https://api.jpush.cn\"}";
//         str = "{\"collectionName\":\"Warning 01\", " +
//                "\"dictName\": \"b052c6a253ef8dc27424b225\"," +
//                "\"pushHostName\": \"https://api.jpush.cn\"}";
        String content = "test";
        try {
            CloseableHttpClient client = createSSLInsecureClient();
            postMethod.setEntity(new StringEntity(str));
            HttpResponse response = client.execute(postMethod);
            content = response.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (GeneralSecurityException e) {
            e.printStackTrace();
        }

        return content;
    }

    public  CloseableHttpClient createSSLInsecureClient() throws GeneralSecurityException {
        try {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                @Override
                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    return true;
                }
            }).build();
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, new X509HostnameVerifier() {

                @Override
                public boolean verify(String arg0, SSLSession arg1) {
                    return true;
                }

                @Override
                public void verify(String host, SSLSocket ssl) throws IOException {
                }

                @Override
                public void verify(String host, X509Certificate cert) throws SSLException {
                }

                @Override
                public void verify(String host, String[] cns, String[] subjectAlts) throws SSLException {
                }

            });
            return HttpClients.custom().setSSLSocketFactory(sslsf).build();
        } catch (GeneralSecurityException e) {
            throw e;
        }
    }

}
