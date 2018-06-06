package cn.core.query.resolver;

import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerMapping;

import java.lang.ref.ReferenceQueue;
import java.lang.reflect.WildcardType;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * BaseMethodArgumentResolver class
 *
 * @author Administrator
 * @date
 */
public abstract class BaseMethodArgumentResolver implements HandlerMethodArgumentResolver {

    /**
     *
     * @param namePrefix 请求参数的前缀
     * @param request 当前请求对象
     * @param subPrefix 是否有分割符
     * @return 过滤只有前缀参数，如果参数有分割符，把参数前缀去掉，存放到Map中；如果没有分割符直接存放到Map中，返回Map
     */
    public Map<String, String[]> getPrefixParameterMap(String namePrefix, NativeWebRequest request, boolean subPrefix) {
        Map<String, String[]> result = new HashMap<>();
        int namePrefixLength = namePrefix.length();

        //处理查询时的参数
        Iterator<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasNext())
        {
            String name=parameterNames.next();
            //参数以前缀开头时,检索只有前缀的参数
            if(name.startsWith(namePrefix))
            {
                //如果有分割符，检查是否合法
                if(subPrefix)
                {
                    //传参有误
                    if (name.length() <= namePrefix.length()) {
                        continue;
                    }
                    char split = name.charAt(namePrefix.length());
                    if (illegalChar(split)) {
                        continue;
                    }
                    String key=name.substring(namePrefixLength + 1);
                    result.put(key,request.getParameterValues(name));
                }
                else
                {
                    result.put(name, request.getParameterValues(name));
                }
            }
        }

        //处理查询时的路径参数
        Map<String, String> uriVariables = getUriTemplateVariables(request);
        for (String name : uriVariables.keySet()) {
            if (name.startsWith(namePrefix)) {
                if (subPrefix) {
                    if (name.length() <= namePrefix.length()) {
                        continue;
                    }
                    char ch = name.charAt(namePrefix.length());
                    if (illegalChar(ch)) {
                        continue;
                    }
                    String key=name.substring(namePrefixLength + 1);
                    result.put(key, new String[] { uriVariables.get(name) });
                } else {
                    result.put(name, new String[] { uriVariables.get(name) });
                }
            }
        }


        return result;
    }

    /**
     * @param request 请求对象
     * @return 如果请求中有路径参数，获取路径参数并返回
     */
    @SuppressWarnings("unchecked")
    public final Map<String, String> getUriTemplateVariables(NativeWebRequest request) {
        Map<String, String> variables = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE, RequestAttributes.SCOPE_REQUEST);
        if (variables == null) {
            return Collections.<String, String>emptyMap();
        }
        return variables;
    }

    /**
     *
     * @param ch 分割符
     * @return 分割符是否非法
     */
    private boolean illegalChar(char ch) {
        return ch != '.' && ch != '_' && !(ch >= '0' && ch <= '9');
    }

}
