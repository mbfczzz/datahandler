package com.datahandler.client;

import com.alibaba.fastjson.JSONObject;
import com.datahandler.entity.TiktokAnchorInfo;
import com.datahandler.mapper.TiktokAnchorInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@Slf4j
public class AnchorConsumerService {

    @Autowired
    private KafkaRetryService kafkaRetryService;

    @Autowired
    private TiktokAnchorInfoMapper tiktokAnchorInfoMapper;

    /**
     * 封装后的消费方法 - 方案1: 使用RetryTemplate
     */
    @KafkaListener(topics = "anchor", groupId = "anchor-consumer-1")
    public void consumeMessageWithRetry(String message) {
        log.info("收到消息: topic=anchor, message={}", message);

        boolean success = kafkaRetryService.processWithRetry(message);

        if (success) {
            log.info("消息成功处理完成: topic=anchor");
        } else {
            log.error("消息处理失败，已进入死信队列: topic=anchor");
        }
    }

    /**
     * 封装后的消费方法 - 方案2: 使用@Retryable注解（更简洁）
     */
    @KafkaListener(topics = "anchor", groupId = "anchor-consumer-2")
    @Retryable(
            value = {Exception.class},
            maxAttempts = 3,
            backoff = @Backoff(delay = 1000, multiplier = 1.5)
    )
    public void consumeMessageWithAnnotation(String message) {
        log.info("收到消息: topic=anchor, message={}", message);

        TiktokAnchorInfo tiktokAnchorInfo = JSONObject.parseObject(message, TiktokAnchorInfo.class);

        // 数据验证
        if (!StringUtils.isEmpty(tiktokAnchorInfo.getUniqueId())) {
            throw new IllegalArgumentException("主播名称不能为空");
        }

        // 插入数据库
        int result = tiktokAnchorInfoMapper.insert(tiktokAnchorInfo);

        if (result <= 0) {
            throw new RuntimeException("数据库插入失败");
        }

        log.info("消息成功处理: topic=anchor");
    }

    /**
     * 重试失败的回调
     */
    @Recover
    public void recover(Exception e, String message) {
        log.error("所有重试尝试均失败: message={}, error={}", message, e.getMessage());
        // 这里可以调用DLQ服务
    }
}