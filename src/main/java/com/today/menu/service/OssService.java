package com.today.menu.service;

import com.today.menu.controller.admin.vo.OssCallbackRespVO;
import com.today.menu.controller.admin.vo.OssSignatureRespVO;

public interface OssService {

    OssSignatureRespVO getOssSignature();

    String getFileUrl(String objectKey);

    boolean deleteFile(String objectKey);

    OssCallbackRespVO handleUploadCallback(String objectKey, String filename, Long size);

    String uploadFile(byte[] data, String originalFilename);

    java.io.InputStream getFileStream(String objectKey);

    String extractObjectKey(String url);
}