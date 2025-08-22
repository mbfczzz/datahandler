package com.datahandler.entity;

import lombok.*;

import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class TiktokGiftDetails {
    private Long id;                // id
    private String userId;          // user_id
    private String uniqueId;        // uniqueId
    private String roomId;          // room_id
    private Date sendTime;         // send_time
    private Integer diamondCount;   // diamond_count
    private String giftName;       // gift_name
    private Date createTime;       // create_time
    private Date lastUpdTime;      // last_upd_time
}