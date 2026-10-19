package com.stayhaven.domain;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.Instant;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

@Entity @Table(name="bookings", indexes=@Index(name="idx_booking_dates_room",columnList="room_id,check_in,check_out,status"))
public class Booking {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false,unique=true) private String reference;
    @ManyToOne(optional=false,fetch=FetchType.LAZY) private Account account;
    @ManyToOne(optional=false,fetch=FetchType.LAZY) private Room room;
    @ManyToOne(fetch=FetchType.LAZY) private RoomUnit roomUnit;
    @ManyToOne(fetch=FetchType.LAZY) private Offer offer;
    @Column(name="check_in",nullable=false) private LocalDate checkIn;
    @Column(name="check_out",nullable=false) private LocalDate checkOut;
    private LocalTime checkInTime=LocalTime.of(15,0);
    private LocalTime checkOutTime=LocalTime.of(11,0);
    @Column(nullable=false) private int guests;
    @Column(nullable=false) private double totalPrice;
    @Column(nullable=false) private String status="CONFIRMED";
    @Column(nullable=false) private String paymentStatus="DEMO PAID";
    @Column(nullable=false) private String guestName;
    @Column(nullable=false) private String guestEmail;
    private Instant checkedInAt;
    private Instant checkedOutAt;
    @ManyToOne(fetch=FetchType.LAZY) private Account checkedInBy;
    @ManyToOne(fetch=FetchType.LAZY) private Account checkedOutBy;
    public long getNights(){return checkIn==null||checkOut==null?0:ChronoUnit.DAYS.between(checkIn,checkOut);}
    public Long getId(){return id;} public String getReference(){return reference;} public void setReference(String v){reference=v;}
    public Account getAccount(){return account;} public void setAccount(Account v){account=v;}
    public Room getRoom(){return room;} public void setRoom(Room v){room=v;}
    public RoomUnit getRoomUnit(){return roomUnit;} public void setRoomUnit(RoomUnit v){roomUnit=v;}
    public Offer getOffer(){return offer;} public void setOffer(Offer v){offer=v;}
    public LocalDate getCheckIn(){return checkIn;} public void setCheckIn(LocalDate v){checkIn=v;}
    public LocalDate getCheckOut(){return checkOut;} public void setCheckOut(LocalDate v){checkOut=v;}
    public LocalTime getCheckInTime(){return checkInTime==null?LocalTime.of(15,0):checkInTime;} public void setCheckInTime(LocalTime v){checkInTime=v;}
    public LocalTime getCheckOutTime(){return checkOutTime==null?LocalTime.of(11,0):checkOutTime;} public void setCheckOutTime(LocalTime v){checkOutTime=v;}
    public int getGuests(){return guests;} public void setGuests(int v){guests=v;}
    public double getTotalPrice(){return totalPrice;} public void setTotalPrice(double v){totalPrice=v;}
    public String getStatus(){return status;} public void setStatus(String v){status=v;}
    public String getPaymentStatus(){return paymentStatus;} public void setPaymentStatus(String v){paymentStatus=v;}
    public String getGuestName(){return guestName;} public void setGuestName(String v){guestName=v;}
    public String getGuestEmail(){return guestEmail;} public void setGuestEmail(String v){guestEmail=v;}
    public Instant getCheckedInAt(){return checkedInAt;} public void setCheckedInAt(Instant v){checkedInAt=v;}
    public Instant getCheckedOutAt(){return checkedOutAt;} public void setCheckedOutAt(Instant v){checkedOutAt=v;}
    public Account getCheckedInBy(){return checkedInBy;} public void setCheckedInBy(Account v){checkedInBy=v;}
    public Account getCheckedOutBy(){return checkedOutBy;} public void setCheckedOutBy(Account v){checkedOutBy=v;}
}
