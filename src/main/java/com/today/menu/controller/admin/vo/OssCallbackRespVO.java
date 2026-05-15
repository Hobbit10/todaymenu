package com.today.menu.controller.admin.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "OSS上传回调响应对象")
public class OssCallbackRespVO {

    @Schema(description = "文件完整URL")
    private String fileUrl;

    @Schema(description = "文件路径/对象Key")
    private String objectKey;

    @Schema(description = "文件名")
    private String filename;

    @Schema(description = "文件大小")
    private Long size;
}