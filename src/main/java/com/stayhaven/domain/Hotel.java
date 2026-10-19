package com.stayhaven.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.*;

@Entity @Table(name="hotels")
public class Hotel {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @NotBlank @Size(max=120) private String name;
    @NotBlank @Size(max=100) private String location;
    @Size(max=240) private String address;
    @NotBlank @Column(length=2000) private String description;
    @DecimalMin("0") @DecimalMax("5") private double rating=4.5;
    @Column(length=500) private String imageUrl="https://images.unsplash.com/photo-1566073771259-6a8506099945?auto=format&fit=crop&w=1200&q=80";
    @OneToMany(mappedBy="hotel", cascade=CascadeType.ALL, orphanRemoval=true) private List<Room> rooms=new ArrayList<>();
    public Long getId(){return id;} public void setId(Long id){this.id=id;}
    public String getName(){return name;} public void setName(String name){this.name=name;}
    public String getLocation(){return location;} public void setLocation(String location){this.location=location;}
    public String getAddress(){return address;} public void setAddress(String address){this.address=address;}
    public String getDescription(){return description;} public void setDescription(String description){this.description=description;}
    public double getRating(){return rating;} public void setRating(double rating){this.rating=rating;}
    public String getImageUrl(){return imageUrl;} public void setImageUrl(String imageUrl){this.imageUrl=imageUrl;}
    public List<Room> getRooms(){return rooms;} public void setRooms(List<Room> rooms){this.rooms=rooms;}
}
