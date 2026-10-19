package com.stayhaven.domain;
import jakarta.persistence.*; import java.time.Instant;
@Entity @Table(name="room_service_orders") public class RoomServiceOrder {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @ManyToOne(optional=false,fetch=FetchType.LAZY) private Booking booking;
 @ManyToOne(optional=false,fetch=FetchType.LAZY) private RoomUnit roomUnit;
 @ManyToOne(optional=false,fetch=FetchType.LAZY) private Account orderedBy;
 @Column(nullable=false) private String itemName;
 @Column(nullable=false) private int quantity;
 @Column(nullable=false) private double unitPrice;
 @Column(nullable=false) private String status="PENDING";
 @Column(nullable=false) private Instant createdAt=Instant.now(); private Instant updatedAt;
 public Long getId(){return id;} public Booking getBooking(){return booking;} public void setBooking(Booking v){booking=v;} public RoomUnit getRoomUnit(){return roomUnit;} public void setRoomUnit(RoomUnit v){roomUnit=v;} public Account getOrderedBy(){return orderedBy;} public void setOrderedBy(Account v){orderedBy=v;} public String getItemName(){return itemName;} public void setItemName(String v){itemName=v;} public int getQuantity(){return quantity;} public void setQuantity(int v){quantity=v;} public double getUnitPrice(){return unitPrice;} public void setUnitPrice(double v){unitPrice=v;} public String getStatus(){return status;} public void setStatus(String v){status=v;updatedAt=Instant.now();} public Instant getCreatedAt(){return createdAt;} public Instant getUpdatedAt(){return updatedAt;} public double getTotal(){return quantity*unitPrice;}
}
