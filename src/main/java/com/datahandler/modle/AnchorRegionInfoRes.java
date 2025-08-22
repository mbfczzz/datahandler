package com.datahandler.modle;

public class AnchorRegionInfoRes {
    private int code;
    private String message;
    private Data data;

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

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    // Inner classes
    public static class Data {
        private String uid;
        private String anchorRegion;
        private int statusCode;
        private Extra extra;

        // Getters and Setters
        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getAnchorRegion() {
            return anchorRegion;
        }

        public void setAnchorRegion(String anchorRegion) {
            this.anchorRegion = anchorRegion;
        }

        public int getStatusCode() {
            return statusCode;
        }

        public void setStatusCode(int statusCode) {
            this.statusCode = statusCode;
        }

        public Extra getExtra() {
            return extra;
        }

        public void setExtra(Extra extra) {
            this.extra = extra;
        }
    }

    public static class Extra {
        private long now;

        // Getters and Setters
        public long getNow() {
            return now;
        }

        public void setNow(long now) {
            this.now = now;
        }
    }
}