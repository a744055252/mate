package com.cnsmash.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cnsmash.pojo.entity.Battle;
import com.cnsmash.pojo.entity.Quarter;
import com.cnsmash.pojo.ro.CustomQuarterListRo;
import com.cnsmash.pojo.ro.PageBattleRo;
import com.cnsmash.pojo.vo.PageBattleVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author guanhuan_li
 */
@Repository
public interface QuarterMapper extends BaseMapper<Quarter> {

    /**
     * 分页查询
     * @param page 分页数据
     * @param ro 请求
     * @return 结果
     */
    Page<Quarter> page(Page<Battle> page, @Param("ro") CustomQuarterListRo ro);

    Page<Quarter> pageAttend(Page<Battle> page, @Param("ro") CustomQuarterListRo ro, Long userId);

}
