package com.more.transformation.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.more.transformation.constants.CommonConst;
import com.more.transformation.entity.TDataserviceConfig;
import com.more.transformation.service.TDataserviceConfigService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * (TDataserviceConfig)表控制层
 *
 * @author makejava
 * @since 2021-08-05 09:02:03
 */
@RestController
@RequestMapping("tDataserviceConfig")
public class TDataserviceConfigController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private TDataserviceConfigService tDataserviceConfigService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public TDataserviceConfig selectOne(String id) {
        return this.tDataserviceConfigService.queryById(id);
    }

    /**
     * 分页查询所有数据
     *
     * @param page               分页对象
     * @param dataserviceConfig 查询实体
     * @return 所有数据
     */
    @GetMapping("page")
    public R selectAll(Page<TDataserviceConfig> page, TDataserviceConfig dataserviceConfig) {
        QueryWrapper<TDataserviceConfig> queryWrapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank(dataserviceConfig.getServiceName())) {
            queryWrapper.eq("service_name", dataserviceConfig.getServiceName());
        }
        return success(this.tDataserviceConfigService.page(page, queryWrapper));
    }

    /**
     * 新增数据
     *
     * @param dataserviceConfig 实体对象
     * @return 新增结果
     */
    @PostMapping("save")
    public R insert(TDataserviceConfig dataserviceConfig) {
        dataserviceConfig.setCreateTime(new Date());
        dataserviceConfig.setUpdateTime(new Date());
        return success(this.tDataserviceConfigService.save(dataserviceConfig));
    }

    /**
     * 修改数据
     *
     * @param dataserviceConfig 实体对象
     * @return 修改结果
     */
    @PostMapping("update")
    public R update(TDataserviceConfig dataserviceConfig) {
        dataserviceConfig.setUpdateTime(new Date());
        return success(this.tDataserviceConfigService.updateById(dataserviceConfig));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @PostMapping("del")
    public R delete(@RequestParam("ids") String idList) {
        String[] ids = idList.split(",");
        List<String> strings = Arrays.asList(ids);
        return success(this.tDataserviceConfigService.removeByIds(strings));
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
