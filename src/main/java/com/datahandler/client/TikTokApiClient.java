package com.datahandler.client;

import com.alibaba.fastjson.JSONObject;
import com.datahandler.entity.TiktokAnchorInfo;
import com.datahandler.entity.TiktokRoomInfo;
import com.datahandler.modle.*;
import lombok.extern.slf4j.Slf4j;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.Date;

@Slf4j
public class TikTokApiClient {

    private static final String BASE_URL = "http://45.127.32.76:31002";
    private static final OkHttpClient client = new OkHttpClient();

    /**
     * 获取主播信息接口
     *
     * @param userId 用户ID
     * @return 响应结果
     */
    private static String getAnchorUserInfo(String userId) throws IOException {
        HttpUrl url = HttpUrl.parse(BASE_URL + "/api/v1/app/anchorUserInfo")
                .newBuilder()
                .addQueryParameter("userId", userId)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    /**
     * 获取用户信息接口
     *
     * @param userId 用户ID（可以是 secUid, uid, uniqueId）
     * @return 响应结果
     */
    private static String getWebAnchorUserInfo(String userId) throws IOException {
        HttpUrl url = HttpUrl.parse(BASE_URL + "/api/v1/web/anchorUserInfo")
                .newBuilder()
                .addQueryParameter("userId", userId)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    /**
     * 获取用户所属区域
     *
     * @param userId 用户ID
     * @return 响应结果
     */
    private static String getAnchorRegionInfo(String userId) throws IOException {
        HttpUrl url = HttpUrl.parse(BASE_URL + "/api/v1/anchorRegionInfo")
                .newBuilder()
                .addQueryParameter("userId", userId)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    /**
     * 检测直播间在线状态
     *
     * @param roomIds 房间号列表，以逗号分隔
     * @return 响应结果
     */
    private static String checkRoomAliveBase(String roomIds) throws IOException {
        HttpUrl url = HttpUrl.parse(BASE_URL + "/api/v1/room_check_alive")
                .newBuilder()
                .addQueryParameter("roomIds", "7541216191151442696")
                .build();

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    /**
     * 检测直播间在线状态
     *
     * @param roomIds 房间号列表，以逗号分隔
     * @return 响应结果
     */
    public static CheckRoomAliveRes checkRoomAlive(String roomIds) throws IOException {
        String checkRoomAlive = checkRoomAliveBase(roomIds);
        CheckRoomAliveRes checkRoomAliveRes = JSONObject.parseObject(checkRoomAlive, CheckRoomAliveRes.class);
        log.info(JSONObject.toJSONString(checkRoomAliveRes));
        return checkRoomAliveRes;
    }

    private static String getLiveRoomInfo(String uniqueId) throws IOException {
        // 构建请求 URL
        HttpUrl url = HttpUrl.parse(BASE_URL + "/api/v1/roomProfileByUniqueId")
                .newBuilder()
                .addQueryParameter("uniqueId", uniqueId)
                .build();

        // 创建请求
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        // 发送请求
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    private static String getLiveRoomIdByUid(String userId) throws IOException{
        // 构建请求 URL
        HttpUrl url = HttpUrl.parse(BASE_URL + "/api/v1/liveRoomIdByUid")
                .newBuilder()
                .addQueryParameter("userId", userId)
                .build();

        // 创建请求
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        // 发送请求
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }


    public static LiveRoomExistRes getLiveRoomId(String userId) throws IOException{
        String res = getLiveRoomIdByUid(userId);
        LiveRoomExistRes liveRoomExistRes = JSONObject.parseObject(res,LiveRoomExistRes.class);
        log.info(JSONObject.toJSONString(liveRoomExistRes));
        return liveRoomExistRes;
    }

    public static TiktokAnchorInfo getTiktokAnchorInfo(String userId) throws IOException {
        // 获取用户信息
        String anchorInfo = getAnchorUserInfo(userId);
        String webAnchorInfo = getWebAnchorUserInfo(userId);
        String regionInfo = getAnchorRegionInfo(userId);

        // 转换为实体类
        TiktokAnchorUserRes tiktokAnchorUserRes = JSONObject.parseObject(anchorInfo, TiktokAnchorUserRes.class);
        WebAnchorUserInfoRes webAnchorUserInfoRes = JSONObject.parseObject(webAnchorInfo, WebAnchorUserInfoRes.class);
        AnchorRegionInfoRes anchorRegionInfoRes = JSONObject.parseObject(regionInfo, AnchorRegionInfoRes.class);

        // 日志输出
        log.info("主播信息: {}", JSONObject.toJSONString(tiktokAnchorUserRes));
        log.info("用户信息: {}", JSONObject.toJSONString(webAnchorUserInfoRes));
        log.info("区域信息: {}", JSONObject.toJSONString(anchorRegionInfoRes));

        // 封装 TiktokAnchorInfo
        TiktokAnchorInfo tiktokAnchorInfo = new TiktokAnchorInfo();
        tiktokAnchorInfo.setUserId(userId);

        // 检查 tiktokAnchorUserRes 及其内部对象是否为空
        if (tiktokAnchorUserRes != null && tiktokAnchorUserRes.getData() != null) {
            TiktokAnchorUserRes.Data data = tiktokAnchorUserRes.getData();
            if (data.getUser_profile_info() != null) {
                TiktokAnchorUserRes.UserProfileInfo userProfileInfo = data.getUser_profile_info();
                tiktokAnchorInfo.setSecUid(userProfileInfo.getSec_uid());
                tiktokAnchorInfo.setUniqueId(userProfileInfo.getUniqueId());
                tiktokAnchorInfo.setNickname(userProfileInfo.getNickname());
                tiktokAnchorInfo.setSignature(userProfileInfo.getSignature());
                tiktokAnchorInfo.setAvatar(userProfileInfo.getAvatar());
                tiktokAnchorInfo.setFollowerCount(userProfileInfo.getFollower_count());
                tiktokAnchorInfo.setFollowingCount(userProfileInfo.getFollowing_count());
            }
            if (data.getUser_account_info() != null) {
                tiktokAnchorInfo.setIsPrivateAccount(data.getUser_account_info().isIs_private_account());
            }
        }

        // 检查 webAnchorUserInfoRes 及其内部对象是否为空
        if (webAnchorUserInfoRes != null && webAnchorUserInfoRes.getData() != null) {
            WebAnchorUserInfoRes.Data data = webAnchorUserInfoRes.getData();
            if (data.getUserInfo() != null && data.getUserInfo().getStats() != null) {
                tiktokAnchorInfo.setVideoCount(data.getUserInfo().getStats().getVideoCount());
                tiktokAnchorInfo.setHeartCount(data.getUserInfo().getStats().getHeartCount());
            }
        }

        // 检查 anchorRegionInfoRes 及其内部对象是否为空
        if (anchorRegionInfoRes != null && anchorRegionInfoRes.getData() != null) {
            tiktokAnchorInfo.setRegion(anchorRegionInfoRes.getData().getAnchorRegion());
        }

        // 设置默认值
        tiktokAnchorInfo.setCreateTime(new Date());

        return tiktokAnchorInfo;
    }

    public static TiktokRoomInfo getTiktokRoomChat(String userId, String uniqueId) throws IOException {
        // 获取直播间信息
        String liveRoom = getLiveRoomInfo(uniqueId);
        log.info("直播间信息 JSON: {}", liveRoom);

        // 转换为实体类
        TiktokRoomInfoRes tiktokRoomInfoRes = JSONObject.parseObject(liveRoom, TiktokRoomInfoRes.class);
        log.info("直播间信息实体: {}", JSONObject.toJSONString(tiktokRoomInfoRes));

        // 封装 TiktokRoomInfo
        TiktokRoomInfo tiktokRoomInfo = new TiktokRoomInfo();
        tiktokRoomInfo.setUserId(userId);
        tiktokRoomInfo.setUniqueId(uniqueId);
        tiktokRoomInfo.setCreateTime(new Date());

        // 检查 tiktokRoomInfoRes 及其内部对象是否为空
        if (tiktokRoomInfoRes != null && tiktokRoomInfoRes.getData() != null && tiktokRoomInfoRes.getData().getData() != null) {
            TiktokRoomInfoRes.LiveRoom liveRoomInfo = tiktokRoomInfoRes.getData().getData().getLiveRoom();
            if (liveRoomInfo != null) {
                tiktokRoomInfo.setRoomId(liveRoomInfo.getStreamId());
                tiktokRoomInfo.setTitle(liveRoomInfo.getTitle());

                // 处理 startTime
                long startTime = liveRoomInfo.getStartTime();
                if (startTime > 0) { // 假设时间戳为 0 表示无效值
                    Date date = new Date(startTime * 1000); // 转换为毫秒
                    tiktokRoomInfo.setStartTime(date);
                }
            }
        }

        return tiktokRoomInfo;
    }

    public static AnchorTiktokRoomBo getAnchorTiktokRoomBo(String userId) throws IOException {
        TiktokAnchorInfo tiktokAnchorInfo = TikTokApiClient.getTiktokAnchorInfo(userId);
        TiktokRoomInfo tiktokRoomInfo = TikTokApiClient.getTiktokRoomChat(tiktokAnchorInfo.getUserId(), tiktokAnchorInfo.getUniqueId());
        AnchorTiktokRoomBo anchorTiktokRoomBo = new AnchorTiktokRoomBo();
        anchorTiktokRoomBo.setTiktokAnchorInfo(tiktokAnchorInfo);
        anchorTiktokRoomBo.setTiktokRoomInfo(tiktokRoomInfo);
        return anchorTiktokRoomBo;
    }

}