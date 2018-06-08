package cn.core.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.PropertyPreFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * ComplexPropertyPreFilter class
 *
 * @author Administrator
 * @date
 */
public class ComplexPropertyPreFilter implements PropertyPreFilter {

    private Map<Class<?>, Set<String>> includes = new HashMap<>();
    private Map<Class<?>, Set<String>> excludes = new HashMap<>();

    //???
    static {
        JSON.DEFAULT_GENERATE_FEATURE |= SerializerFeature.DisableCircularReferenceDetect.getMask();
    }

    public ComplexPropertyPreFilter() {
    }

    public ComplexPropertyPreFilter(Map<Class<?>, Set<String>> includes) {
        super();
        this.includes = includes;
    }

    public void addIncludes(Class<?> clazz, String... properties) {
        Set<String> includeSet = null;
        if (!includes.containsKey(clazz)) {
            includeSet = new HashSet<>();
            includes.put(clazz, includeSet);
        } else {
            includeSet = includes.get(clazz);
        }

        for (String pro : properties) {
            if (StringUtils.isNoneEmpty(pro)) {
                includeSet.add(pro);
            }
        }
    }

    public void addExcludes(Class<?> clazz, String... properties) {
        Set<String> excludeSet = null;
        if (!excludes.containsKey(clazz)) {
            excludeSet = new HashSet<>();
            excludes.put(clazz, excludeSet);
        } else {
            excludeSet = excludes.get(clazz);
        }

        for (String pro : properties) {
            if (StringUtils.isNoneEmpty(pro)) {
                excludeSet.add(pro);
            }
        }
    }
    @Override
    public boolean apply(JSONSerializer serializer, Object source, String name) {
        if (source == null) {
            return true;
        }
        Class<?> clazz = source.getClass();
        Set<String> excludeSet = getValue(this.excludes, clazz);
        if (excludeSet != null) {
            if (excludeSet.contains(name)) {
                return false;
            }
        }

        if (this.includes.isEmpty()) {
            return true;
        }

        Set<String> includeSet = getValue(this.includes, clazz);
        if (includeSet != null) {
            if (includeSet.contains(name)) {
                return true;
            }
        } else {
            return true;
        }
        return false;
    }

    public Set<String> getValue(Map<Class<?>, Set<String>> clazzMaps, Class<?> clazz) {
        Set<String> value = null;
        for (Map.Entry<Class<?>, Set<String>> item : clazzMaps.entrySet()) {
            // isAssignableFrom()，用来判断类型间是否有继承关系
            if (item.getKey().isAssignableFrom(clazz)) {
                value = item.getValue();
            }
        }
        return value;
    }

  /*//不起作用？？？，object传来的一直是Page类型，没有Dict类型
  @Override
    public boolean apply(JSONSerializer serializer, Object object, String name) {
        if(name.equals("records"))
        {
            String str="";
        }
        Class<?> clazz = object.getClass();

        for(Map.Entry<Class<?>,Set<String>> entry:includes.entrySet())
        {
           if( entry.getKey().isAssignableFrom(clazz))
           {
               if(entry.getValue().contains(name))
               {
                   return true;
               }
           }
           else
           {
               return true;
           }
        }

      for(Map.Entry<Class<?>,Set<String>> entry:excludes.entrySet())
      {
          if( entry.getKey().isAssignableFrom(clazz))
          {
              if(entry.getValue().contains(name))
              {
                  return false;
              }
          }
          else
          {
              return true;
          }
      }
        return false;
    }*/

    public Map<Class<?>, Set<String>> getExcludes() {
        return excludes;
    }

    public Map<Class<?>, Set<String>> getIncludes() {
        return includes;
    }

    public void setExcludes(Map<Class<?>, Set<String>> excludes) {
        this.excludes = excludes;
    }

    public void setIncludes(Map<Class<?>, Set<String>> includes) {
        this.includes = includes;
    }
}
