package com.work.ssj.home.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.work.ssj.common.core.domain.R;
import com.work.ssj.home.entity.HomeImageEntity;
import com.work.ssj.home.service.HomeImageService;
import com.work.ssj.home.service.HomeTextService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/home")
@AllArgsConstructor
@Api(value = "home", tags = { "home首页接口" })
public class HomeController {

    @Autowired
    private HomeImageService homeImageService;
    @Autowired
    private HomeTextService homeTextService;


    @ApiOperation(value = "首页图片接口", httpMethod = "GET")
    @RequestMapping("image")
    private R getImage(){
        EntityWrapper entityWrapper = (EntityWrapper) new EntityWrapper().eq("is_show",1).
                eq("is_del",0).eq("pid",0)
                .orderBy("sort_num");
        List<HomeImageEntity> list = homeImageService.selectList(entityWrapper);
        return R.ok().put("list",list);
    }
}
