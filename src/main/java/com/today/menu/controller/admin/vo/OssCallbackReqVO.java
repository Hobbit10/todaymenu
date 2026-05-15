package com.today.menu.controller.admin.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "OSS上传回调请求对象")
public class OssCallbackReqVO {

    @Schema(description = "文件名")
    private String filename;

    @Schema(description = "文件大小")
    private Long size;

    @Schema(description = "MIME类型")
    private String mimeType;

    @Schema(description = "文件路径")
    private String objectKey;
}