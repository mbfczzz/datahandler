package com.datahandler.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DailyTaskLog {

    private Long id;  // 主键，自增

    private String taskName;  // 任务名称，例如 DailyTask

    private LocalDateTime startTime;  // 任务开始时间

    private LocalDateTime endTime;  // 任务结束时间

    private Long durationMs;  // 执行耗时（毫秒）

    private Integer totalRecords;  // 总处理记录数

    private Integer successRecords;  // 成功记录数

    private Integer failRecords;  // 失败记录数

    private String status;  // 任务状态 SUCCESS / PARTIAL_FAIL / FAIL

    private String errorMessage;  // 异常信息

    private LocalDateTime createdAt;  // 记录创建时间

    private String type;
}
