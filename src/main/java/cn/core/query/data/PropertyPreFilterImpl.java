package cn.core.query.data;

import cn.core.utils.ComplexPropertyPreFilter;
import com.alibaba.fastjson.serializer.SerializeFilter;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * PropertyPreFilterImpl class
 *
 * @author Administrator
 * @date
 */
public class PropertyPreFilterImpl implements PropertyPreFilter {

    private String queryPropertyStr;
    private Set<String> queryPropertySet;
    private ComplexPropertyPreFilter complexPropertyPreFilter;

    public PropertyPreFilterImpl(String str) {
        init(str);
    }

    public void init(String str)
    {
        queryPropertyStr=str;
        this.queryPropertySet = new HashSet<String>();
        if(StringUtils.isNoneEmpty(str))
        {
            queryPropertySet=Arrays.stream(queryPropertyStr.split(",")).collect(Collectors.toSet());
        }

        if(complexPropertyPreFilter==null)
        {
            complexPropertyPreFilter=new ComplexPropertyPreFilter();
        }
    }


    @Override
    public SerializeFilter constructFilter(Class<?> clazz) {
        queryPropertySet.forEach((x)->{
            if(!x.contains("."))
            {
                complexPropertyPreFilter.addIncludes(clazz,x);
            }
            else
            {}
        });
        return complexPropertyPreFilter;
    }
}
