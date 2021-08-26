package com.openopen.utils;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;

public class Http {

    private final String USER_AGENT = "Mozilla/5.0";


    /**
     * 發送 POST
     *
     * @param _url     : line api url
     * @param _content : 要發送的內容 json string
     */


    public String sendPost(String _url, String _content) throws Exception {
//		System.out.println("_url:" + _url);

        CloseableHttpClient httpClient = null;


        if (_url.startsWith("https")) {
            // https URL : ignore SSL
            SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, (x509CertChain, authType) -> true)
                    .build();

            httpClient = HttpClients.custom().setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
                    .setSSLContext(sslContext).build();
        } else {
            // http URL : use default
            httpClient = HttpClients.createDefault();
        }

        //Set Target URL
        HttpPost post = new HttpPost(_url);

        // Entity Body
        String charset = "UTF-8";
        StringEntity entity = new StringEntity(_content, charset);
        entity.setContentEncoding(charset);
        entity.setContentType("application/json");
        post.setEntity(entity);

        //Set Proxy 拿掉proxy設定 20200619
        //HttpHost proxy = new HttpHost("proxy.walsin.inc", 8080, "http");

        //Config
        //RequestConfig config = RequestConfig.custom().setProxy(proxy).build();

        //post.setConfig(config);

        //Execute
        CloseableHttpResponse response = httpClient.execute(post);

        try {

//			System.out.println("===> response status: " + response.getStatusLine());

            //Get Entity Content
            HttpEntity responseEntity = response.getEntity();
            String content = EntityUtils.toString(response.getEntity(), "utf-8");

//			System.out.println("content");
//			System.out.println(content);


            EntityUtils.consume(responseEntity);


            return content;

        } finally {
            response.close();
        }

    }


    /**
     * 發送 GET
     *
     * @param _url : line api url
     */


    public String sendGet(String _url) throws Exception {
//		System.out.println("[ GET ] " + _url);

        CloseableHttpClient httpClient = null;


        if (_url.startsWith("https")) {
            // https URL : ignore SSL
            SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, (x509CertChain, authType) -> true)
                    .build();

            httpClient = HttpClients.custom().setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
                    .setSSLContext(sslContext).build();
        } else {
            // http URL : use default
            httpClient = HttpClients.createDefault();
        }

        //Set Target URL
        HttpGet httpget = new HttpGet(_url);

        //Set Proxy 拿掉proxy設定 20200619
        //HttpHost proxy = new HttpHost("proxy.walsin.inc", 8080, "http");

        //Config
        //RequestConfig config = RequestConfig.custom().setProxy(proxy).build();

        //httpget.setConfig(config);

        //Execute
        CloseableHttpResponse response = httpClient.execute(httpget);

        try {

//			System.out.println("[RESPONSE STATUS]: " + response.getStatusLine());

            //Get Entity Content
            HttpEntity responseEntity = response.getEntity();
            String content = EntityUtils.toString(response.getEntity(), "utf-8");

//			System.out.println("[RESPONSE CONTENT]: " +content);


            EntityUtils.consume(responseEntity);


            return content;

        } finally {
            response.close();
        }

    }


    /**
     * 發送 POST 給 YonYou
     *
     * @param _url     : line api url
     * @param _content : 要發送的內容 json string
     */


    public String sendPostToYonYou(String _url) throws Exception {
//		System.out.println("_url:" + _url);

        CloseableHttpClient httpClient = null;


        if (_url.startsWith("https")) {
            // https URL : ignore SSL
            SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, (x509CertChain, authType) -> true)
                    .build();

            httpClient = HttpClients.custom().setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
                    .setSSLContext(sslContext).build();
        } else {
            // http URL : use default
            httpClient = HttpClients.createDefault();
        }

        //Set Target URL
        HttpPost post = new HttpPost(_url);

        // Entity Body
//		String charset = "UTF-8";
//		StringEntity entity = new StringEntity(_content, charset);
//		entity.setContentEncoding(charset);
//		entity.setContentType("application/json");
//		post.setEntity(entity);

        //Set Proxy 拿掉proxy設定 20200619
        //HttpHost proxy = new HttpHost("proxy.walsin.inc", 8080, "http");

        //Config
        //RequestConfig config = RequestConfig.custom().setProxy(proxy).build();

        //post.setConfig(config);

        //Execute
        CloseableHttpResponse response = httpClient.execute(post);

        try {

//			System.out.println("===> response status: " + response.getStatusLine());

            //Get Entity Content
            HttpEntity responseEntity = response.getEntity();
            String content = EntityUtils.toString(response.getEntity(), "utf-8");

//			System.out.println("content");
//			System.out.println(content);


            EntityUtils.consume(responseEntity);


            return content;

        } finally {
            response.close();
        }

    }


}
