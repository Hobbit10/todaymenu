package com.today.menu.controller.admin.vo;

import com.today.menu.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "菜品列表查询请求对象")
public class DishListReqVO extends PageParam {

    @Schema(description = "菜品分类(1特色菜 2荤菜 3素菜 4酒水 5主食 6其他)")
    private Integer category;

    @Schema(description = "搜索关键词")
    private String keyword;
}