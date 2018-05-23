package cn.core.common.controller;

import com.mysql.cj.core.util.StringUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * BaseController class
 *
 * @author Administrator
 * @date
 */
public class BaseController {
    private String viewPrefix;

    public BaseController() {
        setViewPrefix(defaultViewPrefix());
    }

    public void setViewPrefix(String viewPrefix) {
        if (viewPrefix.startsWith("/")) {
            viewPrefix = viewPrefix.substring(1);
        }
        this.viewPrefix = viewPrefix;
    }

    public String getViewPrefix() {
        return viewPrefix;
    }

    public String display(String suffixName)
    {
        if(!suffixName.startsWith("/"))
        {
            suffixName="/"+suffixName;
        }
        return getViewPrefix().toLowerCase()+suffixName;
    }

    protected String defaultViewPrefix() {
        String currentViewPrefix = "";
        RequestMapping requestMapping = AnnotationUtils.findAnnotation(getClass(), RequestMapping.class);
        if (requestMapping != null && requestMapping.value().length > 0) {
            currentViewPrefix = requestMapping.value()[0];
            currentViewPrefix = currentViewPrefix.replace("${admin.url.prefix}", "modules");
        }
        if (StringUtils.isNullOrEmpty(currentViewPrefix)) {
            currentViewPrefix = this.getClass().getSimpleName().replace("Controller", "").toLowerCase();
        }
        return currentViewPrefix;
    }
}
