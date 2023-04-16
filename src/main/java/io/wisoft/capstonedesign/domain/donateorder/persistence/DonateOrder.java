package io.wisoft.capstonedesign.domain.donateorder.persistence;

import io.wisoft.capstonedesign.domain.information.persistence.Information;
import io.wisoft.capstonedesign.domain.user.persistence.User;
import io.wisoft.capstonedesign.domain.donate.persistence.Donate;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.*;
import static lombok.AccessLevel.*;

@Entity
@Table(name = "DOANTE_ORDER")
@NoArgsConstructor(access = PROTECTED)
@Getter
public class DonateOrder {

    @Id @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "send_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime sendDate;

    @Column(name = "body", nullable = false, columnDefinition = "TEXT")
    private String text;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "info_id", nullable = false)
    private Information information;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "donate_id", nullable = false)
    private Donate donate;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * 정적 생성자 메소드
     */
    @Builder
    public static DonateOrder createDonateOrder(
            final String text,
            final Information information,
            final Donate donate,
            final User user
    ) {
        DonateOrder donateOrder = new DonateOrder();
        donateOrder.sendDate = LocalDateTime.now();
        donateOrder.text = text;
        donateOrder.setInformation(information);
        donateOrder.setDonate(donate);
        donateOrder.setUser(user);
        return donateOrder;
    }

    /**
     * 연관관계 편의 메소드
     */
    public void setInformation(final Information information) {

        if (this.information != null) this.information.getDonateOrders().remove(this);
        this.information = information;
        information.getDonateOrders().add(this);
    }

    public void setDonate(final Donate donate) {

        if (this.donate != null) this.donate.setDonateOrder(null);
        this.donate = donate;
        donate.setDonateOrder(this);
    }

    public void setUser(final User user) {

        if (this.user != null) this.user.getDonateOrders().remove(this);
        this.user = user;
        user.getDonateOrders().add(this);
    }

    /**
     * 주문 내역 기타 사항 수정
     */
    public void update(final String text) {
        this.text = text;
    }
}
