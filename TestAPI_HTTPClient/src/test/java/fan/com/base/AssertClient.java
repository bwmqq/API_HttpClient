package fan.com.base;

import com.alibaba.fastjson.JSONObject;
import fan.com.util.*;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.testng.Assert;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AssertClient {
    //验证接口结果
    public static void AssertClient(CloseableHttpResponse cHttpResponse, String Tid, String result,
                                    String sqlFile, String database, String sql, String sqlResult) throws Exception {
        JSONObject responseJson = ResponseData.getResponseJson(cHttpResponse);
        //写入相应数据
        ExcelUtil.setCellData(Integer.parseInt(Tid.split("[.]")[0]), ExcelUtil.getLastColumnNum() - 6, responseJson.toString());
        int statusCode = ResponseData.getStatusCode(cHttpResponse);
        int statusSuccess = ResponseData.getStatusSuccess(responseJson);
        if (statusCode == 200 && statusSuccess == 1){
            if (!"null".equals(result)){
                String[] split = result.split("=");
                String valueByJPath = GetResponseData.getValueByJPath(responseJson, split[0]);
                if (valueByJPath.equals(split[1])){
                    log.info("响应数据验证成功！期望：" + result + " --> 实际：" + valueByJPath);
                    ExcelUtil.setCellData(Integer.parseInt(Tid.split("[.]")[0]), ExcelUtil.getLastColumnNum(), "pass");
                }else {
                    log.error("响应result不对！期望：" + result + " --> 实际：" + valueByJPath);
                    ExcelUtil.setCellData(Integer.parseInt(Tid.split("[.]")[0]), ExcelUtil.getLastColumnNum(), "测试失败！！！result期望：" + result + " --> 实际数据为：" + valueByJPath);
                    Assert.fail("测试用例执行失败 --> 响应result不对！期望：" + result + " --> 实际：" + valueByJPath);
                }
            }else {
                ExcelUtil.setCellData(Integer.parseInt(Tid.split("[.]")[0]), ExcelUtil.getLastColumnNum(), "pass");
            }
            try {
                if (!"null".equals(sql)){
                    ResultSet resultSet = SqlClient.setSql(sqlFile, database, sql);
                    boolean equals = false;
                    String[] split = null;
                    if (resultSet.next()){
                        split = sqlResult.split("=");
                        equals = resultSet.getString(split[0]).equals(split[1]);
                    }
                    //通过keyset获取键 在通过HashMap.get(key)方法通过键获取值
                    if (equals){
                        log.info("数据库验证数据成功！期望：" + sqlResult + " --> 实际：" + resultSet.getString(split[0]));
                    }else {
                        ExcelUtil.setCellData(Integer.parseInt(Tid.split("[.]")[0]), ExcelUtil.getLastColumnNum(), "测试失败！！！sql期望：" + sqlResult + " --> 实际数据为：" + resultSet.getString(split[0]));
                        Assert.fail("测试失败！！！sql期望：" + sqlResult + " --> 实际数据为：" + resultSet.getString(split[0]));
                    }
                }
            } catch (SQLException e) {
                if (e.toString().contains("org.postgresql.util.PSQLException: ResultSet 中找不到栏位名称")){
                    log.error("sql执行结果中未找到所需要的 --> " + sqlResult);
                    ExcelUtil.setCellData(Integer.parseInt(Tid.split("[.]")[0]), ExcelUtil.getLastColumnNum(), "测试失败！！！执行结果中未找到所需要的 --> " + sqlResult);
                    Assert.fail("sql执行结果中未找到所需要的 --> " + sqlResult);
                }
            }
        }else {
            log.error("响应数据码不对！");
            ExcelUtil.setCellData(Integer.parseInt(Tid.split("[.]")[0]), ExcelUtil.getLastColumnNum(), "测试失败！！！");
            Assert.fail("测试用例执行失败 --> 响应数据码不对！");
        }
    }
}
