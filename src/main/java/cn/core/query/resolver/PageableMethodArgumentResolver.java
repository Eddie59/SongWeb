package cn.core.query.resolver;

import cn.core.query.annotation.PageDefaults;
import cn.core.query.data.PageImpl;
import cn.core.query.data.Page;
import cn.core.query.data.Sort;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * PageableMethodArgumentResolver class
 *
 * @author Administrator
 * @date
 */
public class PageableMethodArgumentResolver extends BaseMethodArgumentResolver {

    private static final Page DEFAULT_PAGE_REQUEST = new PageImpl(0, 10);
    private static final String DEFAULT_PAGE_PREFIX = "page";
    private static final String DEFAULT_SORT_PREFIX = "sort";

    private Page fallbackPagable = DEFAULT_PAGE_REQUEST;
    private String pagePrefix = DEFAULT_PAGE_PREFIX;
    private String sortPrefix = DEFAULT_SORT_PREFIX;
    private int minPageSize = 1;
    private int maxPageSize = 100;


    public void setMinPageSize(int minPageSize) {
        this.minPageSize = minPageSize;
    }

    public void setMaxPageSize(int maxPageSize) {
        this.maxPageSize = maxPageSize;
    }

    public void setFallbackPagable(Page fallbackPagable) {
        this.fallbackPagable = null == fallbackPagable ? DEFAULT_PAGE_REQUEST : fallbackPagable;
    }

    public void setPagePrefix(String pagePrefix) {
        this.pagePrefix = null == pagePrefix ? DEFAULT_PAGE_PREFIX : pagePrefix;
    }

    public void setSortPrefix(String sortPrefix) {
        this.sortPrefix = null == sortPrefix ? DEFAULT_SORT_PREFIX : sortPrefix;
    }


    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return Page.class.isAssignableFrom(parameter.getParameterType());
    }

    @Nullable
    @Override
    public Object resolveArgument(MethodParameter parameter, @Nullable ModelAndViewContainer mavContainer, NativeWebRequest webRequest, @Nullable WebDataBinderFactory binderFactory) throws Exception {
        PageDefaults pageDefaults = getPageableDefaults(parameter);
        // 默认的page request
        Page defaultPageRequest = getDefaultFromAnnotationOrFallback(pageDefaults);

        String pageNamePrefix = getPagePrefix(parameter);
        String sortNamePrefix = getSortPrefix(parameter);
        Map<String, String[]> pageMap = getPrefixParameterMap(pageNamePrefix, webRequest, true);
        Map<String, String[]> sortMap = getPrefixParameterMap(sortNamePrefix, webRequest, false);
        // 预处理排序,sort与order分开
        String gridtype = webRequest.getParameter("gridtype");
        if (!StringUtils.isEmpty(gridtype) && gridtype.equals("jqgrid")) {
            sortMap.clear();
            String sort = webRequest.getParameter("sort");
            String order = webRequest.getParameter("order");
            if (sort.contains("asc") || sort.contains("desc")) {
                String[] sortArr = sort.split(",");
                for (String sortItem : sortArr) {
                    String[] sortItemArr = sortItem.trim().split(" ");
                    if (sortItemArr.length == 2) {
                        String[] orderList = {sortItemArr[1].trim()};
                        sortMap.put("sort." + sortItemArr[0].trim(), orderList);
                    } else {
                        String[] orderList = {order};
                        sortMap.put("sort." + sortItemArr[0].trim(), orderList);
                    }
                }
            } else {
                String[] orderList = {webRequest.getParameter("order")};
                sortMap.put("sort." + webRequest.getParameter("sort"), orderList);
            }
        }
        if (sortMap.isEmpty()) {
            if (!StringUtils.isEmpty(webRequest.getParameter("sort"))) {
                String[] orderList = {webRequest.getParameter("order")};
                sortMap.put("sort." + webRequest.getParameter("sort"), orderList);
            }
        }
        Sort sort = getSort(sortNamePrefix, sortMap, defaultPageRequest, webRequest);
        if (pageMap.size() == 0) {
            return new PageImpl(defaultPageRequest.getPageNumber(), defaultPageRequest.getPageSize(),
                    sort == null ? defaultPageRequest.getSort() : sort);
        }

        int pn = getPageNumber(pageMap, defaultPageRequest);
        int pageSize = getPageSize(pageMap, defaultPageRequest);

        return new PageImpl(pn, pageSize, sort);
    }

    private PageDefaults getPageableDefaults(MethodParameter parameter) {
        PageDefaults pageDefaults = parameter.getParameterAnnotation(PageDefaults.class);
        if (pageDefaults == null) {
            pageDefaults = parameter.getMethodAnnotation(PageDefaults.class);
        }
        return pageDefaults;
    }

    private Page getDefaultFromAnnotationOrFallback(PageDefaults pageDefaults) {
        Page defaultPage = defaultPageable(pageDefaults);
        if (defaultPage != null) {
            return defaultPage;
        }
        return fallbackPagable;
    }

    private Page defaultPageable(PageDefaults pageDefaults) {
        if (pageDefaults == null) {
            return null;
        }

        int pageNumber = pageDefaults.pageNumber();
        int pageSize = pageDefaults.pageSize();

        //创建Sort对象
        Sort sort = null;
        String[] sortStrArray = pageDefaults.sort();
        for (String sortStr : sortStrArray) {
            String[] sortPair = sortStr.split("=");
            Sort newSort = new Sort(Sort.Direction.fromString(sortPair[1].toUpperCase()), sortPair[0]);
            if (sort == null) {
                sort = newSort;
            } else {
                sort = sort.and(newSort);
            }
        }

        return new PageImpl(pageNumber, pageSize, sort);
    }


    private String getPagePrefix(MethodParameter parameter) {
        Qualifier qualifier = parameter.getParameterAnnotation(Qualifier.class);
        if (qualifier != null) {
            return new StringBuilder(((Qualifier) qualifier).value()).append("_").append(pagePrefix).toString();
        }
        return pagePrefix;
    }

    private String getSortPrefix(MethodParameter parameter) {
        Qualifier qualifier = parameter.getParameterAnnotation(Qualifier.class);
        if (qualifier != null) {
            return new StringBuilder(qualifier.value()).append("_").append(sortPrefix).toString();
        }
        return sortPrefix;
    }

    private Sort getSort(String sortNamePrefix, Map<String, String[]> sortMap, Page defaultPageRequest,
                         NativeWebRequest webRequest) {
        Sort sort = null;
        try {
            List<OrderedSort> orderedSortList = new ArrayList<>();
            for (String name : sortMap.keySet()) {
                String value = sortMap.get(name)[0];
                if (!StringUtils.isEmpty(value)) {
                    // sort1.abc
                    int propertyIndex = name.indexOf(".") + 1;
                    int order = 0;
                    String orderStr = name.substring(sortNamePrefix.length(), propertyIndex - 1);

                    if (!StringUtils.isEmpty(orderStr)) {
                        order = Integer.valueOf(orderStr);
                    }

                    String property = name.substring(propertyIndex);
                    assertSortProperty(property);
                    Sort.Direction direction = Sort.Direction.fromString(sortMap.get(name)[0].toUpperCase());
                    orderedSortList.add(new OrderedSort(property, direction, order));
                }
            }
            Collections.sort(orderedSortList);
            for (OrderedSort orderedSort : orderedSortList) {
                Sort newSort = new Sort(orderedSort.direction, orderedSort.property);
                if (sort == null) {
                    sort = newSort;
                } else {
                    sort = sort.and(newSort);
                }
            }
        } catch (Exception e) {
        }
        if (sort == null) {
            return defaultPageRequest.getSort();
        }
        return sort;
    }

    private int getPageNumber(Map<String, String[]> pageMap, Page defaultPageRequest) {
        int number = 1;
        try {
            String numberStr = pageMap.get("pn")[0];
            if (numberStr != null) {
                number = Integer.valueOf(numberStr);
            } else {
                number = defaultPageRequest.getPageNumber();
            }
        } catch (Exception e) {
            number = defaultPageRequest.getPageNumber();
        }

        if (number < 1) {
            number = 1;
        }

        return number;
    }

    private int getPageSize(Map<String, String[]> pageMap, Page defaultPageRequest) {
        int pageSize = 0;
        try {
            String pageSizeStr = pageMap.get("size")[0];
            if (pageSizeStr != null) {
                pageSize = Integer.valueOf(pageSizeStr);
            } else {
                pageSize = defaultPageRequest.getPageSize();
            }
        } catch (Exception e) {
            pageSize = defaultPageRequest.getPageSize();
        }

        if (pageSize < minPageSize) {
            pageSize = minPageSize;
        }

        if (pageSize > maxPageSize) {
            pageSize = maxPageSize;
        }
        return pageSize;
    }

    /**
     * 防止sql注入，排序字符串只能包含字符 数字 下划线 点 ` "
     *
     * @param property
     */
    private void assertSortProperty(String property) {
        if (!property.matches("[a-zA-Z0-9_、.`\"]*")) {
            throw new IllegalStateException("Sort property error, only contains [a-zA-Z0-9_.`\"]");
        }
    }

    static class OrderedSort implements Comparable<OrderedSort> {
        private String property;
        private Sort.Direction direction;
        /**
         * 默认0 即无序
         */
        private int order = 0;

        OrderedSort(String property, Sort.Direction direction, int order) {
            this.property = property;
            this.direction = direction;
            this.order = order;
        }

        @Override
        public int compareTo(OrderedSort o) {
            if (o == null) {
                return -1;
            }
            if (this.order > o.order) {
                return 1;
            } else if (this.order < o.order) {
                return -1;
            } else {
                return 0;
            }
        }
    }
}
