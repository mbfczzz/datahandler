package com.datahandler.mapper;

import com.datahandler.entity.DailyTaskLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface DailyTaskLogMapper {

    /**
     * 插入一条定时任务日志
     */
    int insert(DailyTaskLog log);

    /**
     * 根据 id 更新任务执行完成后的信息
     */
    int updateById(DailyTaskLog log);

    /**
     * 根据任务名称获取最新一条任务记录（可选）
     */
    DailyTaskLog selectLatestByTaskName(@Param("taskName") String taskName);

}
