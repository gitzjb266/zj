package com.more.adapter.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.more.adapter.dao.TDataserviceConfigDao;
import com.more.adapter.entity.TDataserviceConfig;
import com.more.adapter.service.TDataserviceConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import com.more.adapter.constants.*;
import java.util.List;

/**
 * (TDataserviceConfig)表控制层
 *
 * @author makejava
 * @since 2021-08-13 11:09:00
 */
@RestController
@RequestMapping("tDataserviceConfig")
public class TDataserviceConfigController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private TDataserviceConfigService tDataserviceConfigService;

    @Autowired
    private TDataserviceConfigDao dc;

    /**
     * 分页查询所有数据
     *
     * @param page               分页对象
     * @param tDataserviceConfig 查询实体
     * @return 所有数据
     */
    @GetMapping("/selectAll")
    public R selectAll(Page<TDataserviceConfig> page, TDataserviceConfig tDataserviceConfig) {
        return success(this.tDataserviceConfigService.page(page, new QueryWrapper<>(tDataserviceConfig)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("/queryById")
    public R selectOne(@RequestParam(name = "id", required = true) String id) {
        TDataserviceConfig ds =  this.dc.selectById(id);
        return success(this.tDataserviceConfigService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param tDataserviceConfig 实体对象
     * @return 新增结果
     */
    @PostMapping
    public R insert(@RequestBody TDataserviceConfig tDataserviceConfig) {
        return success(this.tDataserviceConfigService.save(tDataserviceConfig));
    }

    /**
     * 修改数据
     *
     * @param tDataserviceConfig 实体对象
     * @return 修改结果
     */
    @PutMapping
    public R update(@RequestBody TDataserviceConfig tDataserviceConfig) {
        return success(this.tDataserviceConfigService.updateById(tDataserviceConfig));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public R delete(@RequestParam("idList") List<Long> idList) {
        return success(this.tDataserviceConfigService.removeByIds(idList));
    }

    /**
     * 获取ocr相关服务列表
     * @return
     */
    @GetMapping("getServiceNames")
    public R serviceNames() {
        return success(CommonConst.serviceMap);
    }
}
