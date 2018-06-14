package cn.core.common.service;

import cn.core.query.data.Queryable;
import java.util.List;

import com.baomidou.mybatisplus.mapper.Wrapper;

public interface ITreeCommonService<T> extends ICommonService<T> {
    List<T> selectTreeList(Wrapper<T> wrapper);

    List<T> selectTreeList(Queryable queryable, Wrapper<T> wrapper);
}
