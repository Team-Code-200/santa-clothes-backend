package io.wisoft.capstonedesign.domain;

import jakarta.persistence.*;
import lombok.Getter;

import static jakarta.persistence.FetchType.*;

@Entity
@Table(name = "ORDERS")
@Getter
public class Order {

    @Id @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "address", nullable = false, columnDefinition = "TEXT")
    private String address;

    @Column(name = "body", columnDefinition = "TEXT")
    private String body;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * 연관관계 편의 메소드
     */
    public void setUser(User user) {

        if (this.user != null) this.user.getOrders().remove(this);
        this.user = user;
        user.getOrders().add(this);
    }
}
