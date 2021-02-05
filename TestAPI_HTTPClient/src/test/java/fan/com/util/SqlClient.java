package fan.com.util;

import org.testng.Assert;

import java.sql.*;
import java.util.Map;
import java.util.Properties;

public class SqlClient {
    public static Connection connection = null;

    public static ResultSet setSql(String sqlUrl, String library, String sql) {
        Properties properties = new Properties();
        Map<String, String> propertiesSql = PropertiesUtil.getProperties(sqlUrl);
        for (Map.Entry<String, String> entry : propertiesSql.entrySet()) {
            if (!"urlJdbc".equals(entry.getKey())){
                properties.setProperty(entry.getKey(), entry.getValue());
            }
        }
        /*Map map = null;*/
        ResultSet resultSet = null;
        try {
            //连接数据库
            Class.forName(propertiesSql.get("driver"));
            if ("null".equals(library)){
                connection = DriverManager.getConnection(propertiesSql.get("urlJdbc"), properties);
            }else {
                connection = DriverManager.getConnection(propertiesSql.get("urlJdbc") + library, properties);
            }
            //执行查询sql
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            /*while (resultSet.next()) {
                String user_phone = resultSet.getString("user_phone");
                System.out.println(user_phone);
                ResultSetMetaData metaData = resultSet.getMetaData();
                int columnCount = metaData.getColumnCount();
                map = new HashMap();
                for (int i = 0; i < columnCount; i++) {
                    map.put(metaData.getColumnName(i + 1).toLowerCase(), resultSet.getObject(i + 1));
                }
            }*/
        } catch (Exception e) {
            if (e.toString().contains("org.postgresql.util.PSQLException: FATAL")){
                if ("null".equals(library)){
                    log.error("数据库连接错误 --> " + propertiesSql.get("urlJdbc"));
                    Assert.fail("数据库连接错误 --> " + propertiesSql.get("urlJdbc"));
                }else {
                    log.error("数据库连接错误 --> " + propertiesSql.get("urlJdbc") + library);
                    Assert.fail("数据库连接错误 --> " + propertiesSql.get("urlJdbc") + library);
                }
            }else if (e.toString().contains("org.postgresql.util.PSQLException: ERROR:")){
                log.error("sql执行错误 --> " + sql);
                Assert.fail("sql执行错误 --> " + sql);
            }else {
                e.printStackTrace();
            }
        }
        return resultSet;
    }
}
