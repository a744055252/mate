package com.cnsmash.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cnsmash.pojo.entity.UploadFile;

import java.util.Optional;

/**
 * @author guanhuan_li
 */
public interface UploadFileMapper extends BaseMapper<UploadFile> {

    /**
     * 通过文件的服务器名字获取上传文件对象
     * @param realName 服务器名字{@link UploadFile#getRealName()}
     * @return 文件对象的Optional对象
     */
    Optional<UploadFile> findByRealName(String realName);

}
