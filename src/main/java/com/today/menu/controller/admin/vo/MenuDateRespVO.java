package com.today.menu.controller.admin.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDate;

@Data
@Schema(description = "有菜单的日期响应对象")
public class MenuDateRespVO {

    @Schema(description = "菜单日期")
    private LocalDate menuDate;
}