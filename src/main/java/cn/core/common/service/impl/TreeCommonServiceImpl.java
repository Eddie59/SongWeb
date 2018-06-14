package cn.core.common.service.impl;

import cn.core.common.entity.TreeEntity;
import cn.core.common.mapper.BaseTreeMapper;
import cn.core.common.service.ITreeCommonService;
import cn.core.query.data.Queryable;
import cn.core.query.parse.QueryToWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import java.util.List;



/**
 * TreeCommonServiceImpl class
 *
 * @author Administrator
 * @date
 */
public class TreeCommonServiceImpl<M extends BaseTreeMapper<T>, T extends TreeEntity<T>>
        extends CommonServiceImpl<M, T>
        implements ITreeCommonService<T>{

/*   start 实现ITreeCommonService的方法*/
    @Override
    public List<T> selectTreeList(Wrapper<T> wrapper) {
        wrapper.eq("1", "1");
        List<T> data = baseMapper.selectTreeList(wrapper);
        return data;
    }

    @Override
    public List<T> selectTreeList(Queryable queryable, Wrapper<T> wrapper) {
        wrapper.eq("1", "1");
        QueryToWrapper<T> queryToWrapper = new QueryToWrapper<>();
        queryToWrapper.parseCondition(wrapper, queryable);
        List<T> data = baseMapper.selectTreeList(wrapper);
        return data;
    }
/*   end 实现ITreeCommonService的方法*/



}
