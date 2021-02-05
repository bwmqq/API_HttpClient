package fan.com.util;

public class Util {
    public static boolean isNotEmpty(String s){
        //返回 true 说明不是空
        return null != s && !s.equals("null") && s.length()>0 && s.trim().length()>0;
    }
}
