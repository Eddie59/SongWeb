package cn.core.query.data;

import com.alibaba.fastjson.serializer.SerializeFilter;

public interface PropertyPreFilter {
    SerializeFilter constructFilter(Class<?> clazz);

}
