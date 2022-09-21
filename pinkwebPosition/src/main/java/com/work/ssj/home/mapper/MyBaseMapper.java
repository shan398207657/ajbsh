package com.work.ssj.home.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface MyBaseMapper<T> extends BaseMapper<T> {
    Integer insert(T var1);

    Integer insertAllColumn(T var1);

    Integer deleteById(Serializable var1);

    Integer deleteByMap(@Param("cm") Map<String, Object> var1);

    Integer delete(@Param("ew") Wrapper<T> var1);

    Integer deleteBatchIds(@Param("coll") Collection<? extends Serializable> var1);

    Integer updateById(@Param("et") T var1);

    Integer updateAllColumnById(@Param("et") T var1);

    Integer update(@Param("et") T var1, @Param("ew") Wrapper<T> var2);

    T selectById(Serializable var1);

    List<T> selectBatchIds(@Param("coll") Collection<? extends Serializable> var1);

    List<T> selectByMap(@Param("cm") Map<String, Object> var1);

    T selectOne(@Param("ew") T var1);

    Integer selectCount(@Param("ew") Wrapper<T> var1);

    List<T> selectList(@Param("ew") Wrapper<T> var1);

    List<Map<String, Object>> selectMaps(@Param("ew") Wrapper<T> var1);

    List<Object> selectObjs(@Param("ew") Wrapper<T> var1);

    List<T> selectPage(RowBounds var1, @Param("ew") Wrapper<T> var2);

    List<Map<String, Object>> selectMapsPage(RowBounds var1, @Param("ew") Wrapper<T> var2);
}
