package cn.core.common.service;

import cn.core.query.data.Queryable;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * ICommonService class
 *
 * @author Administrator
 * @date
 */
public interface ICommonService<T> extends IService<T> {

    List<T> listByPage(Page page);

    Page<T> list(Queryable queryable, Wrapper<T> wrapper);
}
