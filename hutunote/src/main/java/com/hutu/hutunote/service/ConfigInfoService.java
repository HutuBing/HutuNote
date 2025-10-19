package com.hutu.hutunote.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hutu.hutunote.mapper.ConfigInfoMapper;
import com.hutu.hutunote.model.entity.ConfigInfo;
import com.hutu.hutunote.model.enums.ConfigEnum;
import org.springframework.stereotype.Service;

@Service
public class ConfigInfoService extends ServiceImpl<ConfigInfoMapper, ConfigInfo> {

    public ConfigInfo getByCode(ConfigEnum configEnum) {
        return getOne(new LambdaQueryWrapper<ConfigInfo>().eq(ConfigInfo::getCode, configEnum.getCode()).last(" limit 1"));
    }

}
