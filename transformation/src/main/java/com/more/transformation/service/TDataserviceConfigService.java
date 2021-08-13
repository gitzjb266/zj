package com.more.transformation.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.more.transformation.entity.TDataserviceConfig;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * (TDataserviceConfig)表服务接口
 *
 * @author makejava
 * @since 2021-08-05 09:02:03
 */
public class TDataserviceConfigService implements IService<TDataserviceConfig> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    public TDataserviceConfig queryById(String id) {
        return null;
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    public List<TDataserviceConfig> queryAllByLimit(int offset, int limit) {
        return null;
    }

    /**
     * 新增数据
     *
     * @param tDataserviceConfig 实例对象
     * @return 实例对象
     */
    public TDataserviceConfig insert(TDataserviceConfig tDataserviceConfig) {
        return null;
    }

    /**
     * 修改数据
     *
     * @param tDataserviceConfig 实例对象
     * @return 实例对象
     */
    public TDataserviceConfig update(TDataserviceConfig tDataserviceConfig) {
        return null;
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    public boolean deleteById(String id) {
        return false;
    }

    @Override
    public boolean save(TDataserviceConfig entity) {
        return false;
    }

    @Override
    public boolean saveBatch(Collection<TDataserviceConfig> entityList, int batchSize) {
        return false;
    }

    @Override
    public boolean saveOrUpdateBatch(Collection<TDataserviceConfig> entityList, int batchSize) {
        return false;
    }

    @Override
    public boolean removeById(Serializable id) {
        return false;
    }

    @Override
    public boolean removeByMap(Map<String, Object> columnMap) {
        return false;
    }

    @Override
    public boolean remove(Wrapper<TDataserviceConfig> queryWrapper) {
        return false;
    }

    @Override
    public boolean removeByIds(Collection<? extends Serializable> idList) {
        return false;
    }

    @Override
    public boolean updateById(TDataserviceConfig entity) {
        return false;
    }

    @Override
    public boolean update(TDataserviceConfig entity, Wrapper<TDataserviceConfig> updateWrapper) {
        return false;
    }

    @Override
    public boolean updateBatchById(Collection<TDataserviceConfig> entityList, int batchSize) {
        return false;
    }

    @Override
    public boolean saveOrUpdate(TDataserviceConfig entity) {
        return false;
    }

    @Override
    public TDataserviceConfig getById(Serializable id) {
        return null;
    }

    @Override
    public Collection<TDataserviceConfig> listByIds(Collection<? extends Serializable> idList) {
        return null;
    }

    @Override
    public Collection<TDataserviceConfig> listByMap(Map<String, Object> columnMap) {
        return null;
    }

    @Override
    public TDataserviceConfig getOne(Wrapper<TDataserviceConfig> queryWrapper, boolean throwEx) {
        return null;
    }

    @Override
    public Map<String, Object> getMap(Wrapper<TDataserviceConfig> queryWrapper) {
        return null;
    }

    @Override
    public <V> V getObj(Wrapper<TDataserviceConfig> queryWrapper, Function<? super Object, V> mapper) {
        return null;
    }

    @Override
    public int count(Wrapper<TDataserviceConfig> queryWrapper) {
        return 0;
    }

    @Override
    public List<TDataserviceConfig> list(Wrapper<TDataserviceConfig> queryWrapper) {
        return null;
    }

    @Override
    public IPage<TDataserviceConfig> page(IPage<TDataserviceConfig> page, Wrapper<TDataserviceConfig> queryWrapper) {
        return null;
    }

    @Override
    public List<Map<String, Object>> listMaps(Wrapper<TDataserviceConfig> queryWrapper) {
        return null;
    }

    @Override
    public <V> List<V> listObjs(Wrapper<TDataserviceConfig> queryWrapper, Function<? super Object, V> mapper) {
        return null;
    }

    @Override
    public IPage<Map<String, Object>> pageMaps(IPage<TDataserviceConfig> page, Wrapper<TDataserviceConfig> queryWrapper) {
        return null;
    }

    @Override
    public BaseMapper<TDataserviceConfig> getBaseMapper() {
        return null;
    }
}
