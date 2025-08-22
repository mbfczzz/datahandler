package com.datahandler.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface DailyTaskFailUserMapper {

    int insertFailUser(@Param("taskLogId") Long taskLogId,
                       @Param("userId") String userId,
                       @Param("reason") String reason);

    int insertFailRoom(@Param("taskLogId") Long taskLogId,
                       @Param("userId") String userId,
                       @Param("reason") String reason);
}
