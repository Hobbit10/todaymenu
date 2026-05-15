package com.today.menu.convert;

import com.today.menu.controller.admin.vo.DishRespVO;
import com.today.menu.controller.admin.vo.DishSaveReqVO;
import com.today.menu.dal.dataobject.DishDO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-15T14:37:16+0800",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.13 (Oracle Corporation)"
)
public class DishConvertImpl implements DishConvert {

    @Override
    public DishRespVO convert(DishDO dishDO) {
        if ( dishDO == null ) {
            return null;
        }

        DishRespVO dishRespVO = new DishRespVO();

        dishRespVO.setId( dishDO.getId() );
        dishRespVO.setCode( dishDO.getCode() );
        dishRespVO.setName( dishDO.getName() );
        dishRespVO.setCategory( dishDO.getCategory() );
        dishRespVO.setImageUrl( dishDO.getImageUrl() );
        dishRespVO.setDescription( dishDO.getDescription() );
        dishRespVO.setSort( dishDO.getSort() );
        dishRespVO.setStatus( dishDO.getStatus() );
        dishRespVO.setCreatedon( dishDO.getCreatedon() );

        dishRespVO.setCategoryName( com.today.menu.api.enums.DishCategoryEnum.getNameByCategory(dishDO.getCategory()) );

        return dishRespVO;
    }

    @Override
    public List<DishRespVO> convertList(List<DishDO> list) {
        if ( list == null ) {
            return null;
        }

        List<DishRespVO> list1 = new ArrayList<DishRespVO>( list.size() );
        for ( DishDO dishDO : list ) {
            list1.add( convert( dishDO ) );
        }

        return list1;
    }

    @Override
    public DishDO convert(DishSaveReqVO reqVO) {
        if ( reqVO == null ) {
            return null;
        }

        DishDO dishDO = new DishDO();

        dishDO.setId( reqVO.getId() );
        dishDO.setName( reqVO.getName() );
        dishDO.setCategory( reqVO.getCategory() );
        dishDO.setImageUrl( reqVO.getImageUrl() );
        dishDO.setDescription( reqVO.getDescription() );

        return dishDO;
    }
}
