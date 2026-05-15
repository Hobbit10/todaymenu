package com.today.menu.utils;

import cn.hutool.core.util.IdUtil;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import com.today.menu.config.OssConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import jakarta.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Date;
import java.io.InputStream;

import com.aliyun.oss.model.OSSObject;

@Slf4j
@Component
public class OssUtils {

    @Resource
    private OssConfig ossConfig;

    public String generateDir() {
        String dateDir = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        return ossConfig.getDirPrefix() + dateDir + "/" + IdUtil.fastSimpleUUID() + "/";
    }

    public String getHost() {
        return "https://" + ossConfig.getBucketName() + "." + ossConfig.getEndpoint();
    }

    public String generatePolicy(String dir) {
        try {
            long expireEndTime = System.currentTimeMillis() + ossConfig.getExpireTime() * 1000;
            Date expiration = new Date(expireEndTime);

            PolicyConditions policyConds = new PolicyConditions();
            policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);
            policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);

            OSS ossClient = new OSSClientBuilder().build(
                ossConfig.getEndpoint(),
                ossConfig.getAccessKeyId(),
                ossConfig.getAccessKeySecret()
            );

            String postPolicy = ossClient.generatePostPolicy(expiration, policyConds);
            byte[] binaryData = postPolicy.getBytes(StandardCharsets.UTF_8);
            String encodedPolicy = BinaryUtil.toBase64String(binaryData);
            String postSignature = ossClient.calculatePostSignature(postPolicy);

            ossClient.shutdown();

            return encodedPolicy;
        } catch (Exception e) {
            log.error("生成OSS Policy失败", e);
            throw new RuntimeException("生成OSS Policy失败");
        }
    }

    public String generateSignature(String policy) {
        try {
            OSS ossClient = new OSSClientBuilder().build(
                ossConfig.getEndpoint(),
                ossConfig.getAccessKeyId(),
                ossConfig.getAccessKeySecret()
            );

            String decodedPolicy = new String(Base64.getDecoder().decode(policy), StandardCharsets.UTF_8);
            String postSignature = ossClient.calculatePostSignature(decodedPolicy);

            ossClient.shutdown();

            return postSignature;
        } catch (Exception e) {
            log.error("生成OSS Signature失败", e);
            throw new RuntimeException("生成OSS Signature失败");
        }
    }

    public String uploadFile(byte[] data, String objectKey) {
        try {
            OSS ossClient = new OSSClientBuilder().build(
                ossConfig.getEndpoint(),
                ossConfig.getAccessKeyId(),
                ossConfig.getAccessKeySecret()
            );

            ossClient.putObject(ossConfig.getBucketName(), objectKey, new java.io.ByteArrayInputStream(data));
            ossClient.shutdown();

            String fileUrl = "https://" + ossConfig.getBucketName() + "." + ossConfig.getEndpoint() + "/" + objectKey;
            log.info("OSS上传成功: objectKey={}, url={}", objectKey, fileUrl);
            return fileUrl;
        } catch (Exception e) {
            log.error("OSS上传文件失败: objectKey={}", objectKey, e);
            throw new RuntimeException("OSS上传文件失败");
        }
    }

    public boolean deleteObject(String objectKey) {
        try {
            OSS ossClient = new OSSClientBuilder().build(
                ossConfig.getEndpoint(),
                ossConfig.getAccessKeyId(),
                ossConfig.getAccessKeySecret()
            );

            ossClient.deleteObject(ossConfig.getBucketName(), objectKey);
            ossClient.shutdown();

            return true;
        } catch (Exception e) {
            log.error("删除OSS文件失败: objectKey={}", objectKey, e);
            return false;
        }
    }

    public boolean doesObjectExist(String objectKey) {
        try {
            OSS ossClient = new OSSClientBuilder().build(
                ossConfig.getEndpoint(),
                ossConfig.getAccessKeyId(),
                ossConfig.getAccessKeySecret()
            );

            boolean exists = ossClient.doesObjectExist(ossConfig.getBucketName(), objectKey);
            ossClient.shutdown();

            return exists;
        } catch (Exception e) {
            log.error("检查OSS文件是否存在失败: objectKey={}", objectKey, e);
            return false;
        }
    }

    public InputStream getFileStream(String objectKey) {
        try {
            OSS ossClient = new OSSClientBuilder().build(
                ossConfig.getEndpoint(),
                ossConfig.getAccessKeyId(),
                ossConfig.getAccessKeySecret()
            );

            OSSObject ossObject = ossClient.getObject(ossConfig.getBucketName(), objectKey);
            InputStream inputStream = ossObject.getObjectContent();
            byte[] data = inputStream.readAllBytes();
            inputStream.close();
            ossClient.shutdown();

            return new java.io.ByteArrayInputStream(data);
        } catch (Exception e) {
            log.error("获取OSS文件流失败: objectKey={}", objectKey, e);
            throw new RuntimeException("获取OSS文件流失败");
        }
    }

    public String extractObjectKey(String url) {
        if (url == null || url.isEmpty()) return url;
        if (!url.startsWith("http")) return url;
        String prefix = "https://" + ossConfig.getBucketName() + "." + ossConfig.getEndpoint() + "/";
        if (url.startsWith(prefix)) {
            return url.substring(prefix.length());
        }
        return url;
    }
}