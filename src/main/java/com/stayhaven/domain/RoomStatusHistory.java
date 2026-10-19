package com.stayhaven.domain;
import jakarta.persistence.*; import java.time.Instant;
@Entity @Table(name="room_status_history")
public class RoomStatusHistory {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @ManyToOne(optional=false,fetch=FetchType.LAZY) private RoomUnit roomUnit;
 @ManyToOne(fetch=FetchType.LAZY) private Account changedBy;
 @Column(nullable=false) private String oldStatus;
 @Column(nullable=false) private String newStatus;
 @Column(length=500) private String note;
 @Column(nullable=false) private Instant changedAt=Instant.now();
 public RoomStatusHistory(){} public RoomStatusHistory(RoomUnit u,Account a,String oldS,String newS,String n){roomUnit=u;changedBy=a;oldStatus=oldS;newStatus=newS;note=n;}
 public Long getId(){return id;} public RoomUnit getRoomUnit(){return roomUnit;} public String getOldStatus(){return oldStatus;} public String getNewStatus(){return newStatus;} public String getNote(){return note;} public Instant getChangedAt(){return changedAt;} public Account getChangedBy(){return changedBy;}
}
