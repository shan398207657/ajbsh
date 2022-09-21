package com.work.ssj.common.core.domain;

import com.baomidou.mybatisplus.annotations.TableField;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * Entity基类
 * 
 * @author shansj
 */
@Getter
@Setter
@Accessors(chain = true)
public class QueryParamEntity implements Serializable
{
    // 当前页
    @TableField(exist = false)
    int pageNum;

    // 页大小
    @TableField(exist = false)
    int pageSize;

    // 排序字段
    @TableField(exist = false)
    String sortField;

    // 排序方式
    @TableField(exist = false)
    String sortOrder;
}
