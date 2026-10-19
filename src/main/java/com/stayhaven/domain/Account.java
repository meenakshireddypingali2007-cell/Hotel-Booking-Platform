package com.stayhaven.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.Instant;

@Entity @Table(name="accounts")
public class Account {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @NotBlank @Size(max=100) private String name;
    @Email @NotBlank @Column(nullable=false, unique=true) private String email;
    @NotBlank @Size(max=30) private String phone;
    @Size(max=240) private String address;
    @NotBlank private String password;
    @Column(nullable=false) private String role="USER";
    private boolean enabled=true;
    private Instant createdAt=Instant.now();
    public Long getId(){return id;} public void setId(Long id){this.id=id;}
    public String getName(){return name;} public void setName(String name){this.name=name;}
    public String getEmail(){return email;} public void setEmail(String email){this.email=email==null?null:email.trim().toLowerCase();}
    public String getPhone(){return phone;} public void setPhone(String phone){this.phone=phone;}
    public String getAddress(){return address;} public void setAddress(String address){this.address=address;}
    public String getPassword(){return password;} public void setPassword(String password){this.password=password;}
    public String getRole(){return role;} public void setRole(String role){this.role=role;}
    public boolean isEnabled(){return enabled;} public void setEnabled(boolean enabled){this.enabled=enabled;}
    public Instant getCreatedAt(){return createdAt;}
}
