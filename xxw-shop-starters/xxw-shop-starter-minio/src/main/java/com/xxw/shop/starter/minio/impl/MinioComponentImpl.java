package com.xxw.shop.starter.minio.impl;

import com.xxw.shop.starter.minio.MinioComponent;
import io.minio.*;
import io.minio.http.Method;
import io.minio.messages.Bucket;
import io.minio.messages.DeleteError;
import io.minio.messages.DeleteObject;
import io.minio.messages.Item;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component("minioComponent")
public class MinioComponentImpl implements MinioComponent {

    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(MinioComponentImpl.class);

    @Value("${minio.bucketName}")
    private String bucketName;

    @Resource
    private MinioClient minioClient;

    @Override
    public boolean bucketExists(String bucketName) {
        try {
            return minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        } catch (Exception e) {
            logger.error("minio_bucket_exists_exception", e);
            return false;
        }
    }

    @Override
    public boolean makeBucket(String bucketName) {
        try {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            return true;
        } catch (Exception e) {
            logger.error("minio_make_bucket_exception", e);
            return false;
        }
    }

    @Override
    public boolean removeBucket(String bucketName) {
        try {
            minioClient.removeBucket(RemoveBucketArgs.builder().bucket(bucketName).build());
            return true;
        } catch (Exception e) {
            logger.error("minio_remove_bucket_exception", e);
            return false;
        }
    }

    @Override
    public List<Bucket> getAllBuckets() {
        try {
            return minioClient.listBuckets();
        } catch (Exception e) {
            logger.error("minio_get_all_buckets_exception", e);
            return null;
        }
    }

    @Override
    public boolean upload(MultipartFile file, String fileName) {
        try {
            if (!bucketExists(bucketName)) {
                makeBucket(bucketName);
            }
            PutObjectArgs objectArgs =
                    PutObjectArgs.builder().bucket(bucketName).object(fileName).stream(file.getInputStream(),
                            file.getSize(), -1).contentType(file.getContentType()).build();
            //文件名称相同会覆盖
            minioClient.putObject(objectArgs);
            return true;
        } catch (Exception e) {
            logger.error("minio_upload_exception", e);
            return false;
        }
    }

    @Override
    public String preview(String fileName) {
        // 查看文件地址
        GetPresignedObjectUrlArgs build =
                GetPresignedObjectUrlArgs.builder().bucket(bucketName).object(fileName).method(Method.GET).build();
        try {
            return minioClient.getPresignedObjectUrl(build);
        } catch (Exception e) {
            logger.error("minio_preview_exception", e);
            return null;
        }
    }

    @Override
    public String getPresignedObjectUrl(String fileName) {
        try {
            return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder().bucket(bucketName).object(fileName).expiry(10, TimeUnit.MINUTES).method(Method.PUT).build());
        } catch (Exception e) {
            logger.error("minio_get_presigned_object_Url_exception", e);
            return null;
        }
    }

    @Override
    public void download(String fileName, HttpServletResponse res) {
        GetObjectArgs objectArgs = GetObjectArgs.builder().bucket(bucketName).object(fileName).build();
        try (GetObjectResponse response = minioClient.getObject(objectArgs)) {
            byte[] buf = new byte[1024];
            int len;
            try (FastByteArrayOutputStream os = new FastByteArrayOutputStream()) {
                while ((len = response.read(buf)) != -1) {
                    os.write(buf, 0, len);
                }
                os.flush();
                byte[] bytes = os.toByteArray();
                res.setCharacterEncoding("utf-8");
                //设置强制下载不打开
                //res.setContentType("application/force-download");
                res.addHeader("Content-Disposition", "attachment;fileName=" + fileName);
                try (ServletOutputStream stream = res.getOutputStream()) {
                    stream.write(bytes);
                    stream.flush();
                }
            }
        } catch (Exception e) {
            logger.error("minio_download_exception", e);
        }
    }

    @Override
    public List<Item> listObjects() {
        Iterable<Result<Item>> results = minioClient.listObjects(ListObjectsArgs.builder().bucket(bucketName).build());
        List<Item> items = new ArrayList<>();
        try {
            for (Result<Item> result : results) {
                items.add(result.get());
            }
            return items;
        } catch (Exception e) {
            logger.error("minio_list_objects_exception", e);
            return null;
        }
    }

    @Override
    public boolean remove(String fileName) {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucketName).object(fileName).build());
            return true;
        } catch (Exception e) {
            logger.error("minio_remove_exception", e);
            return false;
        }
    }

    @Override
    public Iterable<Result<DeleteError>> removeObjects(List<String> objects) {
        try {
            List<DeleteObject> dos = objects.stream().map(DeleteObject::new).collect(Collectors.toList());
            return minioClient.removeObjects(RemoveObjectsArgs.builder().bucket(bucketName).objects(dos).build());
        } catch (Exception e) {
            logger.error("minio_remove_objects_exception", e);
            return null;
        }
    }
}
