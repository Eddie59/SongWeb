package cn.modules.sys.mapper;

import cn.core.common.mapper.BaseTreeMapper;
import cn.modules.sys.entity.Menu;
import java.util.List;

public interface MenuMapper extends BaseTreeMapper<Menu> {
    /**
     *
     * @param userid
     * @return 通过用户查找菜单
     */
    List<Menu> findMenuByUserId(String userid);

    /**
     *
     * @param roleid
     * @return 通过角色查找菜单
     */
    List<Menu> findMenuByRoleId(String roleid);
}
