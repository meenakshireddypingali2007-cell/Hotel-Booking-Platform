package com.stayhaven;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.stayhaven.domain.Account;
import com.stayhaven.repo.AccountRepository;
import com.stayhaven.repo.HotelRepository;
import com.stayhaven.repo.RoomRepository;
import com.stayhaven.repo.BookingRepository;
import com.stayhaven.domain.Hotel;
import com.stayhaven.domain.Room;
import com.stayhaven.domain.Booking;
import com.stayhaven.domain.RoomUnit;
import java.util.UUID;
import java.time.LocalDate;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.containsString;

@SpringBootTest @AutoConfigureMockMvc class SecurityConfigTest {
 @Autowired MockMvc mvc;
 @Autowired AccountRepository accounts;
 @Autowired HotelRepository hotels;
 @Autowired RoomRepository rooms;
 @Autowired BookingRepository bookings;
 @Autowired com.stayhaven.repo.RoomUnitRepository units;
 @Autowired com.stayhaven.repo.LoyaltyTransactionRepository loyalty;
 @Autowired com.stayhaven.repo.RoomStatusHistoryRepository roomHistory;
 @Autowired com.stayhaven.repo.RoomServiceOrderRepository serviceOrders;
 @Autowired PasswordEncoder encoder;
 @AfterEach void cleanFrontDeskFixtures(){for(Hotel hotel:hotels.findAllWithRooms())if("Front Desk Hotel".equals(hotel.getName())){for(Booking b:bookings.findAll())if(b.getRoom().getHotel().getId().equals(hotel.getId())){serviceOrders.deleteAll(serviceOrders.findAllByOrderByCreatedAtDesc().stream().filter(o->o.getBooking().getId().equals(b.getId())).toList());loyalty.deleteByBookingId(b.getId());bookings.delete(b);}for(Room r:rooms.findByHotelId(hotel.getId())){for(RoomUnit u:units.findByRoomId(r.getId())){roomHistory.deleteByRoomUnitId(u.getId());units.delete(u);}}hotels.delete(hotel);}for(Account a:accounts.findAll())if(a.getEmail().startsWith("frontdesk-")||a.getEmail().startsWith("desk-"))accounts.delete(a);}
 @Test void adminRouteRedirectsAnonymousVisitorToLogin() throws Exception {mvc.perform(get("/admin")).andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/login"));}
 @Test void regularUserCannotOpenAdminRoutes() throws Exception {for(String path:new String[]{"/admin","/admin/hotels","/admin/bookings","/admin/check-ins","/admin/check-outs","/admin/room-service","/admin/users"})mvc.perform(get(path).with(user("traveler").roles("USER"))).andExpect(status().isForbidden());}
 @Test void adminCanOpenAdminDashboard() throws Exception {mvc.perform(get("/admin").with(user("manager").roles("ADMIN"))).andExpect(status().isOk()).andExpect(view().name("admin/dashboard"));}
 @Test void everyAdminOperationsPageRendersForAdmin() throws Exception {for(String path:new String[]{"/admin/hotels","/admin/bookings","/admin/rooms","/admin/check-ins","/admin/check-outs","/admin/guests","/admin/guest-history","/admin/payments","/admin/room-service","/admin/reviews","/admin/offers","/admin/loyalty","/admin/reports","/admin/settings"})mvc.perform(get(path).with(user("manager").roles("ADMIN"))).andExpect(status().isOk());}
 @Test void publicHomeAndRegistrationTemplatesRender() throws Exception {
  mvc.perform(get("/")).andExpect(status().isOk()).andExpect(content().string(containsString("Find your")));
  mvc.perform(get("/login")).andExpect(status().isOk()).andExpect(content().string(containsString("Sign in")));
  mvc.perform(get("/register")).andExpect(status().isOk()).andExpect(content().string(containsString("Create your account")));
  mvc.perform(get("/admin/login")).andExpect(status().isOk()).andExpect(content().string(containsString("Administrator sign in")));
 }
 @Test void destinationSearchFindsPersistedHotelRows() throws Exception {
  Hotel hotel=new Hotel();hotel.setName("Search Fixture "+UUID.randomUUID());hotel.setLocation("Kochi, Kerala, India");hotel.setAddress("Test address");hotel.setDescription("Temporary hotel search test fixture.");hotels.save(hotel);
  try{
   mvc.perform(get("/").param("location","Kochi")).andExpect(status().isOk()).andExpect(content().string(containsString(hotel.getName())));
   mvc.perform(get("/").param("location","Kerala")).andExpect(status().isOk()).andExpect(content().string(containsString(hotel.getName())));
  }finally{hotels.deleteById(hotel.getId());}
 }
 @Test void adminInventoryTemplateRendersForAdmin() throws Exception {
  Hotel hotel=new Hotel();hotel.setName("Admin List Fixture "+UUID.randomUUID());hotel.setLocation("Test City");hotel.setDescription("Temporary admin hotel test fixture.");hotels.save(hotel);
  try{mvc.perform(get("/admin/hotels").with(user("manager").roles("ADMIN"))).andExpect(status().isOk()).andExpect(content().string(containsString(hotel.getName()))).andExpect(content().string(containsString("Add a hotel")));}
  finally{hotels.deleteById(hotel.getId());}
 }
 @Test void sharedLoginRedirectsAdminAndUserToTheirPortals() throws Exception {
  assertLoginRedirect("ADMIN","/admin");assertLoginRedirect("USER","/dashboard");
 }
 @Test void adminCanInspectBookingDetails() throws Exception {
  Account account=new Account();account.setName("Detail Guest");account.setEmail("detail-"+UUID.randomUUID()+"@example.test");account.setPhone("9000000000");account.setPassword(encoder.encode("unused-test-password"));accounts.save(account);
  Hotel hotel=new Hotel();hotel.setName("Detail Test Hotel");hotel.setLocation("Test City");hotel.setDescription("Hotel used by the admin detail view test.");hotels.save(hotel);
  Room room=new Room();room.setHotel(hotel);room.setType("Test Suite");room.setDescription("Room used by the admin detail view test.");room.setPricePerNight(100);room.setCapacity(2);room.setQuantity(1);rooms.save(room);
  Booking booking=new Booking();booking.setReference("TEST"+UUID.randomUUID().toString().replace("-","").substring(0,12));booking.setAccount(account);booking.setRoom(room);booking.setCheckIn(LocalDate.now().plusDays(10));booking.setCheckOut(LocalDate.now().plusDays(12));booking.setGuests(2);booking.setGuestName("Detail Guest");booking.setGuestEmail(account.getEmail());booking.setTotalPrice(200);bookings.save(booking);
  try{mvc.perform(get("/admin/bookings/{id}",booking.getId()).with(user("manager").roles("ADMIN"))).andExpect(status().isOk()).andExpect(content().string(containsString("Detail Guest"))).andExpect(content().string(containsString("Test Suite")));
   mvc.perform(get("/admin/bookings").with(user("manager").roles("ADMIN"))).andExpect(status().isOk()).andExpect(content().string(containsString(booking.getReference())));
   mvc.perform(post("/admin/bookings/{id}/status",booking.getId()).with(user("manager").roles("ADMIN")).with(csrf()).param("status","CANCELLED")).andExpect(status().is3xxRedirection());
   org.junit.jupiter.api.Assertions.assertEquals("CANCELLED",bookings.findById(booking.getId()).orElseThrow().getStatus());}
  finally{bookings.deleteById(booking.getId());rooms.deleteById(room.getId());hotels.deleteById(hotel.getId());accounts.deleteById(account.getId());}
 }
 @Test void frontDeskCheckInAndOutRecordsTimesAndHousekeepingStatus() throws Exception {
  Account guest=new Account();guest.setName("Front Desk Guest");guest.setEmail("frontdesk-"+UUID.randomUUID()+"@example.test");guest.setPhone("9000000001");guest.setPassword(encoder.encode("unused-test-password"));accounts.save(guest);
  Account staff=new Account();staff.setName("Front Desk Admin");staff.setEmail("desk-"+UUID.randomUUID()+"@example.test");staff.setPhone("9000000002");staff.setPassword(encoder.encode("unused-test-password"));staff.setRole("ADMIN");accounts.save(staff);
  Hotel hotel=new Hotel();hotel.setName("Front Desk Hotel");hotel.setLocation("Test City");hotel.setDescription("Front desk test hotel.");hotels.save(hotel);
  Room type=new Room();type.setHotel(hotel);type.setType("Front Desk Room");type.setDescription("Test room.");type.setPricePerNight(250);type.setCapacity(2);type.setQuantity(1);rooms.save(type);
  RoomUnit unit=new RoomUnit();unit.setRoom(type);unit.setRoomNumber("FD-1");units.save(unit);
  Booking booking=new Booking();booking.setReference("FD"+UUID.randomUUID().toString().replace("-","").substring(0,10));booking.setAccount(guest);booking.setRoom(type);booking.setRoomUnit(unit);booking.setCheckIn(LocalDate.now());booking.setCheckOut(LocalDate.now().plusDays(2));booking.setGuests(2);booking.setGuestName(guest.getName());booking.setGuestEmail(guest.getEmail());booking.setTotalPrice(500);booking.setStatus("CONFIRMED");bookings.save(booking);
  mvc.perform(get("/admin/check-ins").with(user(staff.getEmail()).roles("ADMIN"))).andExpect(status().isOk()).andExpect(content().string(containsString(booking.getReference())));
  mvc.perform(post("/admin/bookings/{id}/check-in",booking.getId()).with(user(staff.getEmail()).roles("ADMIN")).with(csrf())).andExpect(status().is3xxRedirection());
  Booking checkedIn=bookings.findById(booking.getId()).orElseThrow();assertEquals("CHECKED IN",checkedIn.getStatus());assertNotNull(checkedIn.getCheckedInAt());assertEquals("OCCUPIED",units.findById(unit.getId()).orElseThrow().getStatus());
  mvc.perform(get("/admin/room-service").with(user(staff.getEmail()).roles("ADMIN"))).andExpect(status().isOk()).andExpect(content().string(containsString(guest.getName()))).andExpect(content().string(containsString(booking.getReference())));
  mvc.perform(post("/admin/room-service").with(user(staff.getEmail()).roles("ADMIN")).with(csrf()).param("bookingId",booking.getId().toString()).param("item","Breakfast").param("quantity","1")).andExpect(status().is3xxRedirection());
  assertTrue(serviceOrders.findAllByOrderByCreatedAtDesc().stream().anyMatch(o->o.getBooking().getId().equals(booking.getId())));
  mvc.perform(post("/admin/bookings/{id}/check-out",booking.getId()).with(user(staff.getEmail()).roles("ADMIN")).with(csrf())).andExpect(status().is3xxRedirection());
  Booking checkedOut=bookings.findById(booking.getId()).orElseThrow();assertEquals("CHECKED OUT",checkedOut.getStatus());assertNotNull(checkedOut.getCheckedOutAt());assertEquals("CLEANING",units.findById(unit.getId()).orElseThrow().getStatus());assertTrue(loyalty.existsByBookingIdAndType(booking.getId(),"EARN"));
 }
 @Test void adminCanDisableUsersButCannotChangeAdminAccounts() throws Exception {
  Account traveler=createAccount("USER");Account otherAdmin=createAccount("ADMIN");
  try{
   mvc.perform(post("/admin/users/{id}/toggle",traveler.getId()).with(user("manager").roles("ADMIN")).with(csrf())).andExpect(status().is3xxRedirection());
   mvc.perform(post("/admin/users/{id}/toggle",otherAdmin.getId()).with(user("manager").roles("ADMIN")).with(csrf())).andExpect(status().is3xxRedirection());
   org.junit.jupiter.api.Assertions.assertFalse(accounts.findById(traveler.getId()).orElseThrow().isEnabled());
   org.junit.jupiter.api.Assertions.assertTrue(accounts.findById(otherAdmin.getId()).orElseThrow().isEnabled());
  }finally{accounts.deleteById(traveler.getId());accounts.deleteById(otherAdmin.getId());}
 }
 private Account createAccount(String role){Account a=new Account();a.setName("Access Test");a.setEmail("access-"+UUID.randomUUID()+"@example.test");a.setPhone("9000000000");a.setPassword(encoder.encode("unused-test-password"));a.setRole(role);return accounts.save(a);}
 @Test void adminCanCreateEditAndDeleteHotelsAndRoomTypes() throws Exception {
  String suffix=UUID.randomUUID().toString().substring(0,8);String name="Admin CRUD "+suffix;Hotel hotel=null;Room room=null;
  try{
   mvc.perform(post("/admin/hotels").with(user("manager").roles("ADMIN")).with(csrf()).param("name",name).param("location","Test Coast").param("description","CRUD test stay").param("rating","4.4"))
     .andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/admin/hotels"));
   hotel=hotels.findAll().stream().filter(h->h.getName().equals(name)).findFirst().orElseThrow();
   mvc.perform(post("/admin/hotels/{id}/rooms",hotel.getId()).with(user("manager").roles("ADMIN")).with(csrf()).param("type","Test Room").param("description","CRUD room").param("pricePerNight","125.50").param("capacity","2").param("quantity","3"))
     .andExpect(status().is3xxRedirection());
   room=rooms.findByHotelId(hotel.getId()).getFirst();
   mvc.perform(post("/admin/hotels/{id}/edit",hotel.getId()).with(user("manager").roles("ADMIN")).with(csrf()).param("name",name+" Updated").param("location","Test Coast").param("description","Updated CRUD stay").param("rating","4.8"))
     .andExpect(status().is3xxRedirection());
   mvc.perform(post("/admin/rooms/{id}/edit",room.getId()).with(user("manager").roles("ADMIN")).with(csrf()).param("type","Updated Test Room").param("description","Updated CRUD room").param("pricePerNight","180").param("capacity","3").param("quantity","4"))
     .andExpect(status().is3xxRedirection());
   mvc.perform(get("/admin/hotels").with(user("manager").roles("ADMIN"))).andExpect(status().isOk()).andExpect(content().string(containsString("Updated Test Room")));
   mvc.perform(post("/admin/rooms/{id}/delete",room.getId()).with(user("manager").roles("ADMIN")).with(csrf())).andExpect(status().is3xxRedirection());
   mvc.perform(post("/admin/hotels/{id}/delete",hotel.getId()).with(user("manager").roles("ADMIN")).with(csrf())).andExpect(status().is3xxRedirection());
   org.junit.jupiter.api.Assertions.assertFalse(hotels.existsById(hotel.getId()));
  }finally{if(hotel!=null&&hotels.existsById(hotel.getId()))hotels.deleteById(hotel.getId());}
 }
 private void assertLoginRedirect(String role,String expected)throws Exception{
  String email="security-"+UUID.randomUUID()+"@example.test";Account account=new Account();account.setName("Security Test");account.setEmail(email);account.setPhone("9000000000");account.setPassword(encoder.encode("test-pass-123"));account.setRole(role);accounts.save(account);
  try{mvc.perform(post("/login").with(csrf()).param("username",email).param("password","test-pass-123")).andExpect(status().is3xxRedirection()).andExpect(redirectedUrl(expected));}
  finally{accounts.delete(account);}
 }
}
