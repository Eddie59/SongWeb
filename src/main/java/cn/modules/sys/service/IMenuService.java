package cn.modules.sys.service;

import cn.core.common.service.ITreeCommonService;
import cn.modules.sys.entity.Menu;

import java.util.List;

public interface IMenuService extends ITreeCommonService<Menu> {

    /**
     *
     * @param userId
     * @return 通过用户ID查找菜单
     */
    List<Menu> findMenuByUserId(String userId);

    /**
     *
     * @param roleId
     * @return 通过角色查找菜单
     */
    List<Menu> findMenuByRoleId(String roleId);
}
