package com.datahandler.task;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONObject;
import com.datahandler.client.TikTokApiClient;
import com.datahandler.entity.BaseTiktokAnchorInfo;
import com.datahandler.entity.DailyTaskLog;
import com.datahandler.entity.TiktokAnchorInfo;
import com.datahandler.kafka.KafkaProducerService;
import com.datahandler.mapper.BaseTiktokAnchorInfoMapper;
import com.datahandler.mapper.DailyTaskFailUserMapper;
import com.datahandler.mapper.DailyTaskLogMapper;
import com.datahandler.redis.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StopWatch;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Slf4j
public class ScheduledTasks {

    @Autowired
    private KafkaProducerService kafkaProducerService;

    @Autowired
    private BaseTiktokAnchorInfoMapper baseTiktokAnchorInfoMapper;

    @Autowired
    private DailyTaskLogMapper dailyTaskLogMapper;

    @Autowired
    private DailyTaskFailUserMapper dailyTaskFailUserMapper;

    @Autowired
    private RedisService redisService;

//    @Scheduled(cron = "0 0 0 * * ?")
    @Scheduled(fixedRate = 1000000)
    public void runUserDailyTask() {
        log.info("【runUserDailyTask】start at {}", DateUtil.now());
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        // 初始化日志实体
        DailyTaskLog taskLog = DailyTaskLog.builder()
                .taskName("runUserDailyTask")
                .startTime(LocalDateTime.now())
                .status("SUCCESS") // 默认成功，异常时修改
                .totalRecords(0)
                .successRecords(0)
                .failRecords(0)
                .createdAt(LocalDateTime.now())
                .build();

        // 插入初始日志
        dailyTaskLogMapper.insert(taskLog);

        int total = 0;
        AtomicInteger success = new AtomicInteger(0);
        AtomicInteger fail = new AtomicInteger(0);

        // 线程池（CPU核心数 * 2）
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);

        try {
            // 获取数据
            List<BaseTiktokAnchorInfo> anchorInfoList = baseTiktokAnchorInfoMapper.getBaseTiktokAnchorInfo();
            if (CollectionUtils.isEmpty(anchorInfoList)) {
                log.warn("【DailyTask】no anchor info found, skip task.");
                taskLog.setStatus("SUCCESS");
                return;
            }

            total = anchorInfoList.size();
            CountDownLatch latch = new CountDownLatch(total);

            for (BaseTiktokAnchorInfo anchor : anchorInfoList) {
                executor.submit(() -> {
                    try {
                        TiktokAnchorInfo tiktokAnchorInfo = TikTokApiClient.getTiktokAnchorInfo(anchor.getUniqueId());
                        if (tiktokAnchorInfo != null && !StringUtils.isEmpty(tiktokAnchorInfo.getUserId())) {
                            String msg = JSONObject.toJSONString(tiktokAnchorInfo);
                            kafkaProducerService.sendMessage("anchor", msg);
                            log.info("【Kafka】send success, userId={}", anchor.getUserId());
                            success.incrementAndGet();
                        } else {
                            log.warn("【Kafka】skip send, userId={} return null", anchor.getUserId());
                            fail.incrementAndGet();
                            dailyTaskFailUserMapper.insertFailUser(taskLog.getId(), anchor.getUserId(), "roomBo is null");
                        }
                    } catch (Exception ex) {
                        log.error("【DailyTask】process userId={} failed", anchor.getUserId(), ex);
                        fail.incrementAndGet();
                        dailyTaskFailUserMapper.insertFailUser(taskLog.getId(), anchor.getUserId(), ex.getMessage());
                    } finally {
                        latch.countDown();
                    }
                });
            }

            // 等待所有任务完成（最多 1 小时，避免无限阻塞）
            latch.await(1, TimeUnit.HOURS);

        } catch (Exception e) {
            log.error("【DailyTask】execution failed", e);
            taskLog.setStatus("FAIL");
            taskLog.setErrorMessage(e.getMessage());
        } finally {
            stopWatch.stop();
            executor.shutdown();

            log.info("【DailyTask】end at {}", DateUtil.now());
            log.info("【DailyTask】execution time: {} ms", stopWatch.getTotalTimeMillis());

            // 更新日志
            taskLog.setEndTime(LocalDateTime.now());
            taskLog.setDurationMs(stopWatch.getTotalTimeMillis());
            taskLog.setTotalRecords(total);
            taskLog.setSuccessRecords(success.get());
            taskLog.setFailRecords(fail.get());
            taskLog.setType("runUserDailyTask");

            if (fail.get() > 0 && fail.get() < total) {
                taskLog.setStatus("PARTIAL_FAIL");
            } else if (fail.get() == total) {
                taskLog.setStatus("FAIL");
            } else {
                taskLog.setStatus("SUCCESS");
            }

            dailyTaskLogMapper.updateById(taskLog);
        }
    }


//    // 每 10 分钟执行一次
//    // 每 10 分钟执行一次
////    @Scheduled(cron = "0 0/10 * * * ?")
//    @Scheduled(fixedRate = 1000000)
//    public void runRoomTask() {
//        log.info("【runRoomTask】start at {}", DateUtil.now());
//        StopWatch stopWatch = new StopWatch();
//        stopWatch.start();
//
//        // 初始化日志实体
//        DailyTaskLog taskLog = DailyTaskLog.builder()
//                .taskName("runRoomTask")
//                .startTime(LocalDateTime.now())
//                .status("SUCCESS")
//                .totalRecords(0)
//                .successRecords(0)
//                .failRecords(0)
//                .createdAt(LocalDateTime.now())
//                .build();
//
//        dailyTaskLogMapper.insert(taskLog);
//
//        int total = 0;
//        AtomicInteger success = new AtomicInteger(0);
//        AtomicInteger fail = new AtomicInteger(0);
//
//        // 线程池（CPU核心数*2）
//        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);
//
//        try {
//            List<BaseTiktokAnchorInfo> anchorInfoList = baseTiktokAnchorInfoMapper.getBaseTiktokAnchorInfo();
//            if (CollectionUtils.isEmpty(anchorInfoList)) {
//                log.warn("【DailyTask】no anchor info found, skip task.");
//                taskLog.setStatus("SUCCESS");
//                return;
//            }
//
//            total = anchorInfoList.size();
//            CountDownLatch latch = new CountDownLatch(total);
//
//            for (BaseTiktokAnchorInfo anchor : anchorInfoList) {
//                executor.submit(() -> {
//                    try {
//                        // 已存在的直接跳过
//                        if (redisService.hasKey(USER_LIVE_ACTIVE + anchor.getUserId())) {
//                            return;
//                        }
//
//                        // 查询有没有开播
//                        LiveRoomExistRes liveRoomId = TikTokApiClient.getLiveRoomId(anchor.getUserId());
//                        if (liveRoomId != null
//                                && liveRoomId.getData() != null
//                                && liveRoomId.getData().getData() != null
//                                && !StringUtils.isEmpty(liveRoomId.getData().getData().getRoom_id())) {
//
//                            // 获取直播间信息
//                            TiktokRoomInfo tiktokRoomChat = TikTokApiClient.getTiktokRoomChat(anchor.getUserId(), anchor.getUniqueId());
//                            String msg = JSONObject.toJSONString(tiktokRoomChat);
//                            kafkaProducerService.sendMessage("room", msg);
//                            log.info("【Kafka】send success, userId={}", anchor.getUserId());
//
//                            success.incrementAndGet();
//                            // 缓存到 Redis
//                            redisService.setValue(USER_LIVE_ACTIVE + anchor.getUserId(), liveRoomId.getData().getData().getRoom_id());
//                        } else {
//                            log.warn("【Kafka】skip send, userId={} return null", anchor.getUserId());
//                            fail.incrementAndGet();
//                            dailyTaskFailUserMapper.insertFailRoom(taskLog.getId(), anchor.getUserId(), "roomBo is null");
//                        }
//                    } catch (Exception ex) {
//                        log.error("【DailyTask】process userId={} failed", anchor.getUserId(), ex);
//                        fail.incrementAndGet();
//                        dailyTaskFailUserMapper.insertFailRoom(taskLog.getId(), anchor.getUserId(), ex.getMessage());
//                    } finally {
//                        latch.countDown();
//                    }
//                });
//            }
//
//            // 等待所有任务完成，最多1小时
//            latch.await(1, TimeUnit.HOURS);
//
//        } catch (Exception e) {
//            log.error("【DailyTask】execution failed", e);
//            taskLog.setStatus("FAIL");
//            taskLog.setErrorMessage(e.getMessage());
//        } finally {
//            stopWatch.stop();
//            executor.shutdown();
//
//            log.info("【DailyTask】end at {}", DateUtil.now());
//            log.info("【DailyTask】execution time: {} ms", stopWatch.getTotalTimeMillis());
//
//            taskLog.setEndTime(LocalDateTime.now());
//            taskLog.setDurationMs(stopWatch.getTotalTimeMillis());
//            taskLog.setTotalRecords(total);
//            taskLog.setSuccessRecords(success.get());
//            taskLog.setFailRecords(fail.get());
//            taskLog.setType("runRoomTask");
//
//            if (fail.get() > 0 && fail.get() < total) {
//                taskLog.setStatus("PARTIAL_FAIL");
//            } else if (fail.get() == total) {
//                taskLog.setStatus("FAIL");
//            } else {
//                taskLog.setStatus("SUCCESS");
//            }
//
//            dailyTaskLogMapper.updateById(taskLog);
//        }
//    }
//
//
//    // 每 10 分钟执行一次
////    @Scheduled(cron = "0 * * * * ?")
//    @Scheduled(fixedRate = 1000000)
//    public void runRoomLiveTask() {
//        log.info("【runRoomLiveTask】start at {}", DateUtil.now());
//
//        Set<String> keysByPrefix = redisService.getKeysByPrefix(USER_LIVE_ACTIVE);
//        List<Set<String>> sets = groupKeys(keysByPrefix, 50);
//
//        // 线程池（CPU核心数 * 2）
//        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);
//        CountDownLatch latch = new CountDownLatch(sets.size());
//
//        for (Set<String> batchKeys : sets) {
//            executor.submit(() -> {
//                try {
//                    // 批量从 Redis 获取值
//                    Map<String, String> keyValueMap = redisService.multiGet(batchKeys);
//
//                    List<UserRoomBo> userRoomBos = keyValueMap.entrySet().stream()
//                            .filter(entry -> entry.getValue() != null)
//                            .map(entry -> {
//                                String key = entry.getKey();
//                                String roomId = entry.getValue();
//                                String[] split = key.split("_");
//                                return new UserRoomBo(split[1], roomId);
//                            })
//                            .collect(Collectors.toList());
//
//                    String roomIds = userRoomBos.stream()
//                            .map(UserRoomBo::getRoomId)
//                            .filter(Objects::nonNull)
//                            .distinct()
//                            .collect(Collectors.joining(","));
//
//                    if (StringUtils.hasText(roomIds)) {
//                        try {
//                            CheckRoomAliveRes checkRoomAliveRes = TikTokApiClient.checkRoomAlive(roomIds);
//                            List<CheckRoomAliveRes.RoomAliveInfo> roomInfoList = checkRoomAliveRes.getData().getData();
//
//                            // roomId -> UserRoomBo list
//                            Map<String, List<UserRoomBo>> listMap = userRoomBos.stream()
//                                    .collect(Collectors.groupingBy(UserRoomBo::getRoomId));
//
//                            roomInfoList.stream()
//                                    .filter(r -> !r.isAlive()) // 只处理不在线的
//                                    .forEach(r -> {
//                                        List<UserRoomBo> relatedUsers = listMap.getOrDefault(r.getRoom_id_str(), Collections.emptyList());
//                                        relatedUsers.forEach(userRoomBo -> {
//                                            redisService.deleteValue(USER_LIVE_ACTIVE + userRoomBo.getUserId());
//                                            log.info("【runRoomLiveTask】userId={} roomId={} is not alive, delete redis key",
//                                                    userRoomBo.getUserId(), userRoomBo.getRoomId());
//                                        });
//                                    });
//
//                        } catch (IOException e) {
//                            log.error("【runRoomLiveTask】checkRoomAlive failed, roomIds={}", roomIds, e);
//                        }
//                    }
//                } catch (Exception e) {
//                    log.error("【runRoomLiveTask】process batchKeys failed", e);
//                } finally {
//                    latch.countDown();
//                }
//            });
//        }
//
//        try {
//            // 等待所有批次完成，最多 30 分钟
//            latch.await(30, TimeUnit.MINUTES);
//        } catch (InterruptedException e) {
//            log.error("【runRoomLiveTask】await interrupted", e);
//            Thread.currentThread().interrupt();
//        } finally {
//            executor.shutdown();
//            log.info("【runRoomLiveTask】end, totalBatches={}", sets.size());
//        }
//    }
//
//
//
//    /**
//     * 将键集合按照固定大小分组
//     *
//     * @param keys      键集合
//     * @param groupSize 每组的大小
//     * @return 分组后的键集合列表
//     */
//    public List<Set<String>> groupKeys(Set<String> keys, int groupSize) {
//        List<Set<String>> groupedKeys = new ArrayList<>();
//        Iterator<String> iterator = keys.iterator();
//        while (iterator.hasNext()) {
//            Set<String> group = new HashSet<>();
//            for (int i = 0; i < groupSize && iterator.hasNext(); i++) {
//                group.add(iterator.next());
//            }
//            groupedKeys.add(group);
//        }
//        return groupedKeys;
//    }

}