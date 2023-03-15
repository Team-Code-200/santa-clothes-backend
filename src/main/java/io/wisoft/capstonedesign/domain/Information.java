package io.wisoft.capstonedesign.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "information")
    private List<FindOrder> findOrders = new ArrayList<>();

    @OneToMany(mappedBy = "information")
    private List<DonateOrder> donateOrders = new ArrayList<>();

    @OneToMany(mappedBy = "information")
    private List<UserShop> userShops = new ArrayList<>();
}
