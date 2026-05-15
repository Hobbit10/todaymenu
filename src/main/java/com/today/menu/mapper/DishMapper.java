package com.today.menu.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.today.menu.dal.dataobject.DishDO;
import com.today.menu.common.pojo.PageParam;
import com.today.menu.common.pojo.PageResult;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface DishMapper extends BaseMapperX<DishDO> {

    default DishDO selectByCode(String code) {
        return selectOne(DishDO::getCode, code);
    }

    default DishDO selectByName(String name) {
        return selectOne(DishDO::getName, name);
    }

    default List<DishDO> selectListByCategory(Integer category) {
        return selectList(new LambdaQueryWrapper<DishDO>()
                .eq(DishDO::getCategory, category)
                .eq(DishDO::getStatus, 0)
                .orderByDesc(DishDO::getCreatedon));
    }

    default List<DishDO> selectListByKeyword(String keyword) {
        return selectList(new LambdaQueryWrapper<DishDO>()
                .like(DishDO::getName, keyword)
                .eq(DishDO::getStatus, 0)
                .orderByDesc(DishDO::getCreatedon));
    }

    default List<DishDO> selectAll() {
        return selectList(new LambdaQueryWrapper<DishDO>()
                .eq(DishDO::getStatus, 0)
                .orderByDesc(DishDO::getCreatedon));
    }

    default PageResult<DishDO> selectPage(PageParam pageParam, LambdaQueryWrapper<DishDO> wrapper) {
        return selectPage(pageParam, wrapper);
    }
}