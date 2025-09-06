package com.datahandler.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.datahandler.entity.TiktokRoomInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface TiktokRoomInfoMapper extends BaseMapper<TiktokRoomInfo> {

}