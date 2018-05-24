package cn.core.utils;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * StringUtil class
 *
 * @author Administrator
 * @date
 */
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
}
