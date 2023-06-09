package io.wisoft.capstonedesign.domain.payment.web.dto;

import io.wisoft.capstonedesign.domain.payment.persistence.OrderPayment;
import io.wisoft.capstonedesign.global.enumerated.Status;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GetPaymentDto {

    private Long id;

    private String name;

    private int amount;

    private String buyerEmail;

    private String buyerName;

    private String buyerAddress;

    private String postcode;

    private LocalDateTime createdDate;

    private String status;

    public GetPaymentDto(OrderPayment payment) {
        this.id = payment.getId();
        this.name = payment.getName();
        this.amount = payment.getAmount();
        this.buyerEmail = payment.getBuyerEmail();
        this.buyerName = payment.getBuyerName();
        this.buyerAddress = payment.getBuyerAddress();
        this.postcode = payment.getPostcode();
        this.createdDate = payment.getCreatedDate();
        this.status = String.valueOf(payment.getStatus());
    }
}
