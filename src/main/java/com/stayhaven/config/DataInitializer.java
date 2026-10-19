package com.stayhaven.config;
import com.stayhaven.domain.*;
import com.stayhaven.repo.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@Configuration public class DataInitializer {
 @Bean CommandLineRunner seed(AccountRepository accounts,HotelRepository hotels,RoomRepository rooms,com.stayhaven.repo.RoomUnitRepository units,BookingRepository bookings,PasswordEncoder encoder){return args->{
  String adminEmail=System.getenv().getOrDefault("STAYHAVEN_ADMIN_EMAIL","admin@stayhaven.local");
  String adminPassword=System.getenv("STAYHAVEN_ADMIN_PASSWORD");
  if(adminPassword!=null&&!adminPassword.isBlank()){Account a=accounts.findByEmail(adminEmail).orElse(null);if(a==null){a=new Account();a.setName("StayHaven Admin");a.setEmail(adminEmail);a.setPhone("0000000000");a.setPassword(encoder.encode(adminPassword));a.setRole("ADMIN");accounts.save(a);}else if(!encoder.matches(adminPassword,a.getPassword())){a.setPassword(encoder.encode(adminPassword));accounts.save(a);}}
   // Existing hotel rows are never seeded or replaced here. This initializer only repairs
   // room-unit inventory and legacy booking-to-room assignments for persisted records.
  for(Room room:rooms.findAll()){
   long existing=units.countByRoomId(room.getId());
   for(long i=existing+1;i<=room.getQuantity();i++){RoomUnit unit=new RoomUnit();unit.setRoom(room);unit.setRoomNumber(String.valueOf((room.getId()%9+1)*100+i));unit.setFloor((int)((i-1)/10)+1);unit.setBedType(room.getType().toLowerCase().contains("twin")?"Twin":"King");units.save(unit);}
  }
  // Older saved reservations may predate per-room inventory. Restore their room link
  // only when a compatible unit has no overlapping active reservation.
  LocalDate today=LocalDate.now(ZoneId.of("Asia/Kolkata"));
  for(Booking booking:bookings.findByRoomUnitIsNullAndStatusIn(List.of("PENDING","CONFIRMED"))){
   if(booking.getCheckIn()==null||booking.getCheckOut()==null||booking.getCheckIn().isBefore(today))continue;
   RoomUnit match=units.findByRoomId(booking.getRoom().getId()).stream()
    .filter(unit->!"MAINTENANCE".equals(unit.getStatus())&& !"CLEANING".equals(unit.getStatus()))
    .filter(unit->bookings.countOverlappingUnit(unit.getId(),booking.getCheckIn(),booking.getCheckOut())==0)
    .findFirst().orElse(null);
   if(match!=null){booking.setRoomUnit(match);bookings.save(booking);}
  }
 };}
}
