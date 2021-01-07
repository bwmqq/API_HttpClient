package fan.com.test;

import com.alibaba.fastjson.JSONObject;
import fan.com.restClient.AssertClient;
import fan.com.restClient.RestClient;
import fan.com.util.*;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.log4j.xml.DOMConfigurator;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.IOException;
import java.util.HashMap;

public class TestApiCase {

    @BeforeClass
    @Parameters({"TestDataExcelFileSheet"})
    public void beforeClass(String TestDataExcelFileSheet) throws Exception{
        //读取Log4j.xml配置文件信息
        DOMConfigurator.configure("../TestAPI_HttpClient/src/test/resources/Log4j.xml");
        //使用Constant类中的常量，设定测试数据的文件路径和Sheet名称
        ExcelUtil.setExcelFile(Constant.TestDataExcelFilePath, TestDataExcelFileSheet);
        log.info("使用Constant类中的常量，设定测试数据的文件路径："+ Constant.TestDataExcelFilePath);
        log.info("使用Constant类中的常量，设定测试数据的Sheet名称："+ TestDataExcelFileSheet);
    }
    //定义dataprovider,并命名为testData
    @DataProvider(name="testData")
    public static Object[][] data() throws IOException {
        /*
         *调用ExcelUtil类中的getTestData静态方法，获取Excel数据文件中倒数第二列
         *标记为y的测试数据行，函数参数为常量Constant.TestDataExcelFilePath和常量
         *Constant.TestDataExcelFileSheet,指定数据文件的路径和Sheet名称
         */
        log.info("调用ExcelUtil类中的getTestData静态方法获取Excel中标记为yes的测试数据");
        return ExcelUtil.getTestData(Constant.TestDataExcelFilePath, "Sheet1");
    }
    //使用名称为testData的dataProvider作为测试方法的测试数据集
    @Test(dataProvider="testData")
    public void testEC1(String Tid, String CaseName, String method, String domainName, String address, String type,
                       String headers, String relyOn, String relyReturn, String relyField, String database,
                       String parameter, String results, String errorCode, String result,
                       String sql) {
        log.startTestCase(CaseName);
        RestClient restClient = new RestClient();
        HashMap<String, String> stringStringHashMap = restClient.setHeader(headers, relyOn, relyReturn);
        try {
            if ("get".equals(type)){
                CloseableHttpResponse cHttpResponse = restClient.get(method + "://" + domainName + address, stringStringHashMap);
                AssertClient.AssertClient(cHttpResponse, Tid, result);
            }else if ("post".equals(type)){
                CloseableHttpResponse cHttpResponse = restClient.post(method + "://" + domainName + address, parameter, stringStringHashMap);
                AssertClient.AssertClient(cHttpResponse, Tid, result);
            }
        } catch (Exception e) {
            if (e.toString().equals("java.lang.NullPointerException")){
                ExcelUtil.setCellData(Integer.parseInt(Tid.split("[.]")[0]), ExcelUtil.getLastColumnNum(), "测试失败！！！ --> " + result + "未找到！");
            }else {
                ExcelUtil.setCellData(Integer.parseInt(Tid.split("[.]")[0]), ExcelUtil.getLastColumnNum(), "测试失败！！！");
            }
            log.endTestCase(CaseName);
            Assert.fail(CaseName + " --> 测试用例执行失败！！！");
        }
        log.endTestCase(CaseName);
    }
    @AfterClass
    public void afterClass() {
        log.endTestCase("所有测试用例执行完成");
    }
}