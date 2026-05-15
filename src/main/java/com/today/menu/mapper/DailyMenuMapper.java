package com.today.menu.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.today.menu.dal.dataobject.DailyMenuDO;
import org.apache.ibatis.annotations.Mapper;
import java.time.LocalDate;
import java.util.List;

@Mapper
public interface DailyMenuMapper extends BaseMapperX<DailyMenuDO> {

    default DailyMenuDO selectByCode(String code) {
        return selectOne(DailyMenuDO::getCode, code);
    }

    default List<DailyMenuDO> selectListByMenuDate(LocalDate menuDate) {
        return selectList(new LambdaQueryWrapper<DailyMenuDO>()
                .eq(DailyMenuDO::getMenuDate, menuDate)
                .orderByAsc(DailyMenuDO::getSort));
    }

    default DailyMenuDO selectByMenuDateAndDishId(LocalDate menuDate, Long dishId) {
        return selectOne(new LambdaQueryWrapper<DailyMenuDO>()
                .eq(DailyMenuDO::getMenuDate, menuDate)
                .eq(DailyMenuDO::getDishId, dishId));
    }

    default List<LocalDate> selectMenuDates(LocalDate startDate, LocalDate endDate) {
        List<DailyMenuDO> list = selectList(new LambdaQueryWrapper<DailyMenuDO>()
                .between(DailyMenuDO::getMenuDate, startDate, endDate)
                .groupBy(DailyMenuDO::getMenuDate)
                .select(DailyMenuDO::getMenuDate));
        return list.stream().map(DailyMenuDO::getMenuDate).distinct().toList();
    }

    default int deleteByMenuDateAndDishId(LocalDate menuDate, Long dishId) {
        return delete(new LambdaQueryWrapper<DailyMenuDO>()
                .eq(DailyMenuDO::getMenuDate, menuDate)
                .eq(DailyMenuDO::getDishId, dishId));
    }
}