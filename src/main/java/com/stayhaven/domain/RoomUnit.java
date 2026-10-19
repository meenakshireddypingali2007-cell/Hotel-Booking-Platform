package com.stayhaven.domain;

import jakarta.persistence.*;
import java.time.Instant;

@Entity @Table(name="room_units", uniqueConstraints=@UniqueConstraint(name="uq_room_unit_number",columnNames={"room_id","room_number"}))
public class RoomUnit {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="room_id",nullable=false) private Room room;
 @Column(name="room_number",nullable=false,length=16) private String roomNumber;
 private int floor=1;
 private String bedType="King";
 @Column(nullable=false,length=20) private String status="AVAILABLE";
 private Instant lastCheckedInAt;
 private Instant lastCheckedOutAt;
 public Long getId(){return id;} public Room getRoom(){return room;} public void setRoom(Room v){room=v;}
 public String getRoomNumber(){return roomNumber;} public void setRoomNumber(String v){roomNumber=v;}
 public int getFloor(){return floor;} public void setFloor(int v){floor=v;}
 public String getBedType(){return bedType;} public void setBedType(String v){bedType=v;}
 public String getStatus(){return status;} public void setStatus(String v){status=v;}
 public Instant getLastCheckedInAt(){return lastCheckedInAt;} public void setLastCheckedInAt(Instant v){lastCheckedInAt=v;}
 public Instant getLastCheckedOutAt(){return lastCheckedOutAt;} public void setLastCheckedOutAt(Instant v){lastCheckedOutAt=v;}
}
