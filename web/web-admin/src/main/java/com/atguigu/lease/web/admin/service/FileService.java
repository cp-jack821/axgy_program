package com.atguigu.lease.web.admin.service;

import io.minio.errors.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;


public interface FileService {
    String upload(MultipartFile multipartFile) throws Exception;
}
