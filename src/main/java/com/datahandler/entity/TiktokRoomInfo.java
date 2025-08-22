package com.datahandler.entity;

import lombok.*;

import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class TiktokRoomInfo {
    private String roomId;         // room_id
    private String userId;        // user_id
    private String uniqueId;      // uniqueId
    private Date startTime;      // start_time
    private Date finishTime;     // finish_time
    private String gameTagId;    // gameTagId
    private Integer totalViewer; // total_viewer
    private Integer totalGift;   // total_gift
    private String title;        // title
    private Date createTime;     // create_time
    private Date lastUpdTime;    // last_upd_time
}