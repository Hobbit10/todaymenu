package com.today.menu.service.impl;

import com.today.menu.config.OssConfig;
import com.today.menu.controller.admin.vo.OssCallbackRespVO;
import com.today.menu.controller.admin.vo.OssSignatureRespVO;
import com.today.menu.service.OssService;
import com.today.menu.utils.OssUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import jakarta.annotation.Resource;

@Service
@Validated
@Slf4j
public class OssServiceImpl implements OssService {

    @Resource
    private OssConfig ossConfig;

    @Resource
    private OssUtils ossUtils;

    @Override
    public OssSignatureRespVO getOssSignature() {
        String dir = ossUtils.generateDir();
        String policy = ossUtils.generatePolicy(dir);
        String signature = ossUtils.generateSignature(policy);
        String host = ossUtils.getHost();

        log.info("OSS签名生成: host={}, dir={}", host, dir);

        OssSignatureRespVO respVO = new OssSignatureRespVO();
        respVO.setAccessKeyId(ossConfig.getAccessKeyId());
        respVO.setPolicy(policy);
        respVO.setSignature(signature);
        respVO.setHost(host);
        respVO.setDir(dir);
        respVO.setExpire(ossConfig.getExpireTime());

        return respVO;
    }

    @Override
    public String getFileUrl(String objectKey) {
        return "https://" + ossConfig.getBucketName() + "." + ossConfig.getEndpoint() + "/" + objectKey;
    }

    @Override
    public boolean deleteFile(String objectKey) {
        return ossUtils.deleteObject(objectKey);
    }

    @Override
    public OssCallbackRespVO handleUploadCallback(String objectKey, String filename, Long size) {
        log.info("OSS上传回调: objectKey={}, filename={}, size={}", objectKey, filename, size);

        OssCallbackRespVO respVO = new OssCallbackRespVO();
        respVO.setFileUrl(getFileUrl(objectKey));
        respVO.setObjectKey(objectKey);
        respVO.setFilename(filename);
        respVO.setSize(size);

        return respVO;
    }

    @Override
    public String uploadFile(byte[] data, String originalFilename) {
        String dir = ossUtils.generateDir();
        String extension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        } else {
            extension = ".jpg";
        }
        String objectKey = dir + "dish_" + System.currentTimeMillis() + extension;
        return ossUtils.uploadFile(data, objectKey);
    }

    @Override
    public java.io.InputStream getFileStream(String objectKey) {
        return ossUtils.getFileStream(objectKey);
    }

    @Override
    public String extractObjectKey(String url) {
        return ossUtils.extractObjectKey(url);
    }
}