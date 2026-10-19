package com.stayhaven.repo;
import com.stayhaven.domain.RoomUnit; import org.springframework.data.jpa.repository.*; import org.springframework.data.repository.query.Param; import java.util.*;
public interface RoomUnitRepository extends JpaRepository<RoomUnit,Long>{
 @EntityGraph(attributePaths={"room","room.hotel"}) List<RoomUnit> findAllByOrderByRoomNumberAsc();
 @EntityGraph(attributePaths={"room","room.hotel"}) Optional<RoomUnit> findWithRoomById(Long id);
 long countByStatus(String status);
 long countByRoomId(Long roomId);
 @Query("select u from RoomUnit u where u.room.id=:roomId order by u.roomNumber") List<RoomUnit> findByRoomId(@Param("roomId") Long roomId);
}
