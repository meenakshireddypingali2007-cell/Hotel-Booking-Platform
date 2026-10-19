package com.stayhaven.repo; import com.stayhaven.domain.Offer; import org.springframework.data.jpa.repository.JpaRepository; import java.util.*;
public interface OfferRepository extends JpaRepository<Offer,Long>{List<Offer> findAllByOrderByStartsOnDesc();Optional<Offer> findByCodeIgnoreCase(String code);}
