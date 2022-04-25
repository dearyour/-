package com.seonbi.db.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Guestbook extends BaseEntity{
    // Wrapper 타입인 Long이나 Integer를 쓰면 id가 없는 경우엔 확실히 null이고, 그 자체로 id가 없다는걸 보장함.

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto increment
    private Long guestbookId;
    private Long hostId;
    private Long guestId;
    private Long scheduleId;
    private String content;
    private Integer position=1;
    private Integer imageNo=1;

}
