package com.today.menu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.today.menu.common.pojo.PageResult;
import com.today.menu.controller.admin.vo.DishListReqVO;
import com.today.menu.controller.admin.vo.DishSaveReqVO;
import com.today.menu.dal.dataobject.DishDO;
import java.util.List;

public interface DishService extends IService<DishDO> {

    DishDO getDish(Long id);

    Long createDish(DishSaveReqVO reqVO);

    void updateDish(DishSaveReqVO reqVO);

    void deleteDish(Long id);

    List<DishDO> getDishList(Integer category, String keyword);

    PageResult<DishDO> getDishPage(DishListReqVO reqVO);

    void validateDishExists(Long id);
}