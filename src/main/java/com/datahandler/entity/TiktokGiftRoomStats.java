package com.datahandler.entity;

import lombok.*;

import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class TiktokGiftRoomStats {
    private Long id;                // id
    private String userId;          // user_id
    private String roomId;          // room_id
    private Integer totalDiamondCount; // total_diamond_count
    private Date createTime;       // create_time
    private Date lastUpdTime;      // last_upd_time
}