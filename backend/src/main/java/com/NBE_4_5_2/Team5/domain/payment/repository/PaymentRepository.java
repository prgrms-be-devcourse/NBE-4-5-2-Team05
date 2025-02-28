package com.NBE_4_5_2.Team5.domain.payment.repository;

import com.NBE_4_5_2.Team5.domain.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, String> {
}
