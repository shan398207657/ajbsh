package com.work.ssj.common.core.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.enums.FieldFill;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * Entity基类
 * 
 * @author shansj
 */
@Getter
@Setter
@Accessors(chain = true)
public class BaseDiBootEntity extends QueryParamEntity implements Serializable
{
    /** 创建者 */
    @TableField(value = "create_by",fill = FieldFill.INSERT)
    private Integer              createBy;

    /** 创建者名称 */
    @TableField(value = "create_name",fill = FieldFill.INSERT)
    private String              createName;

    /** 创建时间 */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private Date                createTime;

    /** 更新者 */
    @TableField(value = "update_by",fill = FieldFill.UPDATE)
    private Integer              updateBy;

    /** 更新者名称 */
    @TableField(value = "update_name",fill = FieldFill.UPDATE)
    private String              updateName;

    /** 更新时间 */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "update_time",fill = FieldFill.UPDATE)
    private Date                updateTime;

    /** 逻辑删除 0 未删除， 1 已删除 */
    @TableField(value = "del_flag", fill = FieldFill.INSERT)
    @TableLogic
    private Integer              delFlag;
}
