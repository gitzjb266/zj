package com.more.transformation.service.impl;

import com.more.transformation.entity.TDataserviceConfig;
import com.more.transformation.dao.TDataserviceConfigDao;
import com.more.transformation.service.TDataserviceConfigService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (TDataserviceConfig)表服务实现类
 *
 * @author makejava
 * @since 2021-08-05 09:02:03
 */
@Service("tDataserviceConfigService")
public class TDataserviceConfigServiceImpl extends TDataserviceConfigService {
    @Resource
    private TDataserviceConfigDao tDataserviceConfigDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public TDataserviceConfig queryById(String id) {
        return this.tDataserviceConfigDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<TDataserviceConfig> queryAllByLimit(int offset, int limit) {
        return this.tDataserviceConfigDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param tDataserviceConfig 实例对象
     * @return 实例对象
     */
    @Override
    public TDataserviceConfig insert(TDataserviceConfig tDataserviceConfig) {
        this.tDataserviceConfigDao.insert(tDataserviceConfig);
        return tDataserviceConfig;
    }

    /**
     * 修改数据
     *
     * @param tDataserviceConfig 实例对象
     * @return 实例对象
     */
    @Override
    public TDataserviceConfig update(TDataserviceConfig tDataserviceConfig) {
        this.tDataserviceConfigDao.update(tDataserviceConfig);
        return this.queryById(tDataserviceConfig.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String id) {
        return this.tDataserviceConfigDao.deleteById(id) > 0;
    }
}
