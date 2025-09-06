package com.datahandler.kafka;

import com.alibaba.fastjson.JSONObject;
import com.datahandler.entity.TiktokAnchorInfo;
import com.datahandler.entity.TiktokRoomInfo;
import com.datahandler.mapper.TiktokAnchorInfoMapper;
import com.datahandler.mapper.TiktokRoomInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaConsumer {

    @Autowired
    private TiktokAnchorInfoMapper tiktokAnchorInfoMapper;

    @Autowired
    private TiktokRoomInfoMapper tiktokRoomInfoMapper;

    // 消费单个消息
    @KafkaListener(topics = "anchor", groupId = "anchor-consumer-1")
    public void consumeMessageTopic(String message) {
        log.info("收到消息: topic=anchor, message={}", message);
        System.out.println("Received message: " + message);
        // 处理业务逻辑
        TiktokAnchorInfo tiktokAnchorInfo = JSONObject.parseObject(message, TiktokAnchorInfo.class);
        tiktokAnchorInfoMapper.insert(tiktokAnchorInfo);
        log.info("消息成功: topic=anchor, message={}", message);
    }

    // 消费单个消息
    @KafkaListener(topics = "room",  groupId = "room-consumer-1")
    public void consumeMessageRoom(String message) {
        log.info("收到消息: topic=room, message={}", message);
        System.out.println("Received message: " + message);
        // 处理业务逻辑
        TiktokRoomInfo tiktokRoomInfo = JSONObject.parseObject(message, TiktokRoomInfo.class);
        tiktokRoomInfoMapper.insert(tiktokRoomInfo);
        log.info("消息成功: topic=room, message={}", message);
    }

}