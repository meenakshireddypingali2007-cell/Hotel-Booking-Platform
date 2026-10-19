package com.stayhaven.repo;
import com.stayhaven.domain.Booking;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.*;
public interface BookingRepository extends JpaRepository<Booking,Long>{
 @Override @EntityGraph(attributePaths={"room","room.hotel","account","roomUnit","offer"}) List<Booking> findAll();
 @Override @EntityGraph(attributePaths={"room","room.hotel","account","roomUnit","offer"}) List<Booking> findAll(Sort sort);
 @Override @EntityGraph(attributePaths={"room","room.hotel","account","roomUnit","offer"}) Optional<Booking> findById(Long id);
 @EntityGraph(attributePaths={"room","room.hotel","roomUnit"})
 List<Booking> findByAccountIdOrderByCheckInDesc(Long accountId);
 @Query("select count(b) from Booking b where b.room.id=:room and b.status in ('PENDING','CONFIRMED','CHECKED IN') and b.checkIn < :out and b.checkOut > :in") long countOverlapping(@Param("room") Long room,@Param("in") LocalDate in,@Param("out") LocalDate out);
 @Query("select count(b) from Booking b where b.roomUnit.id=:unit and b.status in ('PENDING','CONFIRMED','CHECKED IN') and b.checkIn < :out and b.checkOut > :in") long countOverlappingUnit(@Param("unit") Long unit,@Param("in") LocalDate in,@Param("out") LocalDate out);
 long countByStatus(String status);
 @EntityGraph(attributePaths={"room","room.hotel","account","roomUnit"})
 @Query("select b from Booking b where b.checkIn=:date") List<Booking> findArrivalsOn(@Param("date") LocalDate date);
 @EntityGraph(attributePaths={"room","room.hotel","account","roomUnit"})
 List<Booking> findByCheckOut(LocalDate date);
 @EntityGraph(attributePaths={"room","room.hotel","account","roomUnit"})
 List<Booking> findByStatus(String status);
 @EntityGraph(attributePaths={"room","room.hotel","account","roomUnit"})
 List<Booking> findByRoomUnitIsNullAndStatusIn(Collection<String> statuses);
 @Query("select count(b) from Booking b where b.status=:status and b.checkIn=:date") long countStatusArrivals(@Param("status") String status,@Param("date") LocalDate date);
 @Query("select count(b) from Booking b where b.status=:status and b.checkOut=:date") long countStatusDepartures(@Param("status") String status,@Param("date") LocalDate date);
 long countByStatusInAndCheckInLessThanEqualAndCheckOutGreaterThan(java.util.Collection<String> statuses,LocalDate date,LocalDate date2);
}
