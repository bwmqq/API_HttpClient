package fan.com.restClient;

import com.alibaba.fastjson.JSONObject;
import fan.com.util.*;
import org.apache.http.client.methods.CloseableHttpResponse;

import java.io.IOException;

public class AssertClient {
    public static void AssertClient(CloseableHttpResponse cHttpResponse, String Tid, String result) throws IOException {
        JSONObject responseJson = ResponseData.getResponseJson(cHttpResponse);
        ExcelUtil.setCellData(Integer.parseInt(Tid.split("[.]")[0]), ExcelUtil.getLastColumnNum() - 5, responseJson.toString());
        int statusCode = ResponseData.getStatusCode(cHttpResponse);
        int statusSuccess = ResponseData.getStatusSuccess(responseJson);
        if (statusCode == 200 && statusSuccess == 1){
            if (Util.isNotEmpty(result)){
                String[] split = result.split("=");
                String valueByJPath = GetResponseData.getValueByJPath(responseJson, split[0]);
                if (valueByJPath.equals(split[1])){
                    log.info("响应数据验证成功！期望：" + result + " --> 实际：" + valueByJPath);
                    ExcelUtil.setCellData(Integer.parseInt(Tid.split("[.]")[0]), ExcelUtil.getLastColumnNum(), "pass");
                }else {
                    log.error("响应result不对！期望：" + result + " --> 实际：" + valueByJPath);
                    ExcelUtil.setCellData(Integer.parseInt(Tid.split("[.]")[0]), ExcelUtil.getLastColumnNum(), "测试失败！！！期望：" + result + " --> 实际数据为：" + valueByJPath);
                }
            }else {
                ExcelUtil.setCellData(Integer.parseInt(Tid.split("[.]")[0]), ExcelUtil.getLastColumnNum(), "pass");
            }
        }else {
            System.out.println("------------------------------------------");
            ExcelUtil.setCellData(Integer.parseInt(Tid.split("[.]")[0]), ExcelUtil.getLastColumnNum(), "测试失败！！！");
        }


        /*if (!code.equals("null")){
            int statusCode = ResponseData.getStatusCode(cHttpResponse);
            a = statusCode;
            if (statusCode == Double.valueOf(code).intValue()){
                log.info("响应code正确！期望：" + Double.valueOf(code).intValue() + " --> 实际：" + statusCode);
                a = 1;
            }else {
                log.error("响应code不对！期望：" + Double.valueOf(code).intValue() + " --> 实际：" + statusCode);
                a = 2;
            }
        }
        if (!success.equals("null")){
            String success1 = GetResponseData.getValueByJPath(responseJson, "success");
            if (Integer.parseInt(success1) == Double.valueOf(success).intValue()){
                log.info("响应success正确！期望：" + Double.valueOf(success).intValue() + " --> 实际：" + success1);
                b = 1;
            }else {
                log.error("响应success不对！期望：" + Double.valueOf(success).intValue() + " --> 实际：" + success1);
                b = 2;
            }
        }
        if (isNotEmpty(result)){
            String[] split = result.split("=");
            String valueByJPath = GetResponseData.getValueByJPath(responseJson, split[0]);
            if (valueByJPath.equals(split[1])){
                log.info("响应数据验证成功！期望：" + result + " --> 实际：" + valueByJPath);
                c = 1;
            }else {
                log.error("响应result不对！期望：" + result + " --> 实际：" + valueByJPath);
                c = 2;
            }
        }
        ExcelUtil.setCellData(Integer.parseInt(Tid.split("[.]")[0]), ExcelUtil.getLastColumnNum() - 6, responseJson.toString());
        if (a == 0){
            if (b == 0){
                if (c == 0){
                    ExcelUtil.setCellData(Integer.parseInt(Tid.split("[.]")[0]), ExcelUtil.getLastColumnNum(), "pass");
                }else if (c == 1){
                    ExcelUtil.setCellData(Integer.parseInt(Tid.split("[.]")[0]), ExcelUtil.getLastColumnNum(), "pass");
                }else {
                    ExcelUtil.setCellData(Integer.parseInt(Tid.split("[.]")[0]), ExcelUtil.getLastColumnNum(), "测试失败：result校验失败！");
                }
            }else if (b == 1){
                if (c == 0){
                    ExcelUtil.setCellData(Integer.parseInt(Tid.split("[.]")[0]), ExcelUtil.getLastColumnNum(), "pass");
                }else if (c == 1){
                    ExcelUtil.setCellData(Integer.parseInt(Tid.split("[.]")[0]), ExcelUtil.getLastColumnNum(), "pass");
                }else {
                    ExcelUtil.setCellData(Integer.parseInt(Tid.split("[.]")[0]), ExcelUtil.getLastColumnNum(), "测试失败：result校验失败！");
                }
            }else {
                ExcelUtil.setCellData(Integer.parseInt(Tid.split("[.]")[0]), ExcelUtil.getLastColumnNum(), "测试失败：success校验失败！");
            }
        }else if (a == 1){
            if (b == 0){
                if (c == 0){
                    ExcelUtil.setCellData(Integer.parseInt(Tid.split("[.]")[0]), ExcelUtil.getLastColumnNum(), "pass");
                }else if (c == 1){
                    ExcelUtil.setCellData(Integer.parseInt(Tid.split("[.]")[0]), ExcelUtil.getLastColumnNum(), "pass");
                }else {
                    ExcelUtil.setCellData(Integer.parseInt(Tid.split("[.]")[0]), ExcelUtil.getLastColumnNum(), "测试失败：result校验失败！");
                }
            }else if (b == 1){
                if (c == 0){
                    ExcelUtil.setCellData(Integer.parseInt(Tid.split("[.]")[0]), ExcelUtil.getLastColumnNum(), "pass");
                }else if (c == 1){
                    ExcelUtil.setCellData(Integer.parseInt(Tid.split("[.]")[0]), ExcelUtil.getLastColumnNum(), "pass");
                }else {
                    ExcelUtil.setCellData(Integer.parseInt(Tid.split("[.]")[0]), ExcelUtil.getLastColumnNum(), "测试失败：result校验失败！");
                }
            }else {
                ExcelUtil.setCellData(Integer.parseInt(Tid.split("[.]")[0]), ExcelUtil.getLastColumnNum(), "测试失败：success校验失败！");
            }
        }else {
            ExcelUtil.setCellData(Integer.parseInt(Tid.split("[.]")[0]), ExcelUtil.getLastColumnNum(), "测试失败：code校验失败！");
        }*/
    }
}
