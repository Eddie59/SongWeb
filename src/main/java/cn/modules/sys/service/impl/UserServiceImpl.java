package cn.modules.sys.service.impl;


import cn.core.common.service.impl.CommonServiceImpl;
import cn.modules.sys.entity.User;
import cn.modules.sys.mapper.UserMapper;
import cn.modules.sys.service.IUserService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * UserServiceImpl class
 *
 * @author Administrator
 * @date
 */
@Transactional
@Service("userService")
public class UserServiceImpl
        extends CommonServiceImpl<UserMapper, User>
        implements IUserService {

    @Override
    public User findByUsername(String username) {
        return selectOne(new EntityWrapper<User>().eq("username", username));
    }

    @Override
    public User findByEmail(String email) {
        return selectOne(new EntityWrapper<User>().eq("email", email));
    }

    @Override
    public User findByMobile(String mobile) {
        return selectOne(new EntityWrapper<User>().eq("phone", mobile));
    }
}
