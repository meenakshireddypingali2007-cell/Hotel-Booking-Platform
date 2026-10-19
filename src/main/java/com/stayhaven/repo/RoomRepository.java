package com.stayhaven.repo;
import com.stayhaven.domain.Room;
import org.springframework.data.jpa.repository.*;
import jakarta.persistence.LockModeType;
import java.util.*;
public interface RoomRepository extends JpaRepository<Room,Long>{ List<Room> findByHotelId(Long hotelId); @EntityGraph(attributePaths={"hotel"}) Optional<Room> findWithHotelById(Long id); @Lock(LockModeType.PESSIMISTIC_WRITE) @Query("select r from Room r where r.id=:id") Optional<Room> findByIdForUpdate(Long id); }
