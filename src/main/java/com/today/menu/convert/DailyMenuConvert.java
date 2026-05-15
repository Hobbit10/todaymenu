package com.today.menu.convert;

import com.today.menu.api.enums.DishCategoryEnum;
import com.today.menu.controller.admin.vo.DailyMenuRespVO;
import com.today.menu.dal.dataobject.DailyMenuDO;
import com.today.menu.dal.dataobject.DishDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface DailyMenuConvert {

    DailyMenuConvert INSTANCE = Mappers.getMapper(DailyMenuConvert.class);

    @Mapping(source = "dailyMenuDO.id", target = "id")
    @Mapping(source = "dailyMenuDO.code", target = "code")
    @Mapping(source = "dailyMenuDO.menuDate", target = "menuDate")
    @Mapping(source = "dailyMenuDO.dishId", target = "dishId")
    @Mapping(source = "dailyMenuDO.sort", target = "sort")
    @Mapping(source = "dish.name", target = "dishName")
    @Mapping(source = "dish.category", target = "category")
    @Mapping(target = "categoryName", expression = "java(com.today.menu.api.enums.DishCategoryEnum.getNameByCategory(dish.getCategory()))")
    @Mapping(source = "dish.imageUrl", target = "imageUrl")
    @Mapping(source = "dish.description", target = "dishDescription")
    DailyMenuRespVO convert(DailyMenuDO dailyMenuDO, DishDO dish);

    default List<DailyMenuRespVO> convertList(List<DailyMenuDO> list, List<DishDO> dishes) {
        if (list == null || dishes == null) {
            return null;
        }
        return list.stream()
                .map(menu -> {
                    DishDO dish = dishes.stream()
                            .filter(d -> d.getId().equals(menu.getDishId()))
                            .findFirst()
                            .orElse(null);
                    return convert(menu, dish);
                })
                .collect(Collectors.toList());
    }
}