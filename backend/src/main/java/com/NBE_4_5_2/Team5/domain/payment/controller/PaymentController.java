package com.NBE_4_5_2.Team5.domain.payment.controller;

import com.NBE_4_5_2.Team5.domain.payment.dto.PaymentMetaData;
import com.NBE_4_5_2.Team5.domain.payment.service.PaymentService;
import com.NBE_4_5_2.Team5.global.response.RsData;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping("/request")
    public RsData<Void> requestPayment(@RequestParam(name = "orderId") @NotNull String id,
                               @RequestParam(name="paymentKey") @NotNull String paymentKey,
                               @RequestParam(name="amount") @NotNull Integer amount) {
        paymentService.requestCharge(id, paymentKey, amount);

        return new RsData<>("페이 충전 결제 요청 성공. ", HttpStatus.OK.toString(), null);
    }

    @GetMapping("/metadata")
    public RsData<PaymentMetaData> saveMetaData(@NotNull @RequestParam String id, @NotNull @RequestParam Integer amount){
        PaymentMetaData metadata= paymentService.saveMetaData(id, amount);
        return new RsData<>("결제 메타데이터 저장 성공.", HttpStatus.OK.toString(), metadata);
    }
}
