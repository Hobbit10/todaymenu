package com.today.menu.controller;

import com.today.menu.common.pojo.CommonResult;
import com.today.menu.controller.admin.vo.DailyMenuRespVO;
import com.today.menu.service.DailyMenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api/today-menu/daily-menu")
@Validated
@Tag(name = "每日菜单管理")
public class DailyMenuController {

    @Resource
    private DailyMenuService dailyMenuService;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @GetMapping("/{date}")
    @Operation(summary = "获取指定日期菜单")
    public CommonResult<List<DailyMenuRespVO>> getDailyMenuByDate(@PathVariable String date) {
        LocalDate menuDate = LocalDate.parse(date, DATE_FORMATTER);
        List<DailyMenuRespVO> list = dailyMenuService.getDailyMenuByDate(menuDate);
        return CommonResult.success(list);
    }

    @PostMapping("/{date}/dish/{dishId}")
    @Operation(summary = "添加菜品到今日菜单")
    public CommonResult<Boolean> addDishToDailyMenu(
            @PathVariable String date,
            @PathVariable Long dishId) {
        LocalDate menuDate = LocalDate.parse(date, DATE_FORMATTER);
        dailyMenuService.addDishToDailyMenu(menuDate, dishId);
        return CommonResult.success(true);
    }

    @DeleteMapping("/{date}/dish/{dishId}")
    @Operation(summary = "从今日菜单移除菜品")
    public CommonResult<Boolean> removeDishFromDailyMenu(
            @PathVariable String date,
            @PathVariable Long dishId) {
        LocalDate menuDate = LocalDate.parse(date, DATE_FORMATTER);
        dailyMenuService.removeDishFromDailyMenu(menuDate, dishId);
        return CommonResult.success(true);
    }

    @GetMapping("/dates")
    @Operation(summary = "获取有菜单的日期列表")
    public CommonResult<List<LocalDate>> getMenuDates(
            @Parameter(description = "开始日期") @RequestParam String startDate,
            @Parameter(description = "结束日期") @RequestParam String endDate) {
        LocalDate start = LocalDate.parse(startDate, DATE_FORMATTER);
        LocalDate end = LocalDate.parse(endDate, DATE_FORMATTER);
        List<LocalDate> dates = dailyMenuService.getMenuDates(start, end);
        return CommonResult.success(dates);
    }
}