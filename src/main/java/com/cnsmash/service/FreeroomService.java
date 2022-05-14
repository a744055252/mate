package com.cnsmash.service;

import com.cnsmash.pojo.ro.AddFreeroomRo;
import com.cnsmash.pojo.ro.ListFreeroomRo;
import com.cnsmash.pojo.vo.FreeroomThumbnailVo;
import com.cnsmash.pojo.vo.FreeroomVo;

import java.util.List;

/**
 * @author Toddhead
 */
public interface FreeroomService {

    /**
     * 添加房间
     * @param ro 请求体
     * @param host 开房者ID
     */
    void add(AddFreeroomRo ro, Long host);

    /**
     * 更新房间信息
     * @param ro 请求体
     * @param host 开房者ID
     */
    void update(AddFreeroomRo ro, Long host);

    /**
     * 获取房间列表
     * @param ro 请求体
     * @return
     */
    List<FreeroomVo> list(ListFreeroomRo ro);

    /**
     * 查询房间详情
     * @param id
     * @return
     */
    FreeroomVo detail(Long id);

    /**
     * 根据用户ID查房间详情
     * @param id 用户ID
     * @return 房间详情
     */
    FreeroomVo detailByUser(Long id);

    /**
     * 按照用户ID关闭房间
     * @param id 用户ID
     */
    void shutdownRoomByPlayer(Long id);

}
