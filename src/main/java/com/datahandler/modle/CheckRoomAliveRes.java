package com.datahandler.modle;

import lombok.Data;

import java.util.List;

@Data
public class CheckRoomAliveRes {
    private int code;
    private String message;
    private Data data;

    // getter/setter
    @lombok.Data
    public static class Data {
        private List<RoomAliveInfo> data;
        private Extra extra;
        private int status_code;

        // getter/setter
    }

    @lombok.Data
    public static class RoomAliveInfo {
        private boolean alive;
        private long room_id;
        private String room_id_str;

        // getter/setter
    }

    @lombok.Data
    public static class Extra {
        private long now;

        // getter/setter
    }
}