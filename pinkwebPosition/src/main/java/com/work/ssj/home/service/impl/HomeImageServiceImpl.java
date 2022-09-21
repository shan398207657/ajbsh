package com.work.ssj.home.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.work.ssj.home.entity.HomeImageEntity;
import com.work.ssj.home.mapper.HomeImageMapper;
import com.work.ssj.home.service.HomeImageService;
import org.springframework.stereotype.Service;

@Service("homeImageService")
public class HomeImageServiceImpl extends ServiceImpl<HomeImageMapper, HomeImageEntity> implements HomeImageService {
}
