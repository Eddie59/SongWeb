package cn.modules.sys.service.impl;

import cn.core.common.service.impl.TreeCommonServiceImpl;
import cn.modules.sys.entity.Menu;
import cn.modules.sys.mapper.MenuMapper;
import cn.modules.sys.service.IMenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * MenuServiceImpl class
 *
 * @author Administrator
 * @date
 */
@Transactional
@Service("MenuService")
public class MenuServiceImpl extends TreeCommonServiceImpl<MenuMapper,Menu> implements IMenuService {

    @Override
    public List<Menu> findMenuByUserId(String userId) {
        return baseMapper.findMenuByUserId(userId);
    }

    @Override
    public List<Menu> findMenuByRoleId(String roleId) {
        return baseMapper.findMenuByRoleId(roleId);
    }
}
