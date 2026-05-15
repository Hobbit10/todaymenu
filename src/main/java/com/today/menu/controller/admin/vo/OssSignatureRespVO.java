package com.today.menu.controller.admin.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "OSS上传签名响应对象")
public class OssSignatureRespVO {

    @Schema(description = "访问密钥ID")
    private String accessKeyId;

    @Schema(description = "Policy策略")
    private String policy;

    @Schema(description = "签名")
    private String signature;

    @Schema(description = "OSS主机地址")
    private String host;

    @Schema(description = "文件存储目录")
    private String dir;

    @Schema(description = "过期时间(秒)")
    private Long expire;
}