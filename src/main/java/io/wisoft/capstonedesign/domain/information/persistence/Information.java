package io.wisoft.capstonedesign.domain.information.persistence;

import io.wisoft.capstonedesign.domain.usershop.persistence.UserShop;
import io.wisoft.capstonedesign.domain.donateorder.persistence.DonateOrder;
import io.wisoft.capstonedesign.domain.findorder.persistence.FindOrder;
import io.wisoft.capstonedesign.domain.user.persistence.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.FetchType.*;
import static lombok.AccessLevel.*;

@Entity
@Table(name = "INFORMATION")
@NoArgsConstructor(access = PROTECTED)
@Getter
public class Information {

    @Id @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "address", nullable = false, columnDefinition = "TEXT")
    private String address;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "information", cascade = REMOVE)
    private List<FindOrder> findOrders = new ArrayList<>();

    @OneToMany(mappedBy = "information", cascade = REMOVE)
    private List<DonateOrder> donateOrders = new ArrayList<>();

    @OneToMany(mappedBy = "information", cascade = REMOVE)
    private List<UserShop> userShops = new ArrayList<>();

    /**
     * 정적 생성자 메소드
     */
    public static Information createInformation(
            String username,
            String address,
            String phoneNumber,
            User user
    ) {
        Information information = new Information();
        information.username = username;
        information.address = address;
        information.phoneNumber = phoneNumber;
        information.user = user;
        return information;
    }

    /**
     * 연관관계 편의 메소드
     */
    public void setUser(User user) {

        if (this.user != null) this.user.getInformation().remove(this);
        this.user = user;
        user.getInformation().add(this);
    }

    /**
     * 배송 정보 전체 수정
     */
    public void update(String username, String address, String phoneNumber) {
        this.username = username;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }
}
