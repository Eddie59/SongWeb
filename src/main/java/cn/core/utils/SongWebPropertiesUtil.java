package cn.core.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * SongWebPropertiesUtil class
 *
 * @author Administrator
 * @date
 */
public class SongWebPropertiesUtil extends PropertiesUtil{
    private static String JEEWE_BPROPERTIES_FILENAME = "songweb.properties";
    private static SongWebPropertiesUtil propertiesUtil = new SongWebPropertiesUtil();
    // 保存全局属性值
    private static Map<String, String> configMap = new HashMap<>();

    public SongWebPropertiesUtil() {
        load(JEEWE_BPROPERTIES_FILENAME);
    }

    public static SongWebPropertiesUtil getProperties() {
        if (propertiesUtil != null) {
            propertiesUtil = new SongWebPropertiesUtil();
        }
        return propertiesUtil;
    }

    /**
     * 获得配置
     *
     * @param key
     * @return
     */
    public static String getConfig(String key) {
        String value = configMap.get(key);
        if (value == null) {
            value = propertiesUtil.getString(key);
            configMap.put(key, value != null ? value : StringUtils.EMPTY);
        }
        return value;
    }

    /**
     * 设置配置
     *
     * @param key
     * @param value
     */
    public static void setConfig(String key, Object value) {
        propertiesUtil.set(key, value);
    }

    /**
     * 移除配置
     *
     * @param key
     * @return
     */
    public static boolean removeConfig(String key) {
        return propertiesUtil.remove(key);
    }
}
