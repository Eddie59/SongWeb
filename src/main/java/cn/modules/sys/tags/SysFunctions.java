package cn.modules.sys.tags;

import cn.core.utils.SongWebPropertiesUtil;

/**
 * SysFunctions class
 *
 * @author Administrator
 * @date
 */
public class SysFunctions {
    /**
     * 获得后台地址
     *
     * @title: getAdminUrlPrefix
     * @description: 获得后台地址
     * @return
     * @return: String
     */
    public static String getAdminUrlPrefix() {
        String adminUrlPrefix = SongWebPropertiesUtil.getConfig("admin.url.prefix");
        return adminUrlPrefix;
    }
}
