package cn.core.query.resolver;

import cn.core.query.annotation.QueryableDefaults;
import cn.core.query.data.Page;
import cn.core.query.data.QueryableImpl;
import cn.core.query.data.Queryable;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * QueryMethodArgumentResolver class
 *
 * @author Administrator
 * @date
 */
public class QueryMethodArgumentResolver extends BaseMethodArgumentResolver {
    private static final PageableMethodArgumentResolver DEFAULT_PAGEABLE_RESOLVER = new PageableMethodArgumentResolver();
    private PageableMethodArgumentResolver pageMethodArgumentResolver = DEFAULT_PAGEABLE_RESOLVER;

    /**
     * 查询参数前缀
     */
    private static final String DEFAULT_QUERY_PREFIX = "query";
    private String prefix = DEFAULT_QUERY_PREFIX;

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }


    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        //当前参数如果是Queryable的子类或子接口，调用resolveArgument解析参数
        return Queryable.class.isAssignableFrom(parameter.getParameterType());
    }

    @Nullable
    @Override
    public Object resolveArgument(MethodParameter parameter, @Nullable ModelAndViewContainer mavContainer, NativeWebRequest webRequest, @Nullable WebDataBinderFactory binderFactory) throws Exception {
        //查询参数的前缀
        String queryPrefix = getQualifierPrefix(parameter);
        //查询参数
        Map<String, String[]> queryParaMap = getPrefixParameterMap(queryPrefix, webRequest, true);
        //使用自定义的Queryable,如果请求中有查询参数，说明想用查询参数来查询分页排序
        boolean hasCustomQueryFilter = queryParaMap.size() > 0;

        Queryable queryRequest = new QueryableImpl();

        //使用默认的Queryable
        //查询方法上的QueryableDefaults注解，使用注解来控制分页排序
        QueryableDefaults queryDefaults = getQueryableDefaults(parameter);
        //方法上有QueryableDefaults注解并且它的merge为true时，把根据QueryableDefaults注解获取queryable对象，和参数生成的queryable对象合并
        boolean needMergeDefault = queryDefaults != null && queryDefaults.merge();
        if (needMergeDefault || !hasCustomQueryFilter) {
            //根据QueryableDefaults注解，获取queryable对象
            queryRequest = getDefaultFromAnnotation(queryDefaults);
        }
        //使用自定义的Queryable
        if (hasCustomQueryFilter) {
            for (String name : queryParaMap.keySet()) {
                //List<String> mapValues = Arrays.asList(queryParaMap.get(name)).stream().filter((x) -> !StringUtils.isNoneEmpty(x)).collect(Collectors.toList());
                List<String> mapValues = Arrays.asList(queryParaMap.get(name));
                if (mapValues.size() == 1) {
                    if (name.endsWith("in") || name.endsWith("between")) {
                        queryRequest.addCondition(name, StringUtils.split(mapValues.get(0), ","));
                    } else {
                        queryRequest.addCondition(name, mapValues.get(0));
                    }
                } else {
                    queryRequest.addCondition(name, mapValues);
                }
            }
        }


        //处理分页及排序
        Page page = (Page) pageMethodArgumentResolver.resolveArgument(parameter, mavContainer, webRequest, binderFactory);
        // 方法上没有QueryableDefaults注解，使用解析得到的的page
        if (queryDefaults == null) {
            queryRequest.setPage(page);
        }
        // 默认的分页及排序
        if (queryDefaults != null && queryDefaults.needPage()) {
            queryRequest.setPage(page);
        }
        // 不要分页，但排序
        if (queryDefaults != null && !queryDefaults.needPage() && queryDefaults.needSort()) {
            queryRequest.addSort(page.getSort());
        }
        if (page.getSort() != null) {
            queryRequest.addSort(page.getSort());
        }

        return queryRequest;
    }


    /**
     * @param parameter 请求方法对象
     * @return 先从参数上找是否有@QueryableDefaults注解，没有的话再从方法上找，返回@QueryableDefaults注解对象
     */
    public QueryableDefaults getQueryableDefaults(MethodParameter parameter) {
        QueryableDefaults queryableDefaults = parameter.getParameterAnnotation(QueryableDefaults.class);
        if (queryableDefaults == null) {
            queryableDefaults = parameter.getMethodAnnotation(QueryableDefaults.class);
        }
        return queryableDefaults;
    }

    /**
     * @param parameter 参数对象
     * @return 如果参数对象有Qualifier注解，获取值，加上“_query”返回
     */
    public String getQualifierPrefix(MethodParameter parameter) {
        Qualifier qualifier = parameter.getParameterAnnotation(Qualifier.class);
        if (qualifier != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(qualifier.value());
            sb.append("_");
            sb.append(prefix);
            return sb.toString();
        }
        return prefix;
    }

    private Queryable getDefaultFromAnnotation(QueryableDefaults queryableDefaults) {
        Queryable queryable = defaultQueryable(queryableDefaults);
        if (queryable != null) {
            return queryable;
        }
        return QueryableImpl.newQueryable();
    }

    private Queryable defaultQueryable(QueryableDefaults queryableDefaults) {
        if (queryableDefaults == null) {
            return null;
        }
        Queryable queryable = QueryableImpl.newQueryable();
        for (String queryParam : queryableDefaults.value()) {
            String[] queryPair = queryParam.split("=");
            String paramName = queryPair[0];
            String paramValue = queryPair[1];
            if (paramName.endsWith("in") || paramName.endsWith("between")) {
                queryable.addCondition(paramName, StringUtils.split(paramValue, ","));
            } else {
                queryable.addCondition(paramName, paramValue);
            }
        }

        return queryable;
    }
}
