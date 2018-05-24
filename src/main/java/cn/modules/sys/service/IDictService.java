package cn.modules.sys.service;

import cn.core.common.service.ICommonService;
import cn.modules.sys.entity.Dict;
import com.baomidou.mybatisplus.plugins.Page;
import javafx.scene.control.Pagination;

import java.util.List;


/**
 * IDictService class
 *
 * @author Administrator
 * @date
 */
public interface IDictService extends ICommonService<Dict> {
    Page<Dict> selectDictList(Page page);
}
