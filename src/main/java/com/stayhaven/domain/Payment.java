package com.stayhaven.domain;
import jakarta.persistence.*; import java.time.Instant;
@Entity @Table(name="payments",uniqueConstraints=@UniqueConstraint(name="uq_payment_booking",columnNames="booking_id"))
public class Payment {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @OneToOne(optional=false,fetch=FetchType.LAZY) @JoinColumn(nullable=false) private Booking booking;
 @Column(nullable=false) private double amount;
 @Column(nullable=false) private String status="TEST_RECORDED";
 @Column(nullable=false) private String provider="DEMO_TEST";
 @Column(nullable=false,unique=true) private String transactionReference;
 @Column(nullable=false) private Instant createdAt=Instant.now();
 public Long getId(){return id;} public Booking getBooking(){return booking;} public void setBooking(Booking b){booking=b;} public double getAmount(){return amount;} public void setAmount(double a){amount=a;} public String getStatus(){return status;} public void setStatus(String v){status=v;} public String getProvider(){return provider;} public void setProvider(String v){provider=v;} public String getTransactionReference(){return transactionReference;} public void setTransactionReference(String v){transactionReference=v;} public Instant getCreatedAt(){return createdAt;}
}
