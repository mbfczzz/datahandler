package com.datahandler.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class BaseTiktokAnchorInfo {
    private String userId;          // user_id
    private String secUid;         // sec_uid
    private String uniqueId;       // uniqueId
    private String nickname;       // nickname
    private String region;         // region
    private Integer followerCount; // follower_count
    private Integer followingCount;// following_count
    private Integer heartCount;   // heart_count
    private Integer videoCount;    // video_count
    private String signature;      // signature
    private Integer mcnStatus;     // mcn_status
    private String avatar;         // avatar
    private Integer statusCode;    // status_code
    private Date updateTime;       // update_time
    private Integer accountType;   // account_type
    private Boolean isPrivateAccount; // is_private_account
    private Date createTime;       // create_time
    private Date lastUpdTime;      // last_upd_time
}