package fan.com.base;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import fan.com.util.ExcelUtil;

import java.util.HashMap;
import java.util.Map;

public class SetRelyOnResponse {
    //获取所依赖的接口需要的结果数据
    public static Map<String ,String> getResponse(String relyOn, String relyReturn) throws Exception {
        Map<String, String> setHeaderHashMap = null;
        if (!"null".equals(relyOn) && !"null".equals(relyReturn)){
            String[] setHeader = relyReturn.split("\\n");
            //根据编号读取单元格数据
            String cellData = ExcelUtil.getCellData(Double.valueOf(relyOn).intValue(), ExcelUtil.getLastColumnNum() - 6);
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

    //修改请求头和参数中的变量
    public static String setParameter(String parameter, String relyOn, String relyReturn) throws Exception {
        Map<String, String> response = getResponse(relyOn, relyReturn);
        //分割出变量名称
        String[] split = parameter.split("'");
        for (int i = 0; i < split.length; i++) {
            if (!split[i].contains("\"")){
                if (!split[i].contains("}")){
                    split[i] = response.get(split[i]);
                }
            }
        }
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < split.length; i++){
            sb.append(split[i]);
        }
        parameter = sb.toString();
        return parameter;
    }
}
