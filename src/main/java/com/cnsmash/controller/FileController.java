package com.cnsmash.controller;

import com.cnsmash.exception.CodeException;
import com.cnsmash.exception.ErrorCode;
import com.cnsmash.pojo.bean.ReposResult;
import com.cnsmash.pojo.entity.UploadFile;
import com.cnsmash.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author liguanhuan
 */
@RestController
@RequestMapping("/file")
@Slf4j
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping("/upload")
    public ReposResult<Long> upload(@RequestParam("file") MultipartFile file) {

        if (file.isEmpty()) {
            throw new CodeException(ErrorCode.FILE_NULL_ERROR, "文件不能为空");
        }

        long fileId = fileService.upload(file);
        return ReposResult.ok(fileId);
    }

    @GetMapping
    public ReposResult<UploadFile> get(@RequestParam Long id){
        return ReposResult.ok(fileService.findById(id));
    }
}
