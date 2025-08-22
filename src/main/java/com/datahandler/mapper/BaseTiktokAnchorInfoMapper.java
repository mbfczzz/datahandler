package com.datahandler.mapper;

import com.datahandler.entity.BaseTiktokAnchorInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface BaseTiktokAnchorInfoMapper {

    List<BaseTiktokAnchorInfo> getBaseTiktokAnchorInfo();

}