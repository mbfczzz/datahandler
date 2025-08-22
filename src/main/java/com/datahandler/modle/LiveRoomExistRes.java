package com.datahandler.modle;

import lombok.Data;

@Data
public class LiveRoomExistRes {
    private int code;
    private String message;
    private ResponseData data; // 确保类型为 ResponseData
    private Extra extra;
    private int status_code;

    // 静态内部类 ResponseData
    @Data
    public static class ResponseData {
        private UserData data;
    }

    // 静态内部类 UserData
    @Data
    public static class UserData {
        private String user_id;
        private String room_id;
    }

    // 静态内部类 Extra
    @Data
    public static class Extra {
        private long now;
    }
}