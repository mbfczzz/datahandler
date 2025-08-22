package com.datahandler.entity;

import lombok.*;

import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class TiktokRoomChat {
    private Long id;                // id
    private String userId;         // user_id
    private String uniqueId;       // uniqueId
    private String roomId;         // room_id
    private Date chatTime;        // chat_time
    private String chatComment;   // chat_comment
    private Date createTime;      // create_time
    private Date lastUpdTime;     // last_upd_time
}