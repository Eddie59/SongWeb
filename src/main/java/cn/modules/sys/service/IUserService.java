package cn.modules.sys.service;

import cn.core.common.service.ICommonService;
import cn.modules.sys.entity.User;

public interface IUserService extends ICommonService<User> {
    User findByUsername(String username);

    User findByEmail(String email);

    User findByMobile(String mobile);
}
