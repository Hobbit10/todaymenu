package com.today.menu.controller.admin.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "菜品创建/更新请求对象")
public class DishSaveReqVO {

    @Schema(description = "菜品ID(更新时必填)")
    private Long id;

    @NotBlank(message = "菜品名称不能为空")
    @Size(min = 2, max = 20, message = "菜品名称长度必须在2-20个字符之间")
    @Schema(description = "菜品名称")
    private String name;

    @NotNull(message = "菜品分类不能为空")
    @Schema(description = "菜品分类(1特色菜 2荤菜 3素菜 4酒水 5主食 6其他)")
    private Integer category;

    @NotBlank(message = "菜品图片不能为空")
    @Schema(description = "菜品图片URL")
    private String imageUrl;

    @Size(max = 500, message = "菜品说明长度不能超过500个字符")
    @Schema(description = "菜品说明")
    private String description;
}