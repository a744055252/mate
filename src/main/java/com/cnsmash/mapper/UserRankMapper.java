package com.cnsmash.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cnsmash.pojo.entity.UserRank;
import com.cnsmash.pojo.ro.PageFighterRo;
import com.cnsmash.pojo.vo.UserDetail;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author guanhuan_li
 */
@Repository
public interface UserRankMapper extends BaseMapper<UserRank> {

    /**
     * 分页查询角色排名
     * @param page 分页
     * @param ro 查询条件
     * @return 结果
     */
    Page<UserDetail> pageFighter(IPage<UserRank> page, @Param("ro") PageFighterRo ro);

}
