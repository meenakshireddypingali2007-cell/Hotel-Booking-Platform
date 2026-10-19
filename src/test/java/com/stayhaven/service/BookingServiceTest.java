package com.stayhaven.service;

import com.stayhaven.domain.*;
import com.stayhaven.repo.*;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookingServiceTest {
 private final BookingRepository bookings=mock(BookingRepository.class);
 private final RoomRepository rooms=mock(RoomRepository.class);
 private final RoomUnitRepository units=mock(RoomUnitRepository.class);
 private final RoomStatusHistoryRepository history=mock(RoomStatusHistoryRepository.class);
 private final OfferRepository offers=mock(OfferRepository.class);
 private final LoyaltyTransactionRepository loyalty=mock(LoyaltyTransactionRepository.class);
 private final PaymentRepository payments=mock(PaymentRepository.class);
 private final BookingService service=new BookingService(bookings,rooms,units,history,offers,loyalty,payments);

 @Test void rejectsCheckoutBeforeCheckin(){
  assertThrows(IllegalArgumentException.class,()->service.create(new Account(),1L,LocalDate.now().plusDays(5),LocalDate.now().plusDays(4),1,"Guest","guest@example.com"));
  verifyNoInteractions(rooms,bookings);
 }
 @Test void calculatesTotalFromServerPriceAndNights(){
  Room room=new Room();room.setPricePerNight(125.50);room.setCapacity(2);room.setQuantity(3);
  RoomUnit unit=new RoomUnit();unit.setRoom(room);unit.setRoomNumber("101");
  when(rooms.findByIdForUpdate(9L)).thenReturn(Optional.of(room));when(bookings.countOverlapping(eq(9L),any(),any())).thenReturn(1L);when(units.findByRoomId(9L)).thenReturn(java.util.List.of(unit));when(bookings.countOverlappingUnit(any(),any(),any())).thenReturn(0L);
  when(bookings.save(any(Booking.class))).thenAnswer(i->i.getArgument(0));
  Booking result=service.create(new Account(),9L,LocalDate.now().plusDays(4),LocalDate.now().plusDays(7),2,"Guest","guest@example.com");
  assertEquals(376.50,result.getTotalPrice(),0.001);assertEquals(3,result.getNights());assertTrue(result.getReference().startsWith("SH"));assertEquals("CONFIRMED",result.getStatus());
 }
 @Test void rejectsWhenRoomCapacityIsExceeded(){
  Room room=new Room();room.setCapacity(1);when(rooms.findByIdForUpdate(9L)).thenReturn(Optional.of(room));
  assertThrows(IllegalArgumentException.class,()->service.create(new Account(),9L,LocalDate.now().plusDays(4),LocalDate.now().plusDays(5),2,"Guest","guest@example.com"));
  verify(bookings,never()).save(any());
 }
 @Test void adminCannotReconfirmPastInventoryWhenRoomTypeIsFull(){
  Room room=new Room();room.setId(9L);room.setQuantity(1);
  Booking booking=new Booking();booking.setRoom(room);booking.setCheckIn(LocalDate.now().plusDays(4));booking.setCheckOut(LocalDate.now().plusDays(5));booking.setStatus("CANCELLED");
  when(bookings.findById(30L)).thenReturn(Optional.of(booking));when(rooms.findByIdForUpdate(9L)).thenReturn(Optional.of(room));when(bookings.countOverlapping(9L,booking.getCheckIn(),booking.getCheckOut())).thenReturn(1L);
  assertThrows(IllegalArgumentException.class,()->service.updateStatus(30L,"CONFIRMED"));verify(bookings,never()).save(any());
 }
}
