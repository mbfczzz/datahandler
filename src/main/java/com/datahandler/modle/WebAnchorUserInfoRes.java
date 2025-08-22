package com.datahandler.modle;

import lombok.Data;

@Data
public class WebAnchorUserInfoRes {
    private int code;
    private String message;
    private Data data;
    private int statusCode;
    private String statusMsg;
    private Extra extra;

    @lombok.Data
    public static class Data {
        private UserInfo userInfo;
    }

    @lombok.Data
    public static class UserInfo {
        private Stats stats;
        private User user;
    }

    @lombok.Data
    public static class Stats {
        private String diggCount;
        private String followerCount;
        private String followingCount;
        private String friendCount;
        private String heart;
        private Integer heartCount;
        private Integer videoCount;
    }

    @lombok.Data
    public static class User {
        private int UserStoryStatus;
        private String avatar;
        private long createTime;
        private int downloadSetting;
        private int duetSetting;
        private String uid;
        private String language;
        private long nickNameModifyTime;
        private String nickname;
        private boolean privateAccount;
        private String roomId;
        private String secUid;
        private String signature;
        private String uniqueId;
        private long uniqueIdModifyTime;
    }

    @lombok.Data
    public static class Extra {
        private long now;
    }
}