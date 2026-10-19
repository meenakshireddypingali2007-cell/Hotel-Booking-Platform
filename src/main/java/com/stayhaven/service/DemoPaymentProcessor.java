package com.stayhaven.service;
import com.stayhaven.domain.Booking; import org.springframework.stereotype.Component; import java.util.UUID;
/** Local test adapter only. Replace with a gateway-backed implementation for real settlement. */
@Component public class DemoPaymentProcessor implements PaymentProcessor {
 public PaymentAttempt process(Booking booking){return new PaymentAttempt("DEMO_TEST","TEST_RECORDED","TEST-"+UUID.randomUUID().toString().replace("-","").substring(0,12).toUpperCase(),booking.getTotalPrice());}
}
