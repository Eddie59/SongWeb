package cn.modules.sys.service;

import cn.core.common.service.ICommonService;
import cn.modules.sys.entity.Dict;

import java.util.List;


/**
 * IDictService class
 *
 * @author Administrator
 * @date
 */
public interface IDictService extends ICommonService<Dict> {
    List<Dict> selectDictList();
}
