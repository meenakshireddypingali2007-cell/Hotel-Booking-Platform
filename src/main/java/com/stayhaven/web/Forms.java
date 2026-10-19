package com.stayhaven.web;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
public final class Forms {
 private Forms(){}
 public static class Registration {
  @NotBlank @Size(max=100) private String name; @NotBlank @Email private String email; @NotBlank @Size(min=8,max=72) private String password; @NotBlank @Size(max=30) private String phone; @Size(max=240) private String address;
  public String getName(){return name;} public void setName(String v){name=v;}
  public String getEmail(){return email;} public void setEmail(String v){email=v;}
  public String getPassword(){return password;} public void setPassword(String v){password=v;}
  public String getPhone(){return phone;} public void setPhone(String v){phone=v;}
  public String getAddress(){return address;} public void setAddress(String v){address=v;}
 }
 public static class BookingForm {
  @NotNull private Long roomId; @NotNull @Future private LocalDate checkIn; @NotNull private LocalDate checkOut; @Min(1) private int guests=1; @NotBlank private String guestName; @NotBlank @Email private String guestEmail; private Long hotelId; private String offerCode; @Min(0) private int loyaltyPoints;
  public Long getRoomId(){return roomId;} public void setRoomId(Long v){roomId=v;}
  public LocalDate getCheckIn(){return checkIn;} public void setCheckIn(LocalDate v){checkIn=v;}
  public LocalDate getCheckOut(){return checkOut;} public void setCheckOut(LocalDate v){checkOut=v;}
  public int getGuests(){return guests;} public void setGuests(int v){guests=v;}
  public String getGuestName(){return guestName;} public void setGuestName(String v){guestName=v;}
  public String getGuestEmail(){return guestEmail;} public void setGuestEmail(String v){guestEmail=v;}
  public Long getHotelId(){return hotelId;} public void setHotelId(Long v){hotelId=v;}
  public String getOfferCode(){return offerCode;} public void setOfferCode(String v){offerCode=v;}
  public int getLoyaltyPoints(){return loyaltyPoints;} public void setLoyaltyPoints(int v){loyaltyPoints=v;}
 }
 public static class HotelForm {
  @NotBlank @Size(max=120) private String name;
  @NotBlank @Size(max=100) private String location;
  @Size(max=240) private String address;
  @NotBlank @Size(max=2000) private String description;
  @DecimalMin("0.0") @DecimalMax("5.0") private double rating=4.5;
  @Size(max=500) private String imageUrl;
  public String getName(){return name;} public void setName(String v){name=v;}
  public String getLocation(){return location;} public void setLocation(String v){location=v;}
  public String getAddress(){return address;} public void setAddress(String v){address=v;}
  public String getDescription(){return description;} public void setDescription(String v){description=v;}
  public double getRating(){return rating;} public void setRating(double v){rating=v;}
  public String getImageUrl(){return imageUrl;} public void setImageUrl(String v){imageUrl=v;}
 }
 public static class RoomForm {
  @NotBlank @Size(max=80) private String type;
  @NotBlank @Size(max=200) private String description;
  @DecimalMin("1.00") private double pricePerNight;
  @Min(1) private int capacity;
  @Min(1) private int quantity;
  public String getType(){return type;} public void setType(String v){type=v;}
  public String getDescription(){return description;} public void setDescription(String v){description=v;}
  public double getPricePerNight(){return pricePerNight;} public void setPricePerNight(double v){pricePerNight=v;}
  public int getCapacity(){return capacity;} public void setCapacity(int v){capacity=v;}
  public int getQuantity(){return quantity;} public void setQuantity(int v){quantity=v;}
 }
}
