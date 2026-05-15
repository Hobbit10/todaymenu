package com.today.menu.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.today.menu.controller.admin.vo.DailyMenuRespVO;
import com.today.menu.dal.dataobject.DailyMenuDO;
import com.today.menu.dal.dataobject.DishDO;
import com.today.menu.exception.ErrorCodeConstants;
import com.today.menu.exception.ServiceException;
import com.today.menu.mapper.DailyMenuMapper;
import com.today.menu.mapper.DishMapper;
import com.today.menu.service.DailyMenuService;
import com.today.menu.service.DishService;
import com.today.menu.convert.DailyMenuConvert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import jakarta.annotation.Resource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Validated
@Slf4j
public class DailyMenuServiceImpl extends ServiceImpl<DailyMenuMapper, DailyMenuDO> implements DailyMenuService {

    @Resource
    private DishMapper dishMapper;

    @Resource
    private DishService dishService;

    @Override
    public List<DailyMenuRespVO> getDailyMenuByDate(LocalDate menuDate) {
        List<DailyMenuDO> dailyMenuList = getBaseMapper().selectListByMenuDate(menuDate);
        if (dailyMenuList.isEmpty()) {
            return new ArrayList<>();
        }
        List<Long> dishIds = dailyMenuList.stream().map(DailyMenuDO::getDishId).toList();
        List<DishDO> dishList = dishMapper.selectByIds(dishIds);
        return DailyMenuConvert.INSTANCE.convertList(dailyMenuList, dishList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addDishToDailyMenu(LocalDate menuDate, Long dishId) {
        dishService.validateDishExists(dishId);
        DailyMenuDO existingMenu = getBaseMapper().selectByMenuDateAndDishId(menuDate, dishId);
        if (existingMenu != null) {
            throw new ServiceException(ErrorCodeConstants.DISH_ALREADY_IN_MENU, "该菜品已存在于今日菜单");
        }
        DailyMenuDO dailyMenu = new DailyMenuDO();
        dailyMenu.setMenuDate(menuDate);
        dailyMenu.setDishId(dishId);
        dailyMenu.setCode("MENU_" + IdUtil.fastSimpleUUID());
        dailyMenu.setSort(0);
        save(dailyMenu);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeDishFromDailyMenu(LocalDate menuDate, Long dishId) {
        getBaseMapper().deleteByMenuDateAndDishId(menuDate, dishId);
    }

    @Override
    public List<LocalDate> getMenuDates(LocalDate startDate, LocalDate endDate) {
        return getBaseMapper().selectMenuDates(startDate, endDate);
    }
}