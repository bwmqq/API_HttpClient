package fan.com.restClient;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import fan.com.util.ExcelUtil;
import fan.com.util.GetResponseData;
import fan.com.util.ResponseData;
import fan.com.util.log;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class RestClient {

    public HashMap<String ,String> setHeader(String headers, String relyOn, String relyReturn){
        HashMap<String ,String> postHeader = null;
        if (!headers.equals("null")){
            postHeader = new HashMap<String, String>();
            String[] split = headers.split("\\n");
            for (String s1 : split) {
                String[] split1 = s1.split("=");
                if (split1[1].contains("'") || split1[1].contains("'") && !"null".equals(relyOn) && !"null".equals(relyReturn)){
                    String cellData = ExcelUtil.getCellData(Double.valueOf(relyOn).intValue(), ExcelUtil.getLastColumnNum() - 5);
                    JSONObject jsonObject = JSON.parseObject(cellData);
                    String valueByJPath = GetResponseData.getValueByJPath(jsonObject, relyReturn);
                    split1[1] = valueByJPath;
                }
                postHeader.put(split1[0], split1[1]);
            }
        }
        return postHeader;
    }

    //1. Get 请求方法
    public CloseableHttpResponse get(String url) throws IOException {
        //创建一个可关闭的HttpClient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();
        //创建一个HttpGet的请求对象
        HttpGet httpget = new HttpGet(url);
        //执行请求,相当于postman上点击发送按钮，然后赋值给HttpResponse对象接收
        log.info("开始发送get请求...");
        CloseableHttpResponse httpResponse = httpclient.execute(httpget);
        log.info("发送请求成功！开始得到响应对象...");
        return httpResponse;
    }

    //2. Get 请求方法（带请求头信息）
    public CloseableHttpResponse get(String url, HashMap<String,String> headerMap) throws IOException {
        //创建一个可关闭的HttpClient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();
        //创建一个HttpGet的请求对象
        HttpGet httpget = new HttpGet(url);
        //加载请求头到httpget对象
        if (headerMap != null){
            for(Map.Entry<String, String> entry : headerMap.entrySet()) {
                httpget.addHeader(entry.getKey(), entry.getValue());
            }
        }
        //执行请求,相当于postman上点击发送按钮，然后赋值给HttpResponse对象接收
        log.info("开始发送带请求头的get请求...");
        CloseableHttpResponse httpResponse = httpclient.execute(httpget);
        log.info("发送请求成功！开始得到响应对象...");
        return httpResponse;
    }

    //3. POST方法
    public CloseableHttpResponse post(String url, String entityString, HashMap<String,String> headerMap) throws IOException {
        //创建一个可关闭的HttpClient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();
        //创建一个HttpPost的请求对象
        HttpPost httppost = new HttpPost(url);
        //设置payload
        httppost.setEntity(new StringEntity(entityString));
        //加载请求头到httppost对象
        if (headerMap != null){
            for(Map.Entry<String, String> entry : headerMap.entrySet()) {
                httppost.addHeader(entry.getKey(), entry.getValue());
            }
        }
        //发送post请求
        log.info("开始发送post请求...");
        log.info("请求地址为：" + url);
        log.info("请求参数为：" + entityString);
        CloseableHttpResponse httpResponse = httpclient.execute(httppost);
        log.info("发送请求成功！开始得到响应对象...");
        return httpResponse;
    }

    //4. Put方法
    public CloseableHttpResponse put(String url, String entityString, HashMap<String,String> headerMap) throws IOException {

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPut httpput = new HttpPut(url);
        httpput.setEntity(new StringEntity(entityString));

        for(Map.Entry<String, String> entry : headerMap.entrySet()) {
            httpput.addHeader(entry.getKey(), entry.getValue());
        }
        //发送put请求
        CloseableHttpResponse httpResponse = httpclient.execute(httpput);
        return httpResponse;
    }

    //5. Delete方法
    public CloseableHttpResponse delete(String url) throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpDelete httpdel = new HttpDelete(url);
        //发送put请求
        CloseableHttpResponse httpResponse = httpclient.execute(httpdel);
        return httpResponse;
    }
}