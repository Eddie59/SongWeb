package cn.core.utils;

import com.alibaba.fastjson.JSONObject;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * StringUtil class
 *
 * @author Administrator
 * @date
 */
@Service
@Lazy(value = false)
public class StringUtil {

    public static void printJson(HttpServletResponse response, String json) {
        try {
            response.reset();
            response.setHeader("Cache-Control", "no-store");
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter writer = response.getWriter();
            writer.write(json);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isBlank(final CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (Character.isWhitespace(cs.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }


    public static void printJson(HttpServletResponse response, Object content) {
        try {
            response.reset();
            response.setContentType("application/json");
            response.setHeader("Cache-Control", "no-store");
            response.setCharacterEncoding("UTF-8");
            PrintWriter pw = response.getWriter();
            pw.write(JSONObject.toJSONString(content));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String trimFirstAndLastChar(String source,char element)
    {
       int begin=  source.indexOf(element)==0?1:0;
       int end=source.indexOf(element)+1==source.length()?source.lastIndexOf(element):source.length();
       return source.substring(begin,end);
    }
}
