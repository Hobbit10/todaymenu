package com.today.menu.controller;

import com.today.menu.common.pojo.CommonResult;
import com.today.menu.controller.admin.vo.FileDeleteReqVO;
import com.today.menu.controller.admin.vo.OssCallbackRespVO;
import com.today.menu.controller.admin.vo.OssSignatureRespVO;
import com.today.menu.service.OssService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import jakarta.annotation.Resource;
import java.io.InputStream;

@Slf4j
@RestController
@RequestMapping("/api/today-menu/oss")
@Validated
@Tag(name = "OSS文件上传")
public class OssController {

    @Resource
    private OssService ossService;

    @PostMapping("/upload")
    @Operation(summary = "上传文件到OSS（后端代理）")
    public CommonResult<String> uploadFile(
            @Parameter(description = "上传的文件") @RequestParam("file") MultipartFile file) {
        try {
            String fileUrl = ossService.uploadFile(file.getBytes(), file.getOriginalFilename());
            return CommonResult.success(fileUrl);
        } catch (Exception e) {
            log.error("文件上传失败", e);
            return CommonResult.error(500, "文件上传失败");
        }
    }

    @GetMapping("/image")
    @Operation(summary = "代理获取OSS图片（私有Bucket安全访问）")
    public ResponseEntity<byte[]> getImage(
            @Parameter(description = "文件完整URL或objectKey") @RequestParam String key) {
        try {
            String objectKey = ossService.extractObjectKey(key);
            log.info("代理获取图片: key={}, objectKey={}", key, objectKey);

            InputStream stream = ossService.getFileStream(objectKey);
            byte[] data = stream.readAllBytes();

            String contentType = "image/jpeg";
            if (objectKey.endsWith(".png")) contentType = "image/png";
            if (objectKey.endsWith(".gif")) contentType = "image/gif";
            if (objectKey.endsWith(".webp")) contentType = "image/webp";

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CACHE_CONTROL, "max-age=3600")
                    .body(data);
        } catch (Exception e) {
            log.error("获取图片失败: key={}", key, e);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/signature")
    @Operation(summary = "获取OSS上传签名")
    public CommonResult<OssSignatureRespVO> getOssSignature() {
        OssSignatureRespVO respVO = ossService.getOssSignature();
        return CommonResult.success(respVO);
    }

    @GetMapping("/url")
    @Operation(summary = "获取文件完整URL")
    public CommonResult<String> getFileUrl(
            @Parameter(description = "文件路径/对象Key") @RequestParam String objectKey) {
        String fileUrl = ossService.getFileUrl(objectKey);
        return CommonResult.success(fileUrl);
    }

    @PostMapping("/delete")
    @Operation(summary = "删除OSS文件")
    public CommonResult<Boolean> deleteFile(@Valid @RequestBody FileDeleteReqVO reqVO) {
        boolean result = ossService.deleteFile(reqVO.getObjectKey());
        return CommonResult.success(result);
    }

    @PostMapping("/callback")
    @Operation(summary = "OSS上传回调处理")
    public CommonResult<OssCallbackRespVO> handleUploadCallback(
            @Parameter(description = "文件路径") @RequestParam String objectKey,
            @Parameter(description = "文件名") @RequestParam(required = false) String filename,
            @Parameter(description = "文件大小") @RequestParam(required = false) Long size) {
        OssCallbackRespVO respVO = ossService.handleUploadCallback(objectKey, filename, size);
        return CommonResult.success(respVO);
    }
}