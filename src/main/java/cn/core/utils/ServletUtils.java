package cn.core.utils;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * ServletUtils class
 *
 * @author Administrator
 * @date
 */
public class ServletUtils {


    public static HttpServletRequest getRequest()
    {
        try {
//            RequestAttributes requestAttributes=  RequestContextHolder.getRequestAttributes();
//            ServletRequestAttributes servletRequestAttributes= ((ServletRequestAttributes)requestAttributes);
//            HttpServletRequest request= servletRequestAttributes.getRequest();

           return  ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        }
        catch (Exception e)
        {
            return null;
        }
    }
}
