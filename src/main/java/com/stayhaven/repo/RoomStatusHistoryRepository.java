package com.stayhaven.repo;
import com.stayhaven.domain.RoomStatusHistory; import org.springframework.data.jpa.repository.*; import java.util.*;
public interface RoomStatusHistoryRepository extends JpaRepository<RoomStatusHistory,Long>{
 @EntityGraph(attributePaths={"changedBy"}) List<RoomStatusHistory> findByRoomUnitIdOrderByChangedAtDesc(Long roomUnitId);
 @org.springframework.transaction.annotation.Transactional void deleteByRoomUnitId(Long roomUnitId);
}
