package com.xxw.shop.starter.minio;

import io.minio.Result;
import io.minio.messages.Bucket;
import io.minio.messages.DeleteError;
import io.minio.messages.Item;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MinioComponent {

    /**
     * 查看存储bucket是否存在
     *
     * @return boolean
     */
    boolean bucketExists(String bucketName);

    /**
     * 创建存储bucket
     *
     * @return Boolean
     */
    boolean makeBucket(String bucketName);

    /**
     * 删除存储bucket
     *
     * @return Boolean
     */
    boolean removeBucket(String bucketName);

    /**
     * 获取全部bucket
     */
    List<Bucket> getAllBuckets();

    /**
     * 文件上传
     *
     * @param file 文件
     * @return Boolean
     */
    boolean upload(MultipartFile file);

    /**
     * 预览图片
     *
     * @param fileName
     * @return
     */
    String preview(String fileName);

    /**
     * 文件下载
     *
     * @param fileName 文件名称
     * @param res      response
     * @return Boolean
     */
    void download(String fileName, HttpServletResponse res);

    /**
     * 查看文件对象
     *
     * @return 存储bucket内文件对象信息
     */
    List<Item> listObjects();

    /**
     * 删除
     *
     * @param fileName
     * @return
     * @throws Exception
     */
    boolean remove(String fileName);

    /**
     * 批量删除文件对象
     *
     * @param objects 对象名称集合
     */
    Iterable<Result<DeleteError>> removeObjects(List<String> objects);

}
