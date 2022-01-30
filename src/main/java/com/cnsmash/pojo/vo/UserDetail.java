package com.cnsmash.pojo.vo;

import com.cnsmash.pojo.entity.UploadFile;
import com.cnsmash.pojo.entity.UserFighter;
import com.cnsmash.pojo.entity.UserRank;
import lombok.Data;

import java.util.List;

/**
 * @author guanhuan_li
 */
@Data
public class UserDetail {

    private Long id;

    /**
     * 主账号id
     */
    private Long accountId;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 头像 {@link UploadFile#getId()}
     */
    private Long head;

    private String headSrc;

    private String code;

    /**
     * 队伍
     */
    private Long teamId;

    /**
     * 标签
     * List<String> json格式
     */
    private String tagJson;

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
     * 服务器
     * 裸连zh 日服jp 港服hk 美服usa
     */
    private Integer server;

    /**
     * 分差
     */
    private Long scoreGap;

    /**
     * 排位分数
     */
    private UserRank userRank;

    /**
     * 使用斗士
     */
    private List<UserFighter> userFighterList;

    /**
     * 使用斗士（字符串）
     */
    private String fighters;

    /**
     * ban图
     */
    private String banMap;

    /**
     * 分数
     */
    private String score;
}
