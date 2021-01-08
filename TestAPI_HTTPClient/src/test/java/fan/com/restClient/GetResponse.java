package fan.com.restClient;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import fan.com.util.ExcelUtil;
import fan.com.util.GetResponseData;

import java.util.HashMap;

public class GetResponse {
    public static HashMap<String ,String> getResponse(String relyOn, String relyReturn){
        HashMap<String, String> setHeaderHashMap = null;
        if (!"null".equals(relyOn) && !"null".equals(relyReturn)){
            String[] setHeader = relyReturn.split("\\n");
            String cellData = ExcelUtil.getCellData(Double.valueOf(relyOn).intValue(), ExcelUtil.getLastColumnNum() - 5);
            JSONObject jsonObject = JSON.parseObject(cellData);
            setHeaderHashMap = new HashMap<>();
            for (String header : setHeader) {
                String valueByJPath = GetResponseData.getValueByJPath(jsonObject, header);
                String[] split = header.split("/");
                setHeaderHashMap.put(split[split.length-1], valueByJPath);
            }
        }
        return setHeaderHashMap;
    }

    public static String setParameter(String parameter, String relyOn, String relyReturn){
        HashMap<String, String> response = getResponse(relyOn, relyReturn);
        String[] split = parameter.split("'");
        for (int i = 0; i < split.length; i++) {
            if (!split[i].contains("\"")){
                split[i] = response.get(split[i]);
            }
        }
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < split.length; i++){
            sb. append(split[i]);
        }
        parameter = sb.toString();
        return parameter;
    }
}
