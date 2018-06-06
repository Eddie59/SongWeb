package cn.modules.sys.service.impl;

import cn.core.common.service.impl.CommonServiceImpl;
import cn.modules.sys.entity.DictGroup;
import cn.modules.sys.mapper.DictGroupMapper;
import cn.modules.sys.service.IDictGroupService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * DictGroupImpl class
 *
 * @author Administrator
 * @date
 */
@Transactional
@Service("dictGroupService")
public class DictGroupImpl
        extends CommonServiceImpl<DictGroupMapper,DictGroup>
        implements IDictGroupService {

}
