package com.example.task.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 定时任务失败用户记录实体类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DailyTaskFailUser {

    private Long id;           // 主键，自增

    private Long taskLogId;    // 对应 daily_task_log.id

    private String userId;     // 失败的 user_id

    private String roomId;     // 失败的 user_id

    private String reason;     // 失败原因，例如 "roomBo is null"

    private LocalDateTime createdAt;  // 记录创建时间
}
