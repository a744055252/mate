package com.cnsmash.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cnsmash.pojo.entity.Freeroom;
import com.cnsmash.pojo.vo.FreeroomThumbnailVo;
import com.cnsmash.pojo.vo.FreeroomVo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FreeroomMapper extends BaseMapper<Freeroom> {

    /**
     * 关闭用户ID对应所有房间
     */
    void updatePlayerRoomShutdown(Long host);

    /**
     * 查询房间列表
     * @param server 服务器
     * @param maximum 人数上限
     * @return
     */
    List<FreeroomVo> list(String server, Integer maximum);

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

}
