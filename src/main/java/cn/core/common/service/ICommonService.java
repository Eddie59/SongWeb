package cn.core.common.service;

import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * ICommonService class
 *
 * @author Administrator
 * @date
 */
public interface ICommonService<T> extends IService<T> {

    List<T> list();

}
