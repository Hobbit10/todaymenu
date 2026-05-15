package com.today.menu.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.today.menu.common.pojo.PageResult;
import com.today.menu.controller.admin.vo.DishListReqVO;
import com.today.menu.controller.admin.vo.DishSaveReqVO;
import com.today.menu.dal.dataobject.DishDO;
import com.today.menu.exception.ErrorCodeConstants;
import com.today.menu.exception.ServiceException;
import com.today.menu.mapper.DishMapper;
import com.today.menu.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import java.util.List;

@Service
@Validated
@Slf4j
public class DishServiceImpl extends ServiceImpl<DishMapper, DishDO> implements DishService {

    @Override
    public DishDO getDish(Long id) {
        return this.getById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createDish(DishSaveReqVO reqVO) {
        DishDO dish = new DishDO();
        dish.setName(reqVO.getName());
        dish.setCategory(reqVO.getCategory());
        dish.setImageUrl(reqVO.getImageUrl());
        dish.setDescription(reqVO.getDescription());
        dish.setCode("DISH_" + IdUtil.getSnowflakeNextIdStr());
        dish.setStatus(0);
        dish.setSort(0);
        this.save(dish);
        return dish.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateDish(DishSaveReqVO reqVO) {
        validateDishExists(reqVO.getId());
        DishDO dish = new DishDO();
        dish.setId(reqVO.getId());
        dish.setName(reqVO.getName());
        dish.setCategory(reqVO.getCategory());
        dish.setImageUrl(reqVO.getImageUrl());
        dish.setDescription(reqVO.getDescription());
        this.updateById(dish);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteDish(Long id) {
        validateDishExists(id);
        this.removeById(id);
    }

    @Override
    public List<DishDO> getDishList(Integer category, String keyword) {
        LambdaQueryWrapper<DishDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DishDO::getStatus, 0);
        if (category != null) {
            wrapper.eq(DishDO::getCategory, category);
        }
        if (keyword != null && !keyword.isEmpty()) {
            wrapper.like(DishDO::getName, keyword);
        }
        wrapper.orderByDesc(DishDO::getCreatedon);
        return this.list(wrapper);
    }

    @Override
    public PageResult<DishDO> getDishPage(DishListReqVO reqVO) {
        LambdaQueryWrapper<DishDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DishDO::getStatus, 0);
        if (reqVO.getCategory() != null) {
            wrapper.eq(DishDO::getCategory, reqVO.getCategory());
        }
        if (reqVO.getKeyword() != null && !reqVO.getKeyword().isEmpty()) {
            wrapper.like(DishDO::getName, reqVO.getKeyword());
        }
        wrapper.orderByDesc(DishDO::getCreatedon);
        return this.baseMapper.selectPage(reqVO, wrapper);
    }

    @Override
    public void validateDishExists(Long id) {
        if (id == null) {
            return;
        }
        DishDO dish = this.getById(id);
        if (dish == null) {
            throw new ServiceException(ErrorCodeConstants.DISH_NOT_FOUND, "菜品不存在");
        }
    }
}