package cn.modules.sys.mapper;

import cn.modules.sys.entity.Dict;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import javafx.scene.control.Pagination;

import java.util.List;

public interface DictMapper extends BaseMapper<Dict> {
    List<Dict> selectDictList(Page<Dict> page);
}
