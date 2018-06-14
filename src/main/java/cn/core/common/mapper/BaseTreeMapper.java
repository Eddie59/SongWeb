package cn.core.common.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;

/**
 * BaseTreeMapper class
 * 定义Tree共有的方法
 *
 * @author Administrator
 * @date
 */
public interface BaseTreeMapper<T> extends BaseMapper<T> {

    /**
     * @param id
     * @return 根据id查找
     */
    T selectByTreeId(Serializable id);


    /**
     * @param wrapper
     * @return 查找列表
     */
    List<T> selectTreeList(@Param("ew") Wrapper<T> wrapper);


    /**
     * 更新ParentIds
     */
    void updateSunTreeParentIds(@Param("newParentIds") String newParentIds, @Param("oldParentIds") String oldParentIds);

    /**
     * @param parentIds 删除节点
     */
    void deleteSunTree(String parentIds);

}
