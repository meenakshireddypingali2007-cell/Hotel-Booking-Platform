package com.stayhaven.repo; import com.stayhaven.domain.RoomServiceOrder; import org.springframework.data.jpa.repository.*; import java.util.*;
public interface RoomServiceOrderRepository extends JpaRepository<RoomServiceOrder,Long>{@EntityGraph(attributePaths={"booking","roomUnit","orderedBy"}) List<RoomServiceOrder> findAllByOrderByCreatedAtDesc();}
