package com.datahandler.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.datahandler.entity.TiktokAnchorInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface TiktokAnchorInfoMapper extends BaseMapper<TiktokAnchorInfo> {

}