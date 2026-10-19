package com.stayhaven.domain;
import jakarta.persistence.*; import java.time.Instant;
@Entity @Table(name="loyalty_transactions",uniqueConstraints=@UniqueConstraint(name="uq_loyalty_booking_earn",columnNames={"booking_id","type"})) public class LoyaltyTransaction {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @ManyToOne(optional=false,fetch=FetchType.LAZY) private Account guest;
 @ManyToOne(optional=false,fetch=FetchType.LAZY) private Booking booking;
 @Column(nullable=false) private int points;
 @Column(nullable=false,length=12) private String type="EARN";
 @Column(nullable=false,length=160) private String reason;
 @Column(nullable=false) private Instant createdAt=Instant.now();
 public Long getId(){return id;} public Account getGuest(){return guest;} public void setGuest(Account v){guest=v;} public Booking getBooking(){return booking;} public void setBooking(Booking v){booking=v;} public int getPoints(){return points;} public void setPoints(int v){points=v;} public String getType(){return type;} public void setType(String v){type=v;} public String getReason(){return reason;} public void setReason(String v){reason=v;} public Instant getCreatedAt(){return createdAt;}
}
