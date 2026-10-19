package com.stayhaven.repo;
import com.stayhaven.domain.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
public interface HotelRepository extends JpaRepository<Hotel,Long>{ List<Hotel> findByLocationContainingIgnoreCase(String location); @EntityGraph(attributePaths="rooms") @Query("select h from Hotel h") List<Hotel> findAllWithRooms(); }
