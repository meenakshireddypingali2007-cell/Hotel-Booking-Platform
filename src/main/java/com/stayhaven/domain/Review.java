package com.stayhaven.domain;
import jakarta.persistence.*; import java.time.Instant;
@Entity @Table(name="reviews",uniqueConstraints=@UniqueConstraint(name="uq_review_booking",columnNames="booking_id"))
public class Review {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @ManyToOne(optional=false,fetch=FetchType.LAZY) private Booking booking;
 @ManyToOne(optional=false,fetch=FetchType.LAZY) private Account guest;
 @Column(nullable=false) private int rating;
 @Column(length=1200) private String comment;
 @Column(nullable=false) private Instant createdAt=Instant.now();
 public Long getId(){return id;} public Booking getBooking(){return booking;} public void setBooking(Booking v){booking=v;} public Account getGuest(){return guest;} public void setGuest(Account v){guest=v;} public int getRating(){return rating;} public void setRating(int v){rating=v;} public String getComment(){return comment;} public void setComment(String v){comment=v;} public Instant getCreatedAt(){return createdAt;}
}
