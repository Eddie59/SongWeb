package cn.modules.sys.service.impl;

import cn.core.common.service.impl.CommonServiceImpl;
import cn.modules.sys.entity.Dict;
import cn.modules.sys.mapper.DictMapper;
import cn.modules.sys.service.IDictService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * DictServiceImpl class
 *
 * @author Administrator
 * @date
 */
@Transactional
@Service("dictService")
public class DictServiceImpl
        extends CommonServiceImpl<DictMapper,Dict>
        implements IDictService{

    @Override
    public List<Dict> selectDictList() {
        return baseMapper.selectDictList();
    }

}
