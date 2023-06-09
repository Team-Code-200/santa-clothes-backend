package io.wisoft.capstonedesign.domain.payment.persistence;

import io.wisoft.capstonedesign.domain.usershop.persistence.UserShop;
import io.wisoft.capstonedesign.global.enumerated.Status;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.*;
import static lombok.AccessLevel.*;

@Entity
@Table(name = "PAYMENT")
@NoArgsConstructor(access = PROTECTED)
@Getter
public class OrderPayment {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "amount", nullable = false)
    private int amount;

    @Column(name = "buyer_email", nullable = false)
    private String buyerEmail;

    @Column(name = "buyer_name", nullable = false)
    private String buyerName;

    @Column(name = "buyer_addr", nullable = false)
    private String buyerAddress;

    @Column(name = "buyer_postcode", nullable = false)
    private String postcode;

    @Column(name = "created_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdDate;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "userShop_id", nullable = false)
    private UserShop userShop;



    /**
     * 정적 생성자 메소드
     */
    @Builder
    public static OrderPayment createPayment(
            final int amount,
            final String buyerEmail,
            final String buyerName,
            final String buyerAddress,
            final String postcode,
            final UserShop userShop
    ) {
        OrderPayment orderPayment = new OrderPayment();
        orderPayment.name = userShop.getShop().getTitle();
        orderPayment.amount = amount;
        orderPayment.buyerEmail = buyerEmail;
        orderPayment.buyerName = buyerName;
        orderPayment.buyerAddress = buyerAddress;
        orderPayment.postcode = postcode;
        orderPayment.createdDate = LocalDateTime.now();
        orderPayment.status = Status.SUCCESS;
        orderPayment.setUserShop(userShop);
        return orderPayment;
    }

    /**
     * 연관관계 편의 메소드
     */
    public void setUserShop(final UserShop userShop) {

        if (this.userShop != null) this.userShop.setOrderPayment(null);
        this.userShop = userShop;
        userShop.setOrderPayment(this);
    }

    /**
     * 결제 정보 취소
     */
    public void cancel() {
        this.status = Status.CANCEL;
    }
}
