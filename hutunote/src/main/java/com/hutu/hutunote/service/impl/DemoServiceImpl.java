package com.hutu.hutunote.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hutu.hutunote.mapper.DemoMapper;
import com.hutu.hutunote.model.entity.Demo;
import com.hutu.hutunote.service.IDemoService;
import org.springframework.stereotype.Service;

@Service
public class DemoServiceImpl extends ServiceImpl<DemoMapper, Demo> implements IDemoService {
}
