package com.fan.util;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class GetDescUtil {
    /**
     * 获取properties文件中的内容,并返回map
     *
     * @return
     */
    public static Map<String, String> getProperties(String filePath) {
        Map<String, String> map = new HashMap<String, String>();
        InputStream in = null;
        Properties p = new Properties();;
        try {
            in = new BufferedInputStream(new FileInputStream(new File(filePath)));
            p.load(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Set<Map.Entry<Object, Object>> entrySet = p.entrySet();
        for (Map.Entry<Object, Object> entry : entrySet) {
            map.put((String) entry.getKey(), (String) entry.getValue());
        }
        return map;
    }
}
