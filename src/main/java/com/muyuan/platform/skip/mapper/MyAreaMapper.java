package com.muyuan.platform.skip.mapper;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.muyuan.platform.skip.entity.db.MyArea;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 站点区域表
 * 
 * @author fanwenwu
 * @email 
 * @date 2018-11-23 15:43:53
 */
@Repository
public interface MyAreaMapper extends BaseMapper<MyArea> {
    @Select("select pots from my_area")
	List<String> selectAllPots();
}
