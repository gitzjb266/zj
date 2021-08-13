package com.more.adapter.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.more.adapter.dao.TDataserviceConfigDao;
import com.more.adapter.entity.TDataserviceConfig;
import com.more.adapter.service.TDataserviceConfigService;
import org.springframework.stereotype.Service;

/**
 * (TDataserviceConfig)表服务实现类
 *
 * @author makejava
 * @since 2021-08-13 11:09:00
 */
@Service("tDataserviceConfigService")
public class TDataserviceConfigServiceImpl extends ServiceImpl<TDataserviceConfigDao, TDataserviceConfig> implements TDataserviceConfigService {

}
