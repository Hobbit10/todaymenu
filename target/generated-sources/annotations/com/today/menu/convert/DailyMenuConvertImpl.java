package com.today.menu.convert;

import com.today.menu.controller.admin.vo.DailyMenuRespVO;
import com.today.menu.dal.dataobject.DailyMenuDO;
import com.today.menu.dal.dataobject.DishDO;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-15T14:37:16+0800",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.13 (Oracle Corporation)"
)
public class DailyMenuConvertImpl implements DailyMenuConvert {

    @Override
    public DailyMenuRespVO convert(DailyMenuDO dailyMenuDO, DishDO dish) {
        if ( dailyMenuDO == null && dish == null ) {
            return null;
        }

        DailyMenuRespVO dailyMenuRespVO = new DailyMenuRespVO();

        if ( dailyMenuDO != null ) {
            dailyMenuRespVO.setId( dailyMenuDO.getId() );
            dailyMenuRespVO.setCode( dailyMenuDO.getCode() );
            dailyMenuRespVO.setMenuDate( dailyMenuDO.getMenuDate() );
            dailyMenuRespVO.setDishId( dailyMenuDO.getDishId() );
            dailyMenuRespVO.setSort( dailyMenuDO.getSort() );
        }
        if ( dish != null ) {
            dailyMenuRespVO.setDishName( dish.getName() );
            dailyMenuRespVO.setCategory( dish.getCategory() );
            dailyMenuRespVO.setImageUrl( dish.getImageUrl() );
            dailyMenuRespVO.setDishDescription( dish.getDescription() );
        }
        dailyMenuRespVO.setCategoryName( com.today.menu.api.enums.DishCategoryEnum.getNameByCategory(dish.getCategory()) );

        return dailyMenuRespVO;
    }
}
