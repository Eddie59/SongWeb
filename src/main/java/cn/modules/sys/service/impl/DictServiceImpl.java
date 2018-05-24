package cn.modules.sys.service.impl;

import cn.core.common.service.impl.CommonServiceImpl;
import cn.modules.sys.entity.Dict;
import cn.modules.sys.mapper.DictMapper;
import cn.modules.sys.service.IDictService;
import com.baomidou.mybatisplus.plugins.Page;
import javafx.scene.control.Pagination;
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
    public Page<Dict> selectDictList(Page page) {
        List<Dict> dicts= baseMapper.selectDictList(page);
        page.setRecords(dicts);
        return page;
    }

}
