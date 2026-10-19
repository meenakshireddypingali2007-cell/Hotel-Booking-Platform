package com.stayhaven.service;
import com.stayhaven.domain.Booking;
public interface PaymentProcessor { PaymentAttempt process(Booking booking); record PaymentAttempt(String provider,String status,String reference,double amount){} }
