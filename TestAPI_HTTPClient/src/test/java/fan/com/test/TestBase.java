package fan.com.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class TestBase {
    public static void main(String[] args) {
        String responseJson = "{\"zi\":[{\"a\":\"a1\"}, {\"b\":\"b1\"}]}";
        JSONObject jsonObject = JSON.parseObject(responseJson);
        String jPath = "zi[1]/b";
        Object obj = responseJson;
        for(String s : jPath.split("/")) {
            if(!s.isEmpty()) {
                if(!(s.contains("[") || s.contains("]"))) {
                    obj = ((JSONObject) obj).get(s);
                }else if(s.contains("[") || s.contains("]")) {
                    System.out.println(s.split("\\[")[0] + "111");
                    System.out.println(s.split("\\[")[1] + "222");
                    obj =((JSONArray)jsonObject.get(s.split("\\[")[0])).
                            get(Integer.parseInt(s.split("\\[")[1].replaceAll("]", "")));
                }
            }
        }
        System.out.println(obj.toString());
    }
}