package com.today.menu.controller.admin.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDate;

@Data
@Schema(description = "每日菜单响应对象")
public class DailyMenuRespVO {

    @Schema(description = "菜单ID")
    private Long id;

    @Schema(description = "业务编码")
    private String code;

    @Schema(description = "菜单日期")
    private LocalDate menuDate;

    @Schema(description = "菜品ID")
    private Long dishId;

    @Schema(description = "菜品名称")
    private String dishName;

    @Schema(description = "菜品分类")
    private Integer category;

    @Schema(description = "菜品分类名称")
    private String categoryName;

    @Schema(description = "菜品图片URL")
    private String imageUrl;

    @Schema(description = "菜品说明")
    private String dishDescription;

    @Schema(description = "排序")
    private Integer sort;
}