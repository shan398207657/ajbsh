package com.work.ssj.oss.controller;

import com.work.ssj.common.core.domain.R;
import com.work.ssj.oss.service.OssService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/upload")
@CrossOrigin //解决跨域问题
@Api(value = "upload", tags = { "文件上传接口" })
public class OssController {

    @Autowired
    private OssService ossService;

    //上传头像的方法
    @ApiOperation(value = "上传图片接口", httpMethod = "POST")
    @PostMapping("/file")
    public R uploadOssFile(MultipartFile file){

        //获取上传的文件，MultipartFile
        //返回上传到oss的路径，最后把路径上传到数据库对应字段
        String url =  ossService.uploadFileAvatar(file);
        return R.ok().put("url",url);
    }
}
