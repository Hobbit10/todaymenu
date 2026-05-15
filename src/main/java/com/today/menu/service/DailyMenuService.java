package com.today.menu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.today.menu.controller.admin.vo.DailyMenuRespVO;
import com.today.menu.dal.dataobject.DailyMenuDO;
import java.time.LocalDate;
import java.util.List;

public interface DailyMenuService extends IService<DailyMenuDO> {

    List<DailyMenuRespVO> getDailyMenuByDate(LocalDate menuDate);

    void addDishToDailyMenu(LocalDate menuDate, Long dishId);

    void removeDishFromDailyMenu(LocalDate menuDate, Long dishId);

    List<LocalDate> getMenuDates(LocalDate startDate, LocalDate endDate);
}