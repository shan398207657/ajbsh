package com.work.ssj.home.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@AllArgsConstructor
@Api(value = "小demo", tags = { "小demo分类" })
public class TestController {

    /**
     * 测试接口DEMO
     */
    @ApiOperation(value = "测试接口DEMO", httpMethod = "GET")
    @GetMapping("/demo")
    public void demo(){
        System.err.println("demo12345");
    }
}
