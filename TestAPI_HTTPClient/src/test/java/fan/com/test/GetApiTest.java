package fan.com.test;

import com.alibaba.fastjson.JSONObject;
import fan.com.restClient.RestClient;
import fan.com.util.log;
import fan.com.util.ResponseData;
import fan.com.util.GetResponseData;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class GetApiTest extends TestBase{

    TestBase testBase;
    String host;
    String url;
    String URLS;
    RestClient restClient;
    CloseableHttpResponse closeableHttpResponse;

    @BeforeClass
    public void setUp() {
        testBase = new TestBase();
        host = prop.getProperty("GETHOST");
        URLS = prop.getProperty("GETURLS");
        url = host + URLS;
    }

    @Test
    public void getAPITest() throws Exception {
        restClient = new RestClient();
        closeableHttpResponse= restClient.get(url);
        //断言状态码是不是200
        JSONObject jsonObject = ResponseData.getResponseJson(closeableHttpResponse);
        String author = GetResponseData.getValueByJPath(jsonObject, "data[0]/author");
        int statusCode = ResponseData.getStatusCode(closeableHttpResponse);
        if (statusCode != RESPNSE_STATUS_CODE_200){
            log.error("response status code is not 200  --->  " + "当前状态为：" + statusCode);
        }
    }
}