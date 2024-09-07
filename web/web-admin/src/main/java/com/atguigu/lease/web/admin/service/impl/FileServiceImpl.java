package com.atguigu.lease.web.admin.service.impl;

import com.atguigu.lease.common.minio.MinioProperties;
import com.atguigu.lease.web.admin.service.FileService;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private MinioProperties minioProperties;

    @Autowired
    private MinioClient minioClient;

    @Override
    public String upload(MultipartFile file) throws Exception {
        // 获取原始文件名
        String originalFilename = file.getOriginalFilename();

        //创建新的文件名
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        originalFilename = sdf.format(new Date()) + "/" + UUID.randomUUID().toString().replaceAll("-", "") + "_" + originalFilename;

        minioClient.putObject(PutObjectArgs.builder()
                .bucket(minioProperties.getBucketName())
                .stream(file.getInputStream(), file.getSize(), -1)
                .object(originalFilename)
                .contentType(file.getContentType())
                .build()
        );
        return String.format("%s/%s",minioProperties.getEndpoint(),minioProperties.getBucketName() + "/" + originalFilename);
    }
}
