package com.datahandler.client;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class DlqService {

    @Value("${kafka.dlq.topic:anchor-dlq}")
    private String dlqTopic;

    @Autowired(required = false)
    private KafkaTemplate<String, String> kafkaTemplate;

    /**
     * 发送到死信队列
     */
    public void sendToDlq(String originalMessage, String errorReason) {
        try {
            Map<String, Object> dlqMessage = new HashMap<>();
            dlqMessage.put("originalMessage", originalMessage);
            dlqMessage.put("errorReason", errorReason);
            dlqMessage.put("timestamp", System.currentTimeMillis());
            dlqMessage.put("service", "anchor-service");

            String dlqPayload = JSONObject.toJSONString(dlqMessage);

            if (kafkaTemplate != null) {
                kafkaTemplate.send(dlqTopic, dlqPayload);
                log.info("消息已发送到死信队列: topic={}", dlqTopic);
            } else {
                // Kafka不可用时的降级处理
                saveToLocalBackup(originalMessage, errorReason);
            }

        } catch (Exception e) {
            log.error("发送死信队列失败: {}", e.getMessage());
            saveToLocalBackup(originalMessage, errorReason);
        }
    }

    /**
     * 本地备份
     */
    private void saveToLocalBackup(String message, String reason) {
        String backupDir = "kafka_dlq_backup";
        File dir = new File(backupDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String filename = String.format("%s/%s.txt",
                backupDir, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")));

        try (FileWriter writer = new FileWriter(filename, true)) {
            writer.write(String.format("%s | %s | %s%n",
                    LocalDateTime.now(), reason, message));
            log.info("消息已备份到本地文件: {}", filename);
        } catch (IOException e) {
            log.error("本地备份失败: {}", e.getMessage());
        }
    }
}