package cn.modules.sys.mapper;

import cn.modules.sys.entity.User;
import com.baomidou.mybatisplus.mapper.BaseMapper;

public interface UserMapper extends BaseMapper<User> {
    User selectByName(String username);
}
