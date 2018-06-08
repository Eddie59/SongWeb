package cn.core.common.service.impl;

import cn.core.common.service.ICommonService;
import cn.core.query.data.Queryable;
import com.baomidou.mybatisplus.plugins.Page;
import cn.core.query.parse.QueryToWrapper;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
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
    public List<T> listByPage(Page page) {

        return null;
    }

    @Override
    public Page<T> list(Queryable queryable, Wrapper<T> wrapper)
    {
        QueryToWrapper<T> queryToWrapper=new QueryToWrapper<>();
        //多条件查询
        queryToWrapper.parseCondition(wrapper,queryable);
        //排序
        queryToWrapper.parseSort(wrapper,queryable);
        //分页
        cn.core.query.data.Page page= queryable.getPage();
        com.baomidou.mybatisplus.plugins.Page<T> plusPage = new com.baomidou.mybatisplus.plugins.Page<T>(page.getPageNumber(), page.getPageSize());
        com.baomidou.mybatisplus.plugins.Page<T> content = selectPage(plusPage, wrapper);
        return content;
    }
}
