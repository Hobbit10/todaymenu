package com.today.menu.controller.admin.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Schema(description = "菜品响应对象")
public class DishRespVO {

    @Schema(description = "菜品ID")
    private Long id;

    @Schema(description = "业务编码")
    private String code;

    @Schema(description = "菜品名称")
    private String name;

    @Schema(description = "菜品分类(1特色菜 2荤菜 3素菜 4酒水 5主食 6其他)")
    private Integer category;

    @Schema(description = "菜品分类名称")
    private String categoryName;

    @Schema(description = "菜品图片URL")
    private String imageUrl;

    @Schema(description = "菜品说明")
    private String description;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "状态(0正常 1停用)")
    private Integer status;

    @Schema(description = "创建时间")
    private LocalDateTime createdon;
}