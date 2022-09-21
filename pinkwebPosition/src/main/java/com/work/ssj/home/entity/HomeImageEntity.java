package com.work.ssj.home.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@ToString
@TableName("home_image")
public class HomeImageEntity implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String describe;
    private String name;
    private Integer pid;
    private String imageUrl;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date creatDate;
    private Integer sortNum;
    private Integer isShow;
    private Integer isDel;
}
