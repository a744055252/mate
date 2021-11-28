package com.cnsmash.service;

import com.cnsmash.pojo.entity.UploadFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author liguanhuan
 */
public interface FileService {

    /**
     * 上传文件
     * @param file 文件对象
     * @return 文件id
     */
    Long upload(MultipartFile file);

    /**
     * 获取文件对象
     * @param id 文件id
     * @return 文件
     */
    UploadFile findById(Long id);

    /**
     * 批量获取文件
     * @param ids 文件id
     * @return 文件列表
     */
    List<UploadFile> list(List<Long> ids);
}
