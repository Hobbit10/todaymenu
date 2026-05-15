package com.today.menu.controller;

import com.today.menu.common.pojo.CommonResult;
import com.today.menu.common.pojo.PageResult;
import com.today.menu.controller.admin.vo.DishListReqVO;
import com.today.menu.controller.admin.vo.DishRespVO;
import com.today.menu.controller.admin.vo.DishSaveReqVO;
import com.today.menu.dal.dataobject.DishDO;
import com.today.menu.service.DishService;
import com.today.menu.convert.DishConvert;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/today-menu/dish")
@Validated
@Tag(name = "菜品管理")
public class DishController {

    @Resource
    private DishService dishService;

    @GetMapping("/list")
    @Operation(summary = "获取菜品列表")
    public CommonResult<List<DishRespVO>> getDishList(
            @Parameter(description = "菜品分类") @RequestParam(required = false) Integer category,
            @Parameter(description = "搜索关键词") @RequestParam(required = false) String keyword) {
        List<DishDO> list = dishService.getDishList(category, keyword);
        return CommonResult.success(DishConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(summary = "分页查询菜品")
    public CommonResult<PageResult<DishRespVO>> getDishPage(@Valid DishListReqVO reqVO) {
        PageResult<DishDO> pageResult = dishService.getDishPage(reqVO);
        return CommonResult.success(new PageResult<>(DishConvert.INSTANCE.convertList(pageResult.getList()), pageResult.getTotal()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取菜品详情")
    public CommonResult<DishRespVO> getDish(@PathVariable Long id) {
        DishDO dish = dishService.getDish(id);
        return CommonResult.success(DishConvert.INSTANCE.convert(dish));
    }

    @PostMapping
    @Operation(summary = "创建菜品")
    public CommonResult<Long> createDish(@Valid @RequestBody DishSaveReqVO reqVO) {
        Long id = dishService.createDish(reqVO);
        return CommonResult.success(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新菜品")
    public CommonResult<Boolean> updateDish(@PathVariable Long id, @Valid @RequestBody DishSaveReqVO reqVO) {
        reqVO.setId(id);
        dishService.updateDish(reqVO);
        return CommonResult.success(true);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除菜品")
    public CommonResult<Boolean> deleteDish(@PathVariable Long id) {
        dishService.deleteDish(id);
        return CommonResult.success(true);
    }
}