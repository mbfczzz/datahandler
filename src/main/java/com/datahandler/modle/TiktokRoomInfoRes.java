package com.datahandler.modle;

public class TiktokRoomInfoRes {
    private int code;
    private String message;
    private DataWrapper data;

    // Getters and Setters
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataWrapper getData() {
        return data;
    }

    public void setData(DataWrapper data) {
        this.data = data;
    }

    // Inner classes
    public static class DataWrapper {
        private Data data;
        private int statusCode;
        private String message;
        private Extra extra;

        // Getters and Setters
        public Data getData() {
            return data;
        }

        public void setData(Data data) {
            this.data = data;
        }

        public int getStatusCode() {
            return statusCode;
        }

        public void setStatusCode(int statusCode) {
            this.statusCode = statusCode;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public Extra getExtra() {
            return extra;
        }

        public void setExtra(Extra extra) {
            this.extra = extra;
        }
    }

    public static class Data {
        private LiveRoom liveRoom;
        private Stats stats;
        private User user;

        // Getters and Setters
        public LiveRoom getLiveRoom() {
            return liveRoom;
        }

        public void setLiveRoom(LiveRoom liveRoom) {
            this.liveRoom = liveRoom;
        }

        public Stats getStats() {
            return stats;
        }

        public void setStats(Stats stats) {
            this.stats = stats;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }
    }

    public static class LiveRoom {
        private String coverUrl;
        private int gameTagId;
        private int liveRoomMode;
        private LiveRoomStats liveRoomStats;
        private int liveSubOnly;
        private int multiStreamScene;
        private int multiStreamSource;
        private PaidEvent paidEvent;
        private String squareCoverImg;
        private long startTime;
        private int status;
        private String streamId;
        private String title;

        // Getters and Setters
        public String getCoverUrl() {
            return coverUrl;
        }

        public void setCoverUrl(String coverUrl) {
            this.coverUrl = coverUrl;
        }

        public int getGameTagId() {
            return gameTagId;
        }

        public void setGameTagId(int gameTagId) {
            this.gameTagId = gameTagId;
        }

        public int getLiveRoomMode() {
            return liveRoomMode;
        }

        public void setLiveRoomMode(int liveRoomMode) {
            this.liveRoomMode = liveRoomMode;
        }

        public LiveRoomStats getLiveRoomStats() {
            return liveRoomStats;
        }

        public void setLiveRoomStats(LiveRoomStats liveRoomStats) {
            this.liveRoomStats = liveRoomStats;
        }

        public int getLiveSubOnly() {
            return liveSubOnly;
        }

        public void setLiveSubOnly(int liveSubOnly) {
            this.liveSubOnly = liveSubOnly;
        }

        public int getMultiStreamScene() {
            return multiStreamScene;
        }

        public void setMultiStreamScene(int multiStreamScene) {
            this.multiStreamScene = multiStreamScene;
        }

        public int getMultiStreamSource() {
            return multiStreamSource;
        }

        public void setMultiStreamSource(int multiStreamSource) {
            this.multiStreamSource = multiStreamSource;
        }

        public PaidEvent getPaidEvent() {
            return paidEvent;
        }

        public void setPaidEvent(PaidEvent paidEvent) {
            this.paidEvent = paidEvent;
        }

        public String getSquareCoverImg() {
            return squareCoverImg;
        }

        public void setSquareCoverImg(String squareCoverImg) {
            this.squareCoverImg = squareCoverImg;
        }

        public long getStartTime() {
            return startTime;
        }

        public void setStartTime(long startTime) {
            this.startTime = startTime;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getStreamId() {
            return streamId;
        }

        public void setStreamId(String streamId) {
            this.streamId = streamId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    public static class LiveRoomStats {
        private int enterCount;
        private int userCount;

        // Getters and Setters
        public int getEnterCount() {
            return enterCount;
        }

        public void setEnterCount(int enterCount) {
            this.enterCount = enterCount;
        }

        public int getUserCount() {
            return userCount;
        }

        public void setUserCount(int userCount) {
            this.userCount = userCount;
        }
    }

    public static class PaidEvent {
        private int event_id;
        private int paid_type;

        // Getters and Setters
        public int getEventId() {
            return event_id;
        }

        public void setEventId(int event_id) {
            this.event_id = event_id;
        }

        public int getPaidType() {
            return paid_type;
        }

        public void setPaidType(int paid_type) {
            this.paid_type = paid_type;
        }
    }

    public static class Stats {
        private int followerCount;
        private int followingCount;

        // Getters and Setters
        public int getFollowerCount() {
            return followerCount;
        }

        public void setFollowerCount(int followerCount) {
            this.followerCount = followerCount;
        }

        public int getFollowingCount() {
            return followingCount;
        }

        public void setFollowingCount(int followingCount) {
            this.followingCount = followingCount;
        }
    }

    public static class User {
        private String avatar;
        private int followStatus;
        private String id;
        private String nickname;
        private String roomId;
        private String secUid;
        private boolean secret;
        private String signature;
        private int status;
        private String uniqueId;
        private boolean verified;

        // Getters and Setters
        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public int getFollowStatus() {
            return followStatus;
        }

        public void setFollowStatus(int followStatus) {
            this.followStatus = followStatus;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getRoomId() {
            return roomId;
        }

        public void setRoomId(String roomId) {
            this.roomId = roomId;
        }

        public String getSecUid() {
            return secUid;
        }

        public void setSecUid(String secUid) {
            this.secUid = secUid;
        }

        public boolean isSecret() {
            return secret;
        }

        public void setSecret(boolean secret) {
            this.secret = secret;
        }

        public String getSignature() {
            return signature;
        }

        public void setSignature(String signature) {
            this.signature = signature;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getUniqueId() {
            return uniqueId;
        }

        public void setUniqueId(String uniqueId) {
            this.uniqueId = uniqueId;
        }

        public boolean isVerified() {
            return verified;
        }

        public void setVerified(boolean verified) {
            this.verified = verified;
        }
    }

    public static class Extra {
        private String id;

        // Getters and Setters
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}