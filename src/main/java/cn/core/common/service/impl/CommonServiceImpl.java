package cn.core.common.service.impl;

import cn.core.common.service.ICommonService;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.service.impl.*;

import java.util.List;

/**
 * CommonServiceImpl class
 *
 * @author Administrator
 * @date
 */
public class CommonServiceImpl<M extends BaseMapper<T>, T>
        extends ServiceImpl<M, T>
        implements ICommonService<T> {

    @Override
    public List<T> list() {
        return null;
    }

}
