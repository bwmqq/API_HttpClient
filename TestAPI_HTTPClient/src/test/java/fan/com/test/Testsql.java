package fan.com.test;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class TestSql {
    public static Connection connection = null;
    public static PreparedStatement preparedStatement = null;
    //public static String sql = "SELECT sys_account FROM um_users WHERE user_phone = '13126668274'";
    public static String sql = "SELECT sys_account FROM um_users WHERE user_phone = '13126668274'";
    public static void main(String[] args) {
        /*String urlWithCe = "jdbc:postgresql://10.10.11.120/jeejio_openfire_qa";
        Properties properties = new Properties();
        properties.setProperty("user", "qa_openfire");
        properties.setProperty("password", "8d8988471d49642a8f4c2fdc91d1ebb8");*/
        String urlWithCe = "jdbc:postgresql://10.10.11.120/jeejio_user_qa";
        Properties properties = new Properties();
        properties.setProperty("user", "qa_openfire");
        properties.setProperty("password", "8d8988471d49642a8f4c2fdc91d1ebb8");
        properties.setProperty("ssl", "true");// 配置以ssl访问
        properties.setProperty("sslrootcert", "E:/ser/root.crt");// 配置根证书地址
        properties.setProperty("sslkey", "E:/ser/client.key.der");// 配置客户端私钥地址
        properties.setProperty("sslcert", "E:/ser/client.crt");// 配置客户端证书地址
        //properties.setProperty("sslpassword", "123456");// 配置私钥密码
        properties.setProperty("sslmode", "require");// 配置ssl模式,可选值为require、verify-ca、verify-full
        //properties.setProperty("loggerLevel","TRACE");//调整日志级别,打印详细日志
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(urlWithCe, properties);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println(resultSet.toString());
            while (resultSet.next()) {
                ResultSetMetaData rsmd = resultSet.getMetaData();
                int columnCount = rsmd.getColumnCount();
                Map map = new HashMap();
                for (int i = 0; i < columnCount; i++) {
                    map.put(rsmd.getColumnName(i + 1).toLowerCase(), resultSet.getObject(i + 1));
                }
                System.out.println(map);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
