package com.cnsmash.pojo.ro;

import com.cnsmash.pojo.entity.UploadFile;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Set;

/**
 * @author guanhuan_li
 */
@Data
public class AddUserRo {

    private Long accountId;

    /**
     * 昵称
     */
    @NotBlank
    private String nickName;

    /**
     * 头像 {@link UploadFile#getId()}
     */
    private Long head;

    /**
     * 标签
     * List<String> json格式
     */
    private List<String> tagList;

    /**
     * 自我介绍
     */
    private String intro;

    /**
     * 链接方式
     * 1.有线 2.无线
     */
    private Integer linkType;

    /**
     * 被ban图
     * Set<String> 格式
     */
    private Set<String> banMapSet;

    /**
     * 服务器
     * 裸连1 港服2 日服4 字节流
     */
    private Integer server;

    /**
     * 分差
     */
    private Long scoreGap;

    /**
     * 主号ID
     */
    private Long mainId;

}
