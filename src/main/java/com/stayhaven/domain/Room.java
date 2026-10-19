package com.stayhaven.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity @Table(name="rooms")
public class Room {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(nullable=false) private Hotel hotel;
    @NotBlank @Size(max=80) private String type;
    @NotBlank @Size(max=200) private String description;
    @DecimalMin("1.00") @Column(nullable=false) private double pricePerNight;
    @Min(1) @Column(nullable=false) private int capacity;
    @Min(1) @Column(nullable=false) private int quantity;
    public Long getId(){return id;} public void setId(Long id){this.id=id;}
    public Hotel getHotel(){return hotel;} public void setHotel(Hotel hotel){this.hotel=hotel;}
    public String getType(){return type;} public void setType(String type){this.type=type;}
    public String getDescription(){return description;} public void setDescription(String description){this.description=description;}
    public double getPricePerNight(){return pricePerNight;} public void setPricePerNight(double pricePerNight){this.pricePerNight=pricePerNight;}
    public int getCapacity(){return capacity;} public void setCapacity(int capacity){this.capacity=capacity;}
    public int getQuantity(){return quantity;} public void setQuantity(int quantity){this.quantity=quantity;}
}
