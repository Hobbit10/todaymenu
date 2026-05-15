package com.today.menu.convert;

import com.today.menu.api.enums.DishCategoryEnum;
import com.today.menu.controller.admin.vo.DishRespVO;
import com.today.menu.controller.admin.vo.DishSaveReqVO;
import com.today.menu.dal.dataobject.DishDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.List;

@Mapper
public interface DishConvert {

    DishConvert INSTANCE = Mappers.getMapper(DishConvert.class);

    @Mapping(target = "categoryName", expression = "java(com.today.menu.api.enums.DishCategoryEnum.getNameByCategory(dishDO.getCategory()))")
    DishRespVO convert(DishDO dishDO);

    List<DishRespVO> convertList(List<DishDO> list);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "createdon", ignore = true)
    @Mapping(target = "lastchangedon", ignore = true)
    @Mapping(target = "createdby", ignore = true)
    @Mapping(target = "lastchangedby", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "code", ignore = true)
    @Mapping(target = "sort", ignore = true)
    @Mapping(target = "status", ignore = true)
    DishDO convert(DishSaveReqVO reqVO);
}