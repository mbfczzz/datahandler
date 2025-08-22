package com.datahandler.modle;

public class TiktokAnchorUserRes {
    private int code;
    private String message;
    private Data data;
    private int status_code;
    private String status_msg;
    private Extra extra;

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

    public int getStatus_code() {
        return status_code;
    }

    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }

    public String getStatus_msg() {
        return status_msg;
    }

    public void setStatus_msg(String status_msg) {
        this.status_msg = status_msg;
    }

    public Extra getExtra() {
        return extra;
    }

    public void setExtra(Extra extra) {
        this.extra = extra;
    }

    public static class Data {
        private UserAccountInfo user_account_info;
        private UserProfileInfo user_profile_info;

        // Getters and Setters
        public UserAccountInfo getUser_account_info() {
            return user_account_info;
        }

        public void setUser_account_info(UserAccountInfo user_account_info) {
            this.user_account_info = user_account_info;
        }

        public UserProfileInfo getUser_profile_info() {
            return user_profile_info;
        }

        public void setUser_profile_info(UserProfileInfo user_profile_info) {
            this.user_profile_info = user_profile_info;
        }
    }

    public static class UserAccountInfo {
        private int account_type;
        private boolean is_private_account;

        // Getters and Setters
        public int getAccount_type() {
            return account_type;
        }

        public void setAccount_type(int account_type) {
            this.account_type = account_type;
        }

        public boolean isIs_private_account() {
            return is_private_account;
        }

        public void setIs_private_account(boolean is_private_account) {
            this.is_private_account = is_private_account;
        }
    }

    public static class UserProfileInfo {
        private String uid;
        private String nickname;
        private String sec_uid;
        private String uniqueId;
        private int following_count;
        private int follower_count;
        private int like_count;
        private int aweme_count;
        private String signature;
        private String avatar;

        // Getters and Setters
        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getSec_uid() {
            return sec_uid;
        }

        public void setSec_uid(String sec_uid) {
            this.sec_uid = sec_uid;
        }

        public String getUniqueId() {
            return uniqueId;
        }

        public void setUniqueId(String uniqueId) {
            this.uniqueId = uniqueId;
        }

        public int getFollowing_count() {
            return following_count;
        }

        public void setFollowing_count(int following_count) {
            this.following_count = following_count;
        }

        public int getFollower_count() {
            return follower_count;
        }

        public void setFollower_count(int follower_count) {
            this.follower_count = follower_count;
        }

        public int getLike_count() {
            return like_count;
        }

        public void setLike_count(int like_count) {
            this.like_count = like_count;
        }

        public int getAweme_count() {
            return aweme_count;
        }

        public void setAweme_count(int aweme_count) {
            this.aweme_count = aweme_count;
        }

        public String getSignature() {
            return signature;
        }

        public void setSignature(String signature) {
            this.signature = signature;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
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