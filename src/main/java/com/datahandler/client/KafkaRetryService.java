package com.datahandler.client;

import com.alibaba.fastjson.JSONObject;
import com.datahandler.entity.TiktokAnchorInfo;
import com.datahandler.mapper.TiktokAnchorInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@Slf4j
public class KafkaRetryService {

    @Autowired
    private TiktokAnchorInfoMapper tiktokAnchorInfoMapper;

    @Autowired
    private RetryTemplate retryTemplate;

    @Autowired
    private DlqService dlqService;

    /**
     * 带重试的消息处理
     */
    public boolean processWithRetry(String message) {
        try {
            return retryTemplate.execute(context -> {
                int retryCount = context.getRetryCount() + 1;
                log.info("第{}次尝试处理消息: {}", retryCount, message);

                return processMessage(message);
            });
        } catch (Exception e) {
            log.error("消息处理失败，已达最大重试次数: message={}, error={}", message, e.getMessage());
            dlqService.sendToDlq(message, "重试失败: " + e.getMessage());
            return false;
        }
    }

    /**
     * 实际的消息处理逻辑
     */
    private boolean processMessage(String message) {
        try {
            TiktokAnchorInfo tiktokAnchorInfo = JSONObject.parseObject(message, TiktokAnchorInfo.class);

            // 数据验证
            validateAnchorInfo(tiktokAnchorInfo);

            // 插入数据库
            int result = tiktokAnchorInfoMapper.insert(tiktokAnchorInfo);

            if (result > 0) {
                log.info("消息处理成功: {}", message);
                return true;
            } else {
                throw new RuntimeException("数据库插入失败");
            }

        } catch (Exception e) {
            log.warn("消息处理失败: {}", e.getMessage());
            throw e; // 抛出异常以便重试
        }
    }

    /**
     * 数据验证
     */
    private void validateAnchorInfo(TiktokAnchorInfo info) {
        if (info == null) {
            throw new IllegalArgumentException("消息体不能为空");
        }
        if (StringUtils.isEmpty(info.getUniqueId())) {
            throw new IllegalArgumentException("主播名称不能为空");
        }
    }
}