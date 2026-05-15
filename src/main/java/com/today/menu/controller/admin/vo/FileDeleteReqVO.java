package com.today.menu.controller.admin.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "文件删除请求对象")
public class FileDeleteReqVO {

    @NotBlank(message = "文件路径不能为空")
    @Schema(description = "文件路径/对象Key")
    private String objectKey;
}