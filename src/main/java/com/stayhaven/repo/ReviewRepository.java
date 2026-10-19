package com.stayhaven.repo; import com.stayhaven.domain.Review; import org.springframework.data.jpa.repository.*; import java.util.*;
public interface ReviewRepository extends JpaRepository<Review,Long>{
 @EntityGraph(attributePaths={"booking","booking.room","booking.room.hotel","guest"}) List<Review> findAllByOrderByCreatedAtDesc();
 @EntityGraph(attributePaths={"booking","booking.room","booking.room.hotel","guest"}) List<Review> findByBookingRoomHotelIdOrderByCreatedAtDesc(Long hotelId);
 @EntityGraph(attributePaths={"booking","booking.room","booking.room.hotel","guest"}) List<Review> findByGuestIdOrderByCreatedAtDesc(Long guestId);
 boolean existsByBookingId(Long bookingId);
 @Query("select coalesce(avg(r.rating),0) from Review r") double averageRating();
}
