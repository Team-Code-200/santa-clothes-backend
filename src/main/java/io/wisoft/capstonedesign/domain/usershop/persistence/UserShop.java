package io.wisoft.capstonedesign.domain.usershop.persistence;

import io.wisoft.capstonedesign.domain.information.persistence.Information;
import io.wisoft.capstonedesign.domain.payment.persistence.OrderPayment;
import io.wisoft.capstonedesign.domain.shop.persistence.Shop;
import io.wisoft.capstonedesign.domain.user.persistence.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.FetchType.*;
import static lombok.AccessLevel.*;

@Entity
@Table(name = "USER_SHOP")
@NoArgsConstructor(access = PROTECTED)
@Getter @Setter
public class UserShop {

    @Id @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "created_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdDate;

    @Column(name = "body", nullable = false, columnDefinition = "TEXT")
    private String text;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "shop_id", nullable = false)
    private Shop shop;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "info_id", nullable = false)
    private Information information;

    @OneToOne(mappedBy = "userShop", fetch = LAZY, cascade = REMOVE)
    private OrderPayment orderPayment;

    /**
     * 정적 생성자 메소드
     */
    @Builder
    public static UserShop createUserShop(
            final String text,
            final User user,
            final Shop shop,
            final Information information
    ) {
        UserShop userShop = new UserShop();
        userShop.createdDate = LocalDateTime.now();
        userShop.text = text;
        userShop.setUser(user);
        userShop.setShop(shop);
        userShop.setInformation(information);
        return userShop;
    }

    /**
     * 연관관계 편의 메소드
     */
    public void setUser(final User user) {

        if (this.user != null) this.user.getUserShops().remove(this);
        this.user = user;
        user.getUserShops().add(this);
    }

    public void setShop(final Shop shop) {

        if (this.shop != null) this.shop.getUserShops().remove(this);
        this.shop = shop;
        shop.getUserShops().add(this);
    }

    public void setInformation(final Information information) {

        if (this.information != null) this.information.getUserShops().remove(this);
        this.information = information;
        information.getUserShops().add(this);
    }

    /**
     * 산타샵 주문 내역 기타 사항 수정
     */
    public void update(final String text) {
        this.text = text;
    }
}
