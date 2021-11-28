package com.cnsmash.pojo.entity;

import com.cnsmash.pojo.bean.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author liguanhuan
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UploadFile extends BaseEntity {

    private Long id;

    /** 文件上传名字 */
    private String fileName;

    /** 文件服务器名字 */
    private String realName;
    
    /** 服务器存储位置 */
    private String path;

    /** web图片地址 */
    private String src;
}
