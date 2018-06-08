package cn.core.query.resolver;

import cn.core.query.data.PropertyPreFilter;
import cn.core.query.data.PropertyPreFilterImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * PropertyPreFilterMethodArgumentResolver class
 *
 * @author Administrator
 * @date
 */
public class PropertyPreFilterMethodArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String DEFAULT_FILTER = "queryFields";
    private String filterName = DEFAULT_FILTER;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return PropertyPreFilter.class.isAssignableFrom(parameter.getParameterType());
    }

    @Nullable
    @Override
    public Object resolveArgument(MethodParameter parameter, @Nullable ModelAndViewContainer mavContainer, NativeWebRequest webRequest, @Nullable WebDataBinderFactory binderFactory) throws Exception {
        String field = this.getFilterName(parameter);
        String queryFields = webRequest.getParameter(field);
        return new PropertyPreFilterImpl(queryFields);
    }

    public String getFilterName(MethodParameter parameter) {
        Qualifier qualifier = parameter.getParameterAnnotation(Qualifier.class);
        if (qualifier != null) {
            return new StringBuilder(((Qualifier) qualifier).value()).append("_").append(filterName).toString();
        }
        return filterName;
    }
}
