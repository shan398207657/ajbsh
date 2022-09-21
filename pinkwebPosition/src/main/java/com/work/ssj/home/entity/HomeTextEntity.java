package com.work.ssj.home.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@ToString
@TableName("home_text")
public class HomeTextEntity implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String title;
    private Date creatDate;
    private Integer sortNum;
    private Integer isShow;
    private Integer isDel;
}
