package com.more.transformation.dao;

import com.more.transformation.entity.TDataserviceConfig;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (TDataserviceConfig)表数据库访问层
 *
 * @author makejava
 * @since 2021-08-05 09:02:03
 */
public interface TDataserviceConfigDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TDataserviceConfig queryById(String id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<TDataserviceConfig> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param tDataserviceConfig 实例对象
     * @return 对象列表
     */
    List<TDataserviceConfig> queryAll(TDataserviceConfig tDataserviceConfig);

    /**
     * 新增数据
     *
     * @param tDataserviceConfig 实例对象
     * @return 影响行数
     */
    int insert(TDataserviceConfig tDataserviceConfig);

    /**
     * 修改数据
     *
     * @param tDataserviceConfig 实例对象
     * @return 影响行数
     */
    int update(TDataserviceConfig tDataserviceConfig);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(String id);

}
