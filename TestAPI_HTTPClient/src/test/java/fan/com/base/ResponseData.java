package fan.com.base;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import fan.com.util.log;
import org.apache.http.Header;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.HashMap;

public class ResponseData {

    /**
     *
     * @param response, 任何请求返回返回的响应对象
     * @return， 返回响应体的json格式对象，方便接下来对JSON对象内容解析
     * 接下来，一般会继续调用TestUtil类下的json解析方法得到某一个json对象的值
     * @throws ParseException
     * @throws IOException
     */
    public static JSONObject getResponseJson (CloseableHttpResponse response) throws IOException {
        //把响应内容存储在字符串对象
        String responseString = EntityUtils.toString(response.getEntity(),"UTF-8");
        //创建Json对象，把上面字符串序列化成Json对象
        JSONObject responseJson = JSON.parseObject(responseString);
        log.info("获得response为 --> " + responseJson);
        return responseJson;
    }

    //输出响应头信息
    public static void getAllHeaders(CloseableHttpResponse httpResponse){
        //获取响应头信息,返回是一个数组
        Header[] headerArray = httpResponse.getAllHeaders();
        //创建一个hashmap对象，通过postman可以看到请求响应头信息都是Key和value得形式，所以我们想起了HashMap
        HashMap<String, String> hm = new HashMap<String, String>();
        //增强for循环遍历headerArray数组，依次把元素添加到hashmap集合
        for(Header header : headerArray) {
            hm.put(header.getName(), header.getValue());
        }
        //打印hashmap
        log.info("response headers --> " + hm);
    }

    /**
     * 获取响应状态码，常用来和TestBase中定义的状态码常量去测试断言使用
     * @param response
     * @return 返回int类型状态码
     */
    public static int getStatusCode (CloseableHttpResponse response) {
        int statusCode = response.getStatusLine().getStatusCode();
        return statusCode;
    }

    public static int getStatusSuccess(JSONObject responseJson) throws Exception {
        int success = Integer.parseInt(GetResponseData.getValueByJPath(responseJson, "success"));
        return success;
    }
}
