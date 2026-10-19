package com.stayhaven.domain;
import jakarta.persistence.*; import java.time.LocalDate;
@Entity @Table(name="offers") public class Offer {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false,length=100) private String name;
 @Column(nullable=false,unique=true,length=32) private String code;
 @Column(nullable=false,length=12) private String discountType;
 @Column(nullable=false) private double discountValue;
 private LocalDate startsOn; private LocalDate endsOn;
 private String applicableRoomType;
 @Column(nullable=false) private boolean active=true;
 public Long getId(){return id;} public String getName(){return name;} public void setName(String v){name=v;} public String getCode(){return code;} public void setCode(String v){code=v;} public String getDiscountType(){return discountType;} public void setDiscountType(String v){discountType=v;} public double getDiscountValue(){return discountValue;} public void setDiscountValue(double v){discountValue=v;} public LocalDate getStartsOn(){return startsOn;} public void setStartsOn(LocalDate v){startsOn=v;} public LocalDate getEndsOn(){return endsOn;} public void setEndsOn(LocalDate v){endsOn=v;} public String getApplicableRoomType(){return applicableRoomType;} public void setApplicableRoomType(String v){applicableRoomType=v;} public boolean isActive(){return active;} public void setActive(boolean v){active=v;}
}
