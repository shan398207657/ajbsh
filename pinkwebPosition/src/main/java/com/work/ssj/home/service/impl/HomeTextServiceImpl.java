package com.work.ssj.home.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.work.ssj.home.entity.HomeTextEntity;
import com.work.ssj.home.mapper.HomeTextMapper;
import com.work.ssj.home.service.HomeTextService;
import org.springframework.stereotype.Service;

@Service("homeTextService")
public class HomeTextServiceImpl extends ServiceImpl<HomeTextMapper, HomeTextEntity> implements HomeTextService {
}
