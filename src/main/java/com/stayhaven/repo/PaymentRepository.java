package com.stayhaven.repo; import com.stayhaven.domain.Payment; import org.springframework.data.jpa.repository.*; import java.util.*;
public interface PaymentRepository extends JpaRepository<Payment,Long>{
 @EntityGraph(attributePaths={"booking","booking.account","booking.room","booking.room.hotel"}) List<Payment> findAllByOrderByCreatedAtDesc();
 Optional<Payment> findByBookingId(Long bookingId);
 boolean existsByBookingId(Long id);
}
