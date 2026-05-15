package com.today.menu.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.today.menu.common.pojo.PageParam;
import com.today.menu.common.pojo.PageResult;
import java.util.List;

public interface BaseMapperX<T> extends BaseMapper<T> {

    default T selectOne(SFunction<T, ?> fieldGetter, Object value) {
        return selectOne(new LambdaQueryWrapper<T>()
                .eq(fieldGetter, value));
    }

    default List<T> selectList(SFunction<T, ?> fieldGetter, Object value) {
        return selectList(new LambdaQueryWrapper<T>()
                .eq(fieldGetter, value));
    }

    default PageResult<T> selectPage(PageParam pageParam, Wrapper<T> wrapper) {
        IPage<T> page = selectPage(new Page<>(pageParam.getPageNo(), pageParam.getPageSize()), wrapper);
        return new PageResult<>(page.getRecords(), page.getTotal());
    }
}